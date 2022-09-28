package kr.megaptera.makaoBank.dtos;

import java.util.List;

public class TransactionsDto extends TransactionDto {
  private List<TransactionDto> transactionsDtos;

  public TransactionsDto(List<TransactionDto> transactionsDtos) {
    this.transactionsDtos = transactionsDtos;
  }

  public TransactionsDto() {
  }

  public List<TransactionDto> getTransactionsDtos() {
    return transactionsDtos;
  }
}
