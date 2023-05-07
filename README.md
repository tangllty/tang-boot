# tang-boot

**简体中文** | [**English**](./README.en.md)

#### 项目简介

tang-boot 是基于 Spring Boot 3 的权限管理系统

* 采用 Spring Boot 3 + Spring Security + MyBatis + Redis + Jwt
* 自定义多种登录认证方式
* 动态权限菜单
* 代码生成器，一键生成前后端基本代码

#### 环境要求

* OpenJDK 17.0.7
* Apache Maven 3.8.7
* Mysql 8.0.29
* Redis 7.0.2

#### 项目启动

1. 执行 databases/20221210.sql 文件 ( **包含 drop database 语句, 请仔细查看后执行 sql 文件** )
2. 修改 tang-admin/src/main/resources/application-dev.yml password 等信息
3. 修改 tang-admin/src/main/resources/logback.xml 日志存放路径
4. ```
   $ mvn package && sh bin/start.sh
   ```

#### 项目地址

| 项目 | Gitee                                          | GitHub                                          |
| ---- | ---------------------------------------------- | ----------------------------------------------- |
| 后端 | [tang-boot](https://gitee.com/tangllty/tang-boot) | [tang-boot](https://github.com/tangllty/tang-boot) |
| 前端 | [tang-vue](https://gitee.com/tangllty/tang-vue)   | [tang-vue](https://github.com/tangllty/tang-vue)   |
