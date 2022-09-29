package kr.megaptera.makaoBank.dtos;

import kr.megaptera.makaoBank.models.AccountNumber;

public class AccountCreatedDto {
  private String name;

  private String accountNumber;

  public AccountCreatedDto() {
  }

  public AccountCreatedDto(String accountNumber, String name) {
    this.accountNumber = accountNumber;
    this.name = name;
  }


  public String getAccountNumber() {
    return accountNumber;
  }

  public String getName() {
    return name;
  }
}
