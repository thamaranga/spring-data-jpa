package com.hasithat.springdatajpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
/*Below annotation is for handling preprocessing logics  for setting values to
createdBy, createdTime , lastModifiedBy , lastModifiedTime fields.
*/
@EntityListeners(AuditingEntityListener.class)
//Below annotation means maintain revisions using spring data envers
@Audited

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private String description;
    private String productType;

    @CreatedBy
    private String createdBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedTime;

    public Product(int id,String name, double price, String description, String productType) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.productType = productType;
    }
}
