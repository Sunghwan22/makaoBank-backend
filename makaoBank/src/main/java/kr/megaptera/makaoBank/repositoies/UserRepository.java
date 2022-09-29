package kr.megaptera.makaoBank.repositoies;

import kr.megaptera.makaoBank.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User save(User user);
}
