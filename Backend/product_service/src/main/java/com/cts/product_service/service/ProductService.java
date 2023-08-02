package com.cts.product_service.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cts.product_service.dto.ProductResponse;
import com.cts.product_service.entity.Product;
import com.cts.product_service.exceptions.ProductNotFoundException;
import com.cts.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	public String createProduct(MultipartFile file,String productName,
			String productCategory,String description,long productPrice) throws IOException{
		Product prod = new Product();
		prod.setProductName(productName);
		prod.setProductCategory(productCategory);
		prod.setDescription(description);
		prod.setProductPrice(productPrice);
		prod.setProductImage(file.getBytes());
		productRepository.save(prod);
		log.info("Product "+prod.getProductId()+" is inserted");
		return "Product "+prod.getProductId()+" is inserted";
		
	}
	
	public List<Product> getAllProducts(){
		List<Product> products = productRepository.findAll();
		return products;
	}
	
	public List<Product> getProductByCatgeory(String category){
		List<Product> products = productRepository.getByProductCategory(category);
		return products;
	}
	
	public Product getProductById(long productId) {
		Product p = productRepository.findByProductId(productId);
		if(p==null)
		{
			throw new ProductNotFoundException("Product with "+productId+" not found!");
		}
		else {
			return p;
		}
	}
	
	public Page<Product> paginationProduct(int offset,int pageSize)
	{ 
		Page<Product> products=productRepository.findAll(PageRequest.of(offset,pageSize));
		return products;
		
	}
	
	public String updateProductById(MultipartFile file,String productName,
			String productCategory,String description,long productPrice,long productId) throws IOException {
		
		Product prod = productRepository.findByProductId(productId);
		
		if(prod==null)
		{
			throw new ProductNotFoundException("Product with "+productId+" not found!");
		}
		prod.setProductName(productName);
		prod.setProductCategory(productCategory);
		prod.setDescription(description);
		prod.setProductPrice(productPrice);
		prod.setProductImage(file.getBytes());		
		productRepository.save(prod);
		log.info("Product "+prod.getProductId()+" is Updated");
		return "Product "+prod.getProductId()+" is Updated";
		
	}
	
	public void deleteProductById(long productId) {
		
		Product prod = productRepository.findByProductId(productId);
		if(prod!=null) {
			productRepository.deleteById(productId);
			log.info("product deleted with id "+productId);
		}
		else {
			throw new ProductNotFoundException("Product with "+productId+" not found!");
		}
//				.orElseThrow(()-> new ProductNotFoundException("Product with "+productId+" not found!"));
		
	}
	

}
