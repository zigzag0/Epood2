package ee.ttu.t031687.web.beans;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "emp_user")
public class User {
    
    @Id
    @Column(name = "emp_user")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_emp_user")
    @SequenceGenerator(name = "s_emp_user", sequenceName = "s_emp_user", allocationSize = 1)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "employee")
    private Employee employee;
    
    @Basic
    private String username;
    
    @Basic
    private String passw;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }
    
}
