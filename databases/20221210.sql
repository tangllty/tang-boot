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
insert into sys_dept values (6, 1, '0,1',  '高级工程师', '2', '0', '0', 'admin', sysdate(), '', null, '高级工程师');
insert into sys_dept values (7, 1, '0',    '第三方用户', '3', '0', '0', 'admin', sysdate(), '', null, '第三方用户');
insert into sys_dept values (8, 7, '0,7',  'GitHub',   '1', '0', '0', 'admin', sysdate(), '', null, 'GitHub');

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
    user_type    char(1)       default '0'              comment '用户类型{0=系统用户, 1=GitHub用户}',
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

insert into sys_user values (1, 4, 'admin', '糖猫猫', 'admin@163.com', '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '超级管理员');
insert into sys_user values (2, 4, 'tang',  '糖糖',   'tang@163.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');
insert into sys_user values (3, 4, 'miao',  '猫猫',   'miao@163.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');
insert into sys_user values (4, 6, 'james',  'James Gosling', 'james-gosling@gmail.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');
insert into sys_user values (5, 6, 'linus',  'Linus Torvalds', 'linus-torvalds@gmail.com',  '16888888888', '0', '', '$2a$10$M5ET/kgWHSiZn.3w5M1h1ePzo2PY7ZowvvthQbQITG9GjhvQZR7c6', '0', '0', '0', '127.0.0.1', null, 'admin', sysdate(), '', null, '普通用户');


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
insert into sys_menu values (2, 0, '0', '表单设计', 'form-designer', 'form/designer/index', 'form:designer:menu', '表单设计', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '表单设计菜单');
insert into sys_menu values (3, 0, '0', 'Monaco Editor', 'monaco-editor', 'editor/monaco/index', 'monaco:editor:menu', 'monaco-editor', 'M', '0', 3, '0', '0', 'admin', sysdate(), '', null, 'Monaco Editor菜单');
insert into sys_menu values (4, 0, '0', '系统监控', 'monitor',  '', '', '系统监控', 'D', '0', 4, '0', '0', 'admin', sysdate(), '', null, '系统监控目录');
insert into sys_menu values (5, 0, '0', '系统工具', 'tool',  '', '', '系统工具', 'D', '0', 5, '0', '0', 'admin', sysdate(), '', null, '系统工具目录');
insert into sys_menu values (6, 0, '0', '猫猫APP', 'app',  '', '', '猫猫APP', 'D', '0', 6, '0', '0', 'admin', sysdate(), '', null, '猫猫APP目录');
insert into sys_menu values (7, 0, '0', '调查问卷', 'survey',  '', '', '调查问卷', 'D', '0', 7, '0', '0', 'admin', sysdate(), '', null, '调查问卷目录');
insert into sys_menu values (8, 1, '0,1', '用户管理', 'user', 'system/user/index', 'system:user:menu', '用户管理', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '用户管理菜单');
insert into sys_menu values (9, 1, '0,1', '部门管理', 'dept', 'system/dept/index', 'system:dept:menu', '部门管理', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '部门管理菜单');
insert into sys_menu values (10, 1, '0,1', '角色管理', 'role', 'system/role/index', 'system:role:menu', '角色管理', 'M', '0', 3, '0', '0', 'admin', sysdate(), '', null, '角色管理菜单');
insert into sys_menu values (11, 1, '0,1', '菜单管理', 'menu', 'system/menu/index', 'system:menu:menu', '菜单管理', 'M', '0', 4, '0', '0', 'admin', sysdate(), '', null, '菜单管理菜单');
insert into sys_menu values (12, 1, '0,1', '字典管理', 'dict', 'system/dict/index', 'system:dict:menu', '字典管理', 'M', '0', 5, '0', '0', 'admin', sysdate(), '', null, '字典管理菜单');
insert into sys_menu values (13, 1, '0,1', '日志管理', 'log',  '', '', '日志管理', 'D', '0', 6, '0', '0', 'admin', sysdate(), '', null, '日志管理菜单');
insert into sys_menu values (43, 4, '0,4', '在线用户', 'online', 'monitor/online/index', 'monitor:online:menu', '在线用户', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '在线用户菜单');
insert into sys_menu values (44, 4, '0,4', '服务监控', 'server', 'monitor/server/index', 'monitor:server:menu', '服务监控', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '服务监控菜单');
insert into sys_menu values (45, 4, '0,4', 'Druid 监控', 'druid', 'monitor/druid/index', 'monitor:druid:menu', 'druid', 'M', '0', 3, '0', '0', 'admin', sysdate(), '', null, 'Druid 监控菜单');
insert into sys_menu values (46, 4, '0,4', 'SBA 监控', 'sba', 'monitor/sba/index', 'monitor:sba:menu', 'sba', 'M', '0', 4, '0', '0', 'admin', sysdate(), '', null, 'SBA 监控菜单');
insert into sys_menu values (50, 5, '0,5', '代码生成', 'generator', 'tool/generator/index', 'tool:generator:menu', '代码生成', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '代码生成菜单');
insert into sys_menu values (51, 5, '0,5', '系统接口', 'swagger', 'tool/swagger/index', 'tool:swagger:menu', '系统接口', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '系统接口菜单');
insert into sys_menu values (60, 6, '0,6', '猫猫聊天', 'chat',  '', '', '猫猫聊天', 'D', '0', 1, '0', '0', 'admin', sysdate(), '', null, '猫猫聊天目录');
insert into sys_menu values (82, 7, '0,7', '问卷管理', 'form', 'survey/form/index', 'survey:form:menu', '问卷管理', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '问卷管理菜单');
insert into sys_menu values (83, 7, '0,7', '用户答案', 'answer', 'survey/answer/index', 'survey:answer:menu', '用户答案', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '用户答案菜单');
insert into sys_menu values (14, 8, '0,1,8', '用户查询', '', '', 'system:user:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '用户查询按钮');
insert into sys_menu values (15, 8, '0,1,8', '用户新增', '', '', 'system:user:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '用户新增按钮');
insert into sys_menu values (16, 8, '0,1,8', '用户修改', '', '', 'system:user:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '用户修改按钮');
insert into sys_menu values (17, 8, '0,1,8', '用户删除', '', '', 'system:user:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '用户删除按钮');
insert into sys_menu values (18, 9, '0,1,9', '部门查询', '', '', 'system:dept:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '部门查询按钮');
insert into sys_menu values (19, 9, '0,1,9', '部门新增', '', '', 'system:dept:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '部门新增按钮');
insert into sys_menu values (20, 9, '0,1,9', '部门修改', '', '', 'system:dept:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '部门修改按钮');
insert into sys_menu values (21, 9, '0,1,9', '部门删除', '', '', 'system:dept:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '部门删除按钮');
insert into sys_menu values (22, 10, '0,1,10', '角色查询', '', '', 'system:role:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '角色查询按钮');
insert into sys_menu values (23, 10, '0,1,10', '角色新增', '', '', 'system:role:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '角色新增按钮');
insert into sys_menu values (24, 10, '0,1,10', '角色修改', '', '', 'system:role:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '角色修改按钮');
insert into sys_menu values (25, 10, '0,1,10', '角色删除', '', '', 'system:role:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '角色删除按钮');
insert into sys_menu values (26, 11, '0,1,11', '菜单查询', '', '', 'system:menu:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '菜单查询按钮');
insert into sys_menu values (27, 11, '0,1,11', '菜单新增', '', '', 'system:menu:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '菜单新增按钮');
insert into sys_menu values (28, 11, '0,1,11', '菜单修改', '', '', 'system:menu:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '菜单修改按钮');
insert into sys_menu values (29, 11, '0,1,11', '菜单删除', '', '', 'system:menu:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '菜单删除按钮');
insert into sys_menu values (30, 12, '0,1,12', '字典查询', '', '', 'system:dict:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '字典查询按钮');
insert into sys_menu values (31, 12, '0,1,12', '字典新增', '', '', 'system:dict:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '字典新增按钮');
insert into sys_menu values (32, 12, '0,1,12', '字典修改', '', '', 'system:dict:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '字典修改按钮');
insert into sys_menu values (33, 12, '0,1,12', '字典删除', '', '', 'system:dict:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '字典删除按钮');
insert into sys_menu values (34, 13, '0,1,13', '登陆日志', 'login', 'system/log/login/index', 'system:log:login:menu', '登陆日志', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '登陆日志菜单');
insert into sys_menu values (35, 13, '0,1,13', '接口日志', 'api',  '', '', '接口日志', 'D', '0', 2, '0', '0', 'admin', sysdate(), '', null, '接口日志菜单');
insert into sys_menu values (47, 43, '0,4,43', '在线用户查询', '', '', 'monitor:online:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '在线用户查询按钮');
insert into sys_menu values (48, 43, '0,4,43', '在线用户删除', '', '', 'monitor:online:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '在线用户删除按钮');
insert into sys_menu values (49, 44, '0,4,44', '服务监控查询', '', '', 'monitor:server:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '服务监控查询按钮');
insert into sys_menu values (52, 50, '0,5,50', '代码生成查询', '', '', 'tool:generator:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '代码生成查询按钮');
insert into sys_menu values (53, 50, '0,5,50', '代码生成修改', '', '', 'tool:generator:edit', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '代码生成修改按钮');
insert into sys_menu values (54, 50, '0,5,50', '代码生成删除', '', '', 'tool:generator:delete', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '代码生成删除按钮');
insert into sys_menu values (55, 50, '0,5,50', '代码生成导入', '', '', 'tool:generator:import', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '代码生成导入按钮');
insert into sys_menu values (56, 50, '0,5,50', '代码生成导出', '', '', 'tool:generator:export', '', 'B', '0', 5, '0', '0', 'admin', sysdate(), '', null, '代码生成导出按钮');
insert into sys_menu values (57, 50, '0,5,50', '代码生成执行', '', '', 'tool:generator:execute', '', 'B', '0', 6, '0', '0', 'admin', sysdate(), '', null, '代码生成执行按钮');
insert into sys_menu values (58, 50, '0,5,50', '代码生成同步', '', '', 'tool:generator:sync', '', 'B', '0', 7, '0', '0', 'admin', sysdate(), '', null, '代码生成同步按钮');
insert into sys_menu values (59, 51, '0,5,51', '系统接口查询', '', '', 'tool:swagger:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '系统接口查询按钮');
insert into sys_menu values (61, 60, '0,6,60', '我的聊天', 'room', 'app/chat/room/index', 'app:chat:room:menu', '我的聊天', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '猫猫聊天目录');
insert into sys_menu values (62, 60, '0,6,60', '好友申请', 'apply', 'app/chat/friend-apply/index', 'app:chat:friend-apply:menu', '好友申请', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '好友申请目录');
insert into sys_menu values (63, 60, '0,6,60', '我的好友', 'friend', 'app/chat/friend/index', 'app:chat:friend:menu', '我的好友', 'M', '0', 3, '0', '0', 'admin', sysdate(), '', null, '我的好友目录');
insert into sys_menu values (84, 82, '0,7,82', '调查问卷查询', '', '', 'survey:form:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '调查问卷查询按钮');
insert into sys_menu values (85, 82, '0,7,82', '调查问卷新增', '', '', 'survey:form:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '调查问卷新增按钮');
insert into sys_menu values (86, 82, '0,7,82', '调查问卷修改', '', '', 'survey:form:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '调查问卷修改按钮');
insert into sys_menu values (87, 82, '0,7,82', '调查问卷删除', '', '', 'survey:form:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '调查问卷删除按钮');
insert into sys_menu values (88, 83, '0,7,83', '用户答案查询', '', '', 'survey:answer:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '用户答案查询按钮');
insert into sys_menu values (89, 83, '0,7,83', '用户答案删除', '', '', 'survey:answer:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '用户答案删除按钮');
insert into sys_menu values (36, 34, '0,1,13,34', '登陆日志查询', '', '', 'system:log:login:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '登陆日志查询按钮');
insert into sys_menu values (37, 34, '0,1,13,34', '登陆日志删除', '', '', 'system:log:login:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '登陆日志删除按钮');
insert into sys_menu values (38, 35, '0,1,13,35', '数据列表', 'data', 'system/log/api/data/index', 'system:log:api:data:menu', '数据列表', 'M', '0', 1, '0', '0', 'admin', sysdate(), '', null, '接口日志查询菜单');
insert into sys_menu values (39, 35, '0,1,13,35', '分析列表', 'analysis', 'system/log/api/analysis/index', 'system:log:api:analysis:menu', '分析列表', 'M', '0', 2, '0', '0', 'admin', sysdate(), '', null, '接口日志分析菜单');
insert into sys_menu values (64, 61, '0,6,60,61', '聊天列表查询', '', '', 'app:chat:chat-list:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '聊天列表查询按钮');
insert into sys_menu values (65, 61, '0,6,60,61', '聊天列表新增', '', '', 'app:chat:chat-list:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '聊天列表新增按钮');
insert into sys_menu values (66, 61, '0,6,60,61', '聊天列表修改', '', '', 'app:chat:chat-list:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '聊天列表修改按钮');
insert into sys_menu values (67, 61, '0,6,60,61', '聊天列表删除', '', '', 'app:chat:chat-list:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '聊天列表删除按钮');
insert into sys_menu values (68, 61, '0,6,60,61', '聊天消息查询', '', '', 'app:chat:message:list', '', 'B', '0', 5, '0', '0', 'admin', sysdate(), '', null, '聊天消息查询按钮');
insert into sys_menu values (69, 61, '0,6,60,61', '聊天消息新增', '', '', 'app:chat:message:add', '', 'B', '0', 6, '0', '0', 'admin', sysdate(), '', null, '聊天消息新增按钮');
insert into sys_menu values (70, 61, '0,6,60,61', '聊天消息修改', '', '', 'app:chat:message:edit', '', 'B', '0', 7, '0', '0', 'admin', sysdate(), '', null, '聊天消息修改按钮');
insert into sys_menu values (71, 61, '0,6,60,61', '聊天消息删除', '', '', 'app:chat:message:delete', '', 'B', '0', 8, '0', '0', 'admin', sysdate(), '', null, '聊天消息删除按钮');
insert into sys_menu values (72, 62, '0,6,60,62', '好友申请查询', '', '', 'app:chat:friend-apply:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '好友申请查询按钮');
insert into sys_menu values (73, 62, '0,6,60,62', '好友申请新增', '', '', 'app:chat:friend-apply:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '好友申请新增按钮');
insert into sys_menu values (74, 62, '0,6,60,62', '好友申请修改', '', '', 'app:chat:friend-apply:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '好友申请修改按钮');
insert into sys_menu values (75, 62, '0,6,60,62', '好友申请同意', '', '', 'app:chat:friend-apply:accept', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '好友申请同意按钮');
insert into sys_menu values (76, 62, '0,6,60,62', '好友申请拒绝', '', '', 'app:chat:friend-apply:decline', '', 'B', '0', 5, '0', '0', 'admin', sysdate(), '', null, '好友申请拒绝按钮');
insert into sys_menu values (77, 62, '0,6,60,62', '好友申请删除', '', '', 'app:chat:friend-apply:delete', '', 'B', '0', 6, '0', '0', 'admin', sysdate(), '', null, '好友申请删除按钮');
insert into sys_menu values (78, 63, '0,6,60,63', '我的好友查询', '', '', 'app:chat:friend:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '我的好友查询按钮');
insert into sys_menu values (79, 63, '0,6,60,63', '我的好友新增', '', '', 'app:chat:friend:add', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '我的好友新增按钮');
insert into sys_menu values (80, 63, '0,6,60,63', '我的好友修改', '', '', 'app:chat:friend:edit', '', 'B', '0', 3, '0', '0', 'admin', sysdate(), '', null, '我的好友修改按钮');
insert into sys_menu values (81, 63, '0,6,60,63', '我的好友删除', '', '', 'app:chat:friend:delete', '', 'B', '0', 4, '0', '0', 'admin', sysdate(), '', null, '我的好友删除按钮');
insert into sys_menu values (40, 38, '0,1,13,35,38', '接口日志查询', '', '', 'system:log:api:data:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '接口日志查询按钮');
insert into sys_menu values (41, 38, '0,1,13,35,38', '接口日志删除', '', '', 'system:log:api:data:delete', '', 'B', '0', 2, '0', '0', 'admin', sysdate(), '', null, '接口日志删除按钮');
insert into sys_menu values (42, 39, '0,1,13,35,39', '接口日志分析', '', '', 'system:log:api:analysis:list', '', 'B', '0', 1, '0', '0', 'admin', sysdate(), '', null, '接口日志分析按钮');


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
insert into sys_role_menu values (2, 76);
insert into sys_role_menu values (2, 77);
insert into sys_role_menu values (2, 78);
insert into sys_role_menu values (2, 79);
insert into sys_role_menu values (2, 80);
insert into sys_role_menu values (2, 81);
insert into sys_role_menu values (2, 82);
insert into sys_role_menu values (2, 83);
insert into sys_role_menu values (2, 84);
insert into sys_role_menu values (2, 85);
insert into sys_role_menu values (2, 86);
insert into sys_role_menu values (2, 87);
insert into sys_role_menu values (2, 88);
insert into sys_role_menu values (2, 89);


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
) engine = InnoDB auto_increment = 1 comment = '字典类型表';

insert into sys_dict_type (type_name, dict_type, create_by, create_time, remark) values
    ('信息状态', 'sys_status', 'admin', sysdate(), '信息状态类型'),
    ('删除标志', 'sys_del_flag', 'admin', sysdate(), '删除标志类型'),
    ('用户性别', 'sys_user_gender', 'admin', sysdate(), '用户性别类型'),
    ('语言类型', 'gen_table_language_type', 'admin', sysdate(), '语言类型类型'),
    ('ORM 类型', 'gen_table_orm_type', 'admin', sysdate(), 'ORM 类型类型'),
    ('好友申请类型', 'app_friend_apply_type', 'admin', sysdate(), '好友申请类型类型'),
    ('好友申请状态', 'app_friend_apply_status', 'admin', sysdate(), '好友申请状态类型'),
    ('问卷发布状态', 'qs_survey_form_publish_status', 'admin', sysdate(), '问卷发布状态类型');


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
) engine = InnoDB auto_increment = 1 comment = '字典数据表';

insert into sys_dict_data (dict_type, data_label, data_value, css_class, type_class, sort, create_by, create_time, remark) values
    ('sys_status', '正常', '0', '', '', 1, 'admin', sysdate(), '正常状态'),
    ('sys_status', '停用', '1', '', '', 2, 'admin', sysdate(), '停用状态'),
    ('sys_del_flag', '正常', '0', '', '', 1, 'admin', sysdate(), '正常状态'),
    ('sys_del_flag', '删除', '1', '', '', 2, 'admin', sysdate(), '删除状态'),
    ('sys_user_gender', '保密', '0', '', '', 1, 'admin', sysdate(), '性别保密'),
    ('sys_user_gender', '男',   '1', '', '', 2, 'admin', sysdate(), '性别男'),
    ('sys_user_gender', '女',   '2', '', '', 3, 'admin', sysdate(), '性别女'),
    ('gen_table_language_type', 'Java', 'Java', '', '', 1, 'admin', sysdate(), 'Java语言'),
    ('gen_table_language_type', 'Kotlin', 'Kotlin', '', '', 2, 'admin', sysdate(), 'Kotlin语言'),
    ('gen_table_orm_type', 'MyBatis', 'MyBatis', '', '', 1, 'admin', sysdate(), 'MyBatis ORM'),
    ('gen_table_orm_type', 'MyBatis-Flex', 'MyBatis-Flex', '', '', 2, 'admin', sysdate(), 'MyBatis-Flex ORM'),
    ('gen_table_orm_type', 'MyBatis-Plus', 'MyBatis-Plus', '', '', 3, 'admin', sysdate(), 'MyBatis-Plus ORM'),
    ('app_friend_apply_type', '申请添加好友', '0', '', '', 1, 'admin', sysdate(), '申请添加好友'),
    ('app_friend_apply_type', '申请添加群组', '1', '', '', 2, 'admin', sysdate(), '申请添加群组'),
    ('app_friend_apply_status', '已申请',   '0', '', 'type',    1, 'admin', sysdate(), '已申请'),
    ('app_friend_apply_status', '已同意',   '1', '', 'success', 2, 'admin', sysdate(), '已同意'),
    ('app_friend_apply_status', '已拒绝',   '2', '', 'danger',  3, 'admin', sysdate(), '已拒绝'),
    ('qs_survey_form_publish_status', '未发布', '0', '', 'info',    1, 'admin', sysdate(), '未发布'),
    ('qs_survey_form_publish_status', '已发布', '1', '', 'success', 2, 'admin', sysdate(), '已发布');


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
    message        varchar(2000)   default ''               comment '消息',
    create_by      varchar(64)    default ''               comment '创建者',
    create_time    datetime                                comment '创建时间',
    update_by      varchar(64)    default ''               comment '更新者',
    update_time    datetime                                comment '更新时间',
    remark         varchar(500)   default ''               comment '备注',
    primary key (api_id)
) engine = InnoDB auto_increment = 100 comment = '接口日志表';


-- -----------------------------
-- 汉语拼音编码表
-- -----------------------------
drop table if exists base_chinese_pinyin;
create table base_chinese_pinyin (
    pinyin varchar(8)  not null  comment '拼音',
    code   int(11)     not null  comment '编码',
    primary key (code)
) engine = InnoDB comment = '汉语拼音编码表';

insert into base_chinese_pinyin (pinyin, code) values
    ('a', 20319), ('ai', 20317), ('an', 20304), ('ang', 20295),
    ('ao', 20292), ('ba', 20283), ('bai', 20265), ('ban', 20257),
    ('bang', 20242), ('bao', 20230), ('bei', 20051), ('ben', 20036), ('beng', 20032), ('bi', 20026), ('bian', 20002),
    ('biao', 19990), ('bie', 19986), ('bin', 19982), ('bing', 19976), ('bo', 19805), ('bu', 19784),
    ('ca', 19775), ('cai', 19774), ('can', 19763), ('cang', 19756), ('cao', 19751), ('ce', 19746), ('ceng', 19741),
    ('cha', 19739), ('chai', 19728), ('chan', 19725), ('chang', 19715), ('chao', 19540), ('che', 19531), ('chen', 19525),
    ('cheng', 19515), ('chi', 19500), ('chong', 19484), ('chou', 19479), ('chu', 19467), ('chuai', 19289), ('chuan', 19288),
    ('chuang', 19281), ('chui', 19275), ('chun', 19270), ('chuo', 19263), ('ci', 19261), ('cong', 19249),('cou', 19243),
    ('cu', 19242), ('cuan', 19238), ('cui', 19235), ('cun', 19227), ('cuo', 19224),
    ('da', 19218), ('dai', 19212), ('dan', 19038), ('dang', 19023), ('dao', 19018), ('de', 19006), ('deng', 19003),
    ('di', 18996), ('dian', 18977), ('diao', 18961), ('die', 18952), ('ding', 18783), ('diu', 18774), ('dong', 18773),
    ('dou', 18763), ('du', 18756), ('duan', 18741), ('dui', 18735), ('dun', 18731), ('duo', 18722),
    ('e', 18710), ('en', 18697), ('er', 18696),
    ('fa', 18526), ('fan', 18518), ('fang', 18501), ('fei', 18490), ('fen', 18478), ('feng', 18463), ('fo', 18448),
    ('fou', 18447), ('fu', 18446),
    ('ga', 18239), ('gai', 18237), ('gan', 18231), ('gang', 18220), ('gao', 18211), ('ge', 18201), ('gei', 18184),
    ('gen', 18183), ('geng', 18181), ('gong', 18012), ('gou', 17997), ('gu', 17988), ('gua', 17970), ('guai', 17964),
    ('guan', 17961), ('guang', 17950), ('gui', 17947), ('gun', 17931), ('guo', 17928),
    ('ha', 17922), ('hai', 17759), ('han', 17752), ('hang', 17733), ('hao', 17730), ('he', 17721), ('hei', 17703),
    ('hen', 17701), ('heng', 17697), ('hong', 17692), ('hou', 17683), ('hu', 17676), ('hua', 17496), ('huai', 17487),
    ('huan', 17482), ('huang', 17468), ('hui', 17454), ('hun', 17433), ('huo', 17427),
    ('ji', 17417), ('jia', 17202), ('jian', 17185), ('jiang', 16983), ('jiao', 16970), ('jie', 16942), ('jin', 16915),
    ('jing', 16733), ('jiong', 16708), ('jiu', 16706), ('ju', 16689), ('juan', 16664), ('jue', 16657), ('jun', 16647),
    ('ka', 16474), ('kai', 16470), ('kan', 16465), ('kang', 16459), ('kao', 16452), ('ke', 16448), ('ken', 16433),
    ('keng', 16429), ('kong', 16427), ('kou', 16423), ('ku', 16419), ('kua', 16412), ('kuai', 16407), ('kuan', 16403),
    ('kuang', 16401), ('kui', 16393), ('kun', 16220), ('kuo', 16216),
    ('la', 16212), ('lai', 16205), ('lan', 16202), ('lang', 16187), ('lao', 16180), ('le', 16171), ('lei', 16169),
    ('leng', 16158), ('li', 16155), ('lia', 15959), ('lian', 15958), ('liang', 15944), ('liao', 15933), ('lie', 15920),
    ('lin', 15915), ('ling', 15903), ('liu', 15889), ('long', 15878), ('lou', 15707), ('lu', 15701), ('lv', 15681),
    ('luan', 15667), ('lue', 15661), ('lun', 15659), ('luo', 15652),
    ('ma', 15640), ('mai', 15631), ('man', 15625), ('mang', 15454), ('mao', 15448), ('me', 15436), ('mei', 15435),
    ('men', 15419), ('meng', 15416), ('mi', 15408), ('mian', 15394), ('miao', 15385), ('mie', 15377), ('min', 15375),
    ('ming', 15369), ('miu', 15363), ('mo', 15362), ('mou', 15183), ('mu', 15180),
    ('na', 15165), ('nai', 15158), ('nan', 15153), ('nang', 15150), ('nao', 15149), ('ne', 15144), ('nei', 15143),
    ('nen', 15141), ('neng', 15140), ('ni', 15139), ('nian', 15128), ('niang', 15121), ('niao', 15119), ('nie', 15117),
    ('nin', 15110), ('ning', 15109), ('niu', 14941), ('nong', 14937), ('nu', 14933), ('nv', 14930), ('nuan', 14929),
    ('nue', 14928), ('nuo', 14926),
    ('o', 14922), ('ou', 14921),
    ('pa', 14914), ('pai', 14908), ('pan', 14902), ('pang', 14894), ('pao', 14889), ('pei', 14882), ('pen', 14873),
    ('peng', 14871), ('pi', 14857), ('pian', 14678), ('piao', 14674), ('pie', 14670), ('pin', 14668), ('ping', 14663),
    ('po', 14654), ('pu', 14645),
    ('qi', 14630), ('qia', 14594), ('qian', 14429), ('qiang', 14407), ('qiao', 14399), ('qie', 14384), ('qin', 14379),
    ('qing', 14368), ('qiong', 14355), ('qiu', 14353), ('qu', 14345), ('quan', 14170), ('que', 14159), ('qun', 14151),
    ('ran', 14149), ('rang', 14145), ('rao', 14140), ('re', 14137), ('ren', 14135), ('reng', 14125), ('ri', 14123),
    ('rong', 14122), ('rou', 14112), ('ru', 14109), ('ruan', 14099), ('rui', 14097), ('run', 14094), ('ruo', 14092),
    ('sa', 14090), ('sai', 14087), ('san', 14083), ('sang', 13917), ('sao', 13914), ('se', 13910), ('sen', 13907),
    ('seng', 13906), ('sha', 13905), ('shai', 13896), ('shan', 13894), ('shang', 13878), ('shao', 13870), ('she', 13859),
    ('shen', 13847), ('sheng', 13831), ('shi', 13658), ('shou', 13611), ('shu', 13601), ('shua', 13406), ('shuai', 13404),
    ('shuan', 13400), ('shuang', 13398), ('shui', 13395), ('shun', 13391), ('shuo', 13387), ('si', 13383), ('song', 13367),
    ('sou', 13359), ('su', 13356), ('suan', 13343), ('sui', 13340), ('sun', 13329), ('suo', 13326),
    ('ta', 13318), ('tai', 13147), ('tan', 13138), ('tang', 13120), ('tao', 13107), ('te', 13096), ('teng', 13095),
    ('ti', 13091), ('tian', 13076), ('tiao', 13068), ('tie', 13063), ('ting', 13060), ('tong', 12888), ('tou', 12875),
    ('tu', 12871), ('tuan', 12860), ('tui', 12858), ('tun', 12852), ('tuo', 12849),
    ('wa', 12838), ('wai', 12831), ('wan', 12829), ('wang', 12812), ('wei', 12802), ('wen', 12607), ('weng', 12597),
    ('wo', 12594), ('wu', 12585),
    ('xi', 12556), ('xia', 12359), ('xian', 12346), ('xiang', 12320), ('xiao', 12300), ('xie', 12120), ('xin', 12099),
    ('xing', 12089), ('xiong', 12074), ('xiu', 12067), ('xu', 12058), ('xuan', 12039), ('xue', 11867), ('xun', 11861),
    ('ya', 11847), ('yan', 11831), ('yang', 11798), ('yao', 11781), ('ye', 11604), ('yi', 11589), ('yin', 11536),
    ('ying', 11358), ('yo', 11340), ('yong', 11339), ('you', 11324), ('yu', 11303), ('yuan', 11097), ('yue', 11077),
    ('yun', 11067),
    ('za', 11055), ('zai', 11052), ('zan', 11045), ('zang', 11041), ('zao', 11038), ('ze', 11024), ('zei', 11020),
    ('zen', 11019), ('zeng', 11018), ('zha', 11014), ('zhai', 10838), ('zhan', 10832), ('zhang', 10815), ('zhao', 10800),
    ('zhe', 10790), ('zhen', 10780), ('zheng', 10764), ('zhi', 10587), ('zhong', 10544), ('zhou', 10533), ('zhu', 10519),
    ('zhua', 10331), ('zhuai', 10329), ('zhuan', 10328), ('zhuang', 10322), ('zhui', 10315), ('zhun', 10309), ('zhuo', 10307),
    ('zi', 10296), ('zong', 10281), ('zou', 10274), ('zu', 10270), ('zuan', 10262), ('zui', 10260), ('zun', 10256), ('zuo', 10254);


-- -----------------------------
-- 汉语转拼音函数
-- -----------------------------
drop function if exists to_pinyin;
delimiter $

create function to_pinyin(input_val varchar(128) charset gbk)
    returns varchar(256) charset gbk
    deterministic
    no sql
begin
    declare character_code int;
    declare left_character_code varchar(2) charset gbk;
    declare left_code int;
    declare right_character_code varchar(2) charset gbk;
    declare right_code int;
    declare pinyin_result varchar(256) charset gbk default '';
    declare loop_position int;

    set character_code = 0;
    set loop_position = 1;
    set input_val = hex(input_val);

    while loop_position < length(input_val)
        do
            set left_character_code = substring(input_val, loop_position, 2);
            set left_code = cast(ascii(unhex(left_character_code)) as unsigned);
            set right_character_code = substring(input_val, loop_position + 2, 2);
            set right_code = cast(ascii(unhex(right_character_code)) as unsigned);

            if left_code > 128 then
                set character_code = 65536 - left_code * 256 - right_code;
                select concat(pinyin_result, pinyin)
                into pinyin_result
                from base_chinese_pinyin
                where code >= abs(character_code)
                order by code
                limit 1;
                set loop_position = loop_position + 4;
            else
                set pinyin_result = concat(pinyin_result, char(cast(ascii(unhex(substring(input_val, loop_position, 2))) as unsigned)));
                set loop_position = loop_position + 2;
            end if;
        end while;

    return lower(pinyin_result);
end;
$

delimiter ;


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

-- -----------------------------
-- 调查问卷表
-- -----------------------------
drop table if exists qs_survey_form;
create table qs_survey_form (
    form_id         bigint(20)    not null auto_increment  comment '问卷ID',
    form_code       bigint(20)    default null             comment '问卷编码',
    form_name       varchar(128)  default ''               comment '问卷名称',
    form_data       text                                   comment '问卷数据',
    publish_status  char(1)       default '0'              comment '发布状态{0=未发布, 1=已发布}',
    publish_time    datetime                               comment '发布时间',
    close_time      datetime                               comment '关闭时间',
    status          char(1)       default '0'              comment '状态{0=正常, 1=停用}',
    create_by       varchar(64)   default ''               comment '创建者',
    create_time     datetime                               comment '创建时间',
    update_by       varchar(64)   default ''               comment '更新者',
    update_time     datetime                               comment '更新时间',
    remark          varchar(500)  default ''               comment '备注',
    primary key (form_id)
) engine = InnoDB auto_increment = 100 comment = '调查问卷表';


-- -----------------------------
-- 调查问卷用户答案表
-- -----------------------------
drop table if exists qs_survey_user_answer;
create table qs_survey_user_answer (
    answer_id       bigint(20)    not null auto_increment  comment '答案ID',
    form_id         bigint(20)    default null             comment '问卷ID',
    answer_data     text                                   comment '答案数据',
    answer_time     bigint(20)    default 0                comment '答题耗时(毫秒)',
    ip              varchar(16)   default ''               comment 'IP地址',
    location        varchar(64)   default ''               comment '地点',
    mobile          varchar(8)    default ''               comment '是否为手机',
    browser         varchar(32)   default ''               comment '浏览器',
    version         varchar(16)   default ''               comment '版本',
    platform        varchar(16)   default ''               comment '平台',
    os              varchar(32)   default ''               comment '操作系统',
    os_version       varchar(16)   default ''               comment '系统版本',
    engine          varchar(16)   default ''               comment '引擎',
    engine_version  varchar(16)   default ''               comment '引擎版本',
    create_by       varchar(64)   default ''               comment '创建者',
    create_time     datetime                               comment '创建时间',
    update_by       varchar(64)   default ''               comment '更新者',
    update_time     datetime                               comment '更新时间',
    remark          varchar(500)  default ''               comment '备注',
    primary key (answer_id)
) engine = InnoDB auto_increment = 100 comment = '调查问卷用户答案表';

commit;
