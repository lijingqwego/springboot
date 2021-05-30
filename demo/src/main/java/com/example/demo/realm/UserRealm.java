package com.example.demo.realm;

import com.example.demo.encry.SymmetricEncoder;
import com.example.demo.pojo.Perm;
import com.example.demo.pojo.Role;
import com.example.demo.pojo.User;
import com.example.demo.service.IPermService;
import com.example.demo.service.IRoleService;
import com.example.demo.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录权限控制
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IPermService iPermService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 这里做权限控制
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String principal = (String) principals.getPrimaryPrincipal();
        User user = iUserService.selectUserByName(principal);
        int roleId = user.getRoleId();
        Role role = iRoleService.selectRoleById(roleId);

        authorizationInfo.addRole(role.getRoleName());

        List<Perm> perms = iPermService.selectPermListByRoleId(role.getRoleId());
        List<String> permList = new ArrayList<>();
        perms.forEach(perm -> {
            permList.add(perm.getPermName());
        });
        authorizationInfo.addStringPermissions(permList);
        return authorizationInfo;
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 这里做登录控制
        AuthenticationInfo authenticationInfo = null;
        String principal = (String) token.getPrincipal();
        char[] credentials = (char[]) token.getCredentials();
        String password = String.valueOf(credentials);
        User user = iUserService.selectUserByNameAndPwd(principal, password);
        if(user != null){
            authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),SymmetricEncoder.AESDncode(user.getPassword()),"realmName");
        }
        return authenticationInfo;
    }

}
