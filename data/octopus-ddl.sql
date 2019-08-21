

/*Table structure for table `cfg_dataset` */



CREATE TABLE `cfg_dataset` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '数据集id,ID',
  `table_name` varchar(128) NOT NULL COMMENT '数据表',
  `q_list_xml` text COMMENT '查询列表数据集',
  `q_cond_xml` text COMMENT '查询条件数据集',
  `e_create_xml` text COMMENT '新增数据集',
  `e_update_xml` text COMMENT '修改数据集',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `db_id` bigint(12) NOT NULL COMMENT '数据库id,ID',
  `table_desc` varchar(128) DEFAULT NULL COMMENT '数据表中文名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='数据集';



/*Table structure for table `cfg_db` */



CREATE TABLE `cfg_db` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '数据库id,ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '数据库类型,类型',
  `host` varchar(256) NOT NULL COMMENT '数据库主机,HOST',
  `port` int(11) NOT NULL COMMENT '数据库端口,PROT',
  `database` varchar(64) NOT NULL COMMENT '数据库编码,编码',
  `username` varchar(256) NOT NULL COMMENT '账号',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='数据库';



/*Table structure for table `cfg_relat` */



CREATE TABLE `cfg_relat` (
  `id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '数据关系id,ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '数据关系类型,类型',
  `source_table` varchar(128) NOT NULL COMMENT '来源表',
  `target_table` varchar(128) NOT NULL COMMENT '目标表',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `description` varchar(128) DEFAULT NULL COMMENT '数据关系描述,描述',
  `db_id` bigint(12) NOT NULL COMMENT '数据库id,ID',
  `source_field` varchar(128) NOT NULL COMMENT '来源字段',
  `target_field` varchar(128) NOT NULL COMMENT '目标字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据关系';



/*Table structure for table `dictionary` */



CREATE TABLE `dictionary` (
  `dictionary_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `dictionary_name` varchar(32) DEFAULT NULL COMMENT '字典名称',
  `dictionary_code` varchar(64) DEFAULT NULL COMMENT '字典编码',
  `dictionary_desc` varchar(128) DEFAULT NULL COMMENT '字典描述',
  `ext1` varchar(128) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(128) DEFAULT NULL COMMENT '扩展字段2',
  `op_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_op_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB AUTO_INCREMENT=174 DEFAULT CHARSET=utf8 COMMENT='字典';



/*Table structure for table `dictionary_item` */



CREATE TABLE `dictionary_item` (
  `dictionary_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典项ID',
  `value` varchar(32) DEFAULT NULL COMMENT '字典项值',
  `text` varchar(32) DEFAULT NULL COMMENT '字典项文本',
  `desc` varchar(128) DEFAULT NULL COMMENT '字典项描述',
  `is_default` int(2) DEFAULT NULL COMMENT '是否默认',
  `pri` decimal(4,2) DEFAULT NULL COMMENT '优先级',
  `ext1` varchar(128) DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(128) DEFAULT NULL COMMENT '扩展字段2',
  `dictionary_id` bigint(20) DEFAULT NULL COMMENT '字典ID',
  `dictionary_code` varchar(32) DEFAULT NULL,
  `op_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_op_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`dictionary_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='字典项';



/*Table structure for table `menu` */



CREATE TABLE `menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_code` varchar(64) DEFAULT NULL COMMENT '菜单编码',
  `menu_name` varchar(128) DEFAULT NULL COMMENT '菜单名称',
  `menu_desc` varchar(128) DEFAULT NULL COMMENT '菜单描述',
  `menu_level` int(2) DEFAULT NULL COMMENT '菜单级别',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `url` varchar(128) NOT NULL COMMENT '地址',
  `parent_menu_id` bigint(20) NOT NULL COMMENT '父级菜单ID',
  `pri` decimal(4,2) DEFAULT NULL COMMENT '优先级',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='菜单';



/*Table structure for table `organize` */



CREATE TABLE `organize` (
  `organize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '组织id',
  `organize_code` varchar(64) NOT NULL COMMENT '组织编码',
  `organize_name` varchar(128) NOT NULL COMMENT '组织名称',
  `organize_type` tinyint(4) DEFAULT NULL COMMENT '组织类型',
  `organize_level` tinyint(4) DEFAULT NULL COMMENT '组织级别',
  `parent_organize_id` bigint(12) DEFAULT NULL COMMENT '上级组织id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`organize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='组织';



/*Table structure for table `role` */



CREATE TABLE `role` (
  `role_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_code` varchar(64) NOT NULL COMMENT '角色编码',
  `role_name` varchar(128) NOT NULL COMMENT '角色名称',
  `role_type` tinyint(4) DEFAULT NULL COMMENT '角色类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色';



/*Table structure for table `role_authorize` */



CREATE TABLE `role_authorize` (
  `role_authorize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '角色授权id',
  `role_authorize_type` tinyint(4) DEFAULT NULL COMMENT '角色授权类型',
  `role_id` bigint(12) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_authorize_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色授权';



/*Table structure for table `user` */



CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `account` varchar(64) DEFAULT NULL COMMENT '用户账号',
  `password` varchar(128) DEFAULT NULL COMMENT '用户密码',
  `gender` int(2) DEFAULT NULL COMMENT '性别',
  `mobile` varchar(6) DEFAULT NULL COMMENT '手机号',
  `email` int(2) DEFAULT NULL COMMENT '邮箱',
  `addr` int(2) DEFAULT NULL COMMENT '地址',
  `avatar` varchar(512) DEFAULT NULL COMMENT '头像',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `organize_id` bigint(20) NOT NULL COMMENT '组织ID',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';



/*Table structure for table `user_authorize` */



CREATE TABLE `user_authorize` (
  `user_authorize_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '用户授权id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `organize_id` bigint(12) NOT NULL COMMENT '组织id',
  `role_id` bigint(12) NOT NULL COMMENT '角色id',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint(12) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(12) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_authorize_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户授权';


