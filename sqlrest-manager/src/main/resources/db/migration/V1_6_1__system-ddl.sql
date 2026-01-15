CREATE TABLE `SQLREST_VERSION_COMMIT` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `biz_id` bigint(20) unsigned NOT NULL COMMENT 'Object ID',
  `version` bigint(20) unsigned NOT NULL COMMENT 'Version number',
  `description`  varchar(256) NULL COMMENT 'Description',
  `content` longtext NOT NULL COMMENT 'Content',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `api_id_version` (`biz_id`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Version management table';

CREATE TABLE `SQLREST_API_ONLINE` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT 'API name',
  `method` varchar(16) NOT NULL DEFAULT 'GET' COMMENT 'Request method',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT 'Request path',
  `api_id` bigint(20) unsigned NOT NULL COMMENT 'API ID',
  `group_id` bigint(20) unsigned NOT NULL COMMENT 'Group ID',
  `module_id` bigint(20) unsigned NOT NULL COMMENT 'Module ID',
  `datasource_id` bigint(20) unsigned NOT NULL COMMENT 'Datasource ID',
  `open` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether public',
  `alarm` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether alarm is enabled',
  `flow_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Whether flow control is enabled',
  `commit_id` bigint(20) unsigned NOT NULL COMMENT 'Version CommitId',
  `version` bigint(20) unsigned NOT NULL COMMENT 'Version number',
  `content` longtext NOT NULL COMMENT 'Detailed content JSON',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_method_path` (`method`,`path`),
  KEY `api_id` (`api_id`),
  KEY `group_id` (`group_id`),
  KEY `module_id` (`module_id`),
  KEY `datasource_id` (`datasource_id`),
  FOREIGN KEY (`api_id`) REFERENCES `SQLREST_API_ASSIGNMENT` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`group_id`) REFERENCES `SQLREST_API_GROUP` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`module_id`) REFERENCES `SQLREST_API_MODULE` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`datasource_id`) REFERENCES `SQLREST_DATASOURCE` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (`commit_id`) REFERENCES `SQLREST_VERSION_COMMIT` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='API online table';

