package ee.ttu.t031687.web.beans;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "emp_role")
public class Role {
    
    @Id
    @Column(name = "emp_role")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_emp_role")
    @SequenceGenerator(name = "s_emp_role", sequenceName = "s_emp_role", allocationSize = 1)
    private long id;
    
    @Basic
    private String name;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
