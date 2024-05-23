package com.tang.commons.utils

import com.tang.commons.utils.StringUtils.format

/**
 * 该工具类用于生成菜单SQL，只需要修改generateMenuList方法即可
 *
 * @author Tang
 */
object GenSqlUtils {

    @JvmStatic
    fun main(args: Array<String>) {
        val menuSql = generateMenu()
        println(menuSql)
        val menuRoleSql = generateRole(2)
        println(menuRoleSql)
    }

    private fun generateMenu(): StringBuilder {
        val sql = StringBuilder()
        // 目录模板
        val directoryTemplate = "insert into sys_menu values ({}, {}, '{}', '{}', '{}',  '', '', '{}', 'D', '0', {}, '0', '0', 'admin', sysdate(), '', null, '{}');"
        // 菜单模板
        val menuTemplate = "insert into sys_menu values ({}, {}, '{}', '{}', '{}', '{}', '{}', '{}', 'M', '0', {}, '0', '0', 'admin', sysdate(), '', null, '{}');"
        // 按钮模板
        val buttonTemplate = "insert into sys_menu values ({}, {}, '{}', '{}', '', '', '{}', '', 'B', '0', {}, '0', '0', 'admin', sysdate(), '', null, '{}');"
        val menuList = generateMenuList()
        menuList.forEach {
            when (it.menuType) {
                "D" -> sql.append(format(directoryTemplate, it.menuId, it.parentId, it.ancestors, it.menuName, it.path, it.icon, it.sort, it.remark))
                "M" -> sql.append(format(menuTemplate, it.menuId, it.parentId, it.ancestors, it.menuName, it.path, it.component, it.permission, it.icon, it.sort, it.remark))
                "B" -> sql.append(format(buttonTemplate, it.menuId, it.parentId, it.ancestors, it.menuName, it.permission, it.sort, it.remark))
                else -> throw IllegalArgumentException("Invalid menu type: ${it.menuType}")
            }
            sql.append("\n")
        }
        return sql
    }

    private fun generateRole(roleId: Long): StringBuilder {
        val sql = StringBuilder()
        val roleMenuTemplate = "insert into sys_role_menu values ({}, {});"
        for (i in 1..menuId) {
            sql.append(format(roleMenuTemplate, roleId, i))
            sql.append("\n")
        }
        return sql
    }

    private fun generateMenuList(): List<Menu> {
        val menuTree = arrayListOf(
            Menu("系统管理", "system", "系统管理", "系统管理目录", arrayListOf(
                Menu("用户管理", "user", "system/user/index", "system:user:menu", "用户管理", "用户管理菜单", arrayListOf(
                    Menu("用户查询", "system:user:list", "用户查询按钮"),
                    Menu("用户新增", "system:user:add", "用户新增按钮"),
                    Menu("用户修改", "system:user:edit", "用户修改按钮"),
                    Menu("用户删除", "system:user:delete", "用户删除按钮"),
                )),
                Menu("部门管理", "dept", "system/dept/index", "system:dept:menu", "部门管理", "部门管理菜单", arrayListOf(
                    Menu("部门查询", "system:dept:list", "部门查询按钮"),
                    Menu("部门新增", "system:dept:add", "部门新增按钮"),
                    Menu("部门修改", "system:dept:edit", "部门修改按钮"),
                    Menu("部门删除", "system:dept:delete", "部门删除按钮"),
                )),
                Menu("角色管理", "role", "system/role/index", "system:role:menu", "角色管理", "角色管理菜单", arrayListOf(
                    Menu("角色查询", "system:role:list", "角色查询按钮"),
                    Menu("角色新增", "system:role:add", "角色新增按钮"),
                    Menu("角色修改", "system:role:edit", "角色修改按钮"),
                    Menu("角色删除", "system:role:delete", "角色删除按钮"),
                )),
                Menu("菜单管理", "menu", "system/menu/index", "system:menu:menu", "菜单管理", "菜单管理菜单", arrayListOf(
                    Menu("菜单查询", "system:menu:list", "菜单查询按钮"),
                    Menu("菜单新增", "system:menu:add", "菜单新增按钮"),
                    Menu("菜单修改", "system:menu:edit", "菜单修改按钮"),
                    Menu("菜单删除", "system:menu:delete", "菜单删除按钮"),
                )),
                Menu("字典管理", "dict", "system/dict/index", "system:dict:menu", "字典管理", "字典管理菜单", arrayListOf(
                    Menu("字典查询", "system:dict:list", "字典查询按钮"),
                    Menu("字典新增", "system:dict:add", "字典新增按钮"),
                    Menu("字典修改", "system:dict:edit", "字典修改按钮"),
                    Menu("字典删除", "system:dict:delete", "字典删除按钮"),
                )),
                Menu("日志管理", "log", "日志管理", "日志管理菜单", arrayListOf(
                    Menu("登陆日志", "login", "system/log/login/index", "system:log:login:menu", "登陆日志", "登陆日志菜单", arrayListOf(
                        Menu("登陆日志查询", "system:log:login:list", "登陆日志查询按钮"),
                        Menu("登陆日志删除", "system:log:login:delete", "登陆日志删除按钮"),
                    )),
                    Menu("接口日志", "api", "接口日志", "接口日志菜单", arrayListOf(
                        Menu("数据列表", "data", "system/log/api/data/index", "system:log:api:data:menu", "数据列表", "接口日志查询菜单", arrayListOf(
                            Menu("接口日志查询", "system:log:api:data:list", "接口日志查询按钮"),
                            Menu("接口日志删除", "system:log:api:data:delete", "接口日志删除按钮"),
                        )),
                        Menu("分析列表", "analysis", "system/log/api/analysis/index", "system:log:api:analysis:menu", "分析列表", "接口日志分析菜单", arrayListOf(
                            Menu("接口日志分析", "system:log:api:analysis:list", "接口日志分析按钮"),
                        )),
                    )),
                )),
            )),
            Menu("表单设计", "form-designer", "form/designer/index", "form:designer:menu", "表单设计", "表单设计菜单", arrayListOf()),
            Menu("Monaco Editor", "monaco-editor", "editor/monaco/index", "monaco:editor:menu", "Monaco Editor", "Monaco Editor菜单", arrayListOf()),
            Menu("系统监控", "monitor", "系统监控", "系统监控目录", arrayListOf(
                Menu("在线用户", "online", "monitor/online/index", "monitor:online:menu", "在线用户", "在线用户菜单", arrayListOf(
                    Menu("在线用户查询", "monitor:online:list", "在线用户查询按钮"),
                    Menu("在线用户删除", "monitor:online:delete", "在线用户删除按钮"),
                )),
                Menu("服务监控", "server", "monitor/server/index", "monitor:server:menu", "服务监控", "服务监控菜单", arrayListOf(
                    Menu("服务监控查询", "monitor:server:list", "服务监控查询按钮"),
                )),
                Menu("Druid 监控", "druid", "monitor/druid/index", "monitor:druid:menu", "Druid 监控", "Druid 监控菜单", arrayListOf()),
                Menu("SBA 监控", "sba", "monitor/sba/index", "monitor:sba:menu", "SBA 监控", "SBA 监控菜单", arrayListOf()),
            )),
            Menu("系统工具", "tool", "系统工具", "系统工具目录", arrayListOf(
                Menu("代码生成", "generator", "tool/generator/index", "tool:generator:menu", "代码生成", "代码生成菜单", arrayListOf(
                    Menu("代码生成查询", "tool:generator:list", "代码生成查询按钮"),
                    Menu("代码生成修改", "tool:generator:edit", "代码生成修改按钮"),
                    Menu("代码生成删除", "tool:generator:delete", "代码生成删除按钮"),
                    Menu("代码生成导入", "tool:generator:import", "代码生成导入按钮"),
                    Menu("代码生成导出", "tool:generator:export", "代码生成导出按钮"),
                    Menu("代码生成执行", "tool:generator:execute", "代码生成执行按钮"),
                    Menu("代码生成同步", "tool:generator:sync", "代码生成同步按钮"),
                )),
                Menu("系统接口", "swagger", "tool/swagger/index", "tool:swagger:menu", "系统接口", "系统接口菜单", arrayListOf(
                    Menu("系统接口查询", "tool:swagger:list", "系统接口查询按钮"),
                )),
            )),
            Menu("猫猫APP", "app", "猫猫APP", "猫猫APP目录", arrayListOf(
                Menu("猫猫聊天", "chat", "猫猫聊天", "猫猫聊天目录", arrayListOf(
                    Menu("我的聊天", "room", "app/chat/room/index", "app:chat:room:menu", "我的聊天", "猫猫聊天目录", arrayListOf(
                        Menu("聊天列表查询", "app:chat:chat-list:list", "聊天列表查询按钮"),
                        Menu("聊天列表新增", "app:chat:chat-list:add", "聊天列表新增按钮"),
                        Menu("聊天列表修改", "app:chat:chat-list:edit", "聊天列表修改按钮"),
                        Menu("聊天列表删除", "app:chat:chat-list:delete", "聊天列表删除按钮"),
                        Menu("聊天消息查询", "app:chat:message:list", "聊天消息查询按钮"),
                        Menu("聊天消息新增", "app:chat:message:add", "聊天消息新增按钮"),
                        Menu("聊天消息修改", "app:chat:message:edit", "聊天消息修改按钮"),
                        Menu("聊天消息删除", "app:chat:message:delete", "聊天消息删除按钮"),
                    )),
                    Menu("好友申请", "apply", "app/chat/friend-apply/index", "app:chat:friend-apply:menu", "好友申请", "好友申请目录", arrayListOf(
                        Menu("好友申请查询", "app:chat:friend-apply:list", "好友申请查询按钮"),
                        Menu("好友申请新增", "app:chat:friend-apply:add", "好友申请新增按钮"),
                        Menu("好友申请修改", "app:chat:friend-apply:edit", "好友申请修改按钮"),
                        Menu("好友申请同意", "app:chat:friend-apply:accept", "好友申请同意按钮"),
                        Menu("好友申请拒绝", "app:chat:friend-apply:decline", "好友申请拒绝按钮"),
                        Menu("好友申请删除", "app:chat:friend-apply:delete", "好友申请删除按钮"),
                    )),
                    Menu("我的好友", "friend", "app/chat/friend/index", "app:chat:friend:menu", "我的好友", "我的好友目录", arrayListOf(
                        Menu("我的好友查询", "app:chat:friend:list", "我的好友查询按钮"),
                        Menu("我的好友新增", "app:chat:friend:add", "我的好友新增按钮"),
                        Menu("我的好友修改", "app:chat:friend:edit", "我的好友修改按钮"),
                        Menu("我的好友删除", "app:chat:friend:delete", "我的好友删除按钮"),
                    )),
                )),
            )),
        )

        val parentMenu = Menu()
        parentMenu.menuId = 0
        parentMenu.ancestors = "0"
        generateMenuId(menuTree, parentMenu)

        val menuList = ArrayList<Menu>()
        treeToList(menuTree, menuList)
        return menuList
    }

    private fun treeToList(menuTree: List<Menu>, menuList: ArrayList<Menu>) {
        val childrenList = menuTree.flatMap {
            menuList.add(it)
            it.children ?: arrayListOf()
        }
        if (childrenList.isEmpty()) {
            return
        }
        treeToList(childrenList, menuList)
    }

    private var menuId = 0L

    private fun generateMenuId(menuTree: List<Menu>, parentMenu: Menu) {
        menuTree.forEachIndexed { index, it ->
            ++menuId
            it.menuId = menuId
            it.parentId = parentMenu.menuId
            it.ancestors = if (parentMenu.menuId == 0L) "0" else "${parentMenu.ancestors},${parentMenu.menuId}"
            it.sort = index + 1
        }

        menuTree
            .filter { it.children != null && it.children!!.isNotEmpty() }
            .forEach { generateMenuId(it.children!!, it) }
    }

    class Menu {

        constructor()

        /**
         * 构造目录
         */
        constructor(
            menuName: String?,
            path: String?,
            icon: String?,
            remark: String?,
            children: List<Menu>?
        ) {
            this.menuName = menuName
            this.path = path
            this.icon = icon
            this.menuType = "D"
            this.remark = remark
            this.children = children
        }

        /**
         * 构造菜单
         */
        constructor(
            menuName: String?,
            path: String?,
            component: String?,
            permission: String?,
            icon: String?,
            remark: String?,
            children: List<Menu>?
        ) {
            this.menuName = menuName
            this.path = path
            this.component = component
            this.permission = permission
            this.icon = icon
            this.menuType = "M"
            this.remark = remark
            this.children = children
        }

        /**
         * 构造按钮
         */
        constructor(menuName: String?, permission: String?, remark: String?) {
            this.menuName = menuName
            this.permission = permission
            this.menuType = "B"
            this.remark = remark
        }


        /**
         * 菜单ID
         */
        var menuId: Long? = null

        /**
         * 父菜单ID
         */
        var parentId: Long? = null

        /**
         * 祖级列表
         */
        var ancestors: String? = null

        /**
         * 菜单名称
         */
        var menuName: String? = null

        /**
         * 路由地址
         */
        var path: String? = null

        /**
         * 组件路径
         */
        var component: String? = null

        /**
         * 权限标识
         */
        var permission: String? = null

        /**
         * 菜单图标
         */
        var icon: String? = null

        /**
         * 菜单类型（M目录 C菜单 F按钮）
         */
        var menuType: String? = null

        /**
         * 菜单状态（0显示 1隐藏）
         */
        var visible: String? = null

        /**
         * 显示顺序
         */
        var sort: Int? = null

        /**
         * 菜单状态（0正常 1停用）
         */
        var status: String? = null

        /**
         * 删除标志
         */
        var delFlag: String? = null

        /**
         * 备注
         */
        var remark: String? = null

        /**
         * 子菜单
         */
        var children: List<Menu>? = null

    }

}
