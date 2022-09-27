package kr.megaptera.makaoBank.dtos;

public class TransferResultDto {
  private Long transferred;

  public TransferResultDto(Long amount) {
    this.transferred = amount;
  }

  public Long getTransferred() {
    return transferred;
  }
}
