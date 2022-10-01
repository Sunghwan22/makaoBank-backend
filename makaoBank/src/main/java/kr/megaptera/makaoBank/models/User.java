package kr.megaptera.makaoBank.models;

import kr.megaptera.makaoBank.dtos.AccountCreatedDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class User {
  @Id
  @GeneratedValue
  private Long id;

  private String name;

  private AccountNumber accountNumber;

  private String password;

  public User() {
  }

  public User(Long id, String name, AccountNumber accountNumber, String password) {
    this.id = id;
    this.name = name;
    this.accountNumber = accountNumber;
    this.password = password;
  }

  public User(Long id, String name, AccountNumber accountNumber) {
    this.id = id;
    this.name = name;
    this.accountNumber = accountNumber;
  }

  public Long id() {
    return id;
  }

  public String name() {
    return name;
  }

  public AccountNumber accountNumber() {
    return accountNumber;
  }

  public String password() {
    return password;
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(password);
  }

//  public AccountCreatedDto toCreatedDto() {
//    return new AccountCreatedDto(id, accountNumber.value(), name);
//  }
}
