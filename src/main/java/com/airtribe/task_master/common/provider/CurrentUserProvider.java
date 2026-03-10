package com.airtribe.task_master.common.provider;

import com.airtribe.task_master.config.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    public Long getCurrentUserId() {
        CustomUserDetails user =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return user.getUserId();
    }

    public String getCurrentUserEmail() {
        CustomUserDetails user =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return user.getUsername();
    }
}