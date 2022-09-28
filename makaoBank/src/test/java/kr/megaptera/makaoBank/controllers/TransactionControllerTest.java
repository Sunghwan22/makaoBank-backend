package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import kr.megaptera.makaoBank.repositoies.TransactionRepository;
import kr.megaptera.makaoBank.exceptions.IncorrectAmount;
import kr.megaptera.makaoBank.services.TransactionService;
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

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TransactionController.class)
@ActiveProfiles("test")
class TransactionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransferService transferService;

  @MockBean
  private TransactionService transactionService;

  @MockBean
  private TransactionRepository transactionRepository;

  @Test
  void list() throws Exception {
    Transaction transaction = mock(Transaction.class);

    AccountNumber accountNumber = new AccountNumber("1234");

    given(transactionService.list(accountNumber, 1))
        .willReturn(List.of(transaction));

    mockMvc.perform(MockMvcRequestBuilders.get("/transactions"))
        .andExpect(MockMvcResultMatchers.status().isOk());
//        .andExpect(content().string(
//            containsString("\"transactions\":[")
//        ));

    verify(transactionService).list(accountNumber, 1);
  }

  @Test
  void transfer() throws Exception {
    AccountNumber sender = new AccountNumber("1234");
    AccountNumber receiver = new AccountNumber("5678");
    String name = "Test";

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"to\":\"" + receiver.value() + "\"," +
                "\"amount\":100000" + "," +
                "\"name\":\"Test\"" +
                "}"))
        .andExpect(status().isCreated());

    verify(transferService).transfer(sender, receiver, 100_000L, name);
  }

  @Test
  void transferWithIncorrectAmount() throws Exception {
    Long amount = -100_000L;

    given(transferService.transfer(any(), any(), any(), any()))
        .willThrow(new IncorrectAmount(amount));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("" +
                "{\"to\":\"5678\"," +
                "\"amount\":" + amount + "," +
                "\"name\":\"Test\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("\"code\":1002")));
  }

  @Test
  void transferWithIncorrectAmountNumber() throws Exception {
    AccountNumber accountNumber = new AccountNumber("5678");

    given(transferService.transfer(any(), any(), any(), any()))
        .willThrow(new AccountNotFound(accountNumber));

    mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("" +
                "{\"to\":\"" + accountNumber + "\"," +
                "\"amount\": 1000000" + "," +
                "\"name\":\"Test\"" +
                "}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().string(
            containsString("\"code\":1001")
        ));
  }
}
