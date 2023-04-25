package com.gdu.app09.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.app09.domain.EmpDTO;
import com.gdu.app09.mapper.EmployeeListMapper;
import com.gdu.app09.util.PageUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor    // field에 @Autowired 처리가 된다.
@Service
public class EmployeeListServiceImpl implements EmployeeListService {
	
	// field 빈이 2개 적혀있으면, 일반적으로 생성자나 메소드로 처리
	private EmployeeListMapper employeeListMapper;
	private PageUtil pageUtil;

	@Override
	public void getEmployeeListUsingPagination(HttpServletRequest request, Model model) {
		
		// 파라미터 page가 전달되지 않는 경우 page=1 로 처리한다.
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		// 전체 레코드 개수를 구한다.(DB에서 구해온다 => 불러주기만 하면 끝남)
		int totalRecord = employeeListMapper.getEmployeeCount();
		
		// 세션에 있는 recordPerPage를 가져온다. 세션에 없는 경우 recordPerPage=10으로 처리한다. 세션에 있는 데이터는 Object타입
		HttpSession session = request.getSession();
		Optional<Object> opt2 = Optional.ofNullable(session.getAttribute("recordPerPage"));
		int recordPerPage = (int) (opt2.orElse(10));
		
		// PageUtil(Pagination에 필요한 모든 정보(9개 필드값)) 계산하기
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기(PageUtil에서 계산한 begin과 end값 가져다 map에 저장)
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		// begin ~ end 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingPagination(map); 
		
		// pagination.jsp로 전달할(forward)할 정보를 저장하기
		model.addAttribute("employees", employees);
		// jsp에서 쓸 ${pagination} 만들어서 보내줘야한다.
		model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/employees/pagination.do"));
	}

}