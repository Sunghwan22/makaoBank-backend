package kr.megaptera.makaoBank.exceptions;

public class AlreadyExistAccountNumber extends RuntimeException {
  public AlreadyExistAccountNumber() {
    super("이미 존재하는 계좌 번호 입니다");
  }
}
