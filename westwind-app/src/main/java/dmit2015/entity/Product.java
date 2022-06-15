package dmit2015.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Products", schema = "WestWind", catalog = "DMIT2015_1213_E01_swu2015")
public class Product implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ProductID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "ProductName", nullable = false, length = 40)
    private String productName;
    @Basic
    @Column(name = "SupplierID", nullable = false, insertable = false, updatable = false)
    private Integer supplierId;
    @Basic
    @Column(name = "CategoryID", nullable = false, insertable = false, updatable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "QuantityPerUnit", nullable = false, length = 20)
    private String quantityPerUnit;
    @Basic
    @Column(name = "MinimumOrderQuantity", nullable = true)
    private Short minimumOrderQuantity;
    @Basic
    @Column(name = "UnitPrice", nullable = false)
    private BigDecimal unitPrice;
    @Basic
    @Column(name = "UnitsOnOrder", nullable = false)
    private Integer unitsOnOrder;
    @Basic
    @Column(name = "Discontinued", nullable = false)
    private Boolean discontinued;
    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer productId) {
        this.id = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public Short getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public void setMinimumOrderQuantity(Short minimumOrderQuantity) {
        this.minimumOrderQuantity = minimumOrderQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getUnitsOnOrder() {
        return unitsOnOrder;
    }

    public void setUnitsOnOrder(Integer unitsOnOrder) {
        this.unitsOnOrder = unitsOnOrder;
    }

    public Boolean getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Boolean discontinued) {
        this.discontinued = discontinued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product products = (Product) o;

        if (id != null ? !id.equals(products.id) : products.id != null) return false;
        if (productName != null ? !productName.equals(products.productName) : products.productName != null)
            return false;
        if (supplierId != null ? !supplierId.equals(products.supplierId) : products.supplierId != null) return false;
        if (categoryId != null ? !categoryId.equals(products.categoryId) : products.categoryId != null) return false;
        if (quantityPerUnit != null ? !quantityPerUnit.equals(products.quantityPerUnit) : products.quantityPerUnit != null)
            return false;
        if (minimumOrderQuantity != null ? !minimumOrderQuantity.equals(products.minimumOrderQuantity) : products.minimumOrderQuantity != null)
            return false;
        if (unitPrice != null ? !unitPrice.equals(products.unitPrice) : products.unitPrice != null) return false;
        if (unitsOnOrder != null ? !unitsOnOrder.equals(products.unitsOnOrder) : products.unitsOnOrder != null)
            return false;
        if (discontinued != null ? !discontinued.equals(products.discontinued) : products.discontinued != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (supplierId != null ? supplierId.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (quantityPerUnit != null ? quantityPerUnit.hashCode() : 0);
        result = 31 * result + (minimumOrderQuantity != null ? minimumOrderQuantity.hashCode() : 0);
        result = 31 * result + (unitPrice != null ? unitPrice.hashCode() : 0);
        result = 31 * result + (unitsOnOrder != null ? unitsOnOrder.hashCode() : 0);
        result = 31 * result + (discontinued != null ? discontinued.hashCode() : 0);
        return result;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
