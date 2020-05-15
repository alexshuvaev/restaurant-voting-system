package com.alexshuvaev.topjava.gp.rest.guest;

import com.alexshuvaev.topjava.gp.to.MenuTo;
import com.alexshuvaev.topjava.gp.to.RestaurantTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.*;
import static com.alexshuvaev.topjava.gp.testdata.AllTestData.*;
import static com.alexshuvaev.topjava.gp.util.TestUtil.TODAY_STRING;
import static com.alexshuvaev.topjava.gp.util.TestUtil.YESTERDAY_STRING;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql", config = @SqlConfig(encoding = "UTF-8")),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
})
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void findAll() throws Exception {
        String actual = mockMvc.perform(get(GET_RESTAURANT_LIST)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<RestaurantTo> expected = createRestaurantTosList(Arrays.asList(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3));
        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void findAllMenus() throws Exception {
        String actual = mockMvc.perform(get(GET_MENUS_LIST)
                .param("startDate", YESTERDAY_STRING)
                .param("endDate", TODAY_STRING)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<LocalDate, List<MenuTo>> expected = createMenuTosMap(Arrays.asList(
                DISH_15_R1, DISH_16_R1,
                DISH_17_R2, DISH_18_R2,
                DISH_19_R3, DISH_20_R3,
                DISH_21_R1, DISH_22_R1,
                DISH_23_R2, DISH_24_R2));

        assertEquals(objectMapper.writeValueAsString(expected), actual);
    }

    @Test
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void getSingleRestaurantMenu() throws Exception {
        int restaurantId = 1;
        String actual = mockMvc.perform(get(GET_SINGLE_RESTAURANT_MENU, restaurantId)
                .param("id", String.valueOf(restaurantId))
                .param("startDate", YESTERDAY_STRING)
                .param("endDate", YESTERDAY_STRING)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Map<LocalDate, List<MenuTo>> expected = createMenuTosMap(Arrays.asList(DISH_15_R1, DISH_16_R1));
        assertEquals("Возвращается не правильный результат при запросе GET /api/restaurants/menus", objectMapper.writeValueAsString(expected), actual);
    }
}