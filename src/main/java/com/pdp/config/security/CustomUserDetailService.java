package com.pdp.config.security;

import com.pdp.model.Permission;
import com.pdp.model.Users;
import com.pdp.service.AuthService;
import com.pdp.service.RolePermissionsService;
import com.pdp.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  11:14
 **/
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AuthService authService;
    private final UserRolesService userRolesService;
    private final RolePermissionsService rolePermissionsService;

    @Autowired
    public CustomUserDetailService(AuthService authService, UserRolesService userRolesService, RolePermissionsService rolePermissionsService) {
        this.authService = authService;
        this.userRolesService = userRolesService;
        this.rolePermissionsService = rolePermissionsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = authService.findByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        userRolesService.getUserRoles(user.getId())
                .forEach(role -> {
                    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                    rolePermissionsService.getRolePermissions(role.getId())
                            .forEach(permission -> {
                                grantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
                            });
                });
        return new CustomUserDetails(user, grantedAuthorities);
    }
}
