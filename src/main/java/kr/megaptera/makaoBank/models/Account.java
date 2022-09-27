package kr.megaptera.makaoBank.models;

import kr.megaptera.makaoBank.dtos.AccountDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
  @Id
  @GeneratedValue
  private Long id;

  private String accountNumber;

  private String name;

  private Long amount;

  public Account() {
  }

  public Account(long id, String accountNumber, String name, Long amount) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.name = name;
    this.amount = amount;
  }

  public String accountNumber() {
    return accountNumber;
  }

  public static Account fake(String accountNumber) {
    return new Account(1L, accountNumber, "Tester", 100L);
  }

  public AccountDto toDto() {
    return new AccountDto(accountNumber, name, amount);
  }
}
