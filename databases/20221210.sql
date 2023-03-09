drop database if exists `tang-vue`;

create database `tang-vue` default character set utf8mb4 collate utf8mb4_unicode_ci;

set names utf8mb4;

use `tang-vue`;

-- -----------------------------
-- 部门表
-- -----------------------------
drop table if exists sys_dept;
create table sys_dept (
    dept_id      bigint(20)    not null auto_increment  comment '部门id',
    parent_id    bigint(20)    default 0                comment '父部门id',
    ancestors    varchar(128)  default ''               comment '祖级列表',
    dept_name    varchar(32)   default ''               comment '部门名称',
    sort         int(4)        default 0                comment '显示顺序',
    status       char(1)       default '0'              comment '部门状态{0=正常, 1=停用}',
    del_flag     char(1)       default '0'              comment '删除标志{0=正常, 1=删除}',
    create_by    varchar(64)   default ''               comment '创建者',
    create_time  datetime                               comment '创建时间',
    update_by    varchar(64)   default ''               comment '更新者',
    update_time  datetime                               comment '更新时间',
    remark       varchar(500)  default ''               comment '备注',
    primary key (dept_id)
) engine = InnoDB auto_increment = 100 comment = '部门表';

insert into sys_dept values (1, 0, '0',     '猫猫科技', '1', '0', '0', 'admin', sysdate(), '', null, '猫猫科技');
insert into sys_dept values (2, 1, '0,1',   '研发部门', '1', '0', '0', 'admin', sysdate(), '', null, '研发部门');
insert into sys_dept values (3, 1, '0,1',   '财务部门', '2', '0', '0', 'admin', sysdate(), '', null, '财务部门');
insert into sys_dept values (4, 2, '0,1,2', '研发一组', '1', '0', '0', 'admin', sysdate(), '', null, '研发一组');
insert into sys_dept values (5, 2, '0,1,2', '研发二组', '2', '0', '0', 'admin', sysdate(), '', null, '研发二组');


-- -----------------------------
-- 用户表
-- -----------------------------
drop table if exists sys_user;
create table sys_user (
    user_id      bigint(20)    not null auto_increment  comment '用户ID',
    dept_id      bigint(20)    default null             comment '部门ID',
    username     varchar(32)   not null                 comment '用户账号',
    nickname     varchar(32)   default ''               comment '昵称',
    email        varchar(64)   default ''               comment '邮箱',
    phone        varchar(11)   default ''               comment '手机号码',
    gender       char(2)       default '0'              comment '性别{0=保密, 1=男, 2=女}',
    avatar       varchar(128)  default ''               comment '头像地址',
    password     varchar(128)  default ''               comment '密码',
    status       char(1)       default '0'              comment '帐号状态{0=正常, 1=停用}',
    del_flag     char(1)       default '0'              comment '删除标志{0=正常, 1=删除}',
    login_ip     varchar(128)  default ''               comment '最后登录IP',
    login_date   datetime                               comment '最后登录时间',
    create_by    varchar(64)   default ''               comment '创建者',
    create_time  datetime                               comment '创建时间',
    update_by    varchar(64)   default ''               comment '更新者',
    update_time  datetime                               comment '更新时间',
    remark       varchar(500)  default ''               comment '备注',
    primary key (user_id)
) engine = InnoDB auto_increment = 100 comment = '用户表';

insert into sys_user values (1, 4, 'admin', '糖猫猫', 'admin@163.com', '16888888888', '保密', '', '$2a$10$QclgMfXBXPoXX6BEJxNyz.aARYqwyOwCnikxWiRK0v4c8zR9/kuQu', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '超级管理员');
insert into sys_user values (2, 4, 'tang',  '糖糖',   'tang@163.com',  '16888888888', '保密', '', '$2a$10$QclgMfXBXPoXX6BEJxNyz.aARYqwyOwCnikxWiRK0v4c8zR9/kuQu', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');
insert into sys_user values (3, 4, 'miao',  '猫猫',   'miao@163.com',  '16888888888', '保密', '', '$2a$10$QclgMfXBXPoXX6BEJxNyz.aARYqwyOwCnikxWiRK0v4c8zR9/kuQu', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '游客用户');


-- -----------------------------
-- 角色表
-- -----------------------------
drop table if exists sys_role;
create table sys_role (
    role_id      bigint(20)    not null auto_increment  comment '角色ID',
    role_name    varchar(32)   not null                 comment '角色名称',
    role_key     varchar(64)   not null                 comment '角色权限字符串',
    data_scope   char(1)       default '0'              comment '数据范围{0=全部}',
    sort         int(4)        default 0                comment '显示顺序',
    status       char(1)       default '0'              comment '角色状态{0=正常, 1=停用}',
    del_flag     char(1)       default '0'              comment '删除标志{0=正常, 1=删除}',
    create_by    varchar(64)   default ''               comment '创建者',
    create_time  datetime                               comment '创建时间',
    update_by    varchar(64)   default ''               comment '更新者',
    update_time  datetime                               comment '更新时间',
    remark       varchar(500)  default ''               comment '备注',
    primary key (role_id)
) engine = InnoDB auto_increment = 100 comment = '角色表';

insert into sys_role values (1, '超级管理员', 'admin', '0', '1', '0', '0', 'admin', sysdate(), '', null, '超级管理员');
insert into sys_role values (2, '普通用户',   'tang',  '0', '2', '0', '0', 'admin', sysdate(), '', null, '普通用户');
insert into sys_role values (3, '游客用户',   'miao',  '0', '3', '0', '0', 'admin', sysdate(), '', null, '游客用户');


-- -----------------------------
-- 菜单表
-- -----------------------------
drop table if exists sys_menu;
create table sys_menu (
    menu_id      bigint(20)    not null auto_increment  comment '菜单ID',
    parent_id    bigint(20)    default 0                comment '父菜单ID',
    ancestors    varchar(128)  default ''               comment '祖级列表',
    menu_name    varchar(32)   default ''               comment '菜单名称',
    path         varchar(255)  default ''               comment '路由地址',
    component    varchar(255)  default ''               comment '组件路径',
    permission   varchar(128)  default ''               comment '权限标识',
    icon         varchar(128)  default ''               comment '菜单图标',
    menu_type    char(1)       default ''               comment '菜单类型{D=目录, M=菜单, B=按钮}',
    visible      char(1)       default '0'              comment '菜单状态{0=显示, 1=隐藏}',
    sort         int(4)        default 0                comment '显示顺序',
    status       char(1)       default '0'              comment '菜单状态{0=正常, 1=停用}',
    del_flag     char(1)       default '0'              comment '删除标志{0=正常, 1=删除}',
    create_by    varchar(64)   default ''               comment '创建者',
    create_time  datetime                               comment '创建时间',
    update_by    varchar(64)   default ''               comment '更新者',
    update_time  datetime                               comment '更新时间',
    remark       varchar(500)  default ''               comment '备注',
    primary key (menu_id)
) engine = InnoDB auto_increment = 100 comment = '菜单权限表';

-- 目录
insert into sys_menu values (1, 0, '0', '系统管理', 'system',  '', '', '系统管理', 'D', '0', 1, '0', '0', 'admin', sysdate(), '', null, '系统管理目录');
insert into sys_menu values (2, 0, '0', '系统监控', 'monitor', '', '', '系统监控', 'D', '0', 2, '0', '0', 'admin', sysdate(), '', null, '系统监控目录');

-- 菜单
insert into sys_menu values (11, 1, '0,1', '用户管理', 'user',   'system/user/index',    'system:user:menu',    '用户管理', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values (12, 1, '0,1', '部门管理', 'dept',   'system/dept/index',    'system:dept:menu',    '部门管理', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '部门管理菜单');
insert into sys_menu values (13, 1, '0,1', '角色管理', 'role',   'system/role/index',    'system:role:menu',    '角色管理', 'M', '0', 3, '0', '0', 'admin', sysdate(), '', null, '角色管理菜单');
insert into sys_menu values (14, 1, '0,1', '菜单管理', 'menu',   'system/menu/index',    'system:menu:menu',    '菜单管理', 'M', '0', 4, '0', '0', 'admin', sysdate(), '', null, '菜单管理菜单');
insert into sys_menu values (15, 1, '0,1', '字典管理', 'dict',   'system/dict/index',    'system:dict:menu',    '字典管理', 'M', '0', 5, '0', '0', 'admin', sysdate(), '', null, '字典管理菜单');
insert into sys_menu values (16, 2, '0,2', '在线用户', 'online', 'monitor/online/index', 'monitor:online:menu', '在线用户', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '在线用户菜单');

-- 用户管理按钮
insert into sys_menu values (101, 11, '0,1,11', '用户查询', '', '', 'system:user:list',   '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '用户查询按钮');
insert into sys_menu values (102, 11, '0,1,11', '用户新增', '', '', 'system:user:add',    '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '用户新增按钮');
insert into sys_menu values (103, 11, '0,1,11', '用户修改', '', '', 'system:user:edit',   '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '用户修改按钮');
insert into sys_menu values (104, 11, '0,1,11', '用户删除', '', '', 'system:user:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '用户删除按钮');

-- 部门管理按钮
insert into sys_menu values (105, 12, '0,1,12', '部门查询', '', '', 'system:dept:list',   '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '部门查询按钮');
insert into sys_menu values (106, 12, '0,1,12', '部门添加', '', '', 'system:dept:add',    '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '部门添加按钮');
insert into sys_menu values (107, 12, '0,1,12', '部门修改', '', '', 'system:dept:edit',   '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '部门修改按钮');
insert into sys_menu values (108, 12, '0,1,12', '部门删除', '', '', 'system:dept:edlete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '部门删除按钮');

-- 角色管理按钮
insert into sys_menu values (109, 13, '0,1,13', '角色查询', '', '', 'system:role:list',   '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '角色查询按钮');
insert into sys_menu values (110, 13, '0,1,13', '角色添加', '', '', 'system:role:add',    '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '角色添加按钮');
insert into sys_menu values (111, 13, '0,1,13', '角色修改', '', '', 'system:role:edit',   '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '角色修改按钮');
insert into sys_menu values (112, 13, '0,1,13', '角色删除', '', '', 'system:role:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '角色删除按钮');

-- 菜单管理按钮
insert into sys_menu values (113, 14, '0,1,14', '菜单查询', '', '', 'system:menu:list',   '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '菜单查询按钮');
insert into sys_menu values (114, 14, '0,1,14', '菜单添加', '', '', 'system:menu:add',    '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '菜单添加按钮');
insert into sys_menu values (115, 14, '0,1,14', '菜单修改', '', '', 'system:menu:edit',   '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '菜单修改按钮');
insert into sys_menu values (116, 14, '0,1,14', '菜单删除', '', '', 'system:menu:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '菜单删除按钮');

-- 菜单管理按钮
insert into sys_menu values (117, 15, '0,1,15', '字典查询', '', '', 'system:dict:list',   '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '字典查询按钮');
insert into sys_menu values (118, 15, '0,1,15', '字典添加', '', '', 'system:dict:add',    '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '字典添加按钮');
insert into sys_menu values (119, 15, '0,1,15', '字典修改', '', '', 'system:dict:edit',   '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '字典修改按钮');
insert into sys_menu values (120, 15, '0,1,15', '字典删除', '', '', 'system:dict:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '字典删除按钮');

-- 在线用户按钮
insert into sys_menu values (121, 16, '0,1,16', '在线用户查询', '', '', 'monitor:online:list',   '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '在线用户查询按钮');
insert into sys_menu values (122, 16, '0,1,16', '在线用户删除', '', '', 'monitor:online:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '在线用户删除按钮');


-- -----------------------------
-- 用户与角色关联表
-- -----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
    user_id  bigint(20) not null  comment '用户ID',
    role_id  bigint(20) not null  comment '角色ID',
    primary key(user_id, role_id)
) engine = InnoDB comment = '用户与角色关联表';

insert into sys_user_role values (1, 1);
insert into sys_user_role values (2, 2);
insert into sys_user_role values (3, 3);


-- -----------------------------
-- 角色与菜单关联表
-- -----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
    role_id  bigint(20) not null  comment '角色ID',
    menu_id  bigint(20) not null  comment '菜单ID',
    primary key(role_id, menu_id)
) engine = InnoDB comment = '角色与菜单关联表';

insert into sys_role_menu values (2, 1);
insert into sys_role_menu values (2, 11);
insert into sys_role_menu values (2, 12);
insert into sys_role_menu values (2, 13);
insert into sys_role_menu values (2, 14);
insert into sys_role_menu values (2, 101);
insert into sys_role_menu values (2, 102);
insert into sys_role_menu values (2, 103);
insert into sys_role_menu values (2, 104);
insert into sys_role_menu values (2, 105);
insert into sys_role_menu values (2, 106);
insert into sys_role_menu values (2, 107);
insert into sys_role_menu values (2, 108);
insert into sys_role_menu values (2, 109);
insert into sys_role_menu values (2, 110);
insert into sys_role_menu values (2, 111);
insert into sys_role_menu values (2, 112);
insert into sys_role_menu values (2, 113);
insert into sys_role_menu values (2, 114);
insert into sys_role_menu values (2, 115);
insert into sys_role_menu values (2, 116);
insert into sys_role_menu values (2, 117);
insert into sys_role_menu values (2, 118);
insert into sys_role_menu values (2, 119);
insert into sys_role_menu values (2, 120);
insert into sys_role_menu values (2, 121);
insert into sys_role_menu values (2, 122);
insert into sys_role_menu values (3, 1);
insert into sys_role_menu values (3, 11);
insert into sys_role_menu values (3, 12);
insert into sys_role_menu values (3, 13);
insert into sys_role_menu values (3, 14);
insert into sys_role_menu values (3, 101);
insert into sys_role_menu values (3, 105);
insert into sys_role_menu values (3, 109);
insert into sys_role_menu values (3, 113);
insert into sys_role_menu values (3, 117);
insert into sys_role_menu values (3, 121);


-- -----------------------------
-- 字典类型表
-- -----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type (
    type_id      bigint(20)    not null auto_increment  comment '类型ID',
    type_name    varchar(32)   default ''               comment '字典名称',
    dict_type    varchar(64)   default ''               comment '字典类型',
    status       char(1)       default '0'              comment '状态{0=正常, 1=停用}',
    create_by    varchar(64)   default ''               comment '创建者',
    create_time  datetime                               comment '创建时间',
    update_by    varchar(64)   default ''               comment '更新者',
    update_time  datetime                               comment '更新时间',
    remark       varchar(500)  default ''               comment '备注',
    primary key (type_id),
    unique (dict_type)
) engine = InnoDB auto_increment = 100 comment = '字典类型表';

insert into sys_dict_type values(1, '信息状态', 'sys_status',      '0', 'admin', sysdate(), '', null, '信息状态类型');
insert into sys_dict_type values(2, '删除标志', 'sys_del_flag',    '0', 'admin', sysdate(), '', null, '删除标志类型');
insert into sys_dict_type values(3, '用户性别', 'sys_user_gender', '0', 'admin', sysdate(), '', null, '用户性别类型');


-- -----------------------------
-- 字典数据表
-- -----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data (
    data_id      bigint(20)    not null auto_increment  comment '数据ID',
    dict_type    varchar(64)   default ''               comment '字典类型',
    data_label   varchar(32)   default ''               comment '字典标签',
    data_value   varchar(32)   default ''               comment '字典键值',
    css_class    varchar(32)   default ''               comment '样式属性',
    type_class   varchar(32)   default ''               comment '类型样式',
    sort         int(4)        default 0                comment '显示顺序',
    status       char(1)       default '0'              comment '状态{0=正常, 1=停用}',
    create_by    varchar(64)   default ''               comment '创建者',
    create_time  datetime                               comment '创建时间',
    update_by    varchar(64)   default ''               comment '更新者',
    update_time  datetime                               comment '更新时间',
    remark       varchar(500)  default ''               comment '备注',
    primary key (data_id)
) engine = InnoDB auto_increment = 100 comment = '字典数据表';

insert into sys_dict_data values(1, 'sys_status',      '0', '正常', '', '', 1, '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(2, 'sys_status',      '1', '停用', '', '', 2, '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values(3, 'sys_del_flag',    '0', '正常', '', '', 1, '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values(4, 'sys_del_flag',    '1', '删除', '', '', 2, '0', 'admin', sysdate(), '', null, '删除状态');
insert into sys_dict_data values(5, 'sys_user_gender', '0', '保密', '', '', 1, '0', 'admin', sysdate(), '', null, '性别保密');
insert into sys_dict_data values(6, 'sys_user_gender', '1', '男',   '', '', 2, '0', 'admin', sysdate(), '', null, '性别男');
insert into sys_dict_data values(7, 'sys_user_gender', '2', '女',   '', '', 3, '0', 'admin', sysdate(), '', null, '性别女');

commit;