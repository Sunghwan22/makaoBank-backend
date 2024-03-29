package kr.megaptera.makaoBank.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransferDto {
  // 하이픈 제외 숫자 8글자가 들어가야 함
  @NotBlank
  private String to;

  // 내 계좌 금액보다 크면 안됨. 0 이하여서도 안됨 => 이런건 서비스 레이에서도 처리 가능
  @NotNull
  private Long amount;

  @NotBlank
  private String name;

  public TransferDto() {
  }

  public TransferDto(String to, Long amount, String name) {
    this.to = to;
    this.amount = amount;
    this.name = name;
  }

  public String getTo() {
    return to;
  }

  public Long getAmount() {
    return amount;
  }

  public String getName() {
    return name;
  }
}
