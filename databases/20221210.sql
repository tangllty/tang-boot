drop database if exists `tang-boot`;

create database `tang-boot` default character set utf8mb4 collate utf8mb4_unicode_ci;

set names utf8mb4;

use `tang-boot`;

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
insert into sys_dept values (6, 1, '0,1', '高级工程师', '2', '0', '0', 'admin', sysdate(), '', null, '高级工程师');


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
    gender       char(1)       default '0'              comment '性别{0=保密, 1=男, 2=女}',
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

insert into sys_user values (1, 4, 'admin', '糖猫猫', 'admin@163.com', '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '超级管理员');
insert into sys_user values (2, 4, 'tang',  '糖糖',   'tang@163.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');
insert into sys_user values (3, 4, 'miao',  '猫猫',   'miao@163.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');
insert into sys_user values (4, 6, 'james',  'James Gosling', 'james-gosling@gmail.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');
insert into sys_user values (5, 6, 'linus',  'Linus Torvalds', 'linus-torvalds@gmail.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');


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
insert into sys_role values (2, '普通用户',   'user',  '0', '2', '0', '0', 'admin', sysdate(), '', null, '普通用户');
insert into sys_role values (3, '游客用户',   'visitor',  '0', '3', '0', '0', 'admin', sysdate(), '', null, '游客用户');


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

-- 由 com.tang.commons.utils.GenSqlUtils 生成
insert into sys_menu values (1, 0, '0', '系统管理', 'system',  '', '', '系统管理', 'D', '0', 1, '0', '0', 'admin', sysdate(), '', null, '系统管理目录');
insert into sys_menu values (2, 0, '0', '系统监控', 'monitor',  '', '', '系统监控', 'D', '0', 2, '0', '0', 'admin', sysdate(), '', null, '系统监控目录');
insert into sys_menu values (3, 0, '0', '系统工具', 'tool',  '', '', '系统工具', 'D', '0', 3, '0', '0', 'admin', sysdate(), '', null, '系统工具目录');
insert into sys_menu values (4, 0, '0', '猫猫APP', 'app',  '', '', '猫猫APP', 'D', '0', 4, '0', '0', 'admin', sysdate(), '', null, '猫猫APP目录');
insert into sys_menu values (5, 1, '0,1', '用户管理', 'user', 'system/user/index', 'system:user:menu', '用户管理', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values (6, 1, '0,1', '部门管理', 'dept', 'system/dept/index', 'system:dept:menu', '部门管理', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '部门管理菜单');
insert into sys_menu values (7, 1, '0,1', '角色管理', 'role', 'system/role/index', 'system:role:menu', '角色管理', 'M', '0', 3, '0', '0', 'admin', sysdate(), '', null, '角色管理菜单');
insert into sys_menu values (8, 1, '0,1', '菜单管理', 'menu', 'system/menu/index', 'system:menu:menu', '菜单管理', 'M', '0', 4, '0', '0', 'admin', sysdate(), '', null, '菜单管理菜单');
insert into sys_menu values (9, 1, '0,1', '字典管理', 'dict', 'system/dict/index', 'system:dict:menu', '字典管理', 'M', '0', 5, '0', '0', 'admin', sysdate(), '', null, '字典管理菜单');
insert into sys_menu values (10, 1, '0,1', '日志管理', 'log',  '', '', '日志管理', 'D', '0', 6, '0', '0', 'admin', sysdate(), '', null, '日志管理菜单');
insert into sys_menu values (40, 2, '0,2', '在线用户', 'online', 'monitor/online/index', 'monitor:online:menu', '在线用户', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '在线用户菜单');
insert into sys_menu values (41, 2, '0,2', '服务监控', 'server', 'monitor/server/index', 'monitor:server:menu', '服务监控', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '服务监控菜单');
insert into sys_menu values (45, 3, '0,3', '代码生成', 'generator', 'tool/generator/index', 'tool:generator:menu', '代码生成', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '代码生成菜单');
insert into sys_menu values (46, 3, '0,3', '系统接口', 'swagger', 'tool/swagger/index', 'tool:swagger:menu', '系统接口', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '系统接口菜单');
insert into sys_menu values (54, 4, '0,4', '猫猫聊天', 'chat',  '', '', '猫猫聊天', 'D', '0', 1, '0', '0', 'admin', sysdate(), '', null, '猫猫聊天目录');
insert into sys_menu values (11, 5, '0,1,5', '用户查询', '', '', 'system:user:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '用户查询按钮');
insert into sys_menu values (12, 5, '0,1,5', '用户新增', '', '', 'system:user:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '用户新增按钮');
insert into sys_menu values (13, 5, '0,1,5', '用户修改', '', '', 'system:user:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '用户修改按钮');
insert into sys_menu values (14, 5, '0,1,5', '用户删除', '', '', 'system:user:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '用户删除按钮');
insert into sys_menu values (15, 6, '0,1,6', '部门查询', '', '', 'system:dept:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '部门查询按钮');
insert into sys_menu values (16, 6, '0,1,6', '部门新增', '', '', 'system:dept:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '部门新增按钮');
insert into sys_menu values (17, 6, '0,1,6', '部门修改', '', '', 'system:dept:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '部门修改按钮');
insert into sys_menu values (18, 6, '0,1,6', '部门删除', '', '', 'system:dept:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '部门删除按钮');
insert into sys_menu values (19, 7, '0,1,7', '角色查询', '', '', 'system:role:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '角色查询按钮');
insert into sys_menu values (20, 7, '0,1,7', '角色新增', '', '', 'system:role:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '角色新增按钮');
insert into sys_menu values (21, 7, '0,1,7', '角色修改', '', '', 'system:role:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '角色修改按钮');
insert into sys_menu values (22, 7, '0,1,7', '角色删除', '', '', 'system:role:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '角色删除按钮');
insert into sys_menu values (23, 8, '0,1,8', '菜单查询', '', '', 'system:menu:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '菜单查询按钮');
insert into sys_menu values (24, 8, '0,1,8', '菜单新增', '', '', 'system:menu:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '菜单新增按钮');
insert into sys_menu values (25, 8, '0,1,8', '菜单修改', '', '', 'system:menu:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '菜单修改按钮');
insert into sys_menu values (26, 8, '0,1,8', '菜单删除', '', '', 'system:menu:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '菜单删除按钮');
insert into sys_menu values (27, 9, '0,1,9', '字典查询', '', '', 'system:dict:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '字典查询按钮');
insert into sys_menu values (28, 9, '0,1,9', '字典新增', '', '', 'system:dict:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '字典新增按钮');
insert into sys_menu values (29, 9, '0,1,9', '字典修改', '', '', 'system:dict:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '字典修改按钮');
insert into sys_menu values (30, 9, '0,1,9', '字典删除', '', '', 'system:dict:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '字典删除按钮');
insert into sys_menu values (31, 10, '0,1,10', '登陆日志', 'login', 'system/log/login/index', 'system:log:login:menu', '登陆日志', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '登陆日志菜单');
insert into sys_menu values (32, 10, '0,1,10', '接口日志', 'api',  '', '', '接口日志', 'D', '0', 2, '0', '0', 'admin', sysdate(), '', null, '接口日志菜单');
insert into sys_menu values (42, 40, '0,2,40', '在线用户查询', '', '', 'monitor:online:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '在线用户查询按钮');
insert into sys_menu values (43, 40, '0,2,40', '在线用户删除', '', '', 'monitor:online:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '在线用户删除按钮');
insert into sys_menu values (44, 41, '0,2,41', '服务监控查询', '', '', 'monitor:server:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '服务监控查询按钮');
insert into sys_menu values (47, 45, '0,3,45', '代码生成查询', '', '', 'tool:generator:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '代码生成查询按钮');
insert into sys_menu values (48, 45, '0,3,45', '代码生成修改', '', '', 'tool:generator:edit', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '代码生成修改按钮');
insert into sys_menu values (49, 45, '0,3,45', '代码生成删除', '', '', 'tool:generator:delete', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '代码生成删除按钮');
insert into sys_menu values (50, 45, '0,3,45', '代码生成导入', '', '', 'tool:generator:import', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '代码生成导入按钮');
insert into sys_menu values (51, 45, '0,3,45', '代码生成导出', '', '', 'tool:generator:export', '', 'B', '0', 5, '0', '0', 'admin', sysdate(), '', null, '代码生成导出按钮');
insert into sys_menu values (52, 45, '0,3,45', '代码生成执行', '', '', 'tool:generator:execute', '', 'B', '0', 6, '0', '0', 'admin', sysdate(), '', null, '代码生成执行按钮');
insert into sys_menu values (53, 46, '0,3,46', '系统接口查询', '', '', 'tool:swagger:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '系统接口查询按钮');
insert into sys_menu values (55, 54, '0,4,54', '我的聊天', 'room', 'app/chat/room/index', 'app:chat:room:menu', '我的聊天', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '猫猫聊天目录');
insert into sys_menu values (56, 54, '0,4,54', '好友申请', 'apply', 'app/chat/friend-apply/index', 'app:chat:friend-apply:menu', '好友申请', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '好友申请目录');
insert into sys_menu values (57, 54, '0,4,54', '我的好友', 'friend', 'app/chat/friend/index', 'app:chat:friend:menu', '我的好友', 'M', '0', 3, '0', '0', 'admin', sysdate(), '', null, '我的好友目录');
insert into sys_menu values (33, 31, '0,1,10,31', '登陆日志查询', '', '', 'system:log:login:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '登陆日志查询按钮');
insert into sys_menu values (34, 31, '0,1,10,31', '登陆日志删除', '', '', 'system:log:login:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '登陆日志删除按钮');
insert into sys_menu values (35, 32, '0,1,10,32', '数据列表', 'data', 'system/log/api/data/index', 'system:log:api:data:menu', '数据列表', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '接口日志查询菜单');
insert into sys_menu values (36, 32, '0,1,10,32', '分析列表', 'analysis', 'system/log/api/analysis/index', 'system:log:api:analysis:menu', '分析列表', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '接口日志分析菜单');
insert into sys_menu values (58, 55, '0,4,54,55', '聊天列表查询', '', '', 'app:chat:chat-list:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '聊天列表查询按钮');
insert into sys_menu values (59, 55, '0,4,54,55', '聊天列表新增', '', '', 'app:chat:chat-list:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '聊天列表新增按钮');
insert into sys_menu values (60, 55, '0,4,54,55', '聊天列表修改', '', '', 'app:chat:chat-list:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '聊天列表修改按钮');
insert into sys_menu values (61, 55, '0,4,54,55', '聊天列表删除', '', '', 'app:chat:chat-list:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '聊天列表删除按钮');
insert into sys_menu values (62, 55, '0,4,54,55', '聊天消息查询', '', '', 'app:chat:message:list', '', 'B', '0', 5, '0', '0', 'admin', sysdate(), '', null, '聊天消息查询按钮');
insert into sys_menu values (63, 55, '0,4,54,55', '聊天消息新增', '', '', 'app:chat:message:add', '', 'B', '0', 6, '0', '0', 'admin', sysdate(), '', null, '聊天消息新增按钮');
insert into sys_menu values (64, 55, '0,4,54,55', '聊天消息修改', '', '', 'app:chat:message:edit', '', 'B', '0', 7, '0', '0', 'admin', sysdate(), '', null, '聊天消息修改按钮');
insert into sys_menu values (65, 55, '0,4,54,55', '聊天消息删除', '', '', 'app:chat:message:delete', '', 'B', '0', 8, '0', '0', 'admin', sysdate(), '', null, '聊天消息删除按钮');
insert into sys_menu values (66, 56, '0,4,54,56', '好友申请查询', '', '', 'app:chat:friend-apply:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '好友申请查询按钮');
insert into sys_menu values (67, 56, '0,4,54,56', '好友申请新增', '', '', 'app:chat:friend-apply:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '好友申请新增按钮');
insert into sys_menu values (68, 56, '0,4,54,56', '好友申请修改', '', '', 'app:chat:friend-apply:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '好友申请修改按钮');
insert into sys_menu values (69, 56, '0,4,54,56', '好友申请同意', '', '', 'app:chat:friend-apply:accept', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '好友申请同意按钮');
insert into sys_menu values (70, 56, '0,4,54,56', '好友申请拒绝', '', '', 'app:chat:friend-apply:decline', '', 'B', '0', 5, '0', '0', 'admin', sysdate(), '', null, '好友申请拒绝按钮');
insert into sys_menu values (71, 56, '0,4,54,56', '好友申请删除', '', '', 'app:chat:friend-apply:delete', '', 'B', '0', 6, '0', '0', 'admin', sysdate(), '', null, '好友申请删除按钮');
insert into sys_menu values (72, 57, '0,4,54,57', '我的好友查询', '', '', 'app:chat:friend:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '我的好友查询按钮');
insert into sys_menu values (73, 57, '0,4,54,57', '我的好友新增', '', '', 'app:chat:friend:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '我的好友新增按钮');
insert into sys_menu values (74, 57, '0,4,54,57', '我的好友修改', '', '', 'app:chat:friend:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '我的好友修改按钮');
insert into sys_menu values (75, 57, '0,4,54,57', '我的好友删除', '', '', 'app:chat:friend:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '我的好友删除按钮');
insert into sys_menu values (37, 35, '0,1,10,32,35', '接口日志查询', '', '', 'system:log:api:data:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '接口日志查询按钮');
insert into sys_menu values (38, 35, '0,1,10,32,35', '接口日志删除', '', '', 'system:log:api:data:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '接口日志删除按钮');
insert into sys_menu values (39, 36, '0,1,10,32,36', '接口日志分析', '', '', 'system:log:api:analysis:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '接口日志分析按钮');


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
insert into sys_user_role values (3, 2);
insert into sys_user_role values (4, 2);
insert into sys_user_role values (5, 2);
insert into sys_user_role values (6, 2);
insert into sys_user_role values (7, 2);
insert into sys_user_role values (8, 2);
insert into sys_user_role values (9, 2);
insert into sys_user_role values (10, 2);


-- -----------------------------
-- 角色与菜单关联表
-- -----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
    role_id  bigint(20) not null  comment '角色ID',
    menu_id  bigint(20) not null  comment '菜单ID',
    primary key(role_id, menu_id)
) engine = InnoDB comment = '角色与菜单关联表';

-- 由 com.tang.commons.utils.GenSqlUtils 生成
insert into sys_role_menu values (2, 1);
insert into sys_role_menu values (2, 2);
insert into sys_role_menu values (2, 3);
insert into sys_role_menu values (2, 4);
insert into sys_role_menu values (2, 5);
insert into sys_role_menu values (2, 6);
insert into sys_role_menu values (2, 7);
insert into sys_role_menu values (2, 8);
insert into sys_role_menu values (2, 9);
insert into sys_role_menu values (2, 10);
insert into sys_role_menu values (2, 11);
insert into sys_role_menu values (2, 12);
insert into sys_role_menu values (2, 13);
insert into sys_role_menu values (2, 14);
insert into sys_role_menu values (2, 15);
insert into sys_role_menu values (2, 16);
insert into sys_role_menu values (2, 17);
insert into sys_role_menu values (2, 18);
insert into sys_role_menu values (2, 19);
insert into sys_role_menu values (2, 20);
insert into sys_role_menu values (2, 21);
insert into sys_role_menu values (2, 22);
insert into sys_role_menu values (2, 23);
insert into sys_role_menu values (2, 24);
insert into sys_role_menu values (2, 25);
insert into sys_role_menu values (2, 26);
insert into sys_role_menu values (2, 27);
insert into sys_role_menu values (2, 28);
insert into sys_role_menu values (2, 29);
insert into sys_role_menu values (2, 30);
insert into sys_role_menu values (2, 31);
insert into sys_role_menu values (2, 32);
insert into sys_role_menu values (2, 33);
insert into sys_role_menu values (2, 34);
insert into sys_role_menu values (2, 35);
insert into sys_role_menu values (2, 36);
insert into sys_role_menu values (2, 37);
insert into sys_role_menu values (2, 38);
insert into sys_role_menu values (2, 39);
insert into sys_role_menu values (2, 40);
insert into sys_role_menu values (2, 41);
insert into sys_role_menu values (2, 42);
insert into sys_role_menu values (2, 43);
insert into sys_role_menu values (2, 44);
insert into sys_role_menu values (2, 45);
insert into sys_role_menu values (2, 46);
insert into sys_role_menu values (2, 47);
insert into sys_role_menu values (2, 48);
insert into sys_role_menu values (2, 49);
insert into sys_role_menu values (2, 50);
insert into sys_role_menu values (2, 51);
insert into sys_role_menu values (2, 52);
insert into sys_role_menu values (2, 53);
insert into sys_role_menu values (2, 54);
insert into sys_role_menu values (2, 55);
insert into sys_role_menu values (2, 56);
insert into sys_role_menu values (2, 57);
insert into sys_role_menu values (2, 58);
insert into sys_role_menu values (2, 59);
insert into sys_role_menu values (2, 60);
insert into sys_role_menu values (2, 61);
insert into sys_role_menu values (2, 62);
insert into sys_role_menu values (2, 63);
insert into sys_role_menu values (2, 64);
insert into sys_role_menu values (2, 65);
insert into sys_role_menu values (2, 66);
insert into sys_role_menu values (2, 67);
insert into sys_role_menu values (2, 68);
insert into sys_role_menu values (2, 69);
insert into sys_role_menu values (2, 70);
insert into sys_role_menu values (2, 71);
insert into sys_role_menu values (2, 72);
insert into sys_role_menu values (2, 73);
insert into sys_role_menu values (2, 74);
insert into sys_role_menu values (2, 75);
insert into sys_role_menu values (3, 1);
insert into sys_role_menu values (3, 2);
insert into sys_role_menu values (3, 3);
insert into sys_role_menu values (3, 4);
insert into sys_role_menu values (3, 5);
insert into sys_role_menu values (3, 6);
insert into sys_role_menu values (3, 7);
insert into sys_role_menu values (3, 8);
insert into sys_role_menu values (3, 9);
insert into sys_role_menu values (3, 10);
insert into sys_role_menu values (3, 11);
insert into sys_role_menu values (3, 15);
insert into sys_role_menu values (3, 19);
insert into sys_role_menu values (3, 23);
insert into sys_role_menu values (3, 27);
insert into sys_role_menu values (3, 31);
insert into sys_role_menu values (3, 32);
insert into sys_role_menu values (3, 33);
insert into sys_role_menu values (3, 35);
insert into sys_role_menu values (3, 36);
insert into sys_role_menu values (3, 37);
insert into sys_role_menu values (3, 39);
insert into sys_role_menu values (3, 40);
insert into sys_role_menu values (3, 41);
insert into sys_role_menu values (3, 42);
insert into sys_role_menu values (3, 44);
insert into sys_role_menu values (3, 45);
insert into sys_role_menu values (3, 46);
insert into sys_role_menu values (3, 47);
insert into sys_role_menu values (3, 51);
insert into sys_role_menu values (3, 53);
insert into sys_role_menu values (3, 54);
insert into sys_role_menu values (3, 55);
insert into sys_role_menu values (3, 56);
insert into sys_role_menu values (3, 57);
insert into sys_role_menu values (3, 58);
insert into sys_role_menu values (3, 62);
insert into sys_role_menu values (3, 66);
insert into sys_role_menu values (3, 72);


-- -----------------------------
-- 角色与字典关联表
-- -----------------------------
drop table if exists sys_role_dict;
create table sys_role_dict (
    role_id  bigint(20)  not null  comment '角色ID',
    dict_id  varchar(64) not null  comment '字典ID',
    primary key(role_id, dict_id)
) engine = InnoDB comment = '角色与字典关联表';


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

insert into sys_dict_type values (1, '信息状态', 'sys_status',      '0', 'admin', sysdate(), '', null, '信息状态类型');
insert into sys_dict_type values (2, '删除标志', 'sys_del_flag',    '0', 'admin', sysdate(), '', null, '删除标志类型');
insert into sys_dict_type values (3, '用户性别', 'sys_user_gender', '0', 'admin', sysdate(), '', null, '用户性别类型');
insert into sys_dict_type values (4, '好友申请类型', 'app_friend_apply_type', '0', 'admin', sysdate(), '', null, '好友申请类型类型');
insert into sys_dict_type values (5, '好友申请状态', 'app_friend_apply_status', '0', 'admin', sysdate(), '', null, '好友申请状态类型');


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

insert into sys_dict_data values (1, 'sys_status', '正常', '0', '', '', 1, '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values (2, 'sys_status', '停用', '1', '', '', 2, '0', 'admin', sysdate(), '', null, '停用状态');
insert into sys_dict_data values (3, 'sys_del_flag', '正常', '0', '', '', 1, '0', 'admin', sysdate(), '', null, '正常状态');
insert into sys_dict_data values (4, 'sys_del_flag', '删除', '1', '', '', 2, '0', 'admin', sysdate(), '', null, '删除状态');
insert into sys_dict_data values (5, 'sys_user_gender', '保密', '0', '', '', 1, '0', 'admin', sysdate(), '', null, '性别保密');
insert into sys_dict_data values (6, 'sys_user_gender', '男',   '1', '', '', 2, '0', 'admin', sysdate(), '', null, '性别男');
insert into sys_dict_data values (7, 'sys_user_gender', '女',   '2', '', '', 3, '0', 'admin', sysdate(), '', null, '性别女');
insert into sys_dict_data values (8, 'app_friend_apply_type', '申请添加好友', '0', '', '', 1, '0', 'admin', sysdate(), '', null, '申请添加好友');
insert into sys_dict_data values (9, 'app_friend_apply_type', '申请添加群组', '1', '', '', 2, '0', 'admin', sysdate(), '', null, '申请添加群组');
insert into sys_dict_data values (10, 'app_friend_apply_status', '已申请',   '0', '', 'type',    1, '0', 'admin', sysdate(), '', null, '已申请');
insert into sys_dict_data values (11, 'app_friend_apply_status', '已同意',   '1', '', 'success', 2, '0', 'admin', sysdate(), '', null, '已同意');
insert into sys_dict_data values (12, 'app_friend_apply_status', '已拒绝',   '2', '', 'danger',  3, '0', 'admin', sysdate(), '', null, '已拒绝');


-- -----------------------------
-- 登陆日志表
-- -----------------------------
drop table if exists sys_log_login;
create table sys_log_login (
    login_id      bigint(20)    not null auto_increment  comment '日志ID',
    user_id       bigint(20)    default  null            comment '用户ID',
    account       varchar(128)  default ''               comment '登陆账号',
    login_type    varchar(32)   default ''               comment '登陆类型',
    os            varchar(64)   default ''               comment '操作系统',
    browser       varchar(64)   default ''               comment '浏览器类型',
    ip            varchar(64)   default ''               comment '登录IP地址',
    location      varchar(64)   default ''               comment '登录地点',
    login_time    datetime                               comment '登录时间',
    success       char(2)       default ''               comment '是否成功',
    message       varchar(128)  default ''               comment '返回消息',
    primary key (login_id)
) engine = InnoDB auto_increment = 100 comment = '登陆日志表';


-- -----------------------------
-- 接口日志表
-- -----------------------------
drop table if exists sys_log_api;
create table sys_log_api (
    api_id         bigint(20)     not null auto_increment  comment '日志ID',
    user_id        bigint(20)     default  null            comment '用户ID',
    class_name     varchar(128)   default ''               comment '类名称',
    method_name    varchar(128)   default ''               comment '方法名称',
    request_uri    varchar(255)   default ''               comment '请求URI',
    request_type   varchar(32)    default ''               comment '请求类型',
    request_param  varchar(2000)  default ''               comment '请求体',
    request_query  varchar(2000)  default ''               comment '请求参数',
    response_body  text                                    comment '响应体',
    login_type     varchar(32)    default ''               comment '登陆类型',
    ip             varchar(64)    default ''               comment '登录IP地址',
    location       varchar(64)    default ''               comment '登录地点',
    start_time     datetime                                comment '开始时间',
    end_time       datetime                                comment '结束时间',
    cost_time      bigint(20)     default 0                comment '耗时',
    status_code    varchar(32)    default ''               comment '状态码',
    message        varchar(128)   default ''               comment '消息',
    create_by      varchar(64)    default ''               comment '创建者',
    create_time    datetime                                comment '创建时间',
    update_by      varchar(64)    default ''               comment '更新者',
    update_time    datetime                                comment '更新时间',
    remark         varchar(500)   default ''               comment '备注',
    primary key (api_id)
) engine = InnoDB auto_increment = 100 comment = '接口日志表';


-- -----------------------------
-- 代码生成表
-- -----------------------------
drop table if exists gen_table;
create table gen_table (
    table_id         bigint(20)    not null auto_increment  comment '编号',
    table_name       varchar(64)   default ''               comment '表名称',
    table_comment    varchar(128)  default ''               comment '表描述',
    class_name       varchar(128)  default ''               comment '实体类名称',
    package_name     varchar(128)                           comment '包路径',
    module_name      varchar(32)                            comment '模块名',
    business_name    varchar(32)                            comment '业务名',
    class_comment    varchar(64)                            comment '类注释',
    author           varchar(64)                            comment '作者',
    parent_menu_id   bigint(20)    default null             comment '父菜单ID',
    language_type    varchar(32)   default 'Java'           comment '语言类型',
    orm_type         varchar(32)   default 'MyBatis'        comment 'ORM 类型',
    create_by        varchar(64)   default ''               comment '创建者',
    create_time      datetime                               comment '创建时间',
    update_by        varchar(64)   default ''               comment '更新者',
    update_time      datetime                               comment '更新时间',
    remark           varchar(500)  default ''               comment '备注',
    primary key (table_id)
) engine = InnoDB auto_increment = 1 comment = '代码生成表';


-- -----------------------------
-- 代码字段生成表
-- -----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
    column_id       bigint(20)    not null auto_increment  comment '编号',
    table_id        bigint(20)    default null             comment '归属表编号',
    column_name     varchar(64)   default ''               comment '字段名称',
    column_comment  varchar(128)  default ''               comment '字段描述',
    column_type     varchar(128)  default ''               comment '字段类型',
    java_type       varchar(128)  default ''               comment 'JAVA 类型',
    ts_type         varchar(128)  default ''               comment 'TS 类型',
    java_field      varchar(128)  default ''               comment 'JAVA 属性',
    is_pk           char(1)       default '0'              comment '是否主键{0=否, 1=是}',
    is_increment    char(1)       default '0'              comment '是否自增{0=否, 1=是}',
    is_list         char(1)       default '0'              comment '是否列表字段{0=否, 1=是}',
    is_insert       char(1)       default '0'              comment '是否为插入字段{0=否, 1=是}',
    is_edit         char(1)       default '0'              comment '是否编辑字段{0=否, 1=是}',
    is_required     char(1)       default '0'              comment '是否必填{0=否, 1=是}',
    query_type      varchar(128)  default 'equal'          comment '查询方式{equal=等于, fuzzy=模糊, findInSet=包含}',
    html_type       varchar(128)  default ''               comment '显示类型',
    dict_type       varchar(128)  default ''               comment '字典类型',
    sort            int(4)        default 0                comment '显示顺序',
    create_by       varchar(64)   default ''               comment '创建者',
    create_time     datetime                               comment '创建时间',
    update_by       varchar(64)   default ''               comment '更新者',
    update_time     datetime                               comment '更新时间',
    remark          varchar(500)  default ''               comment '备注',
    primary key (column_id)
) engine = InnoDB auto_increment = 1 comment = '代码字段生成表';


-- -----------------------------
-- 用户好友表
-- -----------------------------
drop table if exists app_friend;
create table app_friend (
    user_friend_id  bigint(20)    not null auto_increment  comment '用户好友ID',
    user_id         bigint(20)    default null             comment '用户ID',
    friend_id       bigint(20)    default null             comment '好友ID',
    unique_id       bigint(20)    default null             comment '唯一标识',
    create_by       varchar(64)   default ''               comment '创建者',
    create_time     datetime                               comment '创建时间',
    update_by       varchar(64)   default ''               comment '更新者',
    update_time     datetime                               comment '更新时间',
    remark          varchar(500)  default ''               comment '备注',
    primary key (user_friend_id)
) engine = InnoDB auto_increment = 100 comment = '用户好友表';

insert into app_friend values (100, 1, 2, 885332953918476288, 'admin', sysdate(), '', null, '糖糖');
insert into app_friend values (101, 2, 1, 885332953918476288, 'admin', sysdate(), '', null, '');


-- -----------------------------
-- 用户好友申请表
-- -----------------------------
drop table if exists app_friend_apply;
create table app_friend_apply (
    apply_id        bigint(20)    not null auto_increment  comment '申请ID',
    user_id         bigint(20)    default null             comment '用户ID',
    friend_id       bigint(20)    default null             comment '好友ID',
    requestor_id    bigint(20)    default null             comment '申请者ID',
    unique_id       bigint(20)    default null             comment '唯一标识',
    reason          varchar(128)  default ''               comment '申请理由',
    apply_type      char(1)       default ''               comment '申请类型{0=申请添加好友, 1=申请添加群组}',
    status          char(1)       default '0'              comment '申请状态{0=已申请, 1=已同意, 2=已拒绝}',
    create_by       varchar(64)   default ''               comment '创建者',
    create_time     datetime                               comment '创建时间',
    update_by       varchar(64)   default ''               comment '更新者',
    update_time     datetime                               comment '更新时间',
    remark          varchar(500)  default ''               comment '备注',
    primary key (apply_id)
) engine = InnoDB auto_increment = 100 comment = '用户好友申请表';

insert into app_friend_apply values (100, 1, 2, 1, 885332953918476288, 'Hi, 糖糖', '0', '1', 'admin', sysdate(), '', null, '');
insert into app_friend_apply values (101, 2, 1, 1, 885332953918476288, 'Hi, 糖糖', '0', '1', 'admin', sysdate(), '', null, '');
insert into app_friend_apply values (102, 1, 3, 1, 951182679020277760, 'Hi, 猫猫', '0', '1', 'admin', sysdate(), '', null, '');
insert into app_friend_apply values (103, 3, 1, 1, 951182679020277760, 'Hi, 猫猫', '0', '1', 'admin', sysdate(), '', null, '');

-- -----------------------------
-- 聊天列表表
-- -----------------------------
drop table if exists app_chat_list;
create table app_chat_list (
    chat_list_id    bigint(20)    not null     comment '聊天列表ID',
    user_id         bigint(20)    not null     comment '用户ID',
    chat_id         bigint(20)    not null     comment '聊天ID',
    chat_type       char(1)       default '0'  comment '聊天类型{0=单聊, 1=群聊}',
    stick_flag      char(1)       default '0'  comment '置顶标记{0=否, 1=是}',
    display_flag    char(1)       default '1'  comment '显示标记{0=否, 1=是}',
    mute_flag       char(1)       default '0'  comment '免打扰标记{0=否, 1=是}',
    create_by       varchar(64)   default ''   comment '创建者',
    create_time     datetime                   comment '创建时间',
    update_by       varchar(64)   default ''   comment '更新者',
    update_time     datetime                   comment '更新时间',
    remark          varchar(500)  default ''   comment '备注',
    primary key (chat_list_id, user_id, chat_id)
) engine = InnoDB auto_increment = 100 comment = '聊天列表表';

insert into app_chat_list values (885332953918476288, 1, 2, '0', '0', '1', '0', 'admin', sysdate(), '', null, '');
insert into app_chat_list values (885332953918476288, 2, 1, '0', '0', '1', '0', 'tang', sysdate(), '', null, '');
insert into app_chat_list values (951182679020277760, 1, 3, '0', '0', '1', '0', 'admin', sysdate(), '', null, '');
insert into app_chat_list values (951182679020277760, 3, 1, '0', '0', '1', '0', 'miao', sysdate(), '', null, '');


-- -----------------------------
-- 聊天消息表
-- -----------------------------
drop table if exists app_chat_message;
create table app_chat_message (
    message_id        bigint(20)    not null auto_increment  comment '消息ID',
    chat_list_id      bigint(20)    default null             comment '聊天列表ID',
    sender_id         bigint(20)    default null             comment '发送者ID',
    reply_message_id  bigint(20)    default null             comment '回复消息ID',
    content           varchar(500)  default ''               comment '聊天内容',
    create_by         varchar(64)   default ''               comment '创建者',
    create_time       datetime                               comment '创建时间',
    update_by         varchar(64)   default ''               comment '更新者',
    update_time       datetime                               comment '更新时间',
    remark            varchar(500)  default ''               comment '备注',
    primary key (message_id)
) engine = InnoDB auto_increment = 100 comment = '聊天消息表';

insert into app_chat_message values (100, 885332953918476288, 1, null, '你好吗？', 'admin', sysdate(), '', null, '');
insert into app_chat_message values (101, 885332953918476288, 2, 100,  '也就那么回事', 'tang', sysdate(), '', null, '');
insert into app_chat_message values (102, 951182679020277760, 3, null, '你好嘛瞄~', 'admin', sysdate(), '', null, '');

commit;