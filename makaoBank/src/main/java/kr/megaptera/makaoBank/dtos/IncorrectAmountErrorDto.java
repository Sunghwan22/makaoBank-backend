package kr.megaptera.makaoBank.dtos;

public class IncorrectAmountErrorDto extends ErrorDto {

  public IncorrectAmountErrorDto() {
    super(1002, "금액이 잘못 됬습니다");

  }
}
