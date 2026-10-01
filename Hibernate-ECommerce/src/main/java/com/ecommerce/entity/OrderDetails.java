package com.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name="order_details")
public class OrderDetails {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="quantity",nullable=false) private Integer quantity;
 @Column(name="unit_price",nullable=false,precision=10,scale=2) private BigDecimal unitPrice;
 @Column(name="deleted",nullable=false) private boolean deleted=false;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id",nullable=false,foreignKey=@ForeignKey(name="fk_orderdetails_order")) private Orders order;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_id",nullable=false,foreignKey=@ForeignKey(name="fk_orderdetails_product")) private Product product;
 public OrderDetails(){}
 public OrderDetails(Product p,Integer q,BigDecimal price){product=p;quantity=q;unitPrice=price;}
 public OrderDetails(Orders o,Product p,Integer q,BigDecimal price){order=o;product=p;quantity=q;unitPrice=price;}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;if(order!=null)order.recalculateTotalAmount();}
 public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal v){unitPrice=v;if(order!=null)order.recalculateTotalAmount();}
 public boolean isDeleted(){return deleted;} public void setDeleted(boolean v){deleted=v;if(order!=null)order.recalculateTotalAmount();}
 public Orders getOrder(){return order;} public void setOrder(Orders v){order=v;}
 public Product getProduct(){return product;} public void setProduct(Product v){product=v;}
 public boolean equals(Object o){if(this==o)return true;if(!(o instanceof OrderDetails x))return false;return Objects.equals(id,x.id);}
 public int hashCode(){return Objects.hash(id);}
 public String toString(){return "OrderDetails{id="+id+", quantity="+quantity+", unitPrice="+unitPrice+", deleted="+deleted+"}";}
}
