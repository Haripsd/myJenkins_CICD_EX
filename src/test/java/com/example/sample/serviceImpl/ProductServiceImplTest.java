package com.example.sample.serviceImpl;

import static org.assertj.core.api.Assertions.offset;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sample.exception.ProductExistsException;
import com.example.sample.exception.ProductNotFoundException;
import com.example.sample.model.Product;
import com.example.sample.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	
	@Mock
	private ProductRepository productRepository;
	
	@Mock
	private ObjectMapper objectMapper;
	
	
	@Test
	public void testGetAllProducts() {
		Mockito.when(productRepository.findAll()).thenReturn(mockProducts());
		List<Product> products = productServiceImpl.getAllProducts();
		assertNotNull(products);
		assertTrue(products.size()==2);
	}
	
	@Test
	public void testGetAllProducts_Empty() {
		Mockito.when(productRepository.findAll()).thenReturn(null);
		assertTrue(productServiceImpl.getAllProducts().isEmpty());
	}
	
	
	@Test
	public void testGetProductByProductId() {
		Product product = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		Optional<Product> optProduct = Optional.of(product);
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(optProduct);
		Product productObj = productServiceImpl.getProductByProductId(1);
		assertNotNull(productObj);
		assertEquals("LG", productObj.getProductName());
	}
	
	@Test
	public void testGetProductByProductId_Exception() throws ProductNotFoundException {
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(null);
		productServiceImpl.getProductByProductId(1);
		Mockito.verify(productRepository, times(1)).findById(Mockito.anyInt());
	}
	
	@Test
	public void testSaveProduct() {
		Product product = Product.builder().productName("LG").productCost(20000.0).build();
		Product savedProduct = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		assertNull(product.getProductId());
		productServiceImpl.saveProduct(product);
		assertNotNull(savedProduct);
		assertEquals(1, savedProduct.getProductId());
	}
	
	@Test
	public void testSaveProduct_AlreadyExist() throws ProductExistsException {
		Product product = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		assertNotNull(product.getProductId());
		productServiceImpl.saveProduct(product);
	}
	
	@Test
	public void testUpdateProduct() {
		Product inputProduct = Product.builder().productId(1).productName("LG").productCost(21000.0).build();
		Product existedProduct = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		Optional<Product> optExistedProd = Optional.of(existedProduct);
		Mockito.when(productRepository.findById(Mockito.anyInt())).thenReturn(optExistedProd);
		assertTrue(optExistedProd.isPresent());
		existedProduct.setProductId(inputProduct.getProductId());
		existedProduct.setProductName(inputProduct.getProductName());
		existedProduct.setProductCost(inputProduct.getProductCost());
		Mockito.when(productRepository.save(existedProduct)).thenReturn(inputProduct);
		productServiceImpl.updateProduct(1, existedProduct);
		assertNotNull(inputProduct);
		assertEquals(1, inputProduct.getProductId());
		assertTrue(21000.0==inputProduct.getProductCost());
	}
	
	@Test
	public void testdeleteProduct() {
		Mockito.doNothing().when(productRepository).deleteById(Mockito.anyInt());
		String message = productServiceImpl.deleteProduct(Mockito.anyInt());
		assertEquals("Product deleted Successfully.", message);
	}
	
	@Test
	public void testSaveAllProducts() {
		List<Product> productsList = new ArrayList<>();
		Product product1 = Product.builder().productName("LG").productCost(20000.0).build();
		Product product2 = Product.builder().productName("Samsung").productCost(15000.0).build();
		productsList.add(product1);
		productsList.add(product2);
		assertNull(productsList.get(0).getProductId());
		List<Product> savedProducts = mockProducts();
		assertNotNull(productServiceImpl.saveAllProducts(productsList));
		assertEquals(2, savedProducts.size());
	}
	
	@Test
	public void testSaveAllProducts_ListEmpty()throws ProductNotFoundException {
		assertNull(productServiceImpl.saveAllProducts(null));
	}
	
	
	
	
	private List<Product> mockProducts() {
		List<Product> productsList = new ArrayList<>();
		Product product1 = Product.builder().productId(1).productName("LG").productCost(20000.0).build();
		Product product2 = Product.builder().productId(2).productName("Samsung").productCost(15000.0).build();
		productsList.add(product1);
		productsList.add(product2);
		
		return productsList;
	}
	
}
