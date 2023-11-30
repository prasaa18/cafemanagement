package com.in.cafe.cafemanagement.Model;

import jdk.jfr.Enabled;
import lombok.Data;
import lombok.Generated;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;


@NamedQuery(name="Bill.getAllBills", query="select b from Bill b order by b.id desc")
@NamedQuery(name="Bill.getBillByUserName", query="select b from Bill b where b.createdBy=:username order by b.id desc")
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "bill")
public class Bill implements Serializable {

    private static final long serialVersionId = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "contactnumber")
    private String contactNumber;

    @Column(name = "paymentmethod")
    private String paymentmethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "productdetails", columnDefinition = "json")
    private String productDetails;

    @Column(name = "createdby")
    private String createdBy;
}
