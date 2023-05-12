package com.gdu.staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.staff.domain.StaffDTO;
import com.gdu.staff.mapper.StaffMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class StaffUnitTest {

	@Autowired
	private StaffMapper boardMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StaffUnitTest.class);
	
	@Before
	public void 등록테스트() {
		StaffDTO staff = new StaffDTO("99999", "김기획", "기획부", 0);
		assertEquals(1, boardMapper.addStaff(staff)); 
	}
	
	@Test
	public void 사원조회테스트() {
		String sno = "11111";  
		StaffDTO staff = boardMapper.getStaffByQuery(sno);
		LOGGER.info(staff.toString());
		assertNotNull(staff);
	}
		
}