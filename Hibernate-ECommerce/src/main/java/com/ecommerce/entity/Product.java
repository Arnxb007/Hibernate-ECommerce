package com.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="products")
@NamedQueries({
    @NamedQuery(name="Product.findByCategory",query="SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.deleted = false"),
    @NamedQuery(name="Product.findByCategoryId",query="SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.deleted = false"),
    @NamedQuery(name="Product.findActive",query="SELECT p FROM Product p WHERE p.deleted = false"),
    @NamedQuery(name="Product.findByPriceRange",query="SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.deleted = false ORDER BY p.price ASC")
})
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="name",nullable=false,length=150) private String name;
    @Column(name="price",nullable=false,precision=10,scale=2) private BigDecimal price;
    @Column(name="stock_quantity",nullable=false) private Integer stockQuantity=0;
    @Column(name="deleted",nullable=false) private boolean deleted=false;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="category_id",nullable=false,foreignKey=@ForeignKey(name="fk_product_category"))
    private Category category;
    public Product(){}
    public Product(String name,BigDecimal price,Integer stockQuantity,Category category){this.name=name;this.price=price;this.stockQuantity=stockQuantity;this.category=category;}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String n){name=n;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal p){price=p;}
    public Integer getStockQuantity(){return stockQuantity;} public void setStockQuantity(Integer s){stockQuantity=s;}
    public boolean isDeleted(){return deleted;} public void setDeleted(boolean d){deleted=d;}
    public Category getCategory(){return category;} public void setCategory(Category c){category=c;}
    public boolean equals(Object o){if(this==o)return true;if(!(o instanceof Product p))return false;return Objects.equals(id,p.id)||(Objects.equals(name,p.name)&&Objects.equals(category,p.category));}
    public int hashCode(){return Objects.hash(name);}
    public String toString(){return "Product{id="+id+", name='"+name+"', price="+price+", stockQuantity="+stockQuantity+", deleted="+deleted+"}";}
}
