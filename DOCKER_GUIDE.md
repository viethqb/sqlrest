# SQLREST Docker Build and Deployment Guide

This guide explains how to build Docker images and deploy SQLREST using Docker Compose.

## Prerequisites

Before building, ensure you have the following installed:

- **Docker**: Version 20.10 or later
- **Docker Compose**: Version 1.29 or later (or Docker Compose V2)
- **Node.js**: Version 14.15.4 or later (for frontend build)
- **npm**: Comes with Node.js
- **Maven**: Version 3.6 or later (for backend build)
- **Java JDK**: Version 1.8 or later

### Verify Prerequisites

```bash
# Check Docker
docker --version
docker-compose --version

# Check Node.js and npm
node -v
npm -v

# Check Maven
mvn -version

# Check Java
java -version
```

## Quick Start

### 1. Build Everything (Frontend + Backend + Docker Images)

The `build-local-images.sh` script automates the entire build process:

```bash
cd build-docker
bash build-local-images.sh
```

This script will:
1. ✅ Build the frontend UI (`npm run build`)
2. ✅ Copy frontend build to manager resources
3. ✅ Build the Java backend (`mvn clean package`)
4. ✅ Create Docker images for manager, executor, and gateway

### 2. Start Services

```bash
cd build-docker
docker-compose -f docker-compose-local.yml up -d
```

### 3. Access the Application

- **Web UI**: http://localhost:8090
- **Username**: `admin`
- **Password**: `123456`

---

## Detailed Build Process

### Build Script Overview

The `build-local-images.sh` script performs the following steps:

#### Step 1: Build Frontend UI

```bash
cd sqlrest-manager-ui
npm install          # If node_modules doesn't exist
npm run build        # Build production bundle
```

The frontend build is then copied to `sqlrest-manager/src/main/resources/` so it gets packaged into the JAR file.

#### Step 2: Build Java Backend

```bash
cd project-root
mvn clean package -Dmaven.test.skip=true
```

This creates `target/sqlrest-release-1.7.0.tar.gz` containing all compiled JARs and dependencies.

#### Step 3: Prepare Docker Build Context

- Extracts the release package
- Copies libraries and database drivers to Docker build context
- Creates `sqlrest-release.tar.gz` for Docker images

#### Step 4: Build Docker Images

Builds three Docker images:
- `sqlrest-manager:latest` (port 8090)
- `sqlrest-executor:latest` (port 8092)
- `sqlrest-gateway:latest` (port 8091)

#### Step 5: Cleanup

Removes temporary files and build artifacts.

---

## Docker Compose Configuration

### Services Overview

The `docker-compose-local.yml` defines four services:

1. **mysqldb**: MySQL 5.7 database container
   - Port: 3306
   - Database: `sqlrest`
   - Root password: `123456`
   - User: `sqlrest` / Password: `123456`

2. **manager**: SQLREST Manager service
   - Port: 8090
   - Depends on: mysqldb (waits for healthy status)

3. **executor**: SQLREST Executor service
   - Port: 8092
   - Depends on: mysqldb and manager

4. **gateway**: SQLREST Gateway service
   - Port: 8091
   - Depends on: mysqldb and manager

### Network

All services run on a custom bridge network `sqlrest-network` for internal communication.

### Volumes

- `sqlrest_mysql_data`: Persistent storage for MySQL data

---

## Common Operations

### Start Services

```bash
# Start all services in detached mode
docker-compose -f docker-compose-local.yml up -d

# Start specific service
docker-compose -f docker-compose-local.yml up -d manager
```

### Stop Services

```bash
# Stop all services (containers remain)
docker-compose -f docker-compose-local.yml stop

# Stop specific service
docker-compose -f docker-compose-local.yml stop manager
```

### View Service Status

```bash
# List all containers and their status
docker-compose -f docker-compose-local.yml ps

# View detailed status
docker ps | grep sqlrest
```

### View Logs

```bash
# View logs for all services
docker-compose -f docker-compose-local.yml logs

# View logs for specific service
docker-compose -f docker-compose-local.yml logs manager
docker-compose -f docker-compose-local.yml logs executor
docker-compose -f docker-compose-local.yml logs gateway
docker-compose -f docker-compose-local.yml logs mysqldb

# Follow logs in real-time
docker-compose -f docker-compose-local.yml logs -f manager

# View last 100 lines
docker-compose -f docker-compose-local.yml logs --tail=100 manager
```

### Restart Services

```bash
# Restart all services
docker-compose -f docker-compose-local.yml restart

# Restart specific service
docker-compose -f docker-compose-local.yml restart manager
```

### Stop and Remove Containers

```bash
# Stop and remove containers (keeps volumes)
docker-compose -f docker-compose-local.yml down

# Stop and remove containers + volumes (deletes database data)
docker-compose -f docker-compose-local.yml down -v
```

### Rebuild and Restart

```bash
# Rebuild images and recreate containers
cd build-docker
bash build-local-images.sh
docker-compose -f docker-compose-local.yml up -d --force-recreate
```

---

## Configuration

### Database Configuration

Edit `docker-compose-local.yml` to change database settings:

```yaml
mysqldb:
  environment:
    MYSQL_DATABASE: sqlrest
    MYSQL_USER: sqlrest
    MYSQL_PASSWORD: your_password
    MYSQL_ROOT_PASSWORD: your_root_password

manager:
  environment:
    MYSQLDB_HOST: mysqldb
    MYSQLDB_PORT: 3306
    MYSQLDB_USERNAME: root
    MYSQLDB_PASSWORD: your_root_password
    MYSQLDB_NAME: sqlrest
```

After changing configuration, restart services:

```bash
docker-compose -f docker-compose-local.yml restart
```

### Port Configuration

To change exposed ports, edit `docker-compose-local.yml`:

```yaml
manager:
  ports:
    - "8090:8090"  # Change left side to map to different host port
    # Example: "9080:8090" maps container port 8090 to host port 9080
```

### Environment Variables

Key environment variables for SQLREST services:

- `DB_TYPE`: Database type (`mysql` or `postgres`)
- `MYSQLDB_HOST`: MySQL host (use service name `mysqldb` in Docker)
- `MYSQLDB_PORT`: MySQL port (default: 3306)
- `MYSQLDB_NAME`: Database name (default: `sqlrest`)
- `MYSQLDB_USERNAME`: Database username
- `MYSQLDB_PASSWORD`: Database password
- `MANAGER_HOST`: Manager service hostname
- `MANAGER_PORT`: Manager port (default: 8090)
- `EXECUTOR_PORT`: Executor port (default: 8092)
- `GATEWAY_PORT`: Gateway port (default: 8091)
- `SQLREST_DS_ENCRYPT`: Encrypt datasource passwords (default: `false`)

---

## Troubleshooting

### Container Won't Start

**Check logs:**
```bash
docker-compose -f docker-compose-local.yml logs manager
docker logs sqlrest_manager
```

**Check container status:**
```bash
docker ps -a | grep sqlrest
docker inspect sqlrest_manager
```

### Database Connection Errors

**Verify MySQL is healthy:**
```bash
docker-compose -f docker-compose-local.yml ps mysqldb
# Should show "healthy" status
```

**Test database connection:**
```bash
# Connect to MySQL from manager container
docker exec -it sqlrest_manager sh
# Inside container, test connection (if mysql client available)
```

**Check network connectivity:**
```bash
# Ping database from manager
docker exec sqlrest_manager ping mysqldb

# Check network
docker network inspect build-docker_sqlrest-network
```

### Port Already in Use

**Find process using port:**
```bash
# On Linux/Mac
lsof -i :8090
netstat -an | grep 8090

# On Windows
netstat -ano | findstr :8090
```

**Solutions:**
- Stop the conflicting service
- Change port mapping in `docker-compose-local.yml`
- Use different ports

### Frontend Not Updated

**Issue**: UI changes not reflected after rebuild

**Solution**:
1. Ensure frontend is rebuilt:
   ```bash
   cd sqlrest-manager-ui
   npm run build
   ```

2. Rebuild everything:
   ```bash
   cd build-docker
   bash build-local-images.sh
   ```

3. Force recreate containers:
   ```bash
   docker-compose -f docker-compose-local.yml up -d --force-recreate
   ```

4. Clear browser cache (Ctrl+Shift+R or Cmd+Shift+R)

### Build Failures

**Frontend build fails:**
```bash
# Check Node.js version
node -v  # Should be >= 14.15.4

# Clean and reinstall
cd sqlrest-manager-ui
rm -rf node_modules package-lock.json
npm install
npm run build
```

**Maven build fails:**
```bash
# Check Maven and Java
mvn -version
java -version

# Clean and rebuild
mvn clean
mvn package -Dmaven.test.skip=true
```

**Docker build fails:**
```bash
# Check Docker is running
docker ps

# Check disk space
df -h

# Clean Docker cache if needed
docker system prune -a
```

### Service Health Checks

**Check service health endpoints:**
```bash
# Manager health
curl http://localhost:8090/actuator/health

# Gateway health
curl http://localhost:8091/actuator/health

# Executor health
curl http://localhost:8092/actuator/health
```

---

## Advanced Operations

### Access Container Shell

```bash
# Access manager container
docker exec -it sqlrest_manager sh

# Access MySQL container
docker exec -it sqlrest_mysqldb bash

# Run commands in container
docker exec sqlrest_manager ls -la /sqlrest-release/conf/
```

### View Configuration Files

```bash
# View config.ini in container
docker exec sqlrest_manager cat /sqlrest-release/conf/config.ini

# View application.yaml
docker exec sqlrest_manager cat /sqlrest-release/conf/manager/application.yaml
```

### Database Operations

**Backup database:**
```bash
docker exec sqlrest_mysqldb mysqldump -u root -p123456 sqlrest > backup.sql
```

**Restore database:**
```bash
docker exec -i sqlrest_mysqldb mysql -u root -p123456 sqlrest < backup.sql
```

**Access MySQL directly:**
```bash
docker exec -it sqlrest_mysqldb mysql -u root -p123456
```

### Rebuild Specific Image

```bash
cd build-docker/sqlrest

# Rebuild only manager
docker build -f Dockerfile-manager -t sqlrest-manager:latest .

# Rebuild only executor
docker build -f Dockerfile-executor -t sqlrest-executor:latest .

# Rebuild only gateway
docker build -f Dockerfile-gateway -t sqlrest-gateway:latest .
```

### Clean Everything and Start Fresh

```bash
# Stop and remove all containers and volumes
docker-compose -f docker-compose-local.yml down -v

# Remove images
docker rmi sqlrest-manager:latest sqlrest-executor:latest sqlrest-gateway:latest

# Clean Maven build
cd ..
mvn clean

# Clean frontend build
cd sqlrest-manager-ui
rm -rf dist node_modules

# Rebuild everything
cd ../build-docker
bash build-local-images.sh

# Start services
docker-compose -f docker-compose-local.yml up -d
```

---

## Production Considerations

### Security

1. **Change default passwords** in `docker-compose-local.yml`
2. **Use secrets management** for sensitive data
3. **Enable SSL/TLS** for database connections
4. **Set `SQLREST_DS_ENCRYPT=true`** to encrypt datasource passwords
5. **Use environment files** instead of hardcoding values

### Performance

1. **Adjust JVM memory** in Dockerfile if needed:
   ```dockerfile
   JVMFLAGS="-server -Xms2048m -Xmx2048m ..."
   ```

2. **Configure database connection pool** in `application.yaml`

3. **Use external database** instead of containerized MySQL for production

### Monitoring

1. **Enable health checks** (already configured)
2. **Set up logging aggregation** (ELK, Loki, etc.)
3. **Monitor resource usage**:
   ```bash
   docker stats sqlrest_manager sqlrest_executor sqlrest_gateway
   ```

### Backup Strategy

1. **Database backups**: Regular mysqldump or use MySQL replication
2. **Configuration backups**: Version control for `docker-compose-local.yml`
3. **Volume backups**: Backup `sqlrest_mysql_data` volume

---

## Summary

### Complete Build and Deploy Workflow

```bash
# 1. Build everything (frontend + backend + Docker images)
cd build-docker
bash build-local-images.sh

# 2. Start services
docker-compose -f docker-compose-local.yml up -d

# 3. Check status
docker-compose -f docker-compose-local.yml ps

# 4. View logs
docker-compose -f docker-compose-local.yml logs -f

# 5. Access application
# Open http://localhost:8090
# Login: admin / 123456
```

### Quick Reference Commands

```bash
# Build
cd build-docker && bash build-local-images.sh

# Start
docker-compose -f docker-compose-local.yml up -d

# Stop
docker-compose -f docker-compose-local.yml stop

# Restart
docker-compose -f docker-compose-local.yml restart

# Logs
docker-compose -f docker-compose-local.yml logs -f manager

# Status
docker-compose -f docker-compose-local.yml ps

# Clean restart
docker-compose -f docker-compose-local.yml down -v
cd build-docker && bash build-local-images.sh
docker-compose -f docker-compose-local.yml up -d
```

---

## Additional Resources

- **Project README**: See `README.md` for general project information
- **Frontend README**: See `sqlrest-manager-ui/README.md` for frontend build details
- **Docker Installation**: See `build-docker/install/README.md` for Docker setup

For issues or questions, please refer to the project repository or create an issue.
