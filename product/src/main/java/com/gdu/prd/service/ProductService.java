package com.gdu.prd.service;

import org.springframework.ui.Model;

import com.gdu.prd.domain.ProductDTO;

public interface ProductService {
	
	// 반환 안 하는 이유 -> 반환할 게 너무나 많아서.. return을 어떻게 한번에 하겠니...
	public void loadProductList(Model model);
	public int addProduct(ProductDTO productDTO);
}
