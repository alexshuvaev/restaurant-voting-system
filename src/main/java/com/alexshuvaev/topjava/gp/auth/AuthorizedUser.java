package com.alexshuvaev.topjava.gp.auth;

import com.alexshuvaev.topjava.gp.domain.User;
import com.alexshuvaev.topjava.gp.to.UserTo;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private final UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true,
                true, true, true, user.getRoles());
        this.userTo = new UserTo(user);
    }

    public Integer getId() {
        return userTo.getId();
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}