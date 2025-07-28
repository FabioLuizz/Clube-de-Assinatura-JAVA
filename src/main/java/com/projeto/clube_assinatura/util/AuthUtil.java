package com.projeto.clube_assinatura.util;

import com.projeto.clube_assinatura.config.SecurityConfig;
import com.projeto.clube_assinatura.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

    public static String getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();

    }

}
