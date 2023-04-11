package com.gdu.app01.xml04;

import java.sql.Connection;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MyDAO {
	// Connection 을 가져다 사용할 DAO
	// field
	private Connection con;
	// ps, rs 는 굳이 안씀 DB쓸 거 아니고 커넥션 보려고 하는거라서
	
	// ★singleton pattern - app-context.xml에서 <bean> 태그를 만들 때 사용된다. => scope="singleton" 로 대체★
	
	// method
	public Connection getConnection() {
		// 제일 먼저 커넥션을 가져와야 한다.
		// Spring Container에 만들어 둔 myConn Bean 가져오기
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml04/app-context.xml");
		con = ctx.getBean("myConn", MyConnection.class).getConnection();
		ctx.close();
		return con;
				
	}
	
	public void close() {
		// 그냥 close 메소드로 만들어줌
		try{ 
			if(con != null) {
			con.close();
			}
		} catch(Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public void list() {
		con = getConnection();
		
		close();
	}
	
	
}
