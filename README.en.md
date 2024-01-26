# tang-boot

[![CodeQL](https://github.com/tangllty/tang-boot/actions/workflows/codeql.yml/badge.svg)](https://github.com/tangllty/tang-boot/actions/workflows/codeql.yml)
[![Java CI with Maven](https://github.com/tangllty/tang-boot/actions/workflows/maven.yml/badge.svg)](https://github.com/tangllty/tang-boot/actions/workflows/maven.yml)
[![Build and Deploy](https://github.com/tangllty/tang-boot/actions/workflows/deploy.yml/badge.svg)](https://github.com/tangllty/tang-boot/actions/workflows/deploy.yml)

[**简体中文**](./README.md) | **English**

## Project Description

tang-boot is a permission management system based on Spring Boot 3, written in Java and Kotlin

You can read the [documentation](https://tangllty.eu.org/) for more information

* Using Spring Boot 3 + Spring Security + MyBatis + Redis + Jwt
* Support Java and Kotlin, etc.
* Support MyBatis and MyBatis-Plus, etc.
* Customized multiple login authentication methods
* Dynamic permissions menu
* Dynamic dictionary data permissions
* Code generator, one click to generate the basic front-end and back-end code
* Encapsulate WebSocket, support heartbeat detection, reconnection mechanism, custom message type push and subscription
* Instant messaging, sending and receiving friends and group messages anytime, anywhere, and transferring files to each other
* Friendly code structure and comments, easy to read and secondary development

## Project Preview

* Online Preview: [http://116.196.102.213/](http://116.196.102.213/)
* Account password: admin / 123456

### Server Sponsorship

* [@RyanHo97](https://github.com/RyanHo97/)

## Environment Requirements

* OpenJDK 17.0.7
* Apache Maven 3.8.7
* MySQL 8.2.0
* Redis 7.2.3

## Project Startup

1. execute the databases/20221210.sql file ( **contains drop database statement, please check it carefully and execute the sql file** )
2. modify tang-admin/src/main/resources/application-dev.yml password and other information
3. modify tang-admin/src/main/resources/logback.xml log storage path
4. ```bash
   sh bin/package.sh && sh bin/start.sh
   ```

## Contributing

If you have any questions, suggestions, or find bugs, please submit an [Issues](https://github.com/tangllty/tang-boot/issues/new) or provide a [Pull Request](https://github.com/tangllty/tang-boot/pull/new) to help improve the project.

## License

tang-boot uses the MIT license. For more details, please refer to [LICENSE](https://github.com/tangllty/tang-boot/blob/master/LICENSE) files.

## Project Address

| Project       | Gitee                                          | GitHub                                          | GitCode                                                 |
| ------------- | ---------------------------------------------- | ----------------------------------------------- | ------------------------------------------------------- |
| Back End      | [tang-boot](https://gitee.com/tangllty/tang-boot) | [tang-boot](https://github.com/tangllty/tang-boot) | [tang-boot](https://gitcode.net/weixin_45456454/tang-boot) |
| Front End     | [tang-vue](https://gitee.com/tangllty/tang-vue)   | [tang-vue](https://github.com/tangllty/tang-vue)   | [tang-vue](https://gitcode.net/weixin_45456454/tang-vue)   |
| Documentation | [tang-docs](https://gitee.com/tangllty/tang-docs) | [tang-docs](https://github.com/tangllty/tang-docs) | [tang-docs](https://gitcode.net/weixin_45456454/tang-docs) |

## Discussion Group

- WeChat

  - ![WeChat](https://github.com/tangllty/tang-docs/raw/master/docs/public/wechat.png)
- Telegram
- QQ

  - ![QQ](https://github.com/tangllty/tang-docs/raw/master/docs/public/qq.png)
