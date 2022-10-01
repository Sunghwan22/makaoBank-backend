package kr.megaptera.makaoBank.utils;

import com.auth0.jwt.exceptions.JWTDecodeException;
import kr.megaptera.makaoBank.exceptions.AuthenticationError;
import kr.megaptera.makaoBank.models.AccountNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtUtilTest {
  static final String SECRET = "SECRET";

  private JwtUtil jwtUtil;

  @BeforeEach
  void setUp() {
    jwtUtil = new JwtUtil(SECRET);

  }
  @Test
  void encodeAndDecode() {
    AccountNumber original = new AccountNumber("1234");
    String token = jwtUtil.encode(original);

    assertThat(token).contains(".");

    AccountNumber accountNumber = jwtUtil.decode(token);

    assertThat(accountNumber).isEqualTo(original);
  }

  @Test
  void decodeError() {
    assertThrows(JWTDecodeException.class, () -> {
      jwtUtil.decode("xxx");
    });
  }
}
