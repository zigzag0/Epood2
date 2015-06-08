package ee.ttu.t031687.web.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

@Entity
@Table(name = "product_catalog")
public class Catalog {
    
    @Id
    @Column(name = "product_catalog")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_product_catalog")
    @SequenceGenerator(name = "s_product_catalog", sequenceName = "s_product_catalog", allocationSize = 1)
    private long id;
    
    @Column(name = "struct_unit")
    private long structUnit;
    
    @Basic
    private String name;
    
    @Basic
    private String description;
    
    @Column(name = "upper_catalog")
    private Long upperCatalog;
    
    @Column(name = "created_by")
    private long createdBy;
    
    @Column(name = "updated_by")
    private long updatedBy;
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;
    
    @Transient
    private List<Catalog> subCatalogs;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getStructUnit() {
        return structUnit;
    }
    public void setStructUnit(long structUnit) {
        this.structUnit = structUnit;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getUpperCatalog() {
        return upperCatalog;
    }
    public void setUpperCatalog(Long upperCatalog) {
        this.upperCatalog = upperCatalog;
    }
    public long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }
    public long getUpdatedBy() {
        return updatedBy;
    }
    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public List<Catalog> getSubCatalogs() {
        return subCatalogs;
    }
    public void setSubCatalogs(List<Catalog> subCatalogs) {
        this.subCatalogs = subCatalogs;
    }
    public void addSubCatalog(Catalog catalog) {
        if(subCatalogs == null) subCatalogs = new ArrayList<Catalog>();
        subCatalogs.add(catalog);
    }

}
