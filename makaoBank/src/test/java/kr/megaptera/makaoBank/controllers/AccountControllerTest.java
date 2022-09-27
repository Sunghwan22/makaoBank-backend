package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import kr.megaptera.makaoBank.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
class AccountControllerTest {
  @MockBean
  private AccountService accountService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void account() throws Exception {
    given(accountService.detail(any()))
        .willReturn(Account.fake("1234"));


    mockMvc.perform(MockMvcRequestBuilders.get("/accounts/me"))
        .andExpect(status().isOk())
        .andExpect(content().string(
            containsString("\"accountNumber\":\"1234\"")
        ));
  }

  @Test
  void accountNotFound() throws Exception {
    given(accountService.detail(any()))
        .willThrow(new AccountNotFound("1234"));


    mockMvc.perform(MockMvcRequestBuilders.get("/accounts/me"))
        .andExpect(status().isNotFound());
  }


}
