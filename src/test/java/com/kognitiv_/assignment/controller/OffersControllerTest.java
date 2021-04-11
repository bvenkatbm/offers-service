package com.kognitiv_.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kognitiv_.assignment.domain.OfferRequest;
import com.kognitiv_.assignment.entity.OfferEntity;
import com.kognitiv_.assignment.repository.OfferRepository;
import com.kognitiv_.assignment.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class OffersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    @WithMockUser(username="admin", password = "admin", roles = {"USER", "ADMIN"})
    public void test_success_post_offer() throws Exception {

        LocalDate validFrom = LocalDate.of(2019, 2, 1);
        LocalDate validTill = LocalDate.of(2019, 5, 1);
        OfferRequest offerRequest = new OfferRequest("Internet", validFrom, validTill, "Karnataka");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(offerRequest);
        mockMvc.perform(post("/collect/offer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(offerRequest)));

        List<OfferEntity> offerEntityList = offerRepository.findAll();
        assert offerEntityList.size() == 1;
    }

    @Test
    @WithMockUser(username="user", password = "user", roles = "USER")
    public void test_get_offer() throws Exception {
        mockMvc.perform(get("/collect/offer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)));
    }
}
