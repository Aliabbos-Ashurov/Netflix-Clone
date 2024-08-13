package com.pdp.utils;

import com.pdp.config.security.CustomUserDetails;
import com.pdp.model.Users;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Aliabbos Ashurov
 * @since 08/August/2024  09:38
 **/
@Component
public class SessionUser {
    public Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUser();
        }
        return null;
        //public String createBlog(@AuthenticationPrincipal CustomUserDetails userDetails) {
    }
}
