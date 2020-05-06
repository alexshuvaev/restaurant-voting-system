package com.alexshuvaev.topjava.gp.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

public class VoteTo {
    @JsonProperty(value = "vote_id")
    private int id;

    @NotNull
    @JsonProperty(value = "date_time")
    private LocalDateTime dateTime;

    @JsonProperty(value = "restaurant_id")
    private Integer restaurantId;

    @JsonProperty(value = "restaurant_name")
    private String restaurant_name;

    public VoteTo() {
    }

    public VoteTo(int id, @NotNull LocalDateTime dateTime, Integer restaurantId, String restaurant_name) {
        this.id = id;
        this.dateTime = dateTime;
        this.restaurantId = restaurantId;
        this.restaurant_name = restaurant_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteTo)) return false;
        VoteTo voteTo = (VoteTo) o;
        return getId() == voteTo.getId() &&
                getRestaurantId().equals(voteTo.getRestaurantId()) &&
                getRestaurant_name().equals(voteTo.getRestaurant_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRestaurantId(), getRestaurant_name());
    }

    @Override
    public String toString() {
        return "VoteTo: " + "id=" + id + ", dateTime=" + dateTime + ", restaurant id=" + restaurantId + ", restaurant name='" + restaurant_name;
    }
}
