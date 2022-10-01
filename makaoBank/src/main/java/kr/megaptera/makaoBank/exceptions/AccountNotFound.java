package kr.megaptera.makaoBank.exceptions;

import kr.megaptera.makaoBank.models.AccountNumber;

public class AccountNotFound extends RuntimeException{

  public AccountNotFound(AccountNumber accountNumber) {
    super("Account Not Found (account number: " + accountNumber.value() + ")");
  }
}
