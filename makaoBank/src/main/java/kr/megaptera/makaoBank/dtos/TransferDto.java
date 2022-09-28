package kr.megaptera.makaoBank.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransferDto {
  @NotBlank
  private String to;

  @NotNull
  private Long amount;

  @NotBlank
  private String name;

  public TransferDto(String to, Long amount) {
    this.to = to;
    this.amount = amount;
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
