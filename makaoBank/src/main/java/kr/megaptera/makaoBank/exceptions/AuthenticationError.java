package kr.megaptera.makaoBank.exceptions;

import com.auth0.jwt.exceptions.JWTDecodeException;

public class AuthenticationError extends RuntimeException {
  public AuthenticationError() {
    super("Authentication Error");
  }
}
