package kr.megaptera.makaoBank.dtos;

public class RegisterFailedDto extends ErrorDto{
  private Integer code;

  public RegisterFailedDto(Integer code, String message) {
    super(code, message);
  }
}
