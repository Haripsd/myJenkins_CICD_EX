package com.example.sample.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.example.sample.exception.ProductExistsException;
import com.example.sample.exception.ProductNotFoundException;
import com.example.sample.model.Product;
import com.example.sample.repository.ProductRepository;
import com.example.sample.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductServiceImpl implements IProductService {

	private Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public List<Product> getAllProducts() {
		List<Product> productsList = productRepository.findAll();
		log.info("All product details [{}]", productsList);
		return !CollectionUtils.isEmpty(productsList) ? productsList : new ArrayList<>();
	}

	@Override
	public Product getProductByProductId(Integer productId) {
		try {
			log.info("Get product details by productId: [{}]", productId);
			return productRepository.findById(productId)
					.orElseThrow(() -> new ProductNotFoundException("Product not found with productId : " + productId,
							HttpStatus.NOT_FOUND));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Product saveProduct(Product product) {
		try {
			if (!ObjectUtils.isEmpty(product) && ObjectUtils.isEmpty(product.getProductId())) {
				log.info("product details for save : "+objectMapper.writeValueAsString(product));
				return productRepository.save(product);
			} else {
				throw new ProductExistsException("Product Already Existed", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return product;
	}

	@Override
	public Product updateProduct(Integer productId, Product product) {
		Product updateProduct = null;
		try {
			log.info("product details for updae : "+objectMapper.writeValueAsString(product));
			Product productObj = productRepository.findById(productId)
					.orElseThrow(() -> new ProductNotFoundException("Product not existed.", HttpStatus.NOT_FOUND));
			productObj.setProductId(product.getProductId());
			productObj.setProductName(product.getProductName());
			productObj.setProductCost(product.getProductCost());
			updateProduct = productRepository.save(productObj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return updateProduct;
	}

	@Override
	public String deleteProduct(Integer productId) {
		Product product = null;
		try {
			log.info("delete Product details by productId : {}", productId);
			productRepository.deleteById(productId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "Product deleted Successfully.";

	}

	@Override
	public List<Product> saveAllProducts(List<Product> productList) {
		try {
			if (!CollectionUtils.isEmpty(productList)) {
				log.info("product details for saving list : "+objectMapper.writeValueAsString(productList));
				return productRepository.saveAll(productList);
			} else {
				throw new ProductNotFoundException("Products list is empty.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
		
	}

}
