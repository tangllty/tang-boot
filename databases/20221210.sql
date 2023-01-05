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
) engine=InnoDB auto_increment=100 comment = '部门表';


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
) engine=InnoDB auto_increment=100 comment = '用户表';


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
) engine=InnoDB auto_increment=100 comment = '角色表';


-- -----------------------------
-- 角色表
-- -----------------------------
drop table if exists sys_menu;
create table sys_menu (
    menu_id      bigint(20)    not null auto_increment  comment '菜单ID',
    parent_id    bigint(20)    default 0                comment '父菜单ID',
    ancestors    varchar(128)  default ''               comment '祖级列表',
    menu_name    varchar(32)   default ''               comment '菜单名称',
    path         varchar(255)  default ''               comment '路由地址',
    component    varchar(255)  default ''               comment '组件路径',
    query        varchar(255)  default ''               comment '路由参数',
    perms        varchar(128)  default ''               comment '权限标识',
    icon         varchar(128)  default '#'              comment '菜单图标',
    menu_type    char(1)       default ''               comment '菜单类型{D=目录, M=菜单, B=按钮}',
    is_frame     char(1)       default '0'              comment '是否为外链{0=否, 1=是}',
    is_cache     char(1)       default '0'              comment '是否缓存{0=缓存, 1=不缓存}',
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
) engine=InnoDB auto_increment=100 comment = '菜单权限表';


-- -----------------------------
-- 用户与角色关联表
-- -----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
    user_id  bigint(20) not null  comment '用户ID',
    role_id  bigint(20) not null  comment '角色ID',
    primary key(user_id, role_id)
) engine=InnoDB comment = '用户与角色关联表';


-- -----------------------------
-- 角色与菜单关联表
-- -----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
    role_id  bigint(20) not null  comment '角色ID',
    menu_id  bigint(20) not null  comment '菜单ID',
    primary key(role_id, menu_id)
) engine=InnoDB comment = '角色与菜单关联表';


-- -----------------------------
-- 角色与部门关联表
-- -----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
    role_id  bigint(20) not null  comment '角色ID',
    dept_id  bigint(20) not null  comment '部门ID',
    primary key(role_id, dept_id)
) engine=InnoDB comment = '角色与部门关联表';

commit;