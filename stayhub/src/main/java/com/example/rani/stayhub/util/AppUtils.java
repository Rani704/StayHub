package com.example.rani.stayhub.util;

import org.springframework.security.core.context.SecurityContextHolder;
import com.example.rani.stayhub.entity.User;

public class AppUtils {
      public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
