package com.alexshuvaev.topjava.gp.rest.admin;

import com.alexshuvaev.topjava.gp.repository.DishRepository;
import com.alexshuvaev.topjava.gp.repository.RestaurantRepository;
import com.alexshuvaev.topjava.gp.repository.VoteRepository;
import com.alexshuvaev.topjava.gp.to.MenuTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.*;
import static com.alexshuvaev.topjava.gp.testdata.AllTestData.*;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.ADMIN;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.ADMIN_PASSWORD;
import static com.alexshuvaev.topjava.gp.util.TestUtil.userHttpBasic;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminRestaurantControllerExceptionTest {
    @MockBean
    private RestaurantRepository restaurantRepository;

    @MockBean
    private DishRepository dishRepository;

    @MockBean
    private VoteRepository voteRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void updateRestaurant_NFE() throws Exception {
        when(restaurantRepository
                .findById(100000))
                .thenReturn(Optional.empty());

        mockMvc.perform(put(PUT_UPDATE_RESTAURANT_INFO, "100000")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(createRestaurantTo(UPD_RESTAURANT))))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(restaurantRepository, times(1)).findById(100000);
        verify(restaurantRepository, times(0)).save(any());
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void deleteRestaurant_NFE() throws Exception {
        when(restaurantRepository
                .delete(100000))
                .thenReturn(0);

        mockMvc.perform(delete(DELETE_RESTAURANT, "100000")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD)))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(restaurantRepository, times(1)).delete(100000);
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void createMenu_menuAlreadyExist() throws Exception {
        when(dishRepository
                .getDishesBetweenSingleRestaurant(any(), any(), any()))
                .thenReturn(Optional.of(Arrays.asList(DISH_15_R1, DISH_16_R1)));

        mockMvc.perform(post(POST_ADMIN_CREATE_MENU, "100000")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(new MenuTo(null,null, null, createDishTosSet(Arrays.asList(NEW_DISH_25_R3, NEW_DISH_26_R3))))))
                .andExpect(status().isForbidden())
                .andDo(print());
        verify(dishRepository, times(1)).getDishesBetweenSingleRestaurant(any(), any(), any());
        verify(restaurantRepository, times(0)).getOne(any());
        verify(dishRepository, times(0)).saveAll(any());
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void createMenu_NFE() throws Exception {
        when(dishRepository
                .getDishesBetweenSingleRestaurant(any(), any(), any()))
                .thenReturn(Optional.empty());

        when(restaurantRepository
                .getOne(any()))
                .thenReturn(null);

        mockMvc.perform(post(POST_ADMIN_CREATE_MENU, "100000")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(new MenuTo(null,null, null, createDishTosSet(Arrays.asList(NEW_DISH_25_R3, NEW_DISH_26_R3))))))
                .andExpect(status().isNotFound())
                .andDo(print());
        verify(dishRepository, times(1)).getDishesBetweenSingleRestaurant(any(), any(), any());
        verify(restaurantRepository, times(1)).getOne(any());
        verify(dishRepository, times(0)).saveAll(any());
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void updateMenu_NFE() throws Exception {
        when(restaurantRepository
                .getRestaurantWithDishesForToday(anyInt(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put(PUT_UPDATE_MENU, "2")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(new MenuTo(null,null, null, createDishTosSet(Arrays.asList(UPD_DISH_23_R2, UPD_DISH_24_R2))))))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(restaurantRepository, times(1)).getRestaurantWithDishesForToday(any(), any());
        verify(dishRepository, times(0)).deleteAll(any());
        verify(dishRepository, times(0)).saveAll(any());
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void deleteMenu_NFE() throws Exception {
        when(restaurantRepository
                .existsById(100000))
                .thenReturn(false);

        mockMvc.perform(delete(DELETE_MENU, "100000")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD)))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(restaurantRepository, times(1)).existsById(100000);
        verify(restaurantRepository, times(0)).getRestaurantWithDishesForToday(any(), any());
        verify(dishRepository, times(0)).deleteAll(any());
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void deleteMenu_NFE_2() throws Exception {
        when(restaurantRepository
                .existsById(1))
                .thenReturn(true);

        when(restaurantRepository
                .getRestaurantWithDishesForToday(anyInt(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete(DELETE_MENU, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD)))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(restaurantRepository, times(1)).existsById(1);
        verify(restaurantRepository, times(1)).getRestaurantWithDishesForToday(any(), any());
        verify(dishRepository, times(0)).deleteAll(any());
    }
}
