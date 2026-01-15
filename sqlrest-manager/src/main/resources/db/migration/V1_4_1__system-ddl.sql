ALTER TABLE `SQLREST_ACCESS_RECORD`
MODIFY COLUMN `exception` longtext  DEFAULT NULL COMMENT 'Error log' AFTER `api_id`,
ADD COLUMN `parameters` longtext DEFAULT NULL COMMENT 'Request input parameters' AFTER `api_id`;
