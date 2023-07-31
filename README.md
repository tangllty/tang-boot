# tang-boot

**简体中文** | [**English**](./README.en.md)

## 项目简介

tang-boot 是基于 Spring Boot 3 的权限管理系统

您可以阅读[文档](https://tangllty.eu.org/)获取更多信息

* 采用 Spring Boot 3 + Spring Security + MyBatis + Redis + Jwt
* 自定义多种登录认证方式
* 动态权限菜单
* 代码生成器，一键生成前后端基本代码
* 即时通信，随时随地收发好友和群消息，文件互传 (v1.6.0)

## 环境要求

* OpenJDK 17.0.7
* Apache Maven 3.8.7
* MySQL 8.0.33
* Redis 7.0.11

## 项目启动

1. 执行 databases/20221210.sql 文件 ( **包含 drop database 语句, 请仔细查看后执行 sql 文件** )
2. 修改 tang-admin/src/main/resources/application-dev.yml password 等信息
3. 修改 tang-admin/src/main/resources/logback.xml 日志存放路径
4. ```bash
   sh bin/package.sh && sh bin/start.sh
   ```

## 贡献

如果你有任何问题、建议或发现了 bug，请提交 issue 或提供 Pull Request 来帮助改进该项目。

## 许可证

Tang-Boot 使用 MIT 许可证。更多详情请查阅 [LICENSE](./LICENSE) 文件。

## 项目地址

| 项目 | Gitee                                          | GitHub                                          |
| ---- | ---------------------------------------------- | ----------------------------------------------- |
| 后端 | [tang-boot](https://gitee.com/tangllty/tang-boot) | [tang-boot](https://github.com/tangllty/tang-boot) |
| 前端 | [tang-vue](https://gitee.com/tangllty/tang-vue)   | [tang-vue](https://github.com/tangllty/tang-vue)   |

## 交流群

- 微信

  - ![WeChat](https://gitee.com/tangllty/tang-docs/raw/master/docs/public/wechat.png)
- Telegram
- QQ
