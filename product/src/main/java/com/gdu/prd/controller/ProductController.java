package com.gdu.prd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.prd.domain.ProductDTO;
import com.gdu.prd.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	// Model은 Controller에서 선언할 수 있다. 그래서 컨트롤러에서 서비스로 전달해줘야 한다. 모델에 값을 저장하는 이유는 forward를 위해서
	@GetMapping("/list.do")
	public String list(Model model) {
		productService.loadProductList(model);
		return "product/list";
	}
	
	// request 안 쓰면 2가지 방법 남음. ①@RequestParam, ②커맨드객체(클래스ProductDTO)
	@PostMapping("/add.do")
	public String add(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
		int addResult = productService.addProduct(productDTO);
		redirectAttributes.addFlashAttribute("addRedult", addResult);
		return "redirect:/product/list.do";
	}
	
}
