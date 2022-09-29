package kr.megaptera.makaoBank.dtos;

public abstract class ErrorDto {
  public static final int ACCOUNT_NOT_FOUND = 1001;
  public static final int INCORRECT_AMOUNT = 1002;
  public static final int NOT_EQUAL_CONFIRMPASSWORD = 1003;
  public static final int DEFAULT = 1004;
  public static final int INCORRECT_NAME = 1005;
  public static final int INCORRECT_ACCOUNTNUMBER = 1006;
  public static final int INCORRECT_PASSWORD = 1007;
  public static final int BLANKINFORMATION = 1008;

  private final Integer code;

  private final String message;

  public ErrorDto(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
