package com.alexshuvaev.topjava.gp.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

public class RestaurantTo {
    private Integer id;

    @NotEmpty(message = "should not be empty")
    @Size(min = 2, max = 100, message = "should be in range 2 – 100 chars")
    @JsonProperty(value = "restaurant_name")
    private String name;

    @NotEmpty(message = "should not be empty")
    @JsonProperty(value = "restaurant_telephone")
    private String telephone;

    @NotEmpty(message = "should not be empty")
    @JsonProperty(value = "restaurant_address")
    private String address;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, @NotEmpty(message = "should not be empty") String name, String telephone, String address) {
        this.id = id;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantTo)) return false;
        RestaurantTo that = (RestaurantTo) o;
        return getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                Objects.equals(getTelephone(), that.getTelephone()) &&
                Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getTelephone(), getAddress());
    }

    @Override
    public String toString() {
        return "RestaurantTo: " + " id=" + id + ", name=" + name + ", telephone=" + telephone + ", address=" + address;
    }
}
