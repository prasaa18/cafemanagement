package com.in.cafe.cafemanagement.Model;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
@NamedQuery(
        name = "Product.getProductById",
        query = "SELECT NEW com.in.cafe.cafemanagement.wrapper.ProductWrapper(p.id, p.name,p.description, p.price) FROM Product p WHERE p.id = :id"
)

@NamedQuery(
        name = "Product.getProductByCategory",
        query = "SELECT NEW com.in.cafe.cafemanagement.wrapper.ProductWrapper(p.id, p.name) FROM Product p WHERE p.category.id = :id AND p.status = 'true' "
)



@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")

@NamedQuery(name = "Product.getAllProduct", query =
        "SELECT NEW com.in.cafe.cafemanagement.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status,p.category.id, p.category.name) from Product p"
)

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "Product")
public class Product implements Serializable {

    public static final Long serialVersionId = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;


}
