-- ----------------------------
-- Table structure for t_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_channel`;
CREATE TABLE `t_sms_channel`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道编码',
  `channel_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道名称',
  `secret_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信发送的key id',
  `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信发送的秘钥',
  `region_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'regionId仅阿里云需要',
  `channel_status` tinyint(2) NULL DEFAULT 1 COMMENT '渠道状态 1有效 0禁用',
  `sms_quantity` bigint(20) NOT NULL DEFAULT 0 COMMENT '渠道下可发送的短信数量',
  `sms_validity` datetime(0) NULL DEFAULT NULL COMMENT '短信可发送的有效期',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信渠道表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_template`;
CREATE TABLE `t_sms_template`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_id` bigint(20) NULL DEFAULT NULL COMMENT '短信渠道id',
  `sms_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信编码',
  `sms_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信名称',
  `template_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信模板ID',
  `sign_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信签名',
  `template_status` tinyint(2) NULL DEFAULT 1 COMMENT '短信状态 1有效 0禁用',
  `template_type` tinyint(2) NULL DEFAULT 1 COMMENT '短信类型',
  `cache_code_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缓存key',
  `cache_time_out` bigint(20) NOT NULL DEFAULT 0 COMMENT '缓存有效期 值',
  `cache_time_out_unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缓存有效期 单位',
  `send_times_limit` bigint(20) NOT NULL DEFAULT 0 COMMENT '发送次数限制',
  `send_times_limit_period` bigint(20) NOT NULL DEFAULT 0 COMMENT '限制时间间隔',
  `send_times_limit_period_unit` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '限制时间间隔 单位',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信配置表' ROW_FORMAT = DYNAMIC;
