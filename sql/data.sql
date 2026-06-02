-- =============================================================================
-- 牛客社区项目基础数据脚本
-- 需在 schema.sql 执行完成后运行
-- 执行方式: mysql -uroot -p my_project_db < data.sql
-- =============================================================================

USE `my_project_db`;

-- 系统用户 (CommunityConstant.SYSTEM_USER_ID = 1)，用于发送点赞/评论/关注等通知
INSERT INTO `user` (`id`, `username`, `password`, `salt`, `email`, `type`, `status`, `activation_code`, `header_url`, `create_time`)
VALUES (1, 'SYSTEM', 'SYSTEM', 'SYSTEM', 'nowcoder1@sina.com', 0, 1, NULL, 'http://static.nowcoder.com/images/head/notify.png', '2019-04-13 02:11:03')
ON DUPLICATE KEY UPDATE
  `username` = VALUES(`username`),
  `status` = VALUES(`status`),
  `header_url` = VALUES(`header_url`);
