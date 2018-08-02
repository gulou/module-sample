--语言描述表
CREATE TABLE IF NOT EXISTS `language_desc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `biz_no` bigint(20) DEFAULT NULL COMMENT '业务编号(用于查询)',
  `language_desc_id` bigint(20) NOT NULL COMMENT '语言描述id',
  `language` int(11) NOT NULL COMMENT '语言',
  `description` varchar(500) NOT NULL COMMENT '描述',
  `extra_info` varchar(500) DEFAULT NULL COMMENT '额外信息',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_at` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `update_at` datetime(3) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_laguage_desc_id_language` (`language_desc_id`,`language`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8 COMMENT='语言描述表';

INSERT INTO `language_desc` (`id`, `biz_no`, `language_desc_id`, `language`, `description`, `extra_info`, `remark`, `create_at`, `update_at`) VALUES
	(84, 100001, 123, 0, '参数非法', NULL, NULL, NULL, NULL),
	(85, 100001, 123, 1, '参数非法繁体', NULL, NULL, NULL, NULL),
	(86, 100001, 123, 2, 'param invalid', NULL, NULL, NULL, NULL),
	(87, 0, 124, 0, '成功', NULL, NULL, NULL, NULL),
	(88, 100000, 125, 0, '系统错误', NULL, NULL, NULL, NULL);

-- 返回码表
CREATE TABLE IF NOT EXISTS `resp_code_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` bigint(20) NOT NULL COMMENT '返回码',
  `desc_id` bigint(20) NOT NULL COMMENT '语言描述id',
  `memo` varchar(50) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `desc_id` (`desc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='返回码记录表';

INSERT INTO `resp_code_record` (`id`, `code`, `desc_id`, `memo`) VALUES
	(1, 100001, 123, '参数错误'),
	(2, 0, 124, '参数错误'),
	(3, 10000, 125, '参数错误');
