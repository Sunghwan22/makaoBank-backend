package kr.megaptera.makaoBank.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AccountRegistrationDto {
  @Pattern(regexp = "^[가-힣]{3,7}$", message = "3~7자리의 한글만 사용가능합니다")
  @NotBlank(message = "이름은 공백일 수 없습니다")
  private String name;

  @Pattern(regexp = "^\\d{8}$", message = "로그인 및 거래시 사용될 계좌번호이며 숫자만 사용 가능(8글자)")
  @NotBlank(message = "계좌번호는 공백일 수 없습니다")
  private String accountNumber;

  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}"
  ,message = "8글자 이상의 영문(대소문자),숫자,특수문자가 모두 포함되어야 합니다")
  @NotBlank(message = "비밀번호는 공백일 수 없습니다")
  private String password;

  @NotBlank(message = "확인 비밀번호는 공백일 수 없습니다")
  private String confirmPassword;

  public AccountRegistrationDto() {
  }

  public AccountRegistrationDto(String name, String accountNumber, String password, String confirmPassword) {
    this.name = name;
    this.accountNumber = accountNumber;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getName() {
    return name;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getPassword() {
    return password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }
}
