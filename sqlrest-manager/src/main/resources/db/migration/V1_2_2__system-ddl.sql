create table `SQLREST_MCP_CLIENT`  (
  `id`                  bigint(20)            not null auto_increment   comment 'Primary key id',
  `name`                varchar(256)          not null                  comment 'Client name',
  `token`               varchar(64)           not null                  comment 'Connection TOKEN',
  `create_time`         timestamp             not null default current_timestamp comment 'Create time',
  `update_time`         timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
  primary key (`id`),
  unique key `name` (`name`),
  KEY `idx_token` (`token`) USING BTREE
) engine=InnoDB character set = utf8 comment = 'MCP connection client table';

create table `SQLREST_MCP_TOOL`  (
  `id`                  bigint(20)            not null auto_increment   comment 'Primary key id',
  `name`                varchar(256)          not null                  comment 'Tool name',
  `description`         varchar(1024)         not null                  comment 'Tool description',
  `api_id`              bigint(20) unsigned   not null                  comment 'API ID',
  `create_time`         timestamp             not null default current_timestamp comment 'Create time',
  `update_time`         timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
  primary key (`id`),
  unique key `name` (`name`),
  FOREIGN KEY (`api_id`) REFERENCES `SQLREST_API_ASSIGNMENT` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) engine=InnoDB character set = utf8 comment = 'MCP tool configuration';
