package kr.megaptera.makaoBank.backdoor;

import org.springframework.jdbc.core.JdbcTemplate;
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

  public BackdoorController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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
    jdbcTemplate.execute("DELETE FROM account");

    jdbcTemplate.update("" +
            "INSERT INTO account(" +
            "id, name, account_number, amount, " +
            "created_at, updated_at)" +
            " VALUES(1, 'tester', '1234', 100000, ?, ?)",
        now, now
    );

    jdbcTemplate.update("" +
            "INSERT INTO account(" +
            "id, name, account_number, amount, " +
            "created_at, updated_at)" +
            " VALUES(2, 'sunghwan', '1234567890', 500000, ?, ?)",
        now, now
    );

    return "OK";
  }

  @GetMapping("change-amount")
  public String changeAmount(
      @RequestParam Long userId,
      @RequestParam Long amount
  ) {
    //todo 잔액 변경

    //데이터 수정은 UPDATE SET amount 123000
    // sql inject??

    jdbcTemplate.update("UPDATE account SET amount=? WHERE id=? ", amount, userId);
    // 중간에 &&가 들어가면 ""를 넣어줘야함
    return "OK";
  }
}
