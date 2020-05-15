package com.alexshuvaev.topjava.gp.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Simple JavaBean domain object representing a Dish.
 *
 */
@Entity
@Table(name = "dish", uniqueConstraints = {@UniqueConstraint(columnNames = {"date"}, name = "dish_unique_date_idx")})
public class Dish extends AbstractNamedEntity {
    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "date", columnDefinition = "timestamp default now()", nullable = false)
    private LocalDate date;

    protected Dish() {
    }

    public Dish(Integer id, String name, BigDecimal price, Restaurant restaurant, LocalDate localDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.date = localDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Dish: " + super.toString() + ", price=" + price + ", restaurant: " + " id=" + restaurant.getId() + ", name=" + restaurant.getName();
    }
}
