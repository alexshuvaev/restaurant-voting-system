package com.alexshuvaev.topjava.gp.rest.user;

import com.alexshuvaev.topjava.gp.to.VoteTo;
import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.GET_USER_VOTES_HISTORY;
import static com.alexshuvaev.topjava.gp.rest.RestEndpoints.POST_VOTE_FOR_RESTAURANT;
import static com.alexshuvaev.topjava.gp.testdata.AllTestData.*;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.USER;
import static com.alexshuvaev.topjava.gp.testdata.UserTestData.USER_PASSWORD;
import static com.alexshuvaev.topjava.gp.util.DateTimeUtil.THRESHOLD_TIME;
import static com.alexshuvaev.topjava.gp.util.TestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql", config = @SqlConfig(encoding = "UTF-8")),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql", config = @SqlConfig(encoding = "UTF-8"))
})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getVotesHistory() throws Exception {
        String actual = mockMvc.perform(get(GET_USER_VOTES_HISTORY)
                .param("startDate", TOMORROW_STRING)
                .param("endDate", TODAY_STRING)
                .with(userHttpBasic(USER, USER_PASSWORD))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(Collections.singletonList(createVoteTo(VOTE_5_U1))), actual);
    }

    @Test
    void voteForRestaurant() throws Exception {
        // First iteration: Create vote
        // Second iteration: Update vote
        for (int i = 0; i < 2; i++) {

            ResultActions perform = mockMvc.perform(post(POST_VOTE_FOR_RESTAURANT, i == 0 ? "2" : "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .with(userHttpBasic(USER, USER_PASSWORD))
                    .content(""));

            if (LocalTime.now().isBefore(THRESHOLD_TIME)) {
                String result = perform.andExpect( i == 0 ? status().isCreated() : status().isOk())
                        .andDo(print())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

                VoteTo actual = objectMapper.readValue(result, new TypeReference<>() {
                });
                actual.setDateTime(LocalDateTime.of(
                        actual.getDateTime().toLocalDate(),
                        actual.getDateTime().toLocalTime().withSecond(0).withNano(0)
                ));

                assertEquals(createVoteTo(i == 0 ? NEW_VOTE_7_U1 : UPD_VOTE_7_U1), actual);
            } else {
                perform.andExpect(status().isForbidden()).andDo(print());
            }
        }

    }
}