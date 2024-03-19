package io.hhplus.tdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//DB를 사용하지 않는 경우
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TddApplication {
	public static void main(String[] args) {
		SpringApplication.run(TddApplication.class, args);
	}
}