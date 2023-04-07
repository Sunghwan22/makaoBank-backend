package kr.megaptera.makaoBank.exceptions;

public class LoginFailed extends RuntimeException {
  public LoginFailed() {
    super("Login Failed");
  }
}
