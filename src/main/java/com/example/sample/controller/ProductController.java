package com.example.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sample.model.Product;
import com.example.sample.service.IProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Swagger2ProductRestController", description = "REST APIs related to Product Entity!!!!")
@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private IProductService productService;

	@ApiOperation(value = "Get list of Products in the System ", response = Iterable.class, tags = "getAllProducts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/all")
	public ResponseEntity<?> getAllProducts() {
		List<Product> productsList = productService.getAllProducts();
		if (!CollectionUtils.isEmpty(productsList)) {
			return new ResponseEntity<List<Product>>(productsList, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("No Data found", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Get one Product details by productId", response = Iterable.class, tags = "getProductByProductId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/one/{productId}")
	public ResponseEntity<?> getProduct(@PathVariable("productId") Integer productId) {
		Product product = productService.getProductByProductId(productId);
		if (!ObjectUtils.isEmpty(product)) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Product doesn't exist with productId: " + productId,
					HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Save all Products in the System", response = Iterable.class, tags = "saveAllProducts")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PostMapping("/saveAll")
	public ResponseEntity<?> saveAllProducts(@RequestBody List<Product> productsList) {
		productService.saveAllProducts(productsList);
		if (!CollectionUtils.isEmpty(productsList)) {
			return new ResponseEntity<List<Product>>(productsList, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("List is empty", HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Save one Product in the System", response = Iterable.class, tags = "saveProduct")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PostMapping("/save")
	public ResponseEntity<?> saveProduct(@RequestBody Product product) {
		if (!ObjectUtils.isEmpty(product) && ObjectUtils.isEmpty(product.getProductId())) {
			Product productObj = productService.saveProduct(product);
			return new ResponseEntity<Product>(productObj, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("product already exist", HttpStatus.BAD_REQUEST);
		}

	}

	@ApiOperation(value = "Update Product details by productId", response = Iterable.class, tags = "updateProduct")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PutMapping("/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable("productId") Integer productId, @RequestBody Product product) {
		Product productObj = productService.updateProduct(productId, product);
		return new ResponseEntity<Product>(productObj, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete Product details by productId", response = Iterable.class, tags = "deleteProduct")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"), @ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable("productId") Integer productId) {
		String message = productService.deleteProduct(productId);
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
