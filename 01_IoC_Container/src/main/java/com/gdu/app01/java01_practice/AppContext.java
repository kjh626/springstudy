package com.gdu.app01.java01_practice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {

	@Bean
	public Address addr() {
		Address addr = new Address();
		addr.setAddress("incheon");
		addr.setTel("010-9999-3333");
		return addr;
	}
	@Bean
	public Friend friend() {
		Friend friend = new Friend();
		friend.setName("김정현");
		friend.setGender("남");
		friend.setAddr(addr());
		return friend;
	}
	
	@Bean(name="addr2")
	public Address zzz() {
		return new Address("busan", "010-5555-5555");
	}
	@Bean(name ="friend2")
	public Friend ddd() {
		return new Friend("송충이", "여", zzz());
	}
	
}
