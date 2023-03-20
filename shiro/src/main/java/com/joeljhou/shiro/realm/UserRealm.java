package com.joeljhou.shiro.realm;

import com.joeljhou.shiro.constant.ShiroConstants;
import com.joeljhou.shiro.pojo.SysUser;
import com.joeljhou.shiro.service.ISysMenuService;
import com.joeljhou.shiro.service.ISysRoleService;
import com.joeljhou.shiro.service.ISysUserService;
import com.joeljhou.shiro.utils.servlet.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysMenuService sysMenuService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了==》授权doGetAuthorizationInfo");
        SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
        //角色
        Set<String> roles = sysRoleService.findRolesByUserId(sysUser.getUserId());
        //权限
        Set<String> permissions = sysMenuService.findPermissionsByUserId(sysUser.getUserId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了==》认证doGetAuthenticationInfo");
        // 验证码校验
        if (ShiroConstants.CAPTCHA_ERROR.equals(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
            throw new IncorrectCredentialsException("验证码错误或不存在");
        }

        // 实现身份认证逻辑
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }
        SysUser user = sysUserService.findByLoginName(username);
        if (Objects.isNull(user) || !user.getPassword().equals(password)) {
            throw new UnknownAccountException("用户不存在 或 密码不正确");
        }
        // 返回认证信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
