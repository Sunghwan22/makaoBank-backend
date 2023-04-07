package kr.megaptera.makaoBank.dtos;

import javax.validation.constraints.NotBlank;

public class LoginRequestDto {
  @NotBlank(message = "아이디를 입력해주세요")
  private String accountNumber;


  @NotBlank(message = "비밀번호를 입력해주세요")
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
