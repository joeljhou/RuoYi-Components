package com.joeljhou.shiro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joeljhou.shiro.pojo.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> findPermissionsByUserId(Long userId);

}
