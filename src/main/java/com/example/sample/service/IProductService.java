package com.example.sample.service;

import java.util.List;

import com.example.sample.exception.ProductExistsException;
import com.example.sample.model.Product;

public interface IProductService {

	public List<Product> getAllProducts();
	
	public Product getProductByProductId(Integer productId);
	
	public List<Product> saveAllProducts(List<Product> productList);
	
	public Product saveProduct(Product product);
	
	public Product updateProduct(Integer productId, Product product);
	
	public String deleteProduct(Integer productId);
}
