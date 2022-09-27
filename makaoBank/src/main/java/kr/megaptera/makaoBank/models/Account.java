package kr.megaptera.makaoBank.models;

import kr.megaptera.makaoBank.dtos.AccountDto;
import kr.megaptera.makaoBank.services.IncorrectAmount;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Account {
  @Id
  @GeneratedValue
  private Long id;

  private String accountNumber;

  private String name;

  private Long amount;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Account() {
  }

  public Account(Long id, String accountNumber, String name, Long amount) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.name = name;
    this.amount = amount;
  }

  public Account(String accountNumber, String name) {
    this.accountNumber = accountNumber;
    this.name = name;
    this.amount = 0L;
  }

  public String accountNumber() {
    return accountNumber;
  }

  public Long amount() {
    return amount;
  }

  public void transferTo(Account other, Long amount) {
    if (amount <= 0 || this.amount < amount) {
      throw new IncorrectAmount(amount);
    }

    this.amount -= amount;
    other.amount += amount;
  }

  public AccountDto toDto() {
    return new AccountDto(accountNumber, name, amount);
  }

  public static Account fake(String accountNumber) {
    return new Account(1L, accountNumber, "Tester", 100L);
  }
}
