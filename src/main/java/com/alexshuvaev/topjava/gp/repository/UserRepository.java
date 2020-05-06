package com.alexshuvaev.topjava.gp.repository;

import com.alexshuvaev.topjava.gp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Find User by email.
     * @param email of the User.
     * @return User
     */
    User getByEmail(@Email @NotBlank @Size(max = 100) String email);
}
