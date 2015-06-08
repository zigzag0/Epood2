package ee.ttu.t031687.web.beans;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "produc")
public class Product {
    
    @Id
    @Column(name = "product")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_product")
    @SequenceGenerator(name = "s_product", sequenceName = "s_product", allocationSize = 1)
    private long id;
    
    @Basic
    private String code;
    
    @Basic
    private String name;
    
    @Basic
    private String description;
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    
    @Basic
    private Double price;
    
    @Basic
    private String producer;
}
