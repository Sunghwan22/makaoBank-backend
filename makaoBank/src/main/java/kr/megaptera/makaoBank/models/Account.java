package kr.megaptera.makaoBank.models;

import kr.megaptera.makaoBank.dtos.AccountCreatedDto;
import kr.megaptera.makaoBank.dtos.AccountDto;
import kr.megaptera.makaoBank.exceptions.IncorrectAmount;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Account {
  @Id
  @GeneratedValue
  private Long id;
 // 테이블
  @Embedded
  private AccountNumber accountNumber;

  private String name;

  private Long amount;

  private String encodedPassword;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Account() {
  }

  public Account(AccountNumber accountNumber, String name) {
    this.accountNumber = accountNumber;
    this.name = name;
    this.amount = 0L;
  }

  public Account(Long id, AccountNumber accountNumber,
                 String name, Long amount) {
    this.id = id;
    this.accountNumber = accountNumber;
    this.name = name;
    this.amount = amount;
  }

  public Account(Long id, String name, AccountNumber accountNumber) {
    this.id = id;
    this.name = name;
    this.accountNumber = accountNumber;
  }

  public AccountNumber accountNumber() {
    return accountNumber;
  }

  public Long amount() {
    return amount;
  }

  public String name() {
    return name;
  }

  public String encodedPassword() {
    return encodedPassword;
  }

  public void transfer(Account other, Long amount) {
    if (amount <= 0 || this.amount < amount) {
      throw new IncorrectAmount(amount);
    }

    this.amount -= amount;
    other.amount += amount;
  }

  public static Account fake(String accountNumber) {
    return new Account(1L, new AccountNumber(accountNumber), "Tester", 100L);
  }

  public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, encodedPassword);
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    encodedPassword = passwordEncoder.encode(password);
  }

  public AccountDto toDto() {
    return new AccountDto(accountNumber.value(), name, amount);
  }

  public AccountCreatedDto toCreatedDto() {
    return new AccountCreatedDto(accountNumber.value(), name);
  }
}
