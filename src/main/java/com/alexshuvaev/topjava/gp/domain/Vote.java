package com.alexshuvaev.topjava.gp.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Simple JavaBean domain object representing a Vote.
 *
 */
@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "time"}, name = "vote_uniq_date_time_idx"),
        @UniqueConstraint(columnNames = {"user_email_history"}, name = "vote_uniq_user_idx"),
        @UniqueConstraint(columnNames = {"restaurant_name_history"}, name = "vote_uniq_restaurant_idx"),
})
public class Vote extends AbstractBaseEntity {
    @Column(name = "date", nullable = false, columnDefinition = "timestamp default current_date()")
    private LocalDate date;

    @Column(name = "time", nullable = false, columnDefinition = "timestamp default current_time()")
    private LocalTime time;

    /**
     * Contains the voted User, or null if User was deleted.
     */
    @JsonProperty(value = "userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Contains the email of the user who voted, whether the user was deleted or not.
     */
    @Column(name = "user_email_history")
    private String userEmail;

    /**
     * Contains the restaurant voted for or null, if restaurant was deleted. Need for history.
     */
    @JsonProperty(value = "restaurantId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    /**
     * Contains the restaurant name voted for, whether the restaurant was deleted already or not. Need for history.
     */
    @Column(name = "restaurant_name_history")
    private String restaurantName;

    protected Vote() {
    }

    public Vote(User user, Restaurant restaurant) {
        super(null);
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.user = user;
        this.userEmail = user.getEmail();
        this.restaurant = restaurant;
        this.restaurantName = restaurant.getName();
    }

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate date, LocalTime time) {
        super(id);
        this.user = user;
        this.userEmail = user.getEmail();
        this.restaurant = restaurant;
        this.restaurantName = restaurant.getName();
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public String toString() {
        return super.toString() + ", date=" + date + ", time=" + time + ", user id=" + user.getId() + ", restaurant id=" + restaurant.getId();
    }
}
