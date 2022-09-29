package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.dtos.LoginRequestDto;
import kr.megaptera.makaoBank.dtos.LoginResultDto;
import kr.megaptera.makaoBank.exceptions.LoginFailed;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.services.LoginService;
import kr.megaptera.makaoBank.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {
  private final LoginService loginService;
  private final JwtUtil jwtUtil;

  public SessionController(LoginService loginService, JwtUtil jwtUtil) {
    this.loginService = loginService;
    this.jwtUtil = jwtUtil;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public LoginResultDto login(
      @RequestBody LoginRequestDto loginRequestDto
  ) {
    AccountNumber accountNumber = new AccountNumber(
        loginRequestDto.getAccountNumber()
    );

    String password = loginRequestDto.getPassword();

    Account account = loginService.login(
          accountNumber,
          password);

    String accessToken = jwtUtil.encode(accountNumber);

    return new LoginResultDto(
        accessToken,
        account.name(),
        account.amount()
    );
  }

  @ExceptionHandler(LoginFailed.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String loginFailed() {
    return "Login Failed";
  }
}
