package com.hasithat.springdatajpa.controller;

import com.hasithat.springdatajpa.entity.CustomResponseDTO;
import com.hasithat.springdatajpa.entity.Product;
import com.hasithat.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.fetchAllProducts();
    }
    @GetMapping("/{id}")
    public CustomResponseDTO<Product> getProductById(@PathVariable("id") int id){
        Optional<Product> productOptional= productService.getProductById(id);
        Product product=null;
        CustomResponseDTO<Product> customResponseDTO= new CustomResponseDTO();
        if(productOptional.isPresent()){
            product=productOptional.get();
            customResponseDTO.setT(product);
            customResponseDTO.setMessage("success");

        }else{
            customResponseDTO.setMessage("No product data found");
        }
        return customResponseDTO;
    }

    @GetMapping("/byName/{name}")
    public List<Product> findByName(@PathVariable("name") String name){
        return productService.findByName(name);
    }

    @GetMapping("/byNameAndPrice/{name}/{price}")
    public List<Product> findByNameAndPrice(@PathVariable("name") String name, @PathVariable("price") double price){
        return productService.findByNameAndPrice(name, price);
    }

    @GetMapping("/byPrice/{price}")
    public List<Product> findByProductPrice(@PathVariable("price") double price){
        return productService.findByProductPrice(price);
    }

    @GetMapping("/byType/{type}")
    public List<Product> findByProductType(@PathVariable("type") String productType){
        return productService.findByProductType(productType);
    }

    @GetMapping("/byDescription/{description}")
    public List<Product> findByDescription(@PathVariable("description") String description){
        return productService.findByDescription(description);
    }

    @PutMapping("/{id}")
    public CustomResponseDTO<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product){
      return  productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")  int id){
        return productService.delete(id);

    }

    /*Since this method requires list of double values for processing, we have
    implemented this as post mapping */
    @PostMapping("/findByPriceList")
    public List<Product> findByPriceList(@RequestBody List<Double> priceList){
        return productService.findByPriceList(priceList);
    }

    @GetMapping("/findProductsWithinPriceRange/{minPrice}/{maxPrice}")
    public List<Product> findProductsWithinPriceRange(@PathVariable double minPrice,@PathVariable double maxPrice){
        return productService.findProductsWithinPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/findProductsWithHigherPrice/{price}")
    public List<Product>  findProductsWithHigherPrice(@PathVariable double price){
        return productService.findProductsWithHigherPrice(price);
    }

    @GetMapping("/findProductsWithNameLike/{name}")
    public List<Product> findProductsWithNameLike(@PathVariable String name){
        return productService.findProductsWithNameLike(name);
    }

    //sorting
    @GetMapping("/sort/{fieldName}")
    public List<Product> getProductsWithSorting(@PathVariable String fieldName) {
        return productService.findProductsWithSorting(fieldName);
    }

    //pagination
    @GetMapping("/page/{offset}/{limit}")
    public Page<Product> getProductsWithPageResponse(@PathVariable int offset, @PathVariable int limit) {
        return productService.getProductsWithPageResponse(offset, limit);
    }

    //sorting & pagination
    @GetMapping("/pageWithSort/{fieldName}/{offset}/{limit}")
    public Page<Product> getProductsWithSortingAndPagination(@PathVariable String fieldName, @PathVariable int offset, @PathVariable int limit) {
        return productService.getProductsWithSortingAndPagination(fieldName, offset, limit);
    }


}
