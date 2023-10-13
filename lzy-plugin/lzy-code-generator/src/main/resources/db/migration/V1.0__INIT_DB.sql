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
INSERT INTO `t_sys_resource`
VALUES (71, 4, '0, 1, 2, 3, 4 ', '检查当前密码是否正确', 'system:user:check:password', '4', 'xitongrizhi', 'system/user/check/password', '/lzy-service-system/user/check/password', 12, 1, 1, NULL, 1, '检查当前密码是否正确', '2023-04-19 17:40:14', 1, '2023-04-19 17:47:21', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (72, 8, '0, 1, 2, 8 ', '系统接口', 'system:config:swagger', '2', 'shujuzidian', 'system/swagger/table', 'system/base/swagger/swaggerTable', 2, 1, 1, 'swaggerTable', 1, '系统接口', '2023-04-24 17:53:49', 1, '2023-04-24 17:53:49', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (73, 9, '0, 1, 2, 8, 9 ', '查询系统数据字典列表', 'base:dict:list', '4', 'xitongrizhi', 'base/dict/list', '/lzy-service-base/dict/list', 3, 1, 1, NULL, 1, '查询数据字典列表', '2023-04-24 17:53:49', 1, '2023-04-24 17:53:49', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (76, 9, '0,1,2,8,9', '批量查询数据字典', 'base:dict:batch:query', '4', 'system', 'base/dict/batch/query', '/lzy-service-base/dict/batch/query', 10, 1, 1, '', 1, '', '2023-04-14 11:27:46', 1, NULL, NULL, 0);
INSERT INTO `t_sys_resource`
VALUES (7,  3, '0,1,2,3', '组织管理', 'system:organization:table', '2', 'zuzhiguanli', 'system/organization/table', 'system/organization/organizationTable', 4, 1, 1, 'organizationTable', 1, '组织管理', '2023-05-08 14:32:00', 1, '2023-05-08 14:32:00', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (48, 7, '0,1,2,3,7', '添加组织', 'system:organization:create', '4', 'xitongrizhi', 'system/organization/create', '/lzy-service-system/organization/create', 2, 1, 1, NULL, 1, '添加组织', '2023-05-08 14:32:00', 1, '2023-05-08 14:32:00', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (49, 7, '0,1,2,3,7', '更新组织', 'system:organization:update', '4', 'xitongrizhi', 'system/organization/update', '/lzy-service-system/organization/update', 2, 1, 1, NULL, 1, '更新组织', '2023-05-08 14:32:00', 1, '2023-05-08 14:32:00', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (50, 7, '0,1,2,3,7', '删除组织', 'system:organization:delete', '4', 'xitongrizhi', 'system/organization/delete', '/lzy-service-system/organization/delete/{organizationId}', 2, 1, 1, NULL, 1, '删除组织', '2023-05-08 14:32:00', 1, '2023-05-08 14:32:00', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (51, 7, '0,1,2,3,7', '修改组织状态', 'system:organization:status', '4', 'xitongrizhi', 'system/organization/status', '/lzy-service-system/organization/status/{organizationId}/{organizationStatus}', 2, 1, 1, NULL, 1, '修改组织状态', '2023-05-08 14:32:00', 1, '2023-05-08 14:32:00', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (52, 7, '0,1,2,3,7', '查询组织树', 'system:organization:tree', '4', 'xitongrizhi', 'system/organization/tree', '/lzy-service-system/organization/tree', 2, 1, 1, NULL, 1, '查询组织树', '2023-05-08 14:32:00', 1, '2023-05-08 14:32:00', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (53, 7, '0,1,2,3,7', '校验组织是否存在', 'system:organization:check', '4', 'xitongrizhi', 'system/organization/check', '/lzy-service-system/organization/check', 2, 1, 1, NULL, 1, '校验组织是否存在', '2023-05-08 14:32:00', 1, '2023-05-08 14:32:00', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (74, 4, '0,1,2,3,4', '修改用户机构数据权限', 'system:user:update:organization:data:permission', '4', 'xitongguanli', 'system/user/update/organization/data/permission', '/lzy-service-system/user/update/organization/data/permission', 12, 1, 1, '', 1, '修改用户机构数据权限', '2023-05-10 17:46:32', 1, '2023-05-10 17:47:28', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (75, 9, '0,1,2,8,9', '查询数据字典', 'base:dict:query', '4', 'system', 'base/dict/query', '/lzy-service-base/dict/query/{dictCode}', 10, 1, 1, '', 1, '', '2023-05-11 11:27:46', 1, NULL, NULL, 0);
INSERT INTO `t_sys_resource`
VALUES (111, 3, '0,1,2,3', '数据权限', 'system:data:permission:rule', '2', 'jiaoseguanli', 'system/data/permission/rule', 'system/dataPermission/dataPermissionTable', 5, 1, 1, 'dataPermissionRuleTable', 1, '数据权限', '2023-05-12 10:43:19', 1, '2023-05-12 14:15:50', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (112, 111, '0,1,2,3,111', '获取数据权限列表', 'system:data:permission:rule:list', '4', 'xitongrizhi', 'system/data/permission/rule/list', '/lzy-service-system/data/permission/rule/list', 1, 1, 1, NULL, 1, '获取数据权限列表数据', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (113, 111, '0,1,2,3,111', '添加数据权限', 'system:data:permission:rule:create', '4', 'xitongrizhi', 'system/data/permission/rule/create', '/lzy-service-system/data/permission/rule/create', 2, 1, 1, NULL, 1, '添加数据权限', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (114, 111, '0,1,2,3,111', '更新数据权限', 'system:data:permission:rule:update', '4', 'xitongrizhi', 'system/data/permission/rule/update', '/lzy-service-system/data/permission/rule/update', 3, 1, 1, NULL, 1, '更新数据权限', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (115, 111, '0,1,2,3,111', '删除数据权限', 'system:data:permission:rule:delete', '4', 'xitongrizhi', 'system/data/permission/rule/delete', '/lzy-service-system/data/permission/rule/delete/{dataPermissionRuleId}', 4, 1, 1, NULL, 1, '删除数据权限', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (116, 111, '0,1,2,3,111', '批量删除数据权限', 'system:data:permission:rule:batch:delete', '4', 'xitongrizhi', 'system/data/permission/rule/batch/delete', '/lzy-service-system/data/permission/rule/batch/delete', 5, 1, 1, NULL, 1, '批量删除数据权限', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (118, 111, '0,1,2,3,111', '检查数据权限是否存在', 'system:data:permission:rule:check', '4', 'xitongrizhi', 'system/data/permission/rule/check', '/lzy-service-system/data/permission/rule/check', 6, 1, 1, NULL, 1, '字段校验', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (119, 111, '0,1,2,3,111', '查询拥有数据权限的角色', 'system:data:permission:rule:get:roles', '4', 'xitongrizhi', 'system/data/permission/rule/get/roles', '/lzy-service-system/data/permission/rule/get/roles/{currentDataPermissionId}', 7, 1, 1, NULL, 1, '查询拥有数据权限的角色', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (120, 111, '0,1,2,3,111', '批量更新拥有数据权限的角色', 'system:data:permission:rule:batch:rule:update', '4', 'xitongrizhi', 'system/data/permission/rule/batch/rule/update', '/lzy-service-system/data/permission/rule/batch/role/update', 8, 1, 1, NULL, 1, '批量更新拥有数据权限的角色', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (121, 111, '0,1,2,3,111', '修改数据权限状态', 'extension:data:permission:rule:status', '4', 'xitongrizhi', 'system/data/permission/rule/status', '/lzy-service-system/data/permission/rule/status/{dpId}/{dpStatus}', 2, 1, 1, NULL, 1, '修改数据权限状态', '2023-05-12 17:40:14', 1, '2023-05-12 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (92, 2, '0,86,87', '短信配置', 'extensions:sms:table', '2', 'international', 'extension/sms/table', 'nested', 4, 1, 1, 'smsTable', 1, '短信配置表', '2023-08-02 15:07:48', 2, '2023-08-04 02:13:36', 2, 0);
INSERT INTO `t_sys_resource`
VALUES (93, 92, '0,86,87,92', '短信渠道配置', 'extensions:sms:channel:table', '2', 'star', 'extensions/sms/channel/table', 'extensions/sms/channelTable', 2, 1, 1, 'smsChannelTable', 1, '', '2023-08-02 15:07:48', 2, '2023-08-04 02:13:22', 2, 0);
INSERT INTO `t_sys_resource`
VALUES (94, 92, '0,86,87,92', '短信模板配置', 'extensions:sms:template:table', '2', 'peoples', 'extensions/sms/template/table', 'extensions/sms/templateTable', 1, 1, 1, 'smsTemplateTable', 1, '', '2023-08-02 15:07:48', 2, '2023-08-04 02:14:04', 2, 0);
INSERT INTO `t_sys_resource`
VALUES (95, 93, '0,1,2,92,93', '批量删除短信渠道表', 'extension:sms:channel:batch:delete', '4', 'xitongrizhi', 'gitegg/service/extension/extension/sms/channel/batch/delete', '/lzy-service-extensions/extension/sms/channel/batch/delete', 2, 1, 1, NULL, 1, '批量删除短信渠道表', '2022-05-24 16:47:03', 1, '2022-05-24 16:47:03', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (96, 93, '0,1,2,92,93', '短信渠道表状态修改', 'extension:sms:channel:status', '4', 'xitongrizhi', 'gitegg/service/extension/extension/sms/channel/status', '/lzy-service-extensions/extension/sms/channel/status/{smsChannelId}/{smsChannelStatus}', 2, 1, 1, NULL, 1, '批量删除短信渠道表', '2022-05-24 16:47:03', 1, '2022-05-24 16:47:03', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (97, 93, '0,1,2,92,93', '短信渠道表字段校验是否存在', 'extension:sms:channel:check', '4', 'xitongrizhi', 'gitegg/service/extension/extension/sms/channel/check', '/lzy-service-extensions/extension/sms/channel/check', 2, 1, 1, NULL, 1, '字段校验是否存在短信渠道表', '2022-05-24 16:47:03', 1, '2022-05-24 16:47:03', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (101, 93, '0,1,2,92,93', '获取所有短信渠道列表', 'extension:sms:channel:list:all', '4', 'xitongrizhi', 'extension/sms/channel/list/all', '/lzy-service-extensions/extension/sms/channel/list/all', 2, 1, 1, NULL, 1, '获取所有短信渠道列表数据', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (77, 93, '0,1,2,92,93', '获取短信渠道表列表', 'extension:sms:channel:list', '4', 'xitongrizhi', 'extension/sms/channel/list', '/lzy-service-extensions/extension/sms/channel/list', 2, 1, 1, NULL, 1, '获取短信渠道表列表数据', '2023-05-12 17:40:14', 1, '2023-05-12 17:40:14', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (78, 93, '0,1,2,92,93', '添加短信渠道表', 'extension:sms:channel:create', '4', 'xitongrizhi', 'extension/sms/channel/create', '/lzy-service-extensions/extension/sms/channel/create', 2, 1, 1, NULL, 1, '添加短信渠道表', '2023-05-12 17:40:14', 1, '2023-05-12 17:40:14', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (79, 93, '0,1,2,92,93', '更新短信渠道表', 'extension:sms:channel:update', '4', 'xitongrizhi', 'extension/sms/channel/update', '/lzy-service-extensions/extension/sms/channel/update', 2, 1, 1, NULL, 1, '更新短信渠道表', '2023-05-12 17:40:14', 1, '2023-05-12 17:40:14', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (80, 93, '0,1,2,92,93', '删除短信渠道表', 'extension:sms:channel:delete', '4', 'xitongrizhi', 'extension/sms/channel/delete', '/lzy-service-extensions/extension/sms/channel/delete/{smsChannelId}', 2, 1, 1, NULL, 1, '删除短信渠道表', '2023-05-12 17:40:14', 1, '2023-05-12 17:40:14', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (82, 94, '0,1,2,92,94', '获取短信模板列表', 'extension:sms:template:list', '4', 'xitongrizhi', 'extension/sms/template/list', '/lzy-service-extensions/extension/sms/template/list', 2, 1, 1, NULL, 1, '获取短信配置表列表数据', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (83, 94, '0,1,2,92,94', '添加短信模板表', 'extension:sms:template:create', '4', 'xitongrizhi', 'extension/sms/template/create', '/lzy-service-extensions/extension/sms/template/create', 2, 1, 1, NULL, 1, '添加短信配置表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (84, 94, '0,1,2,92,94', '更新短信模板表', 'extension:sms:template:update', '4', 'xitongrizhi', 'extension/sms/template/update', '/lzy-service-extensions/extension/sms/template/update', 2, 1, 1, NULL, 1, '更新短信配置表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource`
VALUES (85, 94, '0,1,2,92,94', '删除短信模板表', 'extension:sms:template:delete', '4', 'xitongrizhi', 'extension/sms/template/delete', '/lzy-service-extensions/extension/sms/template/delete/{smsTemplateId}', 2, 1, 1, NULL, 1, '删除短信配置表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (122, 2, '0, 1 ,2', '文件存储', 'extensions:dfs', '2', 'clipboard', 'extension/dfs', 'nested', 5, 1, 1, 'dfsFileConfigTable', 1, '', '2023-08-04 01:55:45', 2, '2023-08-04 02:14:53', 2, 0);
INSERT INTO `t_sys_resource` VALUES (123, 122, '0, 1 ,2,122', '存储配置', 'extensions:dfs:table', '2', 'edit', 'extensions/dfs/table', 'extensions/dfs/dfsTable', 1, 1, 1, 'dfsTable', 1, '分布式存储配置表', '2023-08-04 02:06:46', 2, '2023-08-04 02:09:15', 2, 0);
INSERT INTO `t_sys_resource` VALUES (124, 122, '0, 1 ,2,122', '上传记录', 'extensions:dfs:file:table', '2', 'chart', 'extensions/dfs/file/table', 'extensions/dfs/dfsFileTable', 2, 1, 1, 'dfsFileTable', 1, '分布式存储文件记录表', '2023-08-04 02:08:03', 2, '2023-08-04 02:15:43', 2, 0);
INSERT INTO `t_sys_resource` VALUES (125, 123, '0,1,2,122,123', '存储配置列表', 'extension:dfs:list', '4', 'xitongrizhi', 'extension/dfs/list', '/lzy-service-extensions/extension/dfs/list', 2, 1, 1, NULL, 1,  '存储配置列表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (126, 123, '0,1,2,122,123', '添加分布式存储配置', 'extension:dfs:create', '4', 'xitongrizhi', 'extension/dfs/create', '/lzy-service-extensions/extension/dfs/create', 2, 1, 1, NULL, 1, '添加分布式存储配置', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (127, 123, '0,1,2,122,123', '更新分布式存储配置表', 'extension:dfs:update', '4', 'xitongrizhi', 'extension/dfs/update', '/lzy-service-extensions/extension/dfs/update', 2, 1, 1,  NULL, 1, '更新分布式存储配置表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (128, 123, '0,1,2,122,123', '删除分布式存储配置表', 'extension:dfs:delete', '4', 'xitongrizhi', 'extension/dfs/delete', '/lzy-service-extensions/extension/dfs/delete/{dfsId}', 2, 1, 1,  NULL,  1, '删除分布式存储配置表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (129, 123, '0,1,2,122,123', '批量删除分布式存储配置表', 'extension:dfs:batch:delete', '4', 'xitongrizhi', 'extension/dfs/batch/delete', '/lzy-service-extensions/extension/dfs/batch/delete', 2, 1, 1,  NULL,  1, '批量删除分布式存储配置表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (130, 123, '0,1,2,122,123', '修改分布式存储状态', 'extension:dfs:status', '4', 'xitongrizhi', 'extension/dfs/status', '/lzy-service-extensions/extension/dfs/status/{dfsId}/{dfsStatus}', 2, 1, 1, NULL,  1, '修改存储状态', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (131, 123, '0,1,2,122,123', '修改分布式存储默认', 'extension:dfs:default', '4', 'xitongrizhi', 'extension/dfs/default', '/lzy-service-extensions/extension/dfs/default/{dfsId}', 2, 1, 1,  NULL, 1,  '修改存储默认', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (132, 124, '0,1,2,122,124', '获取分布式存储文件记录表列表', 'extension:dfs:file:list', '4', 'xitongrizhi', 'extension/dfs/file/list', '/lzy-service-extensions/extension/dfs/file/list', 2, 1, 1,  NULL, 1,  '获取分布式存储文件记录表列表数据', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (133, 124, '0,1,2,122,124', '添加分布式存储文件记录表', 'extension:dfs:file:create', '4', 'xitongrizhi', 'extension/dfs/file/create', '/lzy-service-extensions/extension/dfs/file/create', 2, 1, 1,  NULL, 1, '添加分布式存储文件记录表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (134, 124, '0,1,2,122,124', '更新分布式存储文件记录表', 'extension:dfs:file:update', '4', 'xitongrizhi', 'extension/dfs/file/update', '/lzy-service-extensions/extension/dfs/file/update', 2, 1, 1,  NULL,  1, '更新分布式存储文件记录表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (135, 124, '0,1,2,122,124', '删除分布式存储文件记录表', 'extension:dfs:file:delete', '4', 'xitongrizhi', 'extension/dfs/file/delete', '/lzy-service-extensions/extension/dfs/file/delete/{dfsFileId}', 2, 1, 1,  NULL,  1, '删除分布式存储文件记录表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (136, 124, '0,1,2,122,124', '批量删除分布式存储文件记录表', 'extension:dfs:file:batch:delete', '4', 'xitongrizhi', 'extension/dfs/file/batch/delete', '/lzy-service-extensions/extension/dfs/file/batch/delete', 2, 1, 1,  NULL, 1, '批量删除分布式存储文件记录表', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (137, 124, '0,1,2,122,124', '获取分布式存储文件链接', 'extension:dfs:file:url', '4', 'xitongrizhi', 'extension/dfs/file/url', '/lzy-service-extensions/extension/get/file/url', 2, 1, 1,  NULL,  1, '获取分布式存储文件链接', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);
INSERT INTO `t_sys_resource` VALUES (138, 124, '0,1,2,122,124', '下载分布式存储文件', 'extension:dfs:file:download', '4', 'xitongrizhi', 'extension/dfs/file/download', '/lzy-service-extensions/extension/get/file/download', 2, 1, 1, NULL,  1, '下载分布式存储文件', '2018-10-27 17:40:14', 1, '2018-11-02 14:53:38', 1, 0);

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
INSERT INTO `t_sys_role_resource`
VALUES (74, 2, 71, '2023-01-05 19:24:13', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (90, 2, 72, '2023-04-24 17:53:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (91, 2, 73, '2023-04-24 17:53:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (92, 2, 7, '2023-05-08 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (93, 2, 48, '2023-05-08 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (94, 2, 49, '2023-05-08 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (95, 2, 50, '2023-05-08 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (96, 2, 51, '2023-05-08 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (97, 2, 52, '2023-05-08 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (98, 2, 53, '2023-05-08 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (99, 2, 74, '2023-05-10 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (100, 2, 75, '2023-05-10 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (101, 2, 111, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (102, 2, 112, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (103, 2, 113, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (104, 2, 114, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (105, 2, 115, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (106, 2, 116, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (107, 2, 118, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (108, 2, 119, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (109, 2, 120, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (110, 2, 121, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (111, 2, 92, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (112, 2, 93, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (113, 2, 94, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (114, 2, 77, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (115, 2, 78, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (116, 2, 79, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (117, 2, 80, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (118, 2, 82, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (119, 2, 83, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (120, 2, 84, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (121, 2, 85, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (122, 2, 95, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (123, 2, 96, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (124, 2, 97, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (130, 2, 101, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (131, 2, 76, '2023-05-12 14:17:49', 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (132, 2, 122, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (133, 2, 123, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (134, 2, 124, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (135, 2, 125, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (136, 2, 126, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (137, 2, 127, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (138, 2, 128, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (139, 2, 129, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (140, 2, 130, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (141, 2, 131, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (142, 2, 132, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (143, 2, 133, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (144, 2, 134, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (145, 2, 135, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (146, 2, 136, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (147, 2, 137, now(), 1, NULL, NULL, 0);
INSERT INTO `t_sys_role_resource`
VALUES (148, 2, 138, now(), 1, NULL, NULL, 0);
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
VALUES (2, 'admin', '后台管理员', '李佐洋', '1', '939822143@qq.com', '17752846201', '{bcrypt}$2a$10$VXoya5iK02RWpdFZPQyJKuAjDuuAnjxuLgQ2cC3zqZWP36svy/3e2',
 1, 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png', NULL, '', '', '', '', '后台管理员账号', '2023-05-24 09:34:33', 1, NULL, NULL, 0);


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

INSERT INTO `t_sys_dict` VALUES (1, 0, '0', '分布式存储分类', 'DFS_TYPE', 1, 1, '分布式存储分类', '2021-05-06 11:05:26', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (2, 1, '0,1', 'MinIO', 'MINIO', 1, 1, '', '2021-05-06 11:05:43', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (3, 1, '0,1', '七牛云Kodo', 'QINIUYUN_KODO', 2, 1, '', '2021-05-06 11:06:29', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (4, 1, '0,1', '阿里云OSS', 'ALIYUN_OSS', 3, 1, '', '2021-05-06 11:06:51', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (6,  0, '0', '数据权限类型', 'DATA_PERMISSION_TYPE', 2, 1, '数据权限类型', '2023-05-11 15:12:03', 1, '2023-05-11 15:12:11', 1, 0);
INSERT INTO `t_sys_dict` VALUES (7,  6, '0,6', '本人数据', '1', 1, 1, '', '2023-05-11 15:13:14', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (8,  6, '0,6', '本机构数据', '2', 2, 1, '', '2023-05-11 15:13:42', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (9,  6, '0,6', '本机构及子机构数据', '3', 3, 1, '', '2023-05-11 15:14:10', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (10,  6, '0,6', '所有机构数据', '4', 4, 1, '', '2023-05-11 15:14:40', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (11, 6, '0,6', '自定义', '5', 5, 1, '', '2023-05-11 15:14:40', 1, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (12, 0, '0', '短信渠道', 'SMS_CHANNEL', 3, 1, '', '2023-05-26 13:57:19', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (13, 12, '0,12', '阿里云', 'ALIYUN', 1, 1, '', '2023-05-26 13:57:30', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (14, 0, '0', '短信类型', 'SMS_TYPE', 4, 1, '短信发送类型', '2023-06-01 14:14:21', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (15, 14, '0,14', '验证码', '1', 1, 1, '', '2023-06-01 14:15:08', 2, '2023-06-01 14:23:15', 2, 0);
INSERT INTO `t_sys_dict` VALUES (16, 14, '0,14', '通知短信', '2', 2, 1, '', '2023-06-01 14:15:36', 2, '2023-06-01 14:23:11', 2, 0);
INSERT INTO `t_sys_dict` VALUES (17, 0, '0', '时间单位', 'TIME_UNIT', 5, 1, '', '2023-06-01 14:44:09', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (18, 17, '0,17', '天', 'DAYS', 1, 1, '', '2023-06-01 14:44:34', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (19, 17, '0,17', '小时', 'HOURS', 2, 1, '', '2023-06-01 14:44:45', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (20, 17, '0,17', '分钟', 'MINUTES', 3, 1, '', '2023-06-01 14:44:57', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (21, 17, '0,17', '秒', 'SECONDS', 4, 1, '', '2023-06-01 14:45:12', 2, NULL, NULL, 0);
INSERT INTO `t_sys_dict` VALUES (22, 17, '0,17', '毫秒', 'MILLISECONDS', 5, 1, '', '2023-06-01 14:45:22', 2, NULL, NULL, 0);

-- ----------------------------
-- Table structure for t_sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_organization`;
CREATE TABLE `t_sys_organization`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父组织id',
  `ancestors` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有上级组织id的集合，便于机构查找',
  `organization_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织类型：1：事业部  2：机构  3：盐城',
  `organization_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织名称',
  `organization_key` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织编码',
  `organization_icon` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织图标',
  `organization_level` int(11) NULL DEFAULT NULL COMMENT '组织级别（排序）',
  `organization_status` tinyint(2) NULL DEFAULT 1 COMMENT '1有效，0禁用',
  `province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市',
  `area` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '区',
  `street` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '街道',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `INDEX_ORG_NAME`(`organization_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '组织表' ROW_FORMAT = DYNAMIC;

INSERT INTO `t_sys_organization` VALUES (1, 0, '0', '1', '有限公司', '0001', '11', 1, 1, '140000', '140400', '140421', '111111', '有限公司', now(), 1, now(), 1, 0);

-- ----------------------------
-- Table structure for t_sys_organization_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_organization_role`;
CREATE TABLE `t_sys_organization_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `organization_id` bigint(20) NOT NULL COMMENT '组织机构id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '可以给组织权限，在该组织下的所有用户都有此权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_sys_organization_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_organization_user`;
CREATE TABLE `t_sys_organization_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `organization_id` bigint(20) NOT NULL COMMENT '机构id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '可以给用户权限' ROW_FORMAT = DYNAMIC;

INSERT INTO `t_sys_organization_user` VALUES (1, 1, 2, NULL, NULL, NULL, NULL, 0);
-- ----------------------------
-- Table structure for t_sys_data_permission_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_data_permission_user`;
CREATE TABLE `t_sys_data_permission_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `organization_id` bigint(20) NOT NULL COMMENT '机构id',
  `status` tinyint(2) NULL DEFAULT 1 COMMENT '状态 0禁用，1 启用,',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据权限多部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_sys_data_permission_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_data_permission_rule`;
CREATE TABLE `t_sys_data_permission_rule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `resource_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '功能权限id',
  `data_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限名称',
  `data_mapper_function` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限对应的mapper方法全路径',
  `data_table_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '需要做数据权限主表',
  `data_table_alias` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '需要做数据权限表的别名',
  `data_column_exclude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限需要排除的字段',
  `data_column_include` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限需要保留的字段',
  `inner_table_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限表,默认t_sys_organization',
  `inner_table_alias` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限表的别名,默认organization',
  `data_permission_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '数据权限类型:1只能查看本人 2只能查看本部门 3只能查看本部门及子部门 4可以查看所有数据',
  `custom_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自定义数据权限（增加 where条件）',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态 0禁用，1 启用,',
  `comments` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据权限配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for t_sys_role_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_data_permission`;
CREATE TABLE `t_sys_role_data_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `data_permission_id` bigint(20) NOT NULL COMMENT '资源id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `del_flag` tinyint(2) NOT NULL DEFAULT 0 COMMENT '1:删除 0:不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和数据权限关联表' ROW_FORMAT = DYNAMIC;
