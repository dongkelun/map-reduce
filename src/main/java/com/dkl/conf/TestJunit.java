package com.dkl.conf;

import org.apache.hadoop.conf.Configuration;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TestJunit {

	@Test
	public  void test() {
		System.out.println("10231950adjkl-0011asd123qe".substring(4, 8));
		System.out.println("10231950adjkl+00011asd123qe".substring(13, 19));
		
		Configuration configuration = new Configuration();
		configuration.addResource("conf1.xml");
		configuration.addResource("conf2.xml");
		Assertions.assertThat(configuration.get("color")).isEqualTo("blue");
		Assertions.assertThat(configuration.get("weight")).isEqualTo("heavy");
	}

}
