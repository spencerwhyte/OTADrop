package com.spencerwhyte;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spencerwhyte.configuration.OtaDropApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OtaDropApplication.class)
@WebAppConfiguration
public class OtaDropApplicationTests {

	@Test
	public void contextLoads() {
	}

}
