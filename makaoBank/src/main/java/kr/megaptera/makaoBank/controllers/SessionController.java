package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.dtos.LoginFailedDto;
import kr.megaptera.makaoBank.dtos.LoginRequestDto;
import kr.megaptera.makaoBank.dtos.LoginResultDto;
import kr.megaptera.makaoBank.exceptions.LoginFailed;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.services.LoginService;
import kr.megaptera.makaoBank.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     @Validated @RequestBody LoginRequestDto loginRequestDto
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
  public LoginFailedDto loginFailed() {
    return new LoginFailedDto(1011, "아이디 혹은 비밀번호가 맞지 않습니다");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public LoginFailedDto registerFailed(MethodArgumentNotValidException exception) {
    for (ObjectError error : exception.getBindingResult().getAllErrors()) {

      return new LoginFailedDto(1011, error.getDefaultMessage());
    }
    return new LoginFailedDto(1011, "공백일 수 없습니다");
  }
}
