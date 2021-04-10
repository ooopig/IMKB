SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `telephone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '住宅电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系地址',
  `enabled` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否启用',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_face` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg' COMMENT '用户头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `contribution` int(11) default 0 COMMENT '贡献度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1001, 'admin', '13812361398', '13800000000', 'xxxxxxx', '1', 'admin', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识管理员',0);
INSERT INTO `user` VALUES (1002, 'ordinary', '14588110811', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);
INSERT INTO `user` VALUES (1003, 'worker', '18875971675', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1004, 'expert', '14588110811', '13800000000', 'xxxxxxx', '1', 'expert', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识专家',0);

INSERT INTO `user` VALUES (1006, 'ordinary01', '13812361398', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);
INSERT INTO `user` VALUES (1007, 'ordinary02', '14588110811', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);
INSERT INTO `user` VALUES (1008, 'ordinary03', '18875971675', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);
INSERT INTO `user` VALUES (1009, 'ordinary04', '14588110811', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);
INSERT INTO `user` VALUES (1010, 'ordinary05', '13812361398', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);
INSERT INTO `user` VALUES (1011, 'ordinary06', '14588110811', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);
INSERT INTO `user` VALUES (1012, 'ordinary07', '18875971675', '13800000000', 'xxxxxxx', '1', 'ordinary', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '普通用户',0);

INSERT INTO `user` VALUES (1013, 'worker01', '14588110811', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1014, 'worker02', '13812361398', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1015, 'worker03', '14588110811', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1016, 'worker04', '18875971675', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1017, 'worker05', '14588110811', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1018, 'worker06', '13812361398', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1019, 'worker07', '14588110811', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1020, 'worker08', '18875971675', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1021, 'worker09', '14588110811', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);
INSERT INTO `user` VALUES (1022, 'worker10', '18875971675', '13800000000', 'xxxxxxx', '1', 'worker', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识贡献者',0);

INSERT INTO `user` VALUES (1023, 'expert01', '14588110811', '13800000000', 'xxxxxxx', '1', 'expert', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识专家',0);
INSERT INTO `user` VALUES (1024, 'expert02', '14588110811', '13800000000', 'xxxxxxx', '1', 'expert', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识专家',0);
INSERT INTO `user` VALUES (1025, 'expert03', '14588110811', '13800000000', 'xxxxxxx', '1', 'expert', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识专家',0);
INSERT INTO `user` VALUES (1026, 'expert04', '14588110811', '13800000000', 'xxxxxxx', '1', 'expert', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识专家',0);
INSERT INTO `user` VALUES (1027, 'expert05', '14588110811', '13800000000', 'xxxxxxx', '1', 'expert', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识专家',0);
INSERT INTO `user` VALUES (1028, 'expert06', '14588110811', '13800000000', 'xxxxxxx', '1', 'expert', '$2a$10$cdBBpkAeGZJepglxTy7fJeVmNNcWutBC6eFt7/I0HxXMqqPxT4OP6', 'https://img2.woyaogexing.com/2020/10/14/bef96747cb564b84866ccfa1204db395!400x400.jpeg', '知识专家',0);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `name_zh` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (900, 'ROLE_admin', '系统管理员');
INSERT INTO `role` VALUES (901, 'ROLE_ordinary', '普通用户');
INSERT INTO `role` VALUES (902, 'ROLE_worker', '知识贡献者');
INSERT INTO `role` VALUES (903, 'ROLE_expert', '知识专家');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `user_id` int(50) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(50) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1001, 900);
INSERT INTO `user_role` VALUES (2, 1002, 901);
INSERT INTO `user_role` VALUES (3, 1003, 902);
INSERT INTO `user_role` VALUES (4, 1004, 903);

INSERT INTO `user_role` VALUES (6, 1006, 901);
INSERT INTO `user_role` VALUES (7, 1007, 901);
INSERT INTO `user_role` VALUES (8, 1008, 901);
INSERT INTO `user_role` VALUES (9, 1009, 901);
INSERT INTO `user_role` VALUES (10, 1010, 901);
INSERT INTO `user_role` VALUES (11, 1011, 901);
INSERT INTO `user_role` VALUES (12, 1012, 901);

INSERT INTO `user_role` VALUES (13, 1013, 902);
INSERT INTO `user_role` VALUES (14, 1014, 902);
INSERT INTO `user_role` VALUES (15, 1015, 902);
INSERT INTO `user_role` VALUES (16, 1016, 902);
INSERT INTO `user_role` VALUES (17, 1017, 902);
INSERT INTO `user_role` VALUES (18, 1018, 902);
INSERT INTO `user_role` VALUES (19, 1019, 902);
INSERT INTO `user_role` VALUES (20, 1020, 902);
INSERT INTO `user_role` VALUES (21, 1021, 902);
INSERT INTO `user_role` VALUES (22, 1022, 902);

INSERT INTO `user_role` VALUES (23, 1023, 903);
INSERT INTO `user_role` VALUES (24, 1024, 903);
INSERT INTO `user_role` VALUES (25, 1025, 903);
INSERT INTO `user_role` VALUES (26, 1026, 903);
INSERT INTO `user_role` VALUES (27, 1027, 903);
INSERT INTO `user_role` VALUES (28, 1028, 903);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求url',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `iconCls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `keep_alive` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否保持激活',
  `require_auth` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否要求权限',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父id',
  `enabled` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (100, NULL, NULL, NULL, '所有', NULL, NULL, NULL, NULL, '1');
INSERT INTO `menu` VALUES (200, '/', '/home', 'Home', '公共知识库', 'fa fa-book', NULL, '1', 100, '1');
INSERT INTO `menu` VALUES (300, '/', '/home', 'Home', '个人信息', 'fa fa-user', NULL, '1', 100, '1');
INSERT INTO `menu` VALUES (400, '/', '/home', 'Home', '专家系统', 'fa fa-graduation-cap', NULL, '1', 100, '1');
INSERT INTO `menu` VALUES (500, '/', '/home', 'Home', '系统管理', 'fa fa-windows', NULL, '1', 100, '1');

INSERT INTO `menu` VALUES (201, '/kg/graph/**', '/kg/basic', 'KbBasic', '知识库概览', '', NULL, '1', 200, '1');
INSERT INTO `menu` VALUES (202, '/kg/graph/**', '/kg/graph', 'KbKG', '知识图谱', '', NULL, '1', 200, '1');
INSERT INTO `menu` VALUES (203, '/kg/import/**', '/kg/import', 'KbImport', '知识导入', '', NULL, '1', 200, '1');
INSERT INTO `menu` VALUES (204, '/kg/search/**', '/kg/search', 'KbSearch', '知识检索', '', NULL, '1', 200, '1');

INSERT INTO `menu` VALUES (301, '/user/knowledge/**', '/user/knowledge', 'UserKnowledge', '我的知识', '', NULL, '1', 300, '1');
INSERT INTO `menu` VALUES (302, '/user/info/**', '/user/info', 'UserInfo', '我的信息', '', NULL, '1', 300, '1');

INSERT INTO `menu` VALUES (401, '/expert/evaluate/**', '/expert/evaluate', 'KnowledgeEvaluate', '知识评审', '', NULL, '1', 400, '1');
INSERT INTO `menu` VALUES (402, '/expert/extract/**', '/expert/extract', 'KnowledgeExtract', '知识抽取', '', NULL, '1', 400, '1');
INSERT INTO `menu` VALUES (403, '/expert/inference/**', '/expert/inference', 'KnowledgeInference', '知识推理', '', NULL, '1', 400, '1');
INSERT INTO `menu` VALUES (404, '/expert/standard/**', '/expert/standard', 'KnowledgeStandard', '知识规范', '', NULL, '1', 400, '1');

INSERT INTO `menu` VALUES (501, '/manage/user/**', '/manage/user/', 'ManageUser', '用户管理', '', NULL, '1', 500, '1');
INSERT INTO `menu` VALUES (502, '/manage/kb/**', '/manage/kb/', 'ManageKb', '知识库管理', '', NULL, '1', 500, '1');
INSERT INTO `menu` VALUES (503, '/manage/sys/**', '/manage/sys/', 'ManageSys', '系统管理', '', NULL, '1', 500, '1');

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (1, 201, 900);
INSERT INTO `menu_role` VALUES (2, 201, 901);
INSERT INTO `menu_role` VALUES (3, 201, 902);
INSERT INTO `menu_role` VALUES (4, 201, 903);

INSERT INTO `menu_role` VALUES (5, 202, 900);
INSERT INTO `menu_role` VALUES (6, 202, 901);
INSERT INTO `menu_role` VALUES (7, 202, 902);
INSERT INTO `menu_role` VALUES (8, 202, 903);


INSERT INTO `menu_role` VALUES (9, 301, 900);
INSERT INTO `menu_role` VALUES (10, 301, 901);
INSERT INTO `menu_role` VALUES (11, 301, 902);
INSERT INTO `menu_role` VALUES (12, 301, 903);

INSERT INTO `menu_role` VALUES (13, 302, 900);
INSERT INTO `menu_role` VALUES (14, 302, 901);
INSERT INTO `menu_role` VALUES (15, 302, 902);
INSERT INTO `menu_role` VALUES (16, 302, 903);

INSERT INTO `menu_role` VALUES (17, 401, 900);
INSERT INTO `menu_role` VALUES (18, 401, 903);
INSERT INTO `menu_role` VALUES (19, 402, 900);
INSERT INTO `menu_role` VALUES (20, 402, 903);
INSERT INTO `menu_role` VALUES (21, 403, 900);
INSERT INTO `menu_role` VALUES (22, 403, 903);
INSERT INTO `menu_role` VALUES (23, 404, 900);
INSERT INTO `menu_role` VALUES (24, 404, 903);

INSERT INTO `menu_role` VALUES (25, 501, 900);
INSERT INTO `menu_role` VALUES (26, 502, 900);
INSERT INTO `menu_role` VALUES (27, 503, 900);

INSERT INTO `menu_role` VALUES (28, 203, 900);
INSERT INTO `menu_role` VALUES (29, 203, 902);
INSERT INTO `menu_role` VALUES (30, 203, 903);

INSERT INTO `menu_role` VALUES (31, 204, 900);
INSERT INTO `menu_role` VALUES (32, 204, 901);
INSERT INTO `menu_role` VALUES (33, 204, 902);
INSERT INTO `menu_role` VALUES (34, 204, 903);


-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '节点主键',
  `name` varchar (255) default null COMMENT '节点名字',
  `level` int(11) DEFAULT 0 COMMENT '等级',
  `status` int(11) DEFAULT 1 COMMENT '状态',
  `properties` varchar(10000) default null COMMENT '节点属性',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(100) DEFAULT NULL COMMENT '创建时间',
  `modify_user` int(11) DEFAULT NULL COMMENT '修改人',
  `modify_time` varchar(100) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for relation
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系主键',
  `name` varchar (255) default null COMMENT '关系名字',
  `source_id` int(11) not null COMMENT '头节点',
  `target_id` int(11) not null COMMENT '尾节点',
  `weight` int(11) DEFAULT 1 COMMENT '权重',
 `properties` varchar(10000) default null COMMENT '关系属性',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(100) DEFAULT NULL COMMENT '创建时间',
  `modify_user` int(11) DEFAULT NULL COMMENT '修改人',
  `modify_time` varchar(100) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for file 文件
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件主键',
  `name` varchar (255) default null COMMENT '文件名字',
  `type` varchar (255) default null COMMENT '文件类型',
  `url` varchar (255) default null COMMENT '文件地址',
  `node_id` int (11) default null COMMENT '节点id',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(100) DEFAULT NULL COMMENT '创建时间',
  `down_times` int(11) default null COMMENT '下载次数'
  PRIMARY KEY (`id`) USING BTREE,
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for search 搜索
-- ----------------------------
DROP TABLE IF EXISTS `search`;
CREATE TABLE `search` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) default null COMMENT '用户id'
  `content` varchar (255) default null COMMENT '搜索内容',
  `search_times` int(11) default null COMMENT '搜索次数',
  PRIMARY KEY (`id`) USING BTREE,
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for collection 收藏
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) default null COMMENT '用户id'
  `node_id` int(11) default null COMMENT '节点id'
  `file_id` int(11) default null COMMENT '文件id'
  PRIMARY KEY (`id`) USING BTREE,
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


