package com.joeljhou.shiro.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.joeljhou.shiro.pojo.SysMenu;

import java.util.Set;

public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> findPermissionsByUserId(Long userId);

}
