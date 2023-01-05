package com.hasithat.springdatajpa.repository;

import com.hasithat.springdatajpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product , Integer> {
    List<Product> findByName(String name);

    List<Product> findByNameAndPrice(String name, double price);

    /*This is the native query*/
    @Query(value = "SELECT * FROM product WHERE price >= ?1" , nativeQuery = true)
    List<Product> findByProductPrice(double price);

    /*This is the jpql with position based parameters. Here instead of table names
    * and column names we need to give Class name and variable names*/
    @Query(value = "FROM Product WHERE productType= ?1")
    List<Product> findByProductType(String productType);

    /*This is the jpql with named parameters . Here instead of table names
     * and column names we need to give Class name and variable names*/
    @Query(value = "FROM Product WHERE description= :desc")
    List<Product> findByDescription(@Param("desc") String description);


    List<Product> findByPriceIn(List<Double> priceList);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByPriceGreaterThan(double price);

    List<Product> findByNameContaining(String name);
}