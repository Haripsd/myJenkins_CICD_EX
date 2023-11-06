package com.example.sample.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.sample.model.Product;
import com.example.sample.service.IProductService;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	
	@InjectMocks
	private ProductController productController;
	
	@MockBean
	private IProductService productServiceImpl;
	
	@Test
	public void testGetAllProducts() {
		List<Product> productsList = mockProductsList();
		Mockito.when(productServiceImpl.getAllProducts()).thenReturn(productsList);
		ResponseEntity<List<Product>> response = new ResponseEntity<List<Product>>(productsList, HttpStatus.OK);
		response = (ResponseEntity<List<Product>>) productController.getAllProducts();
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void testGetAllProducts_ListNotPresent() {
		Mockito.when(productServiceImpl.getAllProducts()).thenReturn(new ArrayList<>());
		ResponseEntity<String> response = new ResponseEntity<String>("No Data found", HttpStatus.BAD_REQUEST);
		response = (ResponseEntity<String>) productController.getAllProducts();
		assertNotNull(response);
		assertEquals("No Data found", response.getBody());
		assertEquals(true, response.getStatusCode().is4xxClientError());
	}
	
	
	@Test
	public void testGetProduct() {
		Product product = new Product(1, "LG", 20000.0);
		Mockito.when(productServiceImpl.getProductByProductId(Mockito.anyInt())).thenReturn(product);
		ResponseEntity<Product> response = new ResponseEntity<Product>(product, HttpStatus.OK);
		response = (ResponseEntity<Product>) productController.getProduct(Mockito.anyInt());
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void testGetProduct_ProductDoesnotExist() {
		Integer productId = 1;
		Mockito.when(productServiceImpl.getProductByProductId(1)).thenReturn(null);
		ResponseEntity<String> response = new ResponseEntity<String>("Product doesn't exist with productId: " + productId,
				HttpStatus.BAD_REQUEST);
		response = (ResponseEntity<String>) productController.getProduct(productId);
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is4xxClientError());
	}
	
	
	@Test
	public void testSaveAllProducts() {
		List<Product> productsList = new ArrayList<>();
		Product product1 = Product.builder().productName("LG").productCost(20000.0).build();
		Product product2 = Product.builder().productName("Samsung").productCost(15000.0).build();
		productsList.add(product1);
		productsList.add(product2);
		Mockito.when(productServiceImpl.saveAllProducts(productsList)).thenReturn(mockProductsList());
		ResponseEntity<List<Product>> response = new ResponseEntity<List<Product>>(productsList, HttpStatus.OK);
		response = (ResponseEntity<List<Product>>) productController.saveAllProducts(productsList);
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void testSaveAllProducts_ListEmpty() {
		Mockito.when(productServiceImpl.saveAllProducts(new ArrayList<>())).thenReturn(null);
		ResponseEntity<String> response = new ResponseEntity<String>("List is empty", HttpStatus.BAD_REQUEST);
		response = (ResponseEntity<String>) productController.saveAllProducts(null);
		assertNotNull(response);
		assertEquals("List is empty", response.getBody());
		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	
	@Test
	public void testSaveProduct() {
		Product product = Product.builder().productName("LG").productCost(20000.0).build();
		Product productObj = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		Mockito.when(productServiceImpl.saveProduct(product)).thenReturn(productObj);
		ResponseEntity<Product> response = new ResponseEntity<Product>(productObj, HttpStatus.OK);
		response = (ResponseEntity<Product>) productController.saveProduct(product);
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void testSaveProduct_ProductAlreadyExist() {
		Product product = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		Mockito.when(productServiceImpl.saveProduct(product)).thenReturn(product);
		ResponseEntity<String> response = new ResponseEntity<String>("product already exist", HttpStatus.BAD_REQUEST);
		response = (ResponseEntity<String>) productController.saveProduct(product);
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is4xxClientError());
	}
	
	@Test
	public void testUpdateProduct() {
		Product product = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		Mockito.when(productServiceImpl.saveProduct(product)).thenReturn(product);
		ResponseEntity<Product> response = new ResponseEntity<Product>(product, HttpStatus.OK);
		response = (ResponseEntity<Product>) productController.updateProduct(Mockito.anyInt(), Mockito.eq(product));
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is2xxSuccessful());
	}
	
	@Test
	public void testDeleteProduct() {
		Product product = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		ResponseEntity<String> response = new ResponseEntity("Product deleted Successfully.", HttpStatus.OK);
		Mockito.when(productServiceImpl.deleteProduct(Mockito.anyInt())).thenReturn("Product deleted Successfully.");
		response = (ResponseEntity<String>) productController.deleteProduct(1);
		assertNotNull(response);
		assertEquals(true, response.getStatusCode().is2xxSuccessful());
	}
	
	private List<Product> mockProductsList() {
		List<Product> productsList = new ArrayList<>();
		Product p1 = new Product(1, "LG", 20000.0);
		Product p2 = new Product(2, "Samsung", 15000.0);
		productsList.add(p1);
		productsList.add(p2);
		
		return productsList;
	}
}
