package com.alexshuvaev.topjava.gp.rest.admin;

import com.alexshuvaev.topjava.gp.repository.RestaurantRepository;
import com.alexshuvaev.topjava.gp.to.MenuTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.*;
import static com.alexshuvaev.topjava.gp.testdata.AllTestData.*;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.ADMIN;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.ADMIN_PASSWORD;
import static com.alexshuvaev.topjava.gp.util.TestUtil.userHttpBasic;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql", config = @SqlConfig(encoding = "UTF-8")),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
})
class AdminRestaurantControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createRestaurant() throws Exception {
        NEW_RESTAURANT.setId(null);
        String actual = mockMvc.perform(post(POST_ADMIN_CREATE_RESTAURANT)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(createRestaurantTo(NEW_RESTAURANT))))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        NEW_RESTAURANT.setId(4);
        assertEquals("Возвращается не правильный результат при запросе POST /api/admin/restaurants", objectMapper.writeValueAsString(createRestaurantTo(NEW_RESTAURANT)), actual);
    }

    @Test
    void updateRestaurant() throws Exception {
        assertEquals(objectMapper.writeValueAsString(RESTAURANT_1), objectMapper.writeValueAsString(restaurantRepository.findById(1)));

        String actual = mockMvc.perform(put(PUT_UPDATE_RESTAURANT_INFO, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(createRestaurantTo(UPD_RESTAURANT))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(createRestaurantTo(UPD_RESTAURANT)), actual);
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(DELETE_RESTAURANT, "3")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void createMenu() throws Exception {
        String actual = mockMvc.perform(post(POST_ADMIN_CREATE_MENU, "3")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(new MenuTo(null,null, null, createDishTosSet(Arrays.asList(NEW_DISH_25_R3, NEW_DISH_26_R3))))))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(createMenuTosMap(Arrays.asList(NEW_DISH_25_R3, NEW_DISH_26_R3))), actual);
    }

    @Test
    void updateMenu() throws Exception {
        String actual = mockMvc.perform(put(PUT_UPDATE_MENU, "2")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD))
                .content(objectMapper.writeValueAsString(new MenuTo(null,null, null, createDishTosSet(Arrays.asList(UPD_DISH_23_R2, UPD_DISH_24_R2))))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UPD_DISH_23_R2.setId(UPD_DISH_23_R2.getId() + 3);
        UPD_DISH_24_R2.setId(UPD_DISH_24_R2.getId() + 1);

        assertEquals(objectMapper.writeValueAsString(createMenuTosMap(Arrays.asList(UPD_DISH_24_R2, UPD_DISH_23_R2))), actual);
    }

    @Test
    void deleteMenu() throws Exception {
        mockMvc.perform(delete(DELETE_MENU, "1")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN, ADMIN_PASSWORD)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}