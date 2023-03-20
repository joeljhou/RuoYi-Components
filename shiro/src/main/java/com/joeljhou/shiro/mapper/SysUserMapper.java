package com.joeljhou.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joeljhou.shiro.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 按用户名查找用户
     */
    SysUser findByLoginName(String userName);

}
