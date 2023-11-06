package com.example.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.sample.model.Product;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {

	@MockBean
	private ProductRepository productRepository;
	
	
	
	@Test
	@Rollback(value = false)
	@Order(1)
	public void testSaveAllProducts() {
		List<Product> productsList = new ArrayList<>();
		Product product1 = Product.builder().productName("LG").productCost(20000.0).build();
		Product product2 = Product.builder().productName("Samsung").productCost(15000.0).build();
		productsList.add(product1);
		productsList.add(product2);
		assertNotNull(productRepository.saveAll(productsList));
	}
	
	
	@Test
	@Rollback(value = false)
	@Order(2)
	public void testSaveProduct() {
		Product product = Product.builder().productName("LG").productCost(20000.0).build();
		Product productObj = productRepository.save(product);
		assertEquals("LG", product.getProductName());
	}
	
	
	@Test
	@Rollback(value = false)
	@Order(3)
	public void testUpdateProduct() {
		Product product = Product.builder().productId(1).productName("LG").productCost(21000.0).build();
		Product productObj = productRepository.save(product);
		assertTrue(21000.0==product.getProductCost());
	}
	
	@Test
	@Order(4)
	public void testGetAllProducts() {
		List<Product> productList = productRepository.findAll();
		assertNotNull(productList);
	}
	
	@Test
	@Order(5)
	public void testGetProductById() {
		Product product = Product.builder().productId(1).productName("LG").productCost(21000.0).build();
		Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
		assertNotNull(productRepository.findById(Mockito.anyInt()));
	}
	
	@Test
	@Order(6)
	public void testDeleteProduct() {
		productRepository.deleteById(Mockito.anyInt());
	}
}
