package com.ecommerce;

import com.ecommerce.entity.*;
import com.ecommerce.util.HibernateUtil;
import com.ecommerce.util.PasswordUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EcommerceHibernateTest {
 @AfterAll public static void tearDown(){HibernateUtil.shutdown();}

 @Test @Order(1) public void testCreateCategoryAndProduct(){
  Long productId;Long categoryId;
  try(Session s=HibernateUtil.getSessionFactory().openSession()){Transaction tx=s.beginTransaction();
   Category c=new Category("Hardware","PC components");s.persist(c);Product p=new Product("NVMe SSD 1TB",new BigDecimal("109.99"),50,c);s.persist(p);tx.commit();categoryId=c.getId();productId=p.getId();}
  try(Session s=HibernateUtil.getSessionFactory().openSession()){Product p=s.get(Product.class,productId);assertThat(p).isNotNull();assertThat(p.getName()).isEqualTo("NVMe SSD 1TB");assertThat(p.getCategory().getId()).isEqualTo(categoryId);}
 }

 @Test @Order(2) public void testPasswordHashing(){
  String raw="TestPassword@123";String hash=PasswordUtil.hashPassword(raw);
  assertThat(hash).isNotEqualTo(raw);assertThat(PasswordUtil.checkPassword(raw,hash)).isTrue();
 }

 @Test @Order(3) public void testOrderCascadeAndTotal(){
  Long orderId;
  try(Session s=HibernateUtil.getSessionFactory().openSession()){Transaction tx=s.beginTransaction();
   Category c=new Category("Gaming","Gaming gear");s.persist(c);Product p=new Product("Console",new BigDecimal("499.00"),10,c);s.persist(p);
   Users u=new Users("gamer_boy",PasswordUtil.hashPassword("Pass@123"),"gamer@test.com",Role.CUSTOMER);s.persist(u);
   Orders o=new Orders(u,LocalDateTime.now());o.addOrderDetail(new OrderDetails(o,p,1,p.getPrice()));s.persist(o);tx.commit();orderId=o.getId();assertThat(o.getTotalAmount()).isEqualByComparingTo("499.00");}
  try(Session s=HibernateUtil.getSessionFactory().openSession()){assertThat(s.get(Orders.class,orderId)).isNotNull();}
 }

 @Test @Order(4) public void testNamedQuery(){
  try(Session s=HibernateUtil.getSessionFactory().openSession()){assertThat(s.createNamedQuery("Product.findActive",Product.class).getResultList()).isNotNull();}
 }

 @Test @Order(5) public void testSoftDelete(){
  Long id;
  try(Session s=HibernateUtil.getSessionFactory().openSession()){Transaction tx=s.beginTransaction();Category c=new Category("Temp","Temp");s.persist(c);Product p=new Product("Temp Item",new BigDecimal("10"),5,c);s.persist(p);tx.commit();id=p.getId();}
  try(Session s=HibernateUtil.getSessionFactory().openSession()){Transaction tx=s.beginTransaction();s.createMutationQuery("UPDATE Product p SET p.deleted=true WHERE p.id=:id").setParameter("id",id).executeUpdate();tx.commit();}
  try(Session s=HibernateUtil.getSessionFactory().openSession()){assertThat(s.get(Product.class,id).isDeleted()).isTrue();}
 }
}
