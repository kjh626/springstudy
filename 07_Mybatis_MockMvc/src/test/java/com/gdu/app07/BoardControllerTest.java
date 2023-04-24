package com.gdu.app07;

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
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// JUnit4
@RunWith(SpringJUnit4ClassRunner.class)

// ContextConfiguration
// 테스트에서 사용할 Bean이 @Component로 생성되었기 때문에 component-scan(태그, 애너테이션 둘다 가능)이 작성된 servlet-context.xml의 경로를 작성한다.
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})

// 테스트 순서를 메소드명의 오름차순(알파벳순)으로 수행
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

// WebApplicationContext를 사용하기 위해서 필요한 애너테이션
@WebAppConfiguration
public class BoardControllerTest {

	/*
		Mock 테스트
		
		1. 가상 MVC 테스트이다.
		2. Controller를 테스트할 수 있는 통합 테스트이다.
		3. method + mapping을 이용해서 테스트를 진행한다.
	*/
	
	// Mock 테스트를 수행하는 객체. (동작을 위해 servlet기능을 사용한다.)
	// WebApplicationContext에 의해서 생성된다.
	private MockMvc mockMvc;
	
	// ctx.getBean을 썼던 클래스들에 getServletContext()가 추가된 아이이다.
	// ※ getServletContext() : 프로젝트 자체를 말한다.(application)
	// @WebApplication이 있어야 자동 주입(@Autowired)이 가능하다.
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	// LOGGER
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardControllerTest.class);
	
	
	// @Before  <- junit 으로 생성할 때 setUp()체크할 수 있는데, 여기서 @Before를 썼다.
	// 1. 모든 @Test 수행 이전에 실행된다.
	// 2. MockMvc mockMvc 객체를 @Before에서 build한다. (테스트 5번 수행(목록상세삽입수정삭제), 테스트이전에 before 5번 수행된다.)
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.build();
	}
	
	
	@Test
	public void a1삽입테스트() throws Exception {
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders
				.post("/board/add.do")					// @PostMapping("/board/add.do")
					.param("title", "테스트제목")		// 파라미터
					.param("content", "테스트내용")		// 파라미터
					.param("writer", "테스트작성자"))	// 파라미터
						.andReturn()					// 삽입결과
						.getFlashMap()					// FlashAttribute로 저장된 결과 확인.    결과를 모델에 저장해서 확인하는 경우 = ModelAndView를 사용해서 결과를 받아보고, 모델의 FlashAttribute에 저장하는 경우 getFlashMap을 사용 => 우리는 삽입할 때 FlashAttribute로 했음
							.toString());  				// 결과를 문자열로 바꿔줬다. => 우리의 목적은 이걸 로거로 찍어보는 것이다. => LOGGER.debug에 다 넣어주면 됨.
	}
	
	@Test
	public void a2수정테스트() throws Exception {
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders
				.post("/board/modify.do")				// @PostMapping("/board/modify.do")
					.param("title", "테스트제목2")		// 파라미터
					.param("content", "테스트내용2")	// 파라미터
					.param("boardNo", "1"))				// 파라미터(보드 초기화하고 테스트 진행할 거라서 위에 삽입테스트 후 삽입된 boardNo가 1이라고 가정하고 테스트)
						.andReturn()					// 수정결과
						.getFlashMap()					// FlashAttribute로 저장된 결과 확인.
							.toString());
	}
	
	@Test
	public void a3상세조회테스트() throws Exception {
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/detail.do")      // @GetMapping("/board/detail.do")
					.param("boardNo", "1"))   // 파라미터
						.andReturn()          // 상세조회결과
						.getModelAndView()    // Model에 저장된 조회 결과를 가져오기 위해서 ModelAndView를 먼저 가져옴
						.getModelMap()        // ModelAndView에서 Model을 가져옴 
							.toString());     
	}
	
	@Test
	public void a4목록조회테스트() throws Exception {
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/list.do"))   // @GetMapping("/board/list.do")
					.andReturn()          // 목록조회결과
					.getModelAndView()    // Model에 저장된 조회 결과를 가져오기 위해서 ModelAndView를 먼저 가져옴
					.getModelMap()        // ModelAndView에서 Model을 가져옴 
						.toString());     
	}
	
	@Test
	public void a5삭제테스트() throws Exception {
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders
				.post("/board/remove.do")				// @PostMapping("/board/remove.do")
					.param("boardNo", "1"))				// 파라미터
						.andReturn()					// 삭제결과
						.getFlashMap()					// FlashAttribute로 저장된 결과 확인.
							.toString());
	}
	
	// 어설트 사용을 안 했기 때문에 JUnit은 테스트 성공으로 보이는데... 로그로 찍은 걸로 성공/실패를 확인해봐야 한다.
	
	
}
