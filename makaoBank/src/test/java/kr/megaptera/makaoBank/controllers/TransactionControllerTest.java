package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.services.IncorrectAmount;
import kr.megaptera.makaoBank.services.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TransactionController.class)
@ActiveProfiles("test")
class TransactionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransferService transferService;

  @Test
  void transfer() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
//        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"5678\"," +
                "\"amount\":\"100000\"" +
                "}"))
        .andExpect(status().isCreated());

    verify(transferService).transfer("1234", "5678", 100_000L);
  }

  @Test
  void transferWithIncorrectAmount() throws Exception {
    Long amount = -100_000L;

    given(transferService.transfer(any(), any(), any()))
        .willThrow(new IncorrectAmount(amount));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("" +
                "{\"to\":\"5678\"," +
                "\"amount\":" + amount +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("\"code\":1002")));
  }

  @Test
  void transferWithIncorrectAmountNumber() throws Exception {
    String accountNumber = "5678";

    given(transferService.transfer(any(), any(), any()))
        .willThrow(new AccountNotFound(accountNumber));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("" +
                "{\"to\":\"" + accountNumber + "\"," +
                "\"amount\": 1000000" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("\"code\":1001")
        ));
  }
}
