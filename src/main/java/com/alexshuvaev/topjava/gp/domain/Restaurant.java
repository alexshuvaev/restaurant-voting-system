package com.alexshuvaev.topjava.gp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Simple JavaBean domain object representing a Restaurant.
 *
 */
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "telephone")
    private String telephone;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @OneToMany(
            mappedBy = "restaurant",
            fetch = FetchType.LAZY)
    private Set<Dish> dishes = new HashSet<>();

    protected Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Set<Dish> dishes) {
        this(id, name);
        this.dishes = dishes;
    }

    public Restaurant(Integer id, String name, Set<Dish> dishes, String telephone, String address) {
        this(id, name, dishes);
        this.telephone = telephone;
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return super.toString() + ", telephone=" + telephone + ", address=" + address + ", menus:\n" + dishes;
    }
}
