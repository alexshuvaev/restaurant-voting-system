package com.alexshuvaev.topjava.gp.rest.guest;

import com.alexshuvaev.topjava.gp.repository.DishRepository;
import com.alexshuvaev.topjava.gp.repository.RestaurantRepository;
import com.alexshuvaev.topjava.gp.repository.VoteRepository;
import com.alexshuvaev.topjava.gp.to.RestaurantTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.GET_MENUS_LIST;
import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.GET_RESTAURANT_LIST;
import static com.alexshuvaev.topjava.gp.testdata.AllTestData.createRestaurantTosList;
import static com.alexshuvaev.topjava.gp.util.TestUtil.TODAY_STRING;
import static com.alexshuvaev.topjava.gp.util.TestUtil.YESTERDAY_STRING;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void findAll_emptyRestaurantList() throws Exception {
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
    @CacheEvict(cacheNames = { "listOfTos", "mapOfTos" }, allEntries = true)
    public void findAllMenus() throws Exception {
        when(dishRepository
                .getDishesBetween(any(), any()))
                .thenReturn(Optional.empty());

        String actual = mockMvc.perform(get(GET_MENUS_LIST)
                .param("startDate", YESTERDAY_STRING)
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
