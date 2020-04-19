package com.example.demo.realm;

import com.example.demo.encry.SymmetricEncoder;
import com.example.demo.pojo.User;
import com.example.demo.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

//然后在com.majiaxueyuan.realm写一个UserRealm做登录权限控制：
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private IUserService iUserService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 这里做权限控制
        return null;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 这里做登录控制
        AuthenticationInfo authenticationInfo = null;
        String principal = (String) token.getPrincipal();
        char[] credentials = (char[]) token.getCredentials();
        String password = String.valueOf(credentials);
        User user = iUserService.selectUserByName(principal, password);
        if(user != null){
            authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),SymmetricEncoder.AESDncode(user.getPassword()),"realmName");
        }
        return authenticationInfo;
    }
}
