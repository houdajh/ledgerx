package com.ledgerx.identity.controller;

import com.ledgerx.identity.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
@Import(SecurityConfig.class)
//@AutoConfigureMockMvc(addFilters = false) // <--- désactive tous les filtres de sécurité
class PublicControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnHello() throws Exception {
        mockMvc.perform(get("/public/hello"))
               .andExpect(status().isOk())
               .andExpect(content().string("hello (public)"));
    }

    @Test
    @WithMockUser(authorities = "ROLE_read")
    void shouldReturnSecureHelloWhenAuthorized() throws Exception {
        mockMvc.perform(get("/secure/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello (secure)"));
    }

    @Test
    void shouldRejectSecureHelloWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/secure/hello"))
                .andExpect(status().isUnauthorized());
    }
}
