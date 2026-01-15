# sqlrest-ui

## 1. Introduction

SQLREST management web interface built with Vue.js 2.0.

## 2. Environment

 **node** : >= v14.15.4
 
### 1. Install Node.js on CentOS
 
```
# Download nodejs
wget https://nodejs.org/dist/v14.15.4/node-v14.15.4-linux-x64.tar.xz
# Extract
tar -xvf node-v14.15.4-linux-x64.tar.xz && mkdir -p /usr/local/nodejs && mv node-v14.15.4-linux-x64/* /usr/local/nodejs/
# Create node soft link
ln -s /usr/local/nodejs/bin/node /usr/local/bin
# Create npm soft link
ln -s /usr/local/nodejs/bin/npm /usr/local/bin
# Set Taobao mirror source (for China)
npm config set registry https://registry.npm.taobao.org
# Disable SSL verification
npm config set strict-ssl false
# View configuration
npm config list
# Verify installation
node -v
npm -v
```

### 2. Install Node.js on Windows

You can refer to [Blog Tutorial](https://blog.csdn.net/inrgihc/article/details/138013024)

## 3. Build

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

## 4. Deployment

After executing the `npm run build` command, copy (or replace) all files generated in the `sqlrest-ui\dist` directory to the `sqlrest-manager\src\main\resources` directory. Then use Maven to package the entire SQLREST project.
