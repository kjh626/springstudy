package com.gdu.prd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.prd.domain.ProductDTO;

@Mapper
public interface ProductMapper {

	public List<ProductDTO> getProductList();
	public int getProductCount();
	public ProductDTO getProduct(int prodNo);
	public int addProduct(ProductDTO productDTO);
	public int updateProduct(ProductDTO productDTO);
	public int deleteProduct(int prodNo);
	
}
