CREATE TABLE `tasklog` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键',
  `taskId` varchar(32) DEFAULT NULL COMMENT '任务ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;