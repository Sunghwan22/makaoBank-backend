package kr.megaptera.makaoBank.dtos;

public class LoginRequestDto {
  private String accountNumber;

  private String password;

  public LoginRequestDto(String accountNumber, String password) {
    this.accountNumber = accountNumber;
    this.password = password;
  }

  public LoginRequestDto() {
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getPassword() {
    return password;
  }
}
