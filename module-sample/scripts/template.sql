-- 消息模板表
CREATE TABLE IF NOT EXISTS `message_template` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` int(11) unsigned NOT NULL COMMENT '模板代码',
  `subject` varchar(50) NOT NULL COMMENT '主题',
  `content` varchar(50) NOT NULL COMMENT '内容',
  `language` int(11) NOT NULL COMMENT '语言',
  `extra_info` varchar(50) DEFAULT NULL COMMENT '额外信息',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_language` (`code`,`language`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='消息模板';

INSERT INTO `message_template` (`id`, `code`, `subject`, `content`, `language`, `extra_info`, `create_at`, `update_at`) VALUES
	(1, 0, '测试发短信', '你好,${name}.', 0, NULL, '2018-08-01 19:21:05.000', '2018-08-01 19:21:07.000'),
	(2, 0, '测试发短信', '你好,${name}.', 1, NULL, '2018-08-01 19:21:05.000', '2018-08-01 19:21:07.000'),
	(3, 0, '测试发短信', 'hello,${name}.', 2, NULL, '2018-08-01 19:21:05.000', '2018-08-01 19:21:07.000');
