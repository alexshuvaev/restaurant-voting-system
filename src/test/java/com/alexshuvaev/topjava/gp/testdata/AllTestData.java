package com.alexshuvaev.topjava.gp.testdata;

import com.alexshuvaev.topjava.gp.domain.Dish;
import com.alexshuvaev.topjava.gp.domain.Restaurant;
import com.alexshuvaev.topjava.gp.domain.Vote;
import com.alexshuvaev.topjava.gp.to.DishTo;
import com.alexshuvaev.topjava.gp.to.MenuTo;
import com.alexshuvaev.topjava.gp.to.RestaurantTo;
import com.alexshuvaev.topjava.gp.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.alexshuvaev.topjava.gp.testdata.UserTestData.USER;
import static com.alexshuvaev.topjava.gp.util.TestUtil.TODAY;

public final class AllTestData {
    private AllTestData() {
    }

    private static final String
            restaurant_1_name = "La Maisie",
            restaurant_2_name = "Courage Couture",
            restaurant_3_name = "Royal Palace";

    private static final String
            restaurant_1_tel = "(416) 901-1050",
            restaurant_2_tel = "(416) 203-1121",
            restaurant_3_tel = "+14169162099";

    private static final String
            restaurant_1_address = "211 King St W, Toronto",
            restaurant_2_address = "2281 Lake Shore Blvd W, Etobicoke, ON. M8V1C5",
            restaurant_3_address = "150 Eglinton Avenue East, Toronto M5H 3H1";

    // Restaurants

    public static final Restaurant RESTAURANT_1 = new Restaurant(1, restaurant_1_name, Collections.emptySet(), restaurant_1_tel, restaurant_1_address);
    public static final Restaurant RESTAURANT_2 = new Restaurant(2, restaurant_2_name, Collections.emptySet(), restaurant_2_tel, restaurant_2_address);
    public static final Restaurant RESTAURANT_3 = new Restaurant(3, restaurant_3_name, Collections.emptySet(), restaurant_3_tel, restaurant_3_address);

    public static final String newString = " new";
    public static final Restaurant NEW_RESTAURANT = new Restaurant(4, restaurant_3_name + newString, Collections.emptySet(), restaurant_3_tel + newString, restaurant_3_address + newString);

    public static final String updString = " upd";
    public static final Restaurant UPD_RESTAURANT = new Restaurant(1, restaurant_3_name + updString, Collections.emptySet(), restaurant_3_tel + updString, restaurant_3_address + updString);

    // Dishes

    public static final Dish DISH_15_R1 = new Dish(15, "Stuffed Grape Leaves", 7.99, RESTAURANT_1, TODAY.minusDays(1));
    public static final Dish DISH_16_R1 = new Dish(16, "Flank Steak", 22.99, RESTAURANT_1, TODAY.minusDays(1));

    public static final Dish DISH_17_R2 = new Dish(17, "Parmesan Chicken", 16.49, RESTAURANT_2, TODAY.minusDays(1));
    public static final Dish DISH_18_R2 = new Dish(18, "Caesar salad", 9.99, RESTAURANT_2, TODAY.minusDays(1));

    public static final Dish DISH_19_R3 = new Dish(19, "Chicken Shawarma on the Sticks", 11.99, RESTAURANT_3, TODAY.minusDays(1));
    public static final Dish DISH_20_R3 = new Dish(20, "Greek salad", 4.99, RESTAURANT_3, TODAY.minusDays(1));

    public static final Dish DISH_21_R1 = new Dish(21, "Green Salad", 4.99, RESTAURANT_1, TODAY);
    public static final Dish DISH_22_R1 = new Dish(22, "Ginger Chicken", 22.99, RESTAURANT_1, TODAY);

    public static final Dish DISH_23_R2 = new Dish(23, "Rib-eye", 49.99, RESTAURANT_2, TODAY);
    public static final Dish DISH_24_R2 = new Dish(24, "Coleslaw Salad", 5.99, RESTAURANT_2, TODAY);

    public static final Dish NEW_DISH_25_R3 = new Dish(25, "Salmon", 39.99, RESTAURANT_3, TODAY);
    public static final Dish NEW_DISH_26_R3 = new Dish(26, "Hummus", 5.99, RESTAURANT_3, TODAY);

    public static final Dish UPD_DISH_23_R2 = new Dish(23, "Rib-eye" + updString, 49.99, RESTAURANT_2, TODAY);
    public static final Dish UPD_DISH_24_R2 = new Dish(24, "Coleslaw Salad" + updString, 5.99, RESTAURANT_2, TODAY);

    // Votes

    public static final Vote VOTE_5_U1 = new Vote(5, USER, RESTAURANT_1, LocalDate.now().minusDays(1), LocalTime.of(10, 0, 0));
    public static final Vote NEW_VOTE_7_U1 = new Vote(7, USER, RESTAURANT_2, LocalDate.now(), LocalTime.now().withSecond(0).withNano(0));
    public static final Vote UPD_VOTE_7_U1 = new Vote(7, USER, RESTAURANT_1, LocalDate.now(), LocalTime.now().withSecond(0).withNano(0));

    // Create RestaurantTo / List

    public static RestaurantTo createRestaurantTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getTelephone(), restaurant.getAddress());
    }

    public static List<RestaurantTo> createRestaurantTosList(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(AllTestData::createRestaurantTo).collect(Collectors.toList());
    }

    // Create DishTo / Set

    public static DishTo createDishTo(Dish dish) {
        return new DishTo(dish.getName(), dish.getPrice());
    }

    public static Set<DishTo> createDishTosSet(Collection<Dish> dishes) {
        return dishes.stream()
                .map(AllTestData::createDishTo).collect(Collectors.toSet());
    }

    // Create MenuTo / Map

    public static Map<LocalDate, List<MenuTo>> createMenuTosMap(List<Dish> dishesBetween) {
        Set<MenuTo> menuToList = dishesBetween.stream()
                .map(e -> new MenuTo(e.getDate(), e.getRestaurant().getId(), e.getRestaurant().getName()))
                .collect(Collectors.toSet());

        List<MenuTo> menuTos = menuToList.stream()
                .peek(e -> e.setDishes(filterDishesBetween(e, dishesBetween)))
                .sorted(Comparator.comparing(MenuTo::getId))
                .collect(Collectors.toList());

        return menuTos.stream()
                .collect(Collectors.groupingBy(MenuTo::getDate));
    }

    private static Set<DishTo> filterDishesBetween(MenuTo menuTo, List<Dish> dishesBetween) {
        List<Dish> dishes = dishesBetween.stream()
                .filter(e -> e.getDate().equals(menuTo.getDate()))
                .filter(e -> e.getRestaurant().getName().equals(menuTo.getName()))
                .collect(Collectors.toList());

        return dishes.stream()
                .map(e -> new DishTo(e.getName(), e.getPrice())).collect(Collectors.toSet());
    }

    // Create VoteTo

    public static VoteTo createVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), LocalDateTime.of(vote.getDate(), vote.getTime()), vote.getRestaurant().getId(), vote.getRestaurant().getName());
    }
}
