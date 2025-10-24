package com.ledgerx.credit.domain.controller;

import com.ledgerx.credit.service.CreditLineService;
import com.ledgerx.credit.web.controller.CreditLineController;
import com.ledgerx.credit.web.dto.CreditLineResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CreditLineController.class)
@AutoConfigureMockMvc(addFilters = false)
class CreditLineControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private CreditLineService creditLineService;


    @Test
    @WithMockUser// default user with ROLE_USER
    void shouldReturn200OnGetById() throws Exception {
        when(creditLineService.findById(any())).thenReturn(
                CreditLineResponse.builder()
                        .currency("MAD")
                        .amount(BigDecimal.valueOf(1000))
                        .build()
        );

        mockMvc.perform(get("/credits/{id}", UUID.randomUUID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("MAD"));
    }

    @Test
    @WithMockUser
    void shouldCreateCreditLine() throws Exception {
        CreditLineResponse response = CreditLineResponse.builder()
                .currency("MAD")
                .amount(BigDecimal.valueOf(1000))
                .build();

        when(creditLineService.create(any())).thenReturn(response);

        mockMvc.perform(post("/credits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "clientId": "d195c7ab-c501-49f0-9c27-005b239e913e",
                  "tenantId": "7944ac25-4ea4-442e-b04a-dfca87909048",
                  "amount": 1000,
                  "currency": "MAD"
                }
            """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currency").value("MAD"));
    }

}
