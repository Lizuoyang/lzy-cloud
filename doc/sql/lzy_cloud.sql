/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.56.102
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.56.102:3306
 Source Schema         : lzy_cloud

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 24/03/2023 11:52:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `t_oauth_client_details`;
CREATE TABLE `t_oauth_client_details`
(
    `client_id`               varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用于唯一标识每一个客户端(client);在注册时必须填写(也可由服务端自动生成).用于唯一标识每一个客户端(client);在注册时必须填写(也可由服务端自动生成)',
    `resource_ids`            varchar(256) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '客户端所能访问的资源id集合,多个资源时用逗号(,)分隔,如: \"unity-resource,mobile-resource\"',
    `client_secret`           varchar(256) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '用于指定客户端(client)的访问密匙;在注册时必须填写(也可由服务端自动生成)',
    `scope`                   varchar(256) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔',
    `authorized_grant_types`  varchar(256) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: \"authorization_code,password\"',
    `web_server_redirect_uri` varchar(256) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '客户端的重定向URI',
    `authorities`             varchar(256) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '指定客户端所拥有的Spring Security的权限值,可选, 若有多个权限值,用逗号(,)分隔',
    `access_token_validity`   int(11)                                                 NULL DEFAULT NULL COMMENT '设定客户端的access_token的有效时间值(单位:秒)',
    `refresh_token_validity`  int(11)                                                 NULL DEFAULT NULL COMMENT '设定客户端的refresh_token的有效时间值(单位:秒)',
    `additional_information`  varchar(4096) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '实际应用中, 可以用该字段来存储关于客户端的一些其他信息,如客户端的国家,地区,注册时的IP地址等等',
    `autoapprove`             varchar(256) CHARACTER
        SET utf8 COLLATE utf8_general_ci                                              NULL DEFAULT NULL COMMENT '设置用户是否自动Approval操作, 默认值为 \'false\', 可选值包括 \'true\',\'false\', \'read\',\'write\'.',
    `create_time`             datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `creator`                 bigint(20)                                              NULL DEFAULT NULL COMMENT '创建者',
    `update_time`             datetime(0)                                             NULL DEFAULT NULL COMMENT '更新时间',
    `operator`                bigint(20)                                              NULL DEFAULT NULL COMMENT '更新者',
    `del_flag`                tinyint(4)                                              NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER
      SET
      = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_oauth_client_details
-- ----------------------------
INSERT INTO `t_oauth_client_details`
VALUES ('lzycloud-admin', NULL, '123456', 'all', 'password,captcha,refresh_token,authorization_code', NULL,
        NULL, 3600, 7200, NULL, 'true', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for t_sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_resource`;
CREATE TABLE `t_sys_resource`
(
    `id`                 bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '主键 ',
    `parent_id`          bigint(20)                                              NULL     DEFAULT NULL COMMENT '父id ',
    `ancestors`          varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '所有上级资源id的集合，便于查找 ',
    `resource_name`      varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '资源名称 ',
    `resource_key`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '资源标识 ',
    `resource_type`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL     DEFAULT NULL COMMENT '资源类型 1、模块 2、菜单 3、按钮 4、链接 ',
    `resource_icon`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '资源图标 ',
    `resource_path`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '资源路径 ',
    `resource_url`       varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '资料链接 ',
    `resource_level`     int(11)                                                 NULL     DEFAULT NULL COMMENT '资源级别 ',
    `resource_show`      tinyint(1)                                              NULL     DEFAULT NULL COMMENT '是否显示 ',
    `resource_cache`     tinyint(1)                                              NULL     DEFAULT NULL COMMENT '是否缓存 ',
    `resource_page_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '资源页面名称 ',
    `resource_status`    tinyint(4)                                              NULL     DEFAULT 1 COMMENT '1有效，0禁用 ',
    `comments`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '备注 ',
    `create_time`        datetime(0)                                             NULL     DEFAULT NULL COMMENT '创建时间 ',
    `creator`            bigint(20)                                              NULL     DEFAULT NULL COMMENT '创建者 ',
    `update_time`        datetime(0)                                             NULL     DEFAULT NULL COMMENT '更新时间 ',
    `operator`           bigint(20)                                              NULL     DEFAULT NULL COMMENT '更新者 ',
    `del_flag`           tinyint(4)                                              NOT NULL DEFAULT 0 COMMENT '1:删除 0:不删除 ',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `INDEX_PERM_NAME` (`resource_name`) USING BTREE,
    INDEX `INDEX_PERM_PID` (`parent_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 71
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '权限表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_resource
-- ----------------------------
INSERT INTO `t_sys_resource`
VALUES (1, 0, '0', '基础系统管理', 'system:mgr', '1', 'xitongguanli', 'system', 'system', 1000, 1, 1, NULL, 1,
        '基础系统管理', '2016-04-22 10:43:19', 1, '2021-08-24 17:23:43', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (2, 1, '0, 1 ', '系统管理', 'system:cfg', '2', 'xitongguanli', 'system/cfg', 'Layout', 1000, 1, 1,
        NULL, 1, '配置管理', '2016-04-22 10:43:19', 1, '2018-11-10 11:19:02', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (3, 2, '0, 1, 2 ', '权限管理', 'system:permission', '2', 'quanxianguanli', 'permission', 'nested', 1,
        1, 1, NULL, 1, '权限管理', '2016-04-22 10:43:19', 1, '2018-11-02 14:48:15', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (4, 3, '0, 1, 2, 3 ', '用户管理', 'system:user:table', '2', 'yonghuguanli', 'system/user/table',
        'system/user/userTable', 1, 1, 1, 'userTable', 1, '用户管理菜单', '2016-04-22 10:43:19', 1,
        '2018-11-02 14:48:42', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (5, 3, '0, 1, 2, 3 ', '角色管理', 'system:role:table', '2', 'jiaoseguanli', 'system/role/table',
        'system/role/roleTable', 2, 1, 1, 'roleTable', 1, '角色管理', '2016-04-22 10:43:19', 1,
        '2018-11-02 14:50:45', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (6, 3, '0, 1, 2, 3 ', '资源管理', 'system:resource:table', '2', 'quanxianguanli',
        'system/resource/table', 'system/resource/resourceTable', 3, 1, 1, 'resourceTable', 1, '资源管理',
        '2016-04-22 10:43:19', 1, '2018-11-02 14:50:54', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (8, 2, '0, 1, 2 ', '基础配置', 'system:base:cfg', '2', 'jichupeizhi', 'system/base', 'nested', 2, 1, 1,
        'systemBase', 1, '系统配置', '2016-04-22 11:03:14', 1, '2018-11-10 10:17:11', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (9, 8, '0, 1, 2, 8 ', '系统字典', 'system:config:dict', '2', 'shujuzidian', 'system/dict/table',
        'system/base/dict/dictTable', 1, 1, 1, 'dictTable', 1, '系统数据字典', '2018-10-27 17:53:49', 1,
        '2021-09-30 16:52:04', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (14, 4, '0, 1, 2, 3, 4 ', '获取用户信息', 'system:auth:user:info', '4', 'xitongrizhi', 'system/user/info',
        '/lzy-service-system/auth/user/info', 1, 1, 1, NULL, 1, '登录用户获取登录信息', '2018-10-27 17:40:14', 1,
        '2021-05-14 17:46:43', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (15, 4, '0, 1, 2, 3, 4 ', '获取用户列表', 'system:user:list', '4', 'xitongrizhi', 'system/user/list',
        '/lzy-service-system/user/list', 2, 1, 1, NULL, 1, '获取用户列表数据', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (17, 6, '0, 1, 2, 3, 6 ', '获取资源权限列表', 'system:resource:list', '4', 'xitongrizhi',
        'system/resource/list', '/lzy-service-system/resource/tree', 2, 1, 1, NULL, 1, '获取资源权限列表数据',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (18, 5, '0, 1, 2, 3, 5 ', '获取角色列表', 'system:role:list', '4', 'xitongrizhi', 'system/role/list',
        '/lzy-service-system/role/list', 2, 1, 1, NULL, 1, '获取角色列表数据', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (19, 5, '0, 1, 2, 3, 5 ', '获取所有角色', 'system:role:all', '4', 'xitongrizhi', 'system/role/all',
        '/lzy-service-system/role/all', 2, 1, 1, NULL, 1, '获取所有角色数据', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (20, 4, '0, 1, 2, 3, 4 ', '创建用户接口', 'system:user:create', '4', 'xitongrizhi', 'system/user/create',
        '/lzy-service-system/user/create', 3, 1, 1, NULL, 1, '创建用户接口', '2018-10-27 17:40:14', 1,
        '2021-05-14 17:46:48', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (21, 4, '0, 1, 2, 3, 4 ', '更新用户接口', 'system:user:update', '4', 'xitongrizhi', 'system/user/update',
        '/lzy-service-system/user/update', 4, 1, 1, NULL, 1, '更新用户接口', '2018-10-27 17:40:14', 1,
        '2021-05-14 17:46:52', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (22, 4, '0, 1, 2, 3, 4 ', '删除用户接口', 'system:user:delete', '4', 'xitongrizhi', 'system/user/delete',
        '/lzy-service-system/user/delete/{userId}', 5, 1, 1, NULL, 1, '删除用户接口', '2018-10-27 17:40:14',
        1, '2021-05-14 17:46:56', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (23, 4, '0, 1, 2, 3, 4 ', '批量删除用户接口', 'system:user:batch:delete', '4', 'xitongrizhi',
        'system/user/batch/delete', '/lzy-service-system/user/batch/delete', 6, 1, 1, NULL, 1,
        '批量删除用户接口', '2018-10-27 17:40:14', 1, '2021-05-14 17:46:59', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (24, 4, '0, 1, 2, 3, 4 ', '用户修改自己密码', 'system:user:password:change', '4', 'xitongrizhi',
        'system/user/password/change', '/lzy-service-system/user/password/change', 7, 1, 1, NULL, 1,
        '用户修改自己密码', '2018-10-27 17:40:14', 1, '2021-05-14 17:47:03', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (25, 4, '0, 1, 2, 3, 4 ', '管理员重置密码', 'system:user:password:reset', '4', 'xitongrizhi',
        'system/user/password/reset', '/lzy-service-system/user/password/reset/{userId}', 8, 1, 1,
        NULL, 1, '管理员重置密码', '2018-10-27 17:40:14', 1, '2021-05-14 17:47:07', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (26, 4, '0, 1, 2, 3, 4 ', '修改用户状态', 'system:user:status', '4', 'xitongrizhi', 'system/user/status',
        '/lzy-service-system/user/status/{userId}/{status}', 9, 1, 1, NULL, 1, '修改用户状态',
        '2018-10-27 17:40:14', 1, '2021-05-14 17:47:11', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (27, 4, '0, 1, 2, 3, 4 ', '用户自己修改信息', 'system:user:update:info', '4', 'xitongrizhi',
        'system/user/update/info', '/lzy-service-system/user/update/info', 10, 1, 1, NULL, 1,
        '用户自己修改信息', '2018-10-27 17:40:14', 1, '2021-05-14 17:47:15', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (29, 4, '0, 1, 2, 3, 4 ', '检查用户是否存在', 'system:user:check', '4', 'xitongrizhi', 'system/role/check',
        '/lzy-service-system/user/check', 11, 1, 1, NULL, 1, '检查用户是否存在', '2018-10-27 17:40:14', 1,
        '2021-05-14 17:47:21', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (30, 5, '0, 1, 2, 3, 5 ', '添加角色', 'system:role:create', '4', 'xitongrizhi', 'system/role/create',
        '/lzy-service-system/role/create', 2, 1, 1, NULL, 1, '添加角色', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (31, 5, '0, 1, 2, 3, 5 ', '更新角色', 'system:role:update', '4', 'xitongrizhi', 'system/role/update',
        '/lzy-service-system/role/update', 2, 1, 1, NULL, 1, '更新角色', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (32, 5, '0, 1, 2, 3, 5 ', '删除角色', 'system:role:delete', '4', 'xitongrizhi', 'system/role/delete',
        '/lzy-service-system/role/delete/{roleId}', 2, 1, 1, NULL, 1, '删除角色', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (33, 5, '0, 1, 2, 3, 5 ', '批量删除角色', 'system:role:batch:delete', '4', 'xitongrizhi',
        'system/role/batch/delete', '/lzy-service-system/role/batch/delete', 2, 1, 1, NULL, 1,
        '批量删除角色', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (34, 5, '0, 1, 2, 3, 5 ', '修改角色状态', 'system:role:status', '4', 'xitongrizhi', 'system/role/status',
        '/lzy-service-system/role/status/{roleId}/{roleStatus}', 2, 1, 1, NULL, 1, '修改角色状态',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (35, 5, '0, 1, 2, 3, 5 ', '获取角色的资源权限', 'system:role:resource:list', '4', 'xitongrizhi',
        'system/role/resource/list', '/lzy-service-system/role/resource/{roleId}', 2, 1, 1, NULL, 1,
        '获取角色的资源权限', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (36, 5, '0, 1, 2, 3, 5 ', '修改角色的资源权限', 'system:role:resource:update', '4', 'xitongrizhi',
        'system/role/resource/update', '/lzy-service-system/role/resource/update', 2, 1, 1, NULL, 1,
        '修改角色的资源权限', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (37, 5, '0, 1, 2, 3, 5 ', '查询所有角色列表', 'system:role:all', '4', 'xitongrizhi', 'system/role/all',
        '/lzy-service-system/role/all', 2, 1, 1, NULL, 1, '查询所有角色列表', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (38, 5, '0, 1, 2, 3, 5 ', '校验角色是否存在', 'system:role:check', '4', 'xitongrizhi', 'system/role/check',
        '/lzy-service-system/role/check', 2, 1, 1, NULL, 1, '校验角色是否存在', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (39, 6, '0, 1, 2, 3, 6 ', '添加资源', 'system:resource:create', '4', 'xitongrizhi',
        'system/resource/create', '/lzy-service-system/resource/create', 2, 1, 1, NULL, 1, '添加资源',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (40, 6, '0, 1, 2, 3, 6 ', '更新资源', 'system:resource:update', '4', 'xitongrizhi',
        'system/resource/update', '/lzy-service-system/resource/update', 2, 1, 1, NULL, 1, '更新资源',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (41, 6, '0, 1, 2, 3, 6 ', '删除资源', 'system:resource:delete', '4', 'xitongrizhi',
        'system/resource/delete', '/lzy-service-system/resource/delete/{resourceId}', 2, 1, 1, NULL, 1,
        '删除资源', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (42, 6, '0, 1, 2, 3, 6 ', '修改资源状态', 'system:resource:status', '4', 'xitongrizhi',
        'system/resource/status', '/lzy-service-system/resource/status/{resourceId}/{resourceStatus}', 2,
        1, 1, NULL, 1, '修改资源状态', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (43, 6, '0, 1, 2, 3, 6 ', '获取用户权限菜单', 'system:resource:menu', '4', 'xitongrizhi',
        'system/resource/menu', '/lzy-service-system/resource/menu', 2, 1, 1, NULL, 1, '获取用户权限菜单',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (44, 6, '0, 1, 2, 3, 6 ', '校验资源是否存在', 'system:resource:check', '4', 'xitongrizhi',
        'system/resource/check', '/lzy-service-system/resource/check', 2, 1, 1, NULL, 1, '校验资源是否存在',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (54, 9, '0, 1, 2, 8, 9 ', '添加系统数据字典', 'base:dict:create', '4', 'xitongrizhi', 'base/dict/create',
        '/lzy-service-base/dict/create', 2, 1, 1, NULL, 1, '添加数据字典', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (55, 9, '0, 1, 2, 8, 9 ', '更新系统数据字典', 'base:dict:update', '4', 'xitongrizhi', 'base/dict/update',
        '/lzy-service-base/dict/update', 2, 1, 1, NULL, 1, '更新数据字典', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (56, 9, '0, 1, 2, 8, 9 ', '删除系统数据字典', 'base:dict:delete', '4', 'xitongrizhi', 'base/dict/delete',
        '/lzy-service-base/dict/delete/{dictId}', 2, 1, 1, NULL, 1, '删除数据字典', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (57, 9, '0, 1, 2, 8, 9 ', '修改系统数据字典状态', 'base:dict:status', '4', 'xitongrizhi', 'base/dict/status',
        '/lzy-service-base/dict/status/{dictId}/{dictStatus}', 2, 1, 1, NULL, 1, '修改数据字典状态',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (58, 9, '0, 1, 2, 8, 9 ', '查询系统数据字典树', 'base:dict:tree', '4', 'xitongrizhi', 'base/dict/tree',
        '/lzy-service-base/dict/tree', 2, 1, 1, NULL, 1, '查询数据字典树', '2018-10-27 17:40:14', 1,
        '2021-01-04 18:20:56', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (59, 9, '0, 1, 2, 8, 9 ', '校验系统数据字典是否存在', 'base:dict:check', '4', 'xitongrizhi', 'base/dict/check',
        '/lzy-service-base/dict/check', 2, 1, 1, NULL, 1, '校验数据字典是否存在', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (67, 9, '0, 1, 2, 8, 9 ', '批量删除数据字典', 'base:dict:batch:delete', '4', 'xitongrizhi',
        'base/dict/batch/delete', '/lzy-service-base/dict/batch/delete', 2, 1, 1, NULL, 1,
        '批量删除数据字典', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (68, 9, '0, 1, 2, 8, 9 ', '校验字典code是否存在', 'base:dict:check', '4', 'xitongrizhi', 'base/dict/check',
        '/lzy-service-base/dict/check', 2, 1, 1, NULL, 1, '校验字典code是否存在', '2018-10-27 17:40:14', 1,
        '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (69, 9, '0, 1, 2, 8, 9 ', '修改数据字典状态', 'base:dict:status', '4', 'xitongrizhi', 'base/dict/status',
        '/lzy-service-base/dict/status/{dictId}/{dictStatus}', 2, 1, 1, NULL, 1, '修改数据字典状态',
        '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (70, 4, '0, 1, 2, 3, 4 ', '导出用户接口', 'system:user:export', '4', 'xitongrizhi', 'system/user/export',
        '/lzy-excel-operate/export/system/user/list', 11, 1, 1, NULL, 1, '导出用户接口',
        '2018-10-27 17:40:14', 1, '2021-05-14 17:46:52', 1, 0);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`
(
    `id`                   bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '主键 ',
    `parent_id`            bigint(20)                                              NULL     DEFAULT 0 COMMENT '父id ',
    `role_name`            varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '角色名称 ',
    `role_key`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '角色标识 ',
    `role_level`           int(11)                                                 NULL     DEFAULT NULL COMMENT '角色级别 ',
    `role_status`          tinyint(4)                                              NULL     DEFAULT 1 COMMENT '1有效，0禁用 ',
    `data_permission_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL DEFAULT 'DATA_PERMISSION_SELF ' COMMENT '角色数据权限类型,默认只能查询本人数据 ',
    `comments`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '描述 ',
    `create_time`          datetime(0)                                             NULL     DEFAULT NULL COMMENT '创建时间 ',
    `creator`              bigint(20)                                              NULL     DEFAULT NULL COMMENT '创建者 ',
    `update_time`          datetime(0)                                             NULL     DEFAULT NULL COMMENT '更新时间 ',
    `operator`             bigint(20)                                              NULL     DEFAULT NULL COMMENT '更新者 ',
    `del_flag`             tinyint(4)                                              NULL     DEFAULT 0 COMMENT '1:删除 0:不删除 ',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `INDEX_ROLE_NAME` (`role_name`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role`
VALUES (1, 0, '超级管理员', 'SPADMIN', 1, 0, '3', '保留，不启用', '2016-07-01 11:30:31', 1, '2020-12-03 20:50:25', 1, 0);
INSERT INTO `t_sys_role`
VALUES (2, 0, '系统管理员', 'SYSADMIN', 2, 1, '3', '管理系统权限资源等后台用户', '2016-07-01 11:30:28', 1, '2021-07-23 17:53:09', 1, 0);
INSERT INTO `t_sys_role`
VALUES (5, 0, '测试', 'test', 1, 1, 'DATA_PERMISSION_SELF', '111', '2023-03-24 03:17:43', 2, NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_resource`;
CREATE TABLE `t_sys_role_resource`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键 ',
    `role_id`     bigint(20)  NOT NULL COMMENT '角色id ',
    `resource_id` bigint(20)  NOT NULL COMMENT '资源id ',
    `create_time` datetime(0) NULL     DEFAULT NULL COMMENT '创建时间 ',
    `creator`     bigint(20)  NULL     DEFAULT NULL COMMENT '创建者 ',
    `update_time` datetime(0) NULL     DEFAULT NULL COMMENT '更新时间 ',
    `operator`    bigint(20)  NULL     DEFAULT NULL COMMENT '更新者 ',
    `del_flag`    tinyint(4)  NOT NULL DEFAULT 0 COMMENT '1:删除 0:不删除 ',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 74
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '角色和权限关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_role_resource
-- ----------------------------
INSERT INTO `t_sys_role_resource`
VALUES (1, 2, 1, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (2, 2, 2, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (3, 2, 3, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (4, 2, 4, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (5, 2, 5, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (6, 2, 6, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (7, 2, 7, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (9, 2, 8, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (10, 2, 9, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (11, 2, 10, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (12, 2, 11, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (13, 2, 12, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (14, 2, 13, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (15, 2, 14, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (16, 2, 19, '2023-01-05 15:57:09', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (17, 2, 20, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (18, 2, 21, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (19, 2, 22, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (20, 2, 23, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (21, 2, 24, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (22, 2, 25, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (23, 2, 26, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (24, 2, 27, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (25, 2, 28, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (26, 2, 29, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (27, 2, 30, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (28, 2, 31, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (29, 2, 32, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (30, 2, 33, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (31, 2, 34, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (32, 2, 35, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (33, 2, 36, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (34, 2, 37, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (35, 2, 38, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (36, 2, 39, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (37, 2, 40, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (38, 2, 41, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (39, 2, 42, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (40, 2, 43, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (41, 2, 44, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (42, 2, 45, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (43, 2, 46, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (44, 2, 47, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (45, 2, 48, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (46, 2, 49, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (47, 2, 50, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (48, 2, 51, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (49, 2, 52, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (50, 2, 53, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (51, 2, 54, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (52, 2, 55, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (53, 2, 56, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (54, 2, 57, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (55, 2, 58, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (56, 2, 59, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (57, 2, 60, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (64, 2, 67, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (65, 2, 68, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (66, 2, 69, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (67, 2, 70, '2023-01-05 17:06:06', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (70, 2, 15, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (71, 2, 16, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (72, 2, 17, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (73, 2, 18, '2023-01-05 19:24:13', 1, NULL, NULL, 0);

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '主键 ',
    `account`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '账号 ',
    `nickname`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '昵称 ',
    `real_name`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '真实姓名 ',
    `gender`      char(1) CHARACTER SET utf8 COLLATE utf8_general_ci      NULL DEFAULT '2' COMMENT '1 : 男，0 : 女 ',
    `email`       varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '邮箱 ',
    `mobile`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '电话 ',
    `password`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码 ',
    `status`      tinyint(4)                                              NULL DEFAULT 1 COMMENT '0 禁用，1 启用, 2 密码过期或初次未修改',
    `avatar`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像 ',
    `country`     varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '国家 ',
    `province`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '省 ',
    `city`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '市 ',
    `area`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '区 ',
    `street`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '街道详细地址 ',
    `comments`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注 ',
    `create_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间 ',
    `creator`     bigint(20)                                              NULL DEFAULT NULL COMMENT '创建者 ',
    `update_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '更新时间 ',
    `operator`    bigint(20)                                              NULL DEFAULT NULL COMMENT '更新者 ',
    `del_flag`    tinyint(4)                                              NULL DEFAULT 0 COMMENT '1:删除 0:不删除 ',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `INDEX_USER_NAME` (`real_name`) USING BTREE,
    INDEX `INDEX_USER_PHONE` (`mobile`) USING BTREE,
    INDEX `INDEX_USER_EMAIL` (`email`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user`
VALUES (2, 'lizuoyang', '老板', '李佐洋', '1', '17752846851@qq.com', '17752846851',
        '{bcrypt}$2a$10$.xiE6X7P.419sKckd2xjQO7gM0PafpQiBpRCXkohU5qTZV7jd1BzK', 1, NULL, NULL, NULL, NULL, NULL, '''',
        '''', '2023-02-01 06:15:29', 1, NULL, NULL, 0);
INSERT INTO `t_sys_user`
VALUES (3, 'test', '测试小白', '王大力', '1', '17753211234@qq.com', '17753211234',
        '{bcrypt}$2a$10$FEVzZiLH4OJJKwIz0I5LR.5qGn8/ERjB86EzIROjrR0NQnlcAYKS2', 1, NULL, NULL, NULL, NULL, NULL, '''',
        '''', '2023-03-24 02:31:40', 2, NULL, NULL, 1);
INSERT INTO `t_sys_user`
VALUES (4, '21212', '212', '12121', '1', '17755556766@qq.com', '17755556766',
        '{bcrypt}$2a$10$ActrhwiD/qvsYztRZ.Tag.5aU8zuUbIvwUioRqD1GZfnVvXWd2/ei', 1, NULL, NULL, NULL, NULL, NULL, '''',
        '''', '2023-03-24 02:54:59', 2, NULL, NULL, 1);

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role`
(
    `id`          bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键 ',
    `user_id`     bigint(20)  NOT NULL COMMENT '用户id ',
    `role_id`     bigint(20)  NOT NULL COMMENT '角色id ',
    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间 ',
    `creator`     bigint(20)  NULL DEFAULT NULL COMMENT '创建人 ',
    `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间 ',
    `operator`    bigint(20)  NULL DEFAULT NULL COMMENT '更新人 ',
    `del_flag`    tinyint(4)  NULL DEFAULT 0 COMMENT '1:删除 0:不删除 ',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `INDEX_USER_ID` (`user_id`) USING BTREE,
    INDEX `INDEX_ROLE_ID` (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户和角色关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role`
VALUES (2, 2, 2, '2023-02-01 06:15:29', 1, NULL, NULL, 0);
INSERT INTO `t_sys_user_role`
VALUES (3, 3, 2, '2023-03-24 02:31:40', 2, NULL, NULL, 1);
INSERT INTO `t_sys_user_role`
VALUES (4, 4, 2, '2023-03-24 02:55:08', 2, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict`
(
    `id`          bigint(20)                                              NOT NULL AUTO_INCREMENT COMMENT '主键',
    `parent_id`   bigint(20)                                              NULL     DEFAULT NULL COMMENT '字典上级',
    `ancestors`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '所有上级字典id的集合，便于查找',
    `dict_name`   varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '字典名称',
    `dict_code`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '字典值',
    `dict_order`  int(11)                                                 NULL     DEFAULT NULL COMMENT '排序',
    `dict_status` tinyint(2)                                              NULL     DEFAULT 1 COMMENT '1有效，0禁用',
    `comments`    varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '备注',
    `create_time` datetime(0)                                             NULL     DEFAULT NULL COMMENT '创建时间',
    `creator`     bigint(20)                                              NULL     DEFAULT NULL COMMENT '创建人',
    `update_time` datetime(0)                                             NULL     DEFAULT NULL COMMENT '更新时间',
    `operator`    bigint(20)                                              NULL     DEFAULT NULL COMMENT '操作人',
    `del_flag`    tinyint(2)                                              NOT NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `INDEX_DICT_NAME` (`dict_name`) USING BTREE,
    INDEX `INDEX_DICT_CODE` (`dict_code`) USING BTREE,
    INDEX `INDEX_PARENT_ID` (`parent_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 83
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '数据字典表'
  ROW_FORMAT = DYNAMIC;
