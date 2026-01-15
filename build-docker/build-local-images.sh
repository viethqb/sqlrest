#!/bin/sh

set -e

SQLREST_VERSION=1.7.0
BUILD_DOCKER_DIR="$( cd "$( dirname "$0"  )" && pwd  )"
PROJECT_ROOT_DIR=$( dirname "$BUILD_DOCKER_DIR")
DOCKER_SQLREST_DIR=$BUILD_DOCKER_DIR/sqlrest
FRONTEND_DIR=$PROJECT_ROOT_DIR/sqlrest-manager-ui
MANAGER_RESOURCES_DIR=$PROJECT_ROOT_DIR/sqlrest-manager/src/main/resources

echo "=========================================="
echo "Building SQLREST Docker Images (Local)"
echo "Including Frontend + Backend Build"
echo "=========================================="

# Step 1: Build Frontend
echo ""
echo "Step 1: Building Frontend UI..."
if [ ! -d "$FRONTEND_DIR" ]; then
    echo "Warning: Frontend directory not found: $FRONTEND_DIR"
    echo "Skipping frontend build..."
else
    cd $FRONTEND_DIR
    
    # Check if node_modules exists, if not install dependencies
    if [ ! -d "node_modules" ]; then
        echo "   Installing npm dependencies..."
        if ! command -v npm &> /dev/null; then
            echo "Error: npm is not installed. Please install Node.js first."
            exit 1
        fi
        
        # Check Node.js version
        NODE_VERSION=$(node -v | cut -d'v' -f2 | cut -d'.' -f1)
        if [ "$NODE_VERSION" -gt 16 ]; then
            echo "   Warning: Node.js version is newer than recommended (v14.15.4)"
            echo "   Using --legacy-peer-deps to resolve dependency conflicts..."
            npm install --legacy-peer-deps
        else
            npm install
        fi
    fi
    
    # Build frontend
    echo "   Running npm run build..."
    npm run build
    
    if [ ! -d "dist" ]; then
        echo "Error: Frontend build failed - dist directory not found"
        exit 1
    fi
    
    echo "   ✓ Frontend build completed!"
    
    # Copy frontend build to manager resources
    echo "   Copying frontend build to manager resources..."
    cd $PROJECT_ROOT_DIR
    
    # Backup existing resources if any
    if [ -d "$MANAGER_RESOURCES_DIR/static" ] && [ "$(ls -A $MANAGER_RESOURCES_DIR/static 2>/dev/null)" ]; then
        echo "   Backing up existing static files..."
        BACKUP_DIR=$MANAGER_RESOURCES_DIR/static.backup.$(date +%Y%m%d_%H%M%S)
        mv $MANAGER_RESOURCES_DIR/static $BACKUP_DIR 2>/dev/null || true
    fi
    
    # Copy dist contents to resources
    echo "   Copying dist/* to $MANAGER_RESOURCES_DIR/..."
    cp -r $FRONTEND_DIR/dist/* $MANAGER_RESOURCES_DIR/
    
    # Copy index.html if exists separately
    if [ -f "$FRONTEND_DIR/dist/index.html" ]; then
        cp $FRONTEND_DIR/dist/index.html $MANAGER_RESOURCES_DIR/index.html
    fi
    
    echo "   ✓ Frontend files copied to manager resources!"
fi

# Step 2: Build Java project
echo ""
echo "Step 2: Building Java project..."
cd $PROJECT_ROOT_DIR

# Check if Maven is available
if ! command -v mvn &> /dev/null; then
    echo "Error: Maven is not installed. Please install Maven first."
    exit 1
fi

echo "   Running mvn clean package..."
mvn clean package -Dmaven.test.skip=true

if [ ! -f "$PROJECT_ROOT_DIR/target/sqlrest-release-${SQLREST_VERSION}.tar.gz" ]; then
    echo "Error: Package not found after build: $PROJECT_ROOT_DIR/target/sqlrest-release-${SQLREST_VERSION}.tar.gz"
    exit 1
fi

echo "   ✓ Java project built successfully!"

# Step 3: Prepare Docker build context
echo ""
echo "Step 3: Preparing Docker build context..."
cd $BUILD_DOCKER_DIR
rm -rf /tmp/sqlrest-release-* 2>/dev/null || true
tar zxvf $PROJECT_ROOT_DIR/target/sqlrest-release-${SQLREST_VERSION}.tar.gz -C /tmp

echo "   Copying lib and drivers to sqlrest-release..."
mkdir -p ${DOCKER_SQLREST_DIR}/sqlrest-release/lib
mkdir -p ${DOCKER_SQLREST_DIR}/sqlrest-release/drivers
cp -r /tmp/sqlrest-release-${SQLREST_VERSION}/lib/* ${DOCKER_SQLREST_DIR}/sqlrest-release/lib/ 2>/dev/null || true
cp -r /tmp/sqlrest-release-${SQLREST_VERSION}/drivers/* ${DOCKER_SQLREST_DIR}/sqlrest-release/drivers/ 2>/dev/null || true
rm -rf /tmp/sqlrest-release-*

echo "   Creating sqlrest-release.tar.gz for Docker..."
cd ${DOCKER_SQLREST_DIR}
if [ -f sqlrest-release.tar.gz ]; then
    rm -f sqlrest-release.tar.gz
fi
tar zcvf sqlrest-release.tar.gz sqlrest-release/

# Step 4: Build Docker images
echo ""
echo "Step 4: Building Docker images..."
echo "   - Building manager image..."
docker build -f Dockerfile-manager -t sqlrest-manager:${SQLREST_VERSION} -t sqlrest-manager:latest .

echo "   - Building executor image..."
docker build -f Dockerfile-executor -t sqlrest-executor:${SQLREST_VERSION} -t sqlrest-executor:latest .

echo "   - Building gateway image..."
docker build -f Dockerfile-gateway -t sqlrest-gateway:${SQLREST_VERSION} -t sqlrest-gateway:latest .

# Step 5: Cleanup
echo ""
echo "Step 5: Cleaning up..."
rm -f sqlrest-release.tar.gz
rm -rf sqlrest-release/lib/* 2>/dev/null || true
rm -rf sqlrest-release/drivers/* 2>/dev/null || true

echo ""
echo "=========================================="
echo "Build completed successfully!"
echo "=========================================="
echo "Images created:"
echo "  - sqlrest-manager:${SQLREST_VERSION}"
echo "  - sqlrest-executor:${SQLREST_VERSION}"
echo "  - sqlrest-gateway:${SQLREST_VERSION}"
echo ""
if [ -d "$FRONTEND_DIR" ]; then
    echo "Frontend has been built and included in the images."
    echo ""
fi
echo "You can now start services with:"
echo "  cd build-docker && docker-compose -f docker-compose-local.yml up -d"
