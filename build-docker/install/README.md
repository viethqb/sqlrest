# SQLREST One-Click Deployment Script Based on docker-compose

**Requirements**: Prepare a Linux operating system that can install Docker

## 1. Install Docker and docker-compose

```
[root@localhost install]# ls -l
total 12
-rw-r--r-- 1 root root 625 Feb 11 22:24 docker-compose.yml
-rw-r--r-- 1 root root 787 Feb 11 20:54 docker_install.sh
-rw-r--r-- 1 root root 378 Feb 11 22:38 README.md

[root@localhost install]# sh docker_install.sh
```

## 2. One-Click Deployment SQLREST

```
[root@localhost install]# ls -l
total 12
-rw-r--r-- 1 root root 625 Feb 11 22:24 docker-compose.yml
-rw-r--r-- 1 root root 787 Feb 11 20:54 docker_install.sh
-rw-r--r-- 1 root root 378 Feb 11 22:38 README.md

[root@localhost install]# docker-compose up -d
```

## 3. Common Operation Commands

- 1. Create and start container services:
```
docker-compose up -d
```

- 2. Destroy container services: 
```
docker-compose down
```

- 3. Stop containers:
```
docker-compose stop
```

- 4. Restart containers:
```
docker-compose restart
```

- 5. Start containers:
```
docker-compose start
```

