package kr.megaptera.makaoBank.services;

import kr.megaptera.makaoBank.dtos.AccountRegistrationDto;
import kr.megaptera.makaoBank.exceptions.ConfirmPasswordMismatch;
import kr.megaptera.makaoBank.models.AccountNumber;
import kr.megaptera.makaoBank.models.User;
import kr.megaptera.makaoBank.repositoies.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User create(AccountRegistrationDto accountRegistrationDto) {
    if(!accountRegistrationDto.getPassword()
        .equals(accountRegistrationDto.getConfirmPassword())) {
      throw new ConfirmPasswordMismatch();
    }

    AccountNumber accountNumber =
        new AccountNumber(accountRegistrationDto.getAccountNumber());

    User user = new User(
        null,
        accountRegistrationDto.getName(),
        accountNumber);

    user.changePassword(accountRegistrationDto.getPassword(), passwordEncoder);

    userRepository.save(user);

    return user;
  }
}
