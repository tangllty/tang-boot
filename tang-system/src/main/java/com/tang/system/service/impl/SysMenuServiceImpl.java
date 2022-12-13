package com.tang.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tang.system.entity.SysMenu;
import com.tang.system.mapper.SysMenuMapper;
import com.tang.system.service.SysMenuService;

/**
 * 菜单权限表 SysMenu 表服务实现类
 *
 * @author Tang
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    /**
     * 获取菜单列表
     *
     * @param menu 菜单对象
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> selectMenuList(SysMenu menu) {
        return menuMapper.selectMenuList(menu);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param menuId 主键
     * @return 菜单对象
     */
    @Override
    public SysMenu selectMenuByMenuId(Long menuId) {
        return menuMapper.selectMenuByMenuId(menuId);
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @Override
    public int insertMenu(SysMenu menu) {
        return menuMapper.insertMenu(menu);
    }

    /**
     * 修改菜单信息
     *
     * @param menu 菜单对象
     * @return 影响行数
     */
    @Override
    public int updateMenuByMenuId(SysMenu menu) {
        return menuMapper.updateMenuByMenuId(menu);
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    @Override
    public int deleteMenuByMenuId(Long menuId) {
        return menuMapper.deleteMenuByMenuId(menuId);
    }

}
