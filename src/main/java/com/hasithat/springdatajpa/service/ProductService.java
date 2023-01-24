package com.hasithat.springdatajpa.service;

import com.hasithat.springdatajpa.entity.CustomResponseDTO;
import com.hasithat.springdatajpa.entity.Product;
import com.hasithat.springdatajpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

    /*@PostConstruct
    public void initDb(){
       List<Product> productList= IntStream.rangeClosed(1, 10000).mapToObj(i->
                new Product(0,"product_"+i, new Random().nextDouble(),
                collect(Collectors.toList());
       productRepository.saveAll(productList);
    }*/

    /* Below annotation means while saving a product, delete products cache completely.
     * Instead of below annotations here we can use @CachePut annotation also.
     *  Which means while saving data into db  insert into products cache also.
     * */
    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true)})
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /*@Cacheable annotation is used to enable caching for below method.
     * Spring boot by default caching is maintained using a concurrent hashmap.
     * So all our products will be saved inside that hash map.
     */
    @Cacheable(cacheNames = "products")
    public List<Product> fetchAllProducts() {
        return productRepository.findAll();
    }


    @Cacheable(cacheNames = "product", key = "#id")
    public Optional<Product> getProductById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findByNameAndPrice(String name, double price) {
        return productRepository.findByNameAndPrice(name, price);
    }

    public List<Product> findByProductPrice(double price) {
        return productRepository.findByProductPrice(price);
    }

    public List<Product> findByProductType(String productType) {
        return productRepository.findByProductType(productType);
    }

    public List<Product> findByDescription(String productDesc) {
        return productRepository.findByDescription(productDesc);
    }

    /* Below annotation means while updating a product, delete products cache completely
    and delete only relevant product from product cache using id as the key.
     * @CachePut annotation means while updating data into db  insert into cache also.
     * Since we have mentioned the cacheNames with above @CacheConfig annotation, here no
     * need to mention it with below annotation also.*/
    //@CachePut(cacheNames = "product" , key="#product.id")
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "products", allEntries = true)})
    public CustomResponseDTO updateProduct(int id, Product product) {
        CustomResponseDTO<Product> customResponseDTO = new CustomResponseDTO<>();
        Optional<Product> existingProductOptional = this.getProductById(id);
        Product existingProduct = null;
        Product updatedProduct = null;
        if (existingProductOptional.isPresent()) {
            existingProduct = existingProductOptional.get();
            existingProduct.setDescription(product.getDescription());
            existingProduct.setProductType(product.getProductType());
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            updatedProduct = productRepository.save(existingProduct);
            customResponseDTO.setT(updatedProduct);
            customResponseDTO.setMessage("Success");

        } else {
            customResponseDTO.setMessage("No Product data found for updating");
        }
        return customResponseDTO;
    }

    /*Below annotation means while deleting a product, delete products cache completely
    and delete only relevant product from product cache using id as the key.
     */
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "products", allEntries = true)})
    public String delete(int id) {
        productRepository.deleteById(id);
        return "Successfully Deleted";
    }

    public List<Product> findByPriceList(List<Double> priceList) {
        return productRepository.findByPriceIn(priceList);
    }

    public List<Product> findProductsWithinPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> findProductsWithHigherPrice(double price) {
        return productRepository.findByPriceGreaterThan(price);
    }

    //JPA we don't have Like. So we have to use Containing instead of Like.
    public List<Product> findProductsWithNameLike(String name) {
        return productRepository.findByNameContaining(name);
    }

    //Sorting
    public List<Product> findProductsWithSorting(String fieldName) {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, fieldName));
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
                                .withSort(Sort.by(Sort.Direction.ASC, fieldName)
                                ));
    }
}
