# tang-boot

[**简体中文**](./README.md) | **English**

#### Project Description

tang-boot is a permission management system based on Spring Boot3

* Using Spring Boot3 + Spring Security + MyBatis + Redis + Jwt
* Customized multiple login authentication methods
* Dynamic permissions menu
* Code generator, one click to generate the basic front-end and back-end code

#### Environment Requirements

* OpenJDK 17.0.6
* Apache Maven 3.8.7
* Mysql 8.0.29
* Redis 7.0.2

#### Project Startup

1. execute the databases/20221210.sql file ( **contains drop database statement, please check it carefully and execute the sql file** )
2. modify tang-admin/src/main/resources/application-dev.yml password and other information
3. modify tang-admin/src/main/resources/logback.xml log storage path
4. ```
   $ mvn package && cd tang-admin/target && java -jar tang-admin.jar
   ```

#### Project address

| Project   | Gitee                                          | GitHub                                          |
| --------- | ---------------------------------------------- | ----------------------------------------------- |
| Back End  | [tang-boot](https://gitee.com/tangllty/tang-boot) | [tang-boot](https://github.com/tangllty/tang-boot) |
| Front End | [tang-vue](https://gitee.com/tangllty/tang-vue)   | [tang-vue](https://github.com/tangllty/tang-vue)   |
