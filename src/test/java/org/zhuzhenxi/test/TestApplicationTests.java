package org.zhuzhenxi.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

	@Test
	public void contextLoads() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2000,2,29);
		System.out.println(calendar.getTime());
	}

}
