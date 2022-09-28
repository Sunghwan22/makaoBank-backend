package kr.megaptera.makaoBank.controllers;

import kr.megaptera.makaoBank.dtos.AccountNotFoundDto;
import kr.megaptera.makaoBank.dtos.ErrorDto;
import kr.megaptera.makaoBank.dtos.IncorrectAmountErrorDto;
import kr.megaptera.makaoBank.dtos.TransactionDto;
import kr.megaptera.makaoBank.dtos.TransactionsDto;
import kr.megaptera.makaoBank.dtos.TransferDto;
import kr.megaptera.makaoBank.dtos.TransferResultDto;
import kr.megaptera.makaoBank.exceptions.AccountNotFound;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.Transaction;
import kr.megaptera.makaoBank.exceptions.IncorrectAmount;
import kr.megaptera.makaoBank.services.TransactionService;
import kr.megaptera.makaoBank.services.TransferService;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
  private final TransferService transferService;
  private final TransactionService transactionService;

  public TransactionController(TransferService transferService, TransactionService transactionService) {
    this.transferService = transferService;
    this.transactionService = transactionService;
  }

  @GetMapping
  public TransactionDto list(
      @RequestParam(required = false, defaultValue = "1") Integer page
  ) {
    AccountNumber accountNumber = new AccountNumber("1234");
    List<Transaction> transactions = transactionService.list(accountNumber, page);


    List<TransactionDto> transactionsDtos =
        transactions.stream()
        .map(transaction -> transaction.toDto(accountNumber))
        .collect(Collectors.toList());

    return new TransactionsDto(transactionsDtos);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TransferResultDto transfer(
      @Validated @RequestBody TransferDto transferDto
  ) {
    // todo 인증후 제대로 처리할 것
    AccountNumber sender = new AccountNumber("1234");
    AccountNumber receiver = new AccountNumber(transferDto.getTo());

    Long amount = transferService.transfer(
        sender,
        receiver,
        transferDto.getAmount(),
        transferDto.getName());

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
