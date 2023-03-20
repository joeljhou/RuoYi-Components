package com.joeljhou.shiro.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.joeljhou.shiro.pojo.SysRole;

import java.util.Set;

public interface ISysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    Set<String> findRolesByUserId(Long userId);

}
