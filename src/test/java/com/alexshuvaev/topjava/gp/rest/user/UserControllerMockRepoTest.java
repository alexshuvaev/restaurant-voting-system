package com.alexshuvaev.topjava.gp.rest.user;

import com.alexshuvaev.topjava.gp.repository.DishRepository;
import com.alexshuvaev.topjava.gp.repository.RestaurantRepository;
import com.alexshuvaev.topjava.gp.repository.VoteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.GET_USER_VOTES_HISTORY;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.USER;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.USER_PASSWORD;
import static com.alexshuvaev.topjava.gp.util.TestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerMockRepoTest {
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
    public void getVotesHistory() throws Exception {
        when(voteRepository
                .getAll(any(), any(), any()))
                .thenReturn(Optional.empty());

        String actual = mockMvc.perform(get(GET_USER_VOTES_HISTORY)
                .param("startDate", YESTERDAY_STRING)
                .param("endDate", TODAY_STRING)
                .with(userHttpBasic(USER, USER_PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(Collections.emptyList()), actual);
        verify(voteRepository, times(1)).getAll(any(), any(), any());
    }
}
