package com.hasithat.springdatajpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasithat.springdatajpa.controller.ProductController;


import com.hasithat.springdatajpa.entity.Product;
import com.hasithat.springdatajpa.repository.ProductRepository;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringDataJpaApplicationTests {


	@Autowired
	private ProductController productController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductRepository productRepository;



	@Before
	public void setup(){
		this.mockMvc= MockMvcBuilders.standaloneSetup(ProductController.class).build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void addProductTest() throws Exception{
			Product demoProduct = new Product(1,"my_demo", 2000, "my demo product", "sample product");
			/*When you call save method of productRepository class always return demoProduct as the response.
			* So, here our test data will not be inserted into the db.
			* Below content means request body, contentType means request  body data type,
			* accept means response body data type*/
			when(productRepository.save(any())).thenReturn(demoProduct);
			mockMvc.perform(MockMvcRequestBuilders.post("/product")
							.content(convertObjectAsString(demoProduct))
							.contentType("application/json")
							.accept("application/json")).
					andExpect(status().isOk()).
					andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());


	}

	/*Here jsonPath("$.*").exists() means return list size >0*/
	@Test
	public void getAllProductsShouldReturnAllProducts() throws Exception {

		List<Product> productList= new ArrayList<Product>();
		productList.add(new Product(1,"my_demo1", 2000, "my demo product 01", "sample product 01"));
		productList.add(new Product(2,"my_demo2", 23000, "my demo product 02", "sample product 02"));
		when(productRepository.findAll()).thenReturn(productList);
		mockMvc.perform(MockMvcRequestBuilders.get("/product").accept("application/json")).
				andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*").exists());

	}

	@Test
	public void getProductByIdShouldReturnAProduct() throws Exception {

		Product product=new Product(5,"my_demo1", 2000, "my demo product 01", "sample product 01");
		when(productRepository.findById(5)).thenReturn(Optional.of(product));
		mockMvc.perform(MockMvcRequestBuilders.get("/product/"+5).accept("application/json")).
				andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.t.id").value(5));

	}

	@Test
	public void updateProductTest() throws Exception{
		Product demoProduct = new Product(10,"my_demo", 2000, "my demo product", "sample product");
		Product newProduct = new Product(10,"my_demo_new", 4000, "my demo product", "sample product");
		when(productRepository.findById(10)).thenReturn(Optional.of(demoProduct));
		when(productRepository.save(any())).thenReturn(newProduct);
		mockMvc.perform(MockMvcRequestBuilders.put("/product/"+10)
						.content(convertObjectAsString(newProduct))
						.contentType("application/json")
						.accept("application/json")).
				andExpect(status().isOk()).
				andExpect(MockMvcResultMatchers.jsonPath("$.t.name").value("my_demo_new")).
				andExpect(MockMvcResultMatchers.jsonPath("$.t.price").value(4000));


	}

	@Test
	public void deleteProductTest() throws Exception{
		Mockito.doNothing().when(productRepository).deleteById(anyInt());

		mockMvc.perform(MockMvcRequestBuilders.delete("/product/"+10)
						.accept("application/json")).
				andExpect(status().isOk());


	}


	private String convertObjectAsString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
