package com.alexshuvaev.topjava.gp.util;

import com.alexshuvaev.topjava.gp.domain.Dish;
import com.alexshuvaev.topjava.gp.domain.Restaurant;
import com.alexshuvaev.topjava.gp.domain.Vote;
import com.alexshuvaev.topjava.gp.to.DishTo;
import com.alexshuvaev.topjava.gp.to.MenuTo;
import com.alexshuvaev.topjava.gp.to.RestaurantTo;
import com.alexshuvaev.topjava.gp.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility methods for transform domains object to DTO and back.
 *
 */
public final class ToUtil {

    private ToUtil() {
    }

    public static Map<LocalDate, List<MenuTo>> allMenuTosCreate(List<Dish> dishes) {
        Set<MenuTo> menuToList = dishes.stream()
                .map(e -> new MenuTo(e.getDate(), e.getRestaurant().getId(), e.getRestaurant().getName()))
                .collect(Collectors.toSet());

        List<MenuTo> menuTos = menuToList.stream()
                .peek(e -> e.setDishes(filterDishes(e, dishes)))
                .sorted(Comparator.comparing(MenuTo::getId))
                .collect(Collectors.toList());

        return menuTos.stream().collect(Collectors.groupingBy(MenuTo::getDate));
    }

    private static Set<DishTo> filterDishes(MenuTo menuTo, List<Dish> dishes) {
        List<Dish> disheList = dishes.stream()
                .filter(e -> e.getDate().equals(menuTo.getDate()))
                .filter(e -> e.getRestaurant().getName().equals(menuTo.getName()))
                .collect(Collectors.toList());

        return disheList.stream()
                .map(e -> new DishTo(e.getName(), e.getPrice())).collect(Collectors.toSet());
    }

    public static Restaurant restaurantCreate(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), Collections.emptySet(), restaurantTo.getTelephone(), restaurantTo.getAddress());
    }

    public static RestaurantTo restaurantToCreate(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getTelephone(), restaurant.getAddress());
    }

    public static Set<Dish> dishesCreate(Set<DishTo> dishes, Restaurant restaurant) {
        return dishes.stream()
                .map(e -> new Dish(null, e.getName(), e.getPrice(), restaurant, LocalDate.now())).collect(Collectors.toSet());
    }

    public static List<RestaurantTo> allRestaurantTosCreate(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(e-> new RestaurantTo(e.getId(), e.getName(), e.getTelephone(), e.getAddress())).collect(Collectors.toList());
    }

    public static List<VoteTo> voteTosCreate(List<Vote> votes) {
        return votes.stream()
                .map(ToUtil::voteToCreate)
                .collect(Collectors.toList());
    }

    public static VoteTo voteToCreate(Vote vote){
        return new VoteTo(vote.getId(), LocalDateTime.of(vote.getDate(), vote.getTime()), vote.getRestaurant().getId(), vote.getRestaurantName());
    }
}
