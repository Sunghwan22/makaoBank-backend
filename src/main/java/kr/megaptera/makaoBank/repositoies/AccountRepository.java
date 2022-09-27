package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByAccountNumber(String accountNumber);
}
