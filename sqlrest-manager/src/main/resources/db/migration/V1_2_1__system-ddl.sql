create table `SQLREST_UNIFY_ALARM`  (
  `id`                  bigint(20)            not null auto_increment   comment 'Primary key id',
  `status`              varchar(4)            not null default 'OFF'    comment 'Status: OFF-disabled; ON-enabled',
  `endpoint`            varchar(256)          not null                  comment 'Alarm system address',
  `content_type`        varchar(128)          not null                  comment 'API input ContentType',
  `input_template`      varchar(4096)         not null                  comment 'API input template',
  `create_time`         timestamp             not null default current_timestamp comment 'Create time',
  `update_time`         timestamp             not null default current_timestamp on update current_timestamp comment 'Update time',
  primary key (`id`)
) engine=InnoDB character set = utf8 comment = 'Unified alarm parameter configuration';

INSERT INTO `SQLREST_UNIFY_ALARM` (`id`, `status`, `endpoint`,`content_type`, `input_template`)
VALUES ('1', 'OFF', 'http://127.0.0.1:8000/api/v1/message/send', 'application/json', '{}');

ALTER TABLE `SQLREST_API_ASSIGNMENT` ADD COLUMN `alarm` tinyint(1) not null default 0 COMMENT 'Whether alarm is enabled' AFTER `open`;
