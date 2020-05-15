package com.alexshuvaev.topjava.gp.rest.guest;

import com.alexshuvaev.topjava.gp.repository.DishRepository;
import com.alexshuvaev.topjava.gp.repository.RestaurantRepository;
import com.alexshuvaev.topjava.gp.repository.VoteRepository;
import com.alexshuvaev.topjava.gp.to.MenuTo;
import com.alexshuvaev.topjava.gp.to.RestaurantTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.*;
import static com.alexshuvaev.topjava.gp.testdata.AllTestData.*;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.ADMIN;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.ADMIN_PASSWORD;
import static com.alexshuvaev.topjava.gp.util.TestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GuestControllerMockRepoTest {
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
    void findAll_emptyRestaurantList() throws Exception {
        when(restaurantRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id")))
                .thenReturn(Collections.emptyList());

        String actual = mockMvc.perform(get(GET_RESTAURANT_LIST)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<RestaurantTo> expected = createRestaurantTosList(Collections.emptyList());
        assertEquals(objectMapper.writeValueAsString(expected), actual);

        verify(restaurantRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Test
    void findAllMenus() throws Exception {
        when(dishRepository
                .getDishesBetween(any(), any()))
                .thenReturn(Optional.empty());

        String actual = mockMvc.perform(get(GET_MENUS_LIST)
                .param("startDate", TOMORROW_STRING)
                .param("endDate", TODAY_STRING)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(Collections.emptyMap()), actual);
    }
}
