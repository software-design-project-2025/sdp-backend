package com.ctrlaltdelinquents.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")  // Use test profile
class BackendApplicationTest {
    
    @Value("${app.api-key}")
    private String apiKey;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void rootEndpoint_returnsDeploymentMessage() throws Exception {
        mockMvc.perform(get("/")
                        .header("Authorization", "Bearer " + apiKey))
                .andExpect(status().isOk())
                .andExpect(content().string("App successfully deployed on Azure"));
    }

}