package com.ecommerce.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="users",uniqueConstraints={
    @UniqueConstraint(name="uk_user_username",columnNames="username"),
    @UniqueConstraint(name="uk_user_email",columnNames="email")
})
@NamedQueries({
    @NamedQuery(name="Users.findByUsername",query="SELECT u FROM Users u WHERE u.username = :username AND u.deleted = false"),
    @NamedQuery(name="Users.findByEmail",query="SELECT u FROM Users u WHERE u.email = :email AND u.deleted = false"),
    @NamedQuery(name="Users.findByRole",query="SELECT u FROM Users u WHERE u.role = :role AND u.deleted = false")
})
public class Users {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="username",nullable=false,unique=true,length=50) private String username;
    @Column(name="password",nullable=false,length=255) private String password;
    @Column(name="email",nullable=false,unique=true,length=100) private String email;
    @Enumerated(EnumType.STRING) @Column(name="role",nullable=false,length=20) private Role role;
    @Column(name="deleted",nullable=false) private boolean deleted=false;
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.LAZY) private List<Orders> orders=new ArrayList<>();
    public Users(){}
    public Users(String username,String password,String email,Role role){this.username=username;this.password=password;this.email=email;this.role=role;}
    public void addOrder(Orders o){orders.add(o);o.setUser(this);}
    public void removeOrder(Orders o){orders.remove(o);o.setUser(null);}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getPassword(){return password;} public void setPassword(String v){password=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public Role getRole(){return role;} public void setRole(Role v){role=v;}
    public boolean isDeleted(){return deleted;} public void setDeleted(boolean v){deleted=v;}
    public List<Orders> getOrders(){return orders;} public void setOrders(List<Orders> v){orders=v;}
    public boolean equals(Object o){if(this==o)return true;if(!(o instanceof Users u))return false;return Objects.equals(username,u.username)||Objects.equals(email,u.email);}
    public int hashCode(){return Objects.hash(username,email);}
    public String toString(){return "Users{id="+id+", username='"+username+"', email='"+email+"', role="+role+", deleted="+deleted+"}";}
}
