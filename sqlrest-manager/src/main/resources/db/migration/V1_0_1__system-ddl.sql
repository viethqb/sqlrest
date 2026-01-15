create table `SQLREST_SYSTEM_USER`  (
  `id`                  bigint(20)            not null auto_increment   comment 'Primary key id',
  `username`            varchar(255)          not null                  comment 'Login name',
  `password`            varchar(128)          not null                  comment 'Login password',
  `salt`                varchar(128)          not null                  comment 'Password salt',
  `real_name`           varchar(255)          not null default ''       comment 'Real name',
  `email`               varchar(255)          not null default ''       comment 'Email address',
  `address`             varchar(255)          not null default ''       comment 'Address',
  `locked`              tinyint(1)            not null default 0        comment 'Whether locked',
  `create_time`         timestamp             not null default current_timestamp comment 'Create time',
  `update_time`         timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
  primary key (`id`),
  unique key (`username`)
) engine=InnoDB character set = utf8 comment = 'System user table';

create table `SQLREST_DATASOURCE` (
  `id`                  bigint(20)   unsigned not null auto_increment            comment 'Primary key',
  `name`                varchar(200)          not null default ''                comment 'Connection name',
  `type`                varchar(200)          not null default ''                comment 'Database type',
  `version`             varchar(255)          not null default ''                comment 'Driver version',
  `driver`              varchar(200)          not null default ''                comment 'Driver class name',
  `url`                 longtext                                                 comment 'JDBC URL connection string',
  `username`            varchar(200)          not null default ''                comment 'Connection username',
  `password`            varchar(200)          not null default ''                comment 'Account password',
  `create_time`         timestamp             not null default current_timestamp comment 'Create time',
  `update_time`         timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
  primary key (`id`),
  unique key (`name`)
) engine=InnoDB default charset=utf8 comment='Database connection';

CREATE TABLE `SQLREST_API_GROUP`
(
    `id`             bigint(20)   unsigned auto_increment                     comment 'Primary key',
    `name`           varchar(255)          not null default ''                comment 'Group name',
    `create_time`    timestamp             not null default current_timestamp comment 'Create time',
    `update_time`    timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
    PRIMARY KEY (`id`) ,
    UNIQUE KEY `uk_name`(`name`)
) engine=InnoDB default charset=utf8 comment='API group table';

CREATE TABLE `SQLREST_API_MODULE`
(
    `id`             bigint(20)   unsigned auto_increment                     comment 'Primary key',
    `name`           varchar(255)          not null default ''                comment 'Module name',
    `create_time`    timestamp             not null default current_timestamp comment 'Create time',
    `update_time`    timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
    PRIMARY KEY (`id`) ,
    UNIQUE KEY `uk_name`(`name`)
) engine=InnoDB default charset=utf8 comment='API module table';

CREATE TABLE `SQLREST_API_ASSIGNMENT`
(
    `id`             bigint(20)   unsigned not null auto_increment            comment 'Primary key',
    `group_id`       bigint(20)   unsigned not null                           comment 'Group ID',
    `module_id`      bigint(20)   unsigned not null                           comment 'Module ID',
    `datasource_id`  bigint(20)   unsigned not null                           comment 'Datasource ID',
    `name`           varchar(255)          not null default ''                comment 'API name',
    `description`    varchar(1024)                  default null              comment 'API description',
    `method`         varchar(16)           not null default 'GET'             comment 'Request method',
    `path`           varchar(255)          not null default ''                comment 'Request path',
    `params`         text                      null                           comment 'Input parameters JSON list',
    `outputs`        text                      null                           comment 'Output parameters JSON list',
    `status`         tinyint(1)            not null default 0                 comment 'Whether published',
    `open`           tinyint(1)            not null default 0                 comment 'Whether public',
    `engine`         varchar(16)           not null default 'SQL'             comment 'Execution engine',
    `response_format`  tinytext                     default null              comment 'Response format configuration',
    `naming_strategy`  varchar(16)         not null default 'NONE'            comment 'Response naming strategy',
    `flow_status`    tinyint(1)            not null default 0                 comment 'Whether flow control enabled',
    `flow_grade`     bigint(20)   unsigned          default null              comment 'Flow control type',
    `flow_count`     bigint(20)   unsigned          default null              comment 'Flow control threshold',
    `content_type`   varchar(50)           not null default ''                comment 'ContentType',
    `create_time`    timestamp             not null default current_timestamp comment 'Create time',
    `update_time`    timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_method_path`(`method`,`path`),
    foreign key (`group_id`) references `SQLREST_API_GROUP` (`id`) on delete cascade on update cascade,
    foreign key (`module_id`) references `SQLREST_API_MODULE` (`id`) on delete cascade on update cascade,
    foreign key (`datasource_id`) references `SQLREST_DATASOURCE` (`id`) on delete cascade on update cascade
) engine=InnoDB auto_increment=1 default charset=utf8 comment='API configuration table';

CREATE TABLE `SQLREST_API_CONTEXT`
(
    `id`                      bigint(20)   unsigned auto_increment             comment 'Primary key',
    `api_id`                  bigint(20)   unsigned not null                   comment 'API ID',
    `sql_text`                text                  not null                   comment 'SQL content',
    primary key (`id`),
    foreign key (`api_id`) references `SQLREST_API_ASSIGNMENT` (`id`) on delete cascade on update cascade
) engine=InnoDB default charset=utf8 comment='API SQL table';

CREATE TABLE `SQLREST_FIREWALL_RULES`
(
    `id`             bigint(20)   unsigned auto_increment                     comment 'Primary key',
    `status`         varchar(4)            not null default 'OFF'             comment 'Status: OFF-disabled; ON-enabled',
    `mode`           varchar(16)           not null default 'BLACK'           comment 'Mode: WHITE-whitelist; BLACK-blacklist',
    `addresses`      text                  default null                       comment 'Client address (IP) list',
    `create_time`    timestamp             not null default current_timestamp comment 'Create time',
    `update_time`    timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
    PRIMARY KEY (`id`)
) engine=InnoDB default charset=utf8 comment='Firewall rules table';

CREATE TABLE `SQLREST_APP_CLIENT`
(
    `id`              bigint(20)   unsigned auto_increment                     comment 'Primary key',
    `name`            varchar(255)          not null                           comment 'Client application name',
    `description`     varchar(1024)         default null                       comment 'Client application description',
    `app_key`         varchar(64)           not null                           comment 'Application account',
    `app_secret`      varchar(64)           not null                           comment 'Application secret key',
    `expire_duration` varchar(16)           not null default 'FOR_EVER'        comment 'Expiration type',
    `expire_at`       bigint(20)            not null default '0'               comment 'Expiration time',
    `access_token`    varchar(64)           default null                       comment 'Latest TOKEN',
    `create_time`     timestamp             not null default current_timestamp comment 'Create time',
    `update_time`     timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_app_key`(`app_key`)
) engine=InnoDB default charset=utf8 comment='Client application table';

CREATE TABLE `SQLREST_CLIENT_GROUP`
(
    `id`        bigint(20)  unsigned auto_increment  comment 'Primary key',
    `client_id` bigint(20)  not null                 comment 'Client application ID',
    `group_id`  bigint(20)  not null                 comment 'API group ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_client_group`(`client_id`,`group_id`)
) engine=InnoDB default charset=utf8 comment='Client application authorization group configuration table';

CREATE TABLE `SQLREST_ACCESS_RECORD`
(
    `id`              bigint(20)   unsigned auto_increment       comment 'Primary key',
    `path`            varchar(255)       default null            comment 'Path',
    `status`          bigint(11)         default null            comment 'HTTP status code',
    `duration`        bigint(20)         default null            comment 'Processing time',
    `ip_addr`         varchar(64)        default null            comment 'Client IP',
    `user_agent`      varchar(255)       default null            comment 'Client UA',
    `client_key`      varchar(64)        default null            comment 'Client Key',
    `api_id`          varchar(50)        default null            comment 'API ID',
    `exception`       varchar(1024)      default null            comment 'Error log',
    `create_time`     timestamp          not null default current_timestamp comment 'Create time',
    PRIMARY KEY (`id`)
) engine=InnoDB default charset=utf8 comment='Client application API access log table';

CREATE TABLE `SQLREST_SYSTEM_PARAM` (
  `id`               bigint(20)        NOT NULL AUTO_INCREMENT  COMMENT 'Primary key id',
  `param_key`        varchar(128)      NOT NULL                 COMMENT 'Parameter KEY',
  `param_type`       varchar(64)       NOT NULL                 COMMENT 'Value type',
  `param_value`      varchar(255)      NOT NULL                 COMMENT 'Parameter value',
  PRIMARY KEY (`id`),
  UNIQUE KEY `param_key` (`param_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='System parameter table';
