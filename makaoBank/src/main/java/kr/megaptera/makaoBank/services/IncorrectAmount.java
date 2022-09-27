package kr.megaptera.makaoBank.services;

public class IncorrectAmount extends RuntimeException {
  public IncorrectAmount(Long amount) {
    super("Incorrect amount (amount: " + amount + ")");
  }
}
