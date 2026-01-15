ALTER TABLE `SQLREST_ACCESS_RECORD`
ADD COLUMN `executor_addr`  varchar(128) NULL COMMENT 'Executor IP address' AFTER `create_time`,
ADD COLUMN `gateway_addr`  varchar(128) NULL COMMENT 'Gateway IP address' AFTER `executor_addr`;
