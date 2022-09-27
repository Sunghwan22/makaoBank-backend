package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.dtos.AccountNotFoundDto;
import kr.megaptera.makaoBank.dtos.ErrorDto;
import kr.megaptera.makaoBank.dtos.IncorrectAmountErrorDto;
import kr.megaptera.makaoBank.dtos.TransferDto;
import kr.megaptera.makaoBank.dtos.TransferResultDto;
import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.services.IncorrectAmount;
import kr.megaptera.makaoBank.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
  private final TransferService transferService;

  public TransactionController(TransferService transferService) {
    this.transferService = transferService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TransferResultDto transfer(
      @Validated @RequestBody TransferDto transferDto
  ) {
    // todo 인증후 제대로 처리할 것
    String accountNumber = "1234";
    Long amount = transferService.transfer(
        accountNumber,
        transferDto.getTo(),
        transferDto.getAmount());
    // 반환 하는 객체는 getter가 있어야 한다.
    return new TransferResultDto(amount);
  }

  @ExceptionHandler(IncorrectAmount.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto inCorrectAmount() {
    // TOdo : 에러 dto 사용할 것
    return new IncorrectAmountErrorDto();
  }

  @ExceptionHandler(AccountNotFound.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDto accountNotFound() {
    // TOdo : 에러 dto 사용할 것
    return new AccountNotFoundDto();
  }
}
