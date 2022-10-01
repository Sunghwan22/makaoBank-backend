package kr.megaptera.makaoBank.dtos;

public class  AlreadyExistAccountNumberDto extends ErrorDto {
  public AlreadyExistAccountNumberDto() {
    super(1010, "이미 존재하는 계좌 번호 입니다");
  }
}
