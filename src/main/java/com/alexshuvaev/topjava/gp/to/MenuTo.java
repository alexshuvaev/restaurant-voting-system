package com.alexshuvaev.topjava.gp.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class MenuTo {
    @JsonIgnore
    private LocalDate date;

    @JsonProperty(value = "restaurant_id")
    private Integer id;

    @JsonProperty(value = "restaurant_name")
    private String name;

    @JsonProperty(value = "restaurant_menu")
    private Set<DishTo> dishes;

    public MenuTo() {
    }

    public MenuTo(Integer id, String name, @NotNull LocalDate date, Set<DishTo> dishes) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.dishes = dishes;
    }

    public MenuTo(LocalDate date, Integer id, String name) {
        this.date = date;
        this.id = id;
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    @JsonProperty(value = "restaurant_menu")
    public Set<DishTo> getDishes() {
        return dishes;
    }

    public void setDishes(Set<DishTo> dishes) {
        this.dishes = dishes;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuTo)) return false;
        MenuTo menuTo = (MenuTo) o;
        return id.equals(menuTo.id) &&
                getName().equals(menuTo.getName()) &&
                getDate().equals(menuTo.getDate()) &&
                Objects.equals(getDishes(), menuTo.getDishes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getName(), getDate(), getDishes());
    }

    @Override
    public String toString() {
        return "MenuTo: " + " id=" + id + ", name=" + name + ", date=" + date + ", dishes=" + dishes;
    }
}
