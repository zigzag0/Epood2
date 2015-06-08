package ee.ttu.t031687.web.beans;

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
@Table(name = "employee")
public class Employee {
    
    @Id
    @Column(name = "employee")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_employee")
    @SequenceGenerator(name = "s_employee", sequenceName = "s_employee", allocationSize = 1)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "emp_role")
    private Role role;
    
    @Column(name = "current_struct_unit")
    private long structUnit;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public long getStructUnit() {
        return structUnit;
    }

    public void setStructUnit(long structUnit) {
        this.structUnit = structUnit;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
}
