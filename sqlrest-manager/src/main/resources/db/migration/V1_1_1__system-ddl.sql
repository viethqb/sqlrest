ALTER TABLE `SQLREST_API_ASSIGNMENT` ADD COLUMN `cache_key_type` varchar(16) not null default 'NONE' COMMENT 'Cache key type' AFTER `content_type`;
ALTER TABLE `SQLREST_API_ASSIGNMENT` ADD COLUMN `cache_key_expr` varchar(255) null COMMENT 'SpEL expression for cache key' AFTER `cache_key_type`;
ALTER TABLE `SQLREST_API_ASSIGNMENT` ADD COLUMN `cache_expire_seconds` bigint(20)   unsigned not null default 0 COMMENT 'Cache expiration duration (seconds)' AFTER `cache_key_expr`;
