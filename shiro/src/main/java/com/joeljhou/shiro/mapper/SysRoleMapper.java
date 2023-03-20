package com.joeljhou.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joeljhou.shiro.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> findRolesByUserId(Long userId);

}
