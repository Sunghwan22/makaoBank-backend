package kr.megaptera.makaoBank.backdoor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RestController
@RequestMapping("backdoor")
@Transactional
public class BackdoorController {
  private final JdbcTemplate jdbcTemplate;

  private final PasswordEncoder passwordEncoder;

  public BackdoorController(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
    this.jdbcTemplate = jdbcTemplate;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("setup-database")
  public String setupDatabase() {
    LocalDateTime now = LocalDateTime.now();
    //Todo setup database

    // 1.기존 데이터 삭제
    // 2.내가 원하는 데이터로 초기화
    // 3. 테스트 서버에서만 해줘야 한다. jdbcTemplate 이라는 걸 지원한다.
    // 4. 기존에 있는 것 삭제 -> DELETE FROM account;
    // 5. 데이터 추가 -> record , row -> INSERT account(id,name,accountNumber,amount)VALUES("Tester"...나머지들)

    // setup database 가 argon2에 뭔가 문제가 있어서 안된다.
    jdbcTemplate.execute("DELETE FROM transaction");
    jdbcTemplate.execute("DELETE FROM account");

    jdbcTemplate.update("" +
            "INSERT INTO account(" +
            "id, name, account_number, encoded_password, amount, " +
            "created_at, updated_at)" +
            " VALUES(1, ?, ?, ?, ?, ?, ?)",
        "Tester", "1234", passwordEncoder.encode("password"), 1_000_000L, now, now
    );

    jdbcTemplate.update("" +
            "INSERT INTO account(" +
            "id, name, account_number, encoded_password, amount, " +
            "created_at, updated_at)" +
            " VALUES(2, ?, ?, ?, ?, ?, ?)",
        "sunghwan", "1234567890", passwordEncoder.encode("password"), 2_000_000L, now, now
    );

    return "OK";
  }

  @GetMapping("change-amount")
  public String changeAmount(
      @RequestParam Long userId,
      @RequestParam Long amount
  ) {
    //todo 잔액 변경

    jdbcTemplate.update("UPDATE account SET amount=? WHERE id=? ", amount, userId);

    return "OK";
  }
}
