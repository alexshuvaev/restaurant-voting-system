package com.alexshuvaev.topjava.gp.rest.guest;

import com.alexshuvaev.topjava.gp.domain.Dish;
import com.alexshuvaev.topjava.gp.domain.Restaurant;
import com.alexshuvaev.topjava.gp.repository.DishRepository;
import com.alexshuvaev.topjava.gp.repository.RestaurantRepository;
import com.alexshuvaev.topjava.gp.to.MenuTo;
import com.alexshuvaev.topjava.gp.to.RestaurantTo;
import com.alexshuvaev.topjava.gp.util.exception.NotFoundException;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.*;
import static com.alexshuvaev.topjava.gp.util.DateTimeUtil.checkAndInitStartDateEndDate;
import static com.alexshuvaev.topjava.gp.util.ToUtil.allMenuTosCreate;
import static com.alexshuvaev.topjava.gp.util.ToUtil.allRestaurantTosCreate;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GuestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Autowired
    public GuestController(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Cacheable("listOfTos")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get restaurants list", notes = "Get all restaurants with info about them.")
    @GetMapping(GET_RESTAURANT_LIST)
    public List<RestaurantTo> findAll() {
        log.info("Get all restaurants");

        List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        if (restaurants.isEmpty()) throw new NotFoundException("Restaurants not found");

        return allRestaurantTosCreate(restaurants);
    }

    @Cacheable("mapOfTos")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get menus list", notes = "Input startDate and endDate to get menus list of restaurants for provided dates. " +
            "If startDate and endDate not provided, will be shown menus on today.")
    @GetMapping(GET_MENUS_LIST)
    public Map<LocalDate, List<MenuTo>> findAllMenus(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<LocalDate> list = checkAndInitStartDateEndDate(startDate, endDate);
        log.info("Get all menus for period: startDate={}, endDate={}", list.get(0), list.get(1));

        Optional<List<Dish>> dishesBetween = dishRepository.getDishesBetween(list.get(0), list.get(1));

        return allMenuTosCreate(dishesBetween.orElseThrow(()-> new NotFoundException("Menus not found")));
    }

    @Cacheable("mapOfTos")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get single restaurant menu", notes = "Input startDate and endDate to get restaurant menu for provided dates. " +
            "If startDate and endDate not provided, will be shown menu on today.")
    @GetMapping(GET_SINGLE_RESTAURANT_MENU)
    public Map<LocalDate, List<MenuTo>> getSingleRestaurantMenu(
            @PathVariable Integer id,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<LocalDate> list = checkAndInitStartDateEndDate(startDate, endDate);
        log.info("Get restaurant menu for period: startDate={}, endDate={}", list.get(0), list.get(1));

        Optional<List<Dish>> dishesBetween = dishRepository.getDishesBetweenSingleRestaurant(list.get(0), list.get(1), id);

        return allMenuTosCreate(dishesBetween.orElseThrow(() -> new NotFoundException("Restaurant menu not found")));
    }
}
