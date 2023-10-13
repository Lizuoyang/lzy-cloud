-- ----------------------------
-- Table structure for t_sys_dfs
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dfs`;
CREATE TABLE `t_sys_dfs`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dfs_type` bigint(20) NULL DEFAULT NULL COMMENT '分布式存储分类',
  `dfs_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分布式存储编号',
  `access_url_prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件访问地址前缀',
  `upload_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分布式存储上传地址',
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '空间名称',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用ID',
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域',
  `access_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'accessKey',
  `secret_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'secretKey',
  `dfs_default` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否默认存储 0否，1是',
  `dfs_status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态 0禁用，1 启用',
  `access_control` tinyint(2) NOT NULL DEFAULT 0 COMMENT '访问控制 0私有，1公开',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分布式存储配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_sys_dfs_file
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dfs_file`;
CREATE TABLE `t_sys_dfs_file`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dfs_id` bigint(20) NULL DEFAULT NULL COMMENT '分布式存储配置id',
  `access_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件访问地址',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原文件名',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储文件名',
  `file_extension` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_size` bigint(20) NULL DEFAULT 0 COMMENT '文件大小',
  `file_status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态 0上传成功失败，1 上传成功',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分布式存储文件记录表' ROW_FORMAT = DYNAMIC;
