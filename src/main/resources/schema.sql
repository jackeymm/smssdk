CREATE TABLE IF NOT EXISTS `user_keypair` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `email` varchar(32) NOT NULL DEFAULT '' COMMENT 'email 地址',
  `token` varchar(64) NOT NULL DEFAULT '' COMMENT 'token',
  `private_key` varchar(512) NOT NULL DEFAULT '' COMMENT '私钥',
  `public_key` varchar(512) NOT NULL DEFAULT '' COMMENT '公钥',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `email_token_index` (`email`,`token`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ;