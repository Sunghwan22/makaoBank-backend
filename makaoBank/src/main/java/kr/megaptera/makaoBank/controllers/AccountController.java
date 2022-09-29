package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.dtos.AccountCreatedDto;
import kr.megaptera.makaoBank.dtos.AccountDto;
import kr.megaptera.makaoBank.dtos.AccountRegistrationDto;
import kr.megaptera.makaoBank.dtos.MismatchPasswordDto;
import kr.megaptera.makaoBank.dtos.RegisterFailedDto;
import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.exceptions.ConfirmPasswordMismatch;
import kr.megaptera.makaoBank.models.Account;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
@CrossOrigin
public class AccountController {
  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("me")
  public AccountDto account(
      @RequestAttribute("accountNumber") AccountNumber accountNumber
  ) {
    // Todo 인증 정보 활용
    Account account = accountService.detail(accountNumber);
    return account.toDto();
  }

  @PostMapping("user")
  @ResponseStatus(HttpStatus.CREATED)
  public AccountCreatedDto register(
      @Validated @RequestBody AccountRegistrationDto accountRegistrationDto
  ) {
    Account account = accountService.create(accountRegistrationDto);
    return account.toCreatedDto();
  }

  @ExceptionHandler(AccountNotFound.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String accountNotFound() {
    return "Account Not Found";
  }

  @ExceptionHandler(ConfirmPasswordMismatch.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public MismatchPasswordDto confirmPasswordMismatch() {
    return new MismatchPasswordDto();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RegisterFailedDto registerFailed(MethodArgumentNotValidException exception) {
    for (ObjectError error : exception.getBindingResult().getAllErrors()) {

      Integer code = getCodeByDefaultMessage(error.getDefaultMessage());

      return new RegisterFailedDto(code, error.getDefaultMessage());
    }
    return new RegisterFailedDto(1004, "멸크망크");
  }

  private Integer getCodeByDefaultMessage(String message) {
    Integer code = 0;

    if(message.equals("3~7자리의 한글만 사용가능합니다")) {
      code = 1005;
    }

    if(message.equals("로그인 및 거래시 사용될 계좌번호이며 숫자만 사용 가능(8글자)")) {
      code =  1006;
    }

    if(message.equals("8글자 이상의 영문(대소문자),숫자,특수문자가 모두 포함되어야 합니다")) {
      code = 1007;
    }

    if(message.equals("공백일 수 없습니다")) {
      code = 1008;
    }

//    return switch (message) {
//      case "3~7자리의 한글만 사용가능합니다" -> 1005;
//      case "로그인 및 거래시 사용될 계좌번호이며 숫자만 사용 가능(8글자)" -> 1006;
//      case "8글자 이상의 영문(대소문자),숫자,특수문자가 모두 포함되어야 합니다" -> 1007;
//      case "공백일 수 없습니다" -> 1008;
//    };
    return code;
  }
}
