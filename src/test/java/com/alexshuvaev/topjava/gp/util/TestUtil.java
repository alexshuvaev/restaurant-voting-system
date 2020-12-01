package com.alexshuvaev.topjava.gp.util;

import com.alexshuvaev.topjava.gp.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtil {

    private TestUtil() {
    }

    public static final LocalDate YESTERDAY = LocalDate.now().minusDays(1);
    public static final LocalDate TODAY = LocalDate.now();

    public static final String YESTERDAY_STRING = YESTERDAY.format(DateTimeFormatter.ISO_DATE);
    public static final String TODAY_STRING = TODAY.format(DateTimeFormatter.ISO_DATE);

    public static RequestPostProcessor userHttpBasic(User user, String password) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), password);
    }

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
    }
}

