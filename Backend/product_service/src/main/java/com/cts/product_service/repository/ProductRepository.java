package com.cts.product_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.product_service.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
	
	Product findByProductId(long productId);
	
	@Query(value="SELECT p FROM Product p WHERE p.productCategory=:category")
	List<Product> getByProductCategory(@Param("category") String category);

}
