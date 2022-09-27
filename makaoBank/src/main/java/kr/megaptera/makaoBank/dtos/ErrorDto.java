package kr.megaptera.makaoBank.dtos;

public abstract class ErrorDto {
  public static final int ACCOUNT_NOT_FOUND = 1001;
  public static final int INCORRECT_AMOUNT = 1002;

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
