package com.ecom.BackendForEcommerce.utility;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtility {
    public static String getUserEmailFromSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
