package kr.megaptera.makaoBank.exceptions;

public class ConfirmPasswordMismatch extends RuntimeException {
  public ConfirmPasswordMismatch() {
    super("Password and confirmation password do not match!");
  }
}
