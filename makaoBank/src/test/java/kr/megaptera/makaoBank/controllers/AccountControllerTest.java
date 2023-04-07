package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.repositoies.AccountRepository;
import kr.megaptera.makaoBank.services.AccountService;
import kr.megaptera.makaoBank.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
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

  @SpyBean
  private JwtUtil jwtUtil;

  @MockBean
  private AccountRepository accountRepository;

  @Test
  void account() throws Exception {
    given(accountService.detail(any()))
        .willReturn(Account.fake("1234"));

    String accessToken = jwtUtil.encode(new AccountNumber("1234"));

    mockMvc.perform(MockMvcRequestBuilders.get("/accounts/me")
            .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isOk())
        .andExpect(content().string(
            containsString("\"accountNumber\":\"1234\"")
        ));
  }

  @Test
  void accountNotFound() throws Exception {
    given(accountService.detail(any()))
        .willThrow(new AccountNotFound(new AccountNumber("1234")));

    String accessToken = jwtUtil.encode(new AccountNumber("1234"));

    mockMvc.perform(MockMvcRequestBuilders.get("/accounts/me")
            .header("Authorization", "Bearer " + accessToken))
        .andExpect(status().isNotFound());
  }

  @Test
  void accountWithOutAccessToken() throws Exception {
    given(accountService.detail(any()))
        .willThrow(new AccountNotFound(new AccountNumber("1234")));

    String accessToken = jwtUtil.encode(new AccountNumber("1234"));

    mockMvc.perform(MockMvcRequestBuilders.get("/accounts/me"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void register() throws Exception {
    AccountNumber accountNumber = new AccountNumber("1234");

    given(accountService.create(any())).willReturn(new Account(
        1L, "Tester", accountNumber
    ));

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"제로콜라\"," +
                "\"accountNumber\":\"12345678\"," +
                "\"password\":\"Tjdghks24535!\"," +
                "\"passwordConfirm\":\"Tjdghks24535!\"" +
                "}"))
        .andExpect(status().isCreated());
  }

  @Test
  void registerWithInvalidNameFormat() throws Exception {
    AccountNumber accountNumber = new AccountNumber("1234");

    given(accountService.create(any())).willReturn(new Account(
        1L, "Tester", accountNumber
    ));

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"Tester\"," +
                "\"accountNumber\":\"12345678\"," +
                "\"password\":\"Tjdghks24535!\"," +
                "\"passwordConfirm\":\"Tjdghks24535!\"" +
                "}"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"콜라\"," +
                "\"accountNumber\":\"12345678\"," +
                "\"password\":\"Tjdghks24535!\"," +
                "\"passwordConfirm\":\"Tjdghks24535!\"" +
                "}"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"제로콜라진짜너무맛있다\"," +
                "\"accountNumber\":\"12345678\"," +
                "\"password\":\"Tjdghks24535!\"," +
                "\"passwordConfirm\":\"Tjdghks24535!\"" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void registerWithInvaildAccountNumber() throws Exception {
    AccountNumber accountNumber = new AccountNumber("1234");

    given(accountService.create(any())).willReturn(new Account(
        1L, "Tester", accountNumber
    ));

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"제로콜라\"," +
                "\"accountNumber\":\"이건 아니지않나요\"," +
                "\"password\":\"Tjdghks24535!\"," +
                "\"passwordConfirm\":\"Tjdghks24535!\"" +
                "}"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"제로콜라\"," +
                "\"accountNumber\":\"12345678900\"," +
                "\"password\":\"Tjdghks24535!\"," +
                "\"passwordConfirm\":\"Tjdghks24535!\"" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void registerWithInvalidPassword() throws Exception {
    AccountNumber accountNumber = new AccountNumber("1234");

    given(accountService.create(any())).willReturn(new Account(
        1L, "Tester", accountNumber
    ));

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"제로콜라\"," +
                "\"accountNumber\":\"12345678\"," +
                "\"password\":\"Tjdghks24535\"," +
                "\"passwordConfirm\":\"Tjdghks24535\"" +
                "}"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"제로콜라\"," +
                "\"accountNumber\":\"12345678\"," +
                "\"password\":\"tjdghks24535!\"," +
                "\"passwordConfirm\":\"tjdghks24535!\"" +
                "}"))
        .andExpect(status().isBadRequest());

    mockMvc.perform(MockMvcRequestBuilders.post("/accounts/user")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"name\":\"제로콜라\"," +
                "\"accountNumber\":\"12345678\"," +
                "\"password\":\"Tjdghks24535!\"," +
                "\"passwordConfirm\":\"Tjdghks24535\"" +
                "}"))
        .andExpect(status().isBadRequest());
  }
}
