package kr.megaptera.makaoBank.dtos;

public class AccountNotFoundDto extends ErrorDto{
  public AccountNotFoundDto() {
    super(1001, "계좌 번호가 잘못됬습니다");
  }
}
