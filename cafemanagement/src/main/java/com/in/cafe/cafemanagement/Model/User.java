package com.in.cafe.cafemanagement.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
@NoArgsConstructor
@NamedQuery( name="User.findByEmailId" ,query="select u from User u where u.email=:email")
@NamedQuery(name="User.updateStatus",query ="update User u set u.status=:status where u.id=:id")
@NamedQuery(
        name = "User.getAllUser",
        query = "SELECT NEW com.in.cafe.cafemanagement.wrapper.UserWrapper(u.id, u.name, u.email, u.contactNumber, u.status) FROM User u WHERE u.role = 'user'"
)
@NamedQuery(
        name = "User.getAllAdmin",
        query = "SELECT u.email FROM User u WHERE u.role = 'admin'"
)

@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionId=1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="contactNumber")
    private String contactNumber;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="status")
    private  String status;

    @Column(name="role")
    private String  role;



}
