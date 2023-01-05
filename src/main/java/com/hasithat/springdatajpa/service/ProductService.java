package com.hasithat.springdatajpa.service;

import com.hasithat.springdatajpa.entity.CustomResponseDTO;
import com.hasithat.springdatajpa.entity.Product;
import com.hasithat.springdatajpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> fetchAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(int id){
        return productRepository.findById(id);
    }

    public List<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public List<Product> findByNameAndPrice(String name, double price){
        return productRepository.findByNameAndPrice(name, price);
    }

    public List<Product> findByProductPrice(double price){
        return productRepository.findByProductPrice(price);
    }

    public List<Product> findByProductType(String productType){
        return productRepository.findByProductType(productType);
    }

    public List<Product> findByDescription(String productDesc){
        return productRepository.findByDescription(productDesc);
    }

    public CustomResponseDTO updateProduct(int id, Product product){
        CustomResponseDTO<Product> customResponseDTO= new CustomResponseDTO<>();
        Optional<Product> existingProductOptional=this.getProductById(id);
        Product existingProduct=null;
        Product updatedProduct=null;
        if(existingProductOptional.isPresent()){
            existingProduct=existingProductOptional.get();
            existingProduct.setDescription(product.getDescription());
            existingProduct.setProductType(product.getProductType());
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            updatedProduct=productRepository.save(existingProduct);
            customResponseDTO.setT(updatedProduct);
            customResponseDTO.setMessage("Success");

        }else{
            customResponseDTO.setMessage("No Product data found for updating");
        }
        return customResponseDTO;
    }

    public String delete(int id){
        productRepository.deleteById(id);
        return "Successfully Deleted";
    }

    public List<Product> findByPriceList(List<Double> priceList){
        return productRepository.findByPriceIn(priceList);
    }

    public List<Product> findProductsWithinPriceRange(double minPrice, double maxPrice){
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product>  findProductsWithHigherPrice(double price){
        return productRepository.findByPriceGreaterThan(price);
    }

    //JPA we don't have Like. So we have to use Containing instead of Like.
    public List<Product> findProductsWithNameLike(String name){
        return productRepository.findByNameContaining(name);
    }

    //Sorting
    public List<Product> findProductsWithSorting(String fieldName){
        return productRepository.findAll(Sort.by(Sort.Direction.ASC,fieldName));
    }


    /*pagination
    * offset means page number (starts from 0)
    * limit means page size
    * */
    public Page<Product> getProductsWithPageResponse(int offset, int limit) {
        return productRepository.findAll(PageRequest.of(offset, limit));
    }

    public Page<Product> getProductsWithSortingAndPagination(String fieldName, int offset, int limit) {
        return productRepository
                .findAll(
                        PageRequest.of(offset, limit)
                                .withSort(Sort.by(Sort.Direction.ASC,fieldName)
                                ));
    }
}
