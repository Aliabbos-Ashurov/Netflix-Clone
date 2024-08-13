package com.pdp.config.security;

import com.pdp.model.Users;
import com.pdp.service.AuthService;
import com.pdp.service.RolePermissionsService;
import com.pdp.service.UserRolesService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  16:50
 **/
@Getter
public class CustomUserDetails implements UserDetails {

    private final Users user;
    private final Set<GrantedAuthority> authorities;

    public CustomUserDetails(Users user, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
