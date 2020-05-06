package com.alexshuvaev.topjava.gp.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class DishTo {
    @NotEmpty(message = "should not be empty")
    @Size(min = 2, max = 100, message = "should be in range 2 – 100 chars")
    @JsonProperty(value = "dish_name")
    private String name;

    @DecimalMin(value = "0.1", message = "should be upper 0.1")
    @JsonProperty(value = "dish_price")
    private double price;

    public DishTo() {
    }

    public DishTo(@NotEmpty(message = "should not be empty") String name, @DecimalMin(value = "0.1", message = "should be upper 0.1") double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishTo)) return false;
        DishTo dishTo = (DishTo) o;
        return Double.compare(dishTo.getPrice(), getPrice()) == 0 &&
                getName().equals(dishTo.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice());
    }

    @Override
    public String toString() {
        return "DishTo: " + "name=" + name + ", price=" + price;
    }
}
