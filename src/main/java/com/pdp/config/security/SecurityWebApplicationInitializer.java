package com.pdp.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  10:52
 **/
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
