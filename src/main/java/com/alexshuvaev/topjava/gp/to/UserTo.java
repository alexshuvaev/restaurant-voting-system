package com.alexshuvaev.topjava.gp.to;

import com.alexshuvaev.topjava.gp.domain.Role;
import com.alexshuvaev.topjava.gp.domain.User;

import java.util.Date;
import java.util.Set;

public class UserTo {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean enabled;
    private Date registered;
    private Set<Role> roles;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password, boolean enabled, Date registered, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        this.roles = roles;
    }

    public UserTo(User user) {
        this(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                true,
                user.getRegistered(),
                user.getRoles()
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserTo: " + "id=" + id + ", name=" + name + ", email=" + email + ", registered=" + registered +
                ", roles=" + roles;
    }
}
