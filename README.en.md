# tang-boot

[**简体中文**](./README.md) | **English**

#### Project Description

tang-boot is a permission management system based on Spring Boot 3

You can read the [documentation](https://tangllty.eu.org/) for more information

* Using Spring Boot 3 + Spring Security + MyBatis + Redis + Jwt
* Customized multiple login authentication methods
* Dynamic permissions menu
* Code generator, one click to generate the basic front-end and back-end code
* Instant messaging, sending and receiving friends and group messages anytime, anywhere, and transferring files to each other (v1.6.0)

#### Environment Requirements

* OpenJDK 17.0.7
* Apache Maven 3.8.7
* MySQL 8.0.33
* Redis 7.0.11

#### Project Startup

1. execute the databases/20221210.sql file ( **contains drop database statement, please check it carefully and execute the sql file** )
2. modify tang-admin/src/main/resources/application-dev.yml password and other information
3. modify tang-admin/src/main/resources/logback.xml log storage path
4. ```bash
   sh package.sh && sh bin/start.sh
   ```

#### Project address

| Project   | Gitee                                          | GitHub                                          |
| --------- | ---------------------------------------------- | ----------------------------------------------- |
| Back End  | [tang-boot](https://gitee.com/tangllty/tang-boot) | [tang-boot](https://github.com/tangllty/tang-boot) |
| Front End | [tang-vue](https://gitee.com/tangllty/tang-vue)   | [tang-vue](https://github.com/tangllty/tang-vue)   |
