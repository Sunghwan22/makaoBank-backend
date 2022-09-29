package kr.megaptera.makaoBank.dtos;

public class MismatchPasswordDto extends ErrorDto{
  public MismatchPasswordDto() {
    super(1003, "비밀번호가 일치하지 않습니다");
  }
}
