package com.joeljhou.shiro.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.joeljhou.shiro.pojo.SysUser;

public interface ISysUserService extends IService<SysUser> {

    SysUser findByLoginName(String loginName);

}
