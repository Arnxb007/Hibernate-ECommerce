package com.ecommerce.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="orders")
@NamedQueries({
 @NamedQuery(name="Orders.findActive",query="SELECT o FROM Orders o WHERE o.deleted = false ORDER BY o.orderDate DESC"),
 @NamedQuery(name="Orders.findByUserId",query="SELECT o FROM Orders o WHERE o.user.id = :userId AND o.deleted = false ORDER BY o.orderDate DESC"),
 @NamedQuery(name="Orders.fetchWithDetailsAndUser",query="SELECT DISTINCT o FROM Orders o JOIN FETCH o.user u LEFT JOIN FETCH o.orderDetails od LEFT JOIN FETCH od.product p WHERE o.id = :orderId AND o.deleted = false")
})
public class Orders {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="order_date",nullable=false) private LocalDateTime orderDate;
 @Column(name="total_amount",nullable=false,precision=12,scale=2) private BigDecimal totalAmount=BigDecimal.ZERO;
 @Column(name="deleted",nullable=false) private boolean deleted=false;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="user_id",nullable=false,foreignKey=@ForeignKey(name="fk_order_user")) private Users user;
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY) private List<OrderDetails> orderDetails=new ArrayList<>();
 public Orders(){orderDate=LocalDateTime.now();}
 public Orders(Users u){user=u;orderDate=LocalDateTime.now();}
 public Orders(Users u,LocalDateTime d){user=u;orderDate=d!=null?d:LocalDateTime.now();}
 public void addOrderDetail(OrderDetails d){orderDetails.add(d);d.setOrder(this);recalculateTotalAmount();}
 public void removeOrderDetail(OrderDetails d){orderDetails.remove(d);d.setOrder(null);recalculateTotalAmount();}
 public void recalculateTotalAmount(){totalAmount=orderDetails.stream().filter(d->!d.isDeleted()).map(d->d.getUnitPrice().multiply(BigDecimal.valueOf(d.getQuantity()))).reduce(BigDecimal.ZERO,BigDecimal::add);}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public LocalDateTime getOrderDate(){return orderDate;} public void setOrderDate(LocalDateTime v){orderDate=v;}
 public BigDecimal getTotalAmount(){return totalAmount;} public void setTotalAmount(BigDecimal v){totalAmount=v;}
 public boolean isDeleted(){return deleted;} public void setDeleted(boolean v){deleted=v;}
 public Users getUser(){return user;} public void setUser(Users v){user=v;}
 public List<OrderDetails> getOrderDetails(){return orderDetails;} public void setOrderDetails(List<OrderDetails> v){orderDetails=v;recalculateTotalAmount();}
 public boolean equals(Object o){if(this==o)return true;if(!(o instanceof Orders x))return false;return Objects.equals(id,x.id);}
 public int hashCode(){return Objects.hash(id);}
 public String toString(){return "Orders{id="+id+", orderDate="+orderDate+", totalAmount="+totalAmount+", deleted="+deleted+"}";}
}
