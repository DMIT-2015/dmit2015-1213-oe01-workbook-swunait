package dmit2015.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Suppliers", schema = "WestWind", catalog = "DMIT2015_1213_E01_swu2015")
public class Supplier {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SupplierID", nullable = false)
    private Integer supplierId;
    @Basic
    @Column(name = "CompanyName", nullable = false, length = 40)
    private String companyName;
    @Basic
    @Column(name = "ContactName", nullable = false, length = 30)
    private String contactName;
    @Basic
    @Column(name = "ContactTitle", nullable = true, length = 30)
    private String contactTitle;
    @Basic
    @Column(name = "Email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "AddressID", nullable = false)
    private Integer addressId;
    @Basic
    @Column(name = "Phone", nullable = false, length = 24)
    private String phone;
    @Basic
    @Column(name = "Fax", nullable = true, length = 24)
    private String fax;
    @OneToMany(mappedBy = "supplier")
    private Collection<Products> productsBySupplierId;

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTitle() {
        return contactTitle;
    }

    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Supplier supplier = (Supplier) o;

        if (supplierId != null ? !supplierId.equals(supplier.supplierId) : supplier.supplierId != null) return false;
        if (companyName != null ? !companyName.equals(supplier.companyName) : supplier.companyName != null)
            return false;
        if (contactName != null ? !contactName.equals(supplier.contactName) : supplier.contactName != null)
            return false;
        if (contactTitle != null ? !contactTitle.equals(supplier.contactTitle) : supplier.contactTitle != null)
            return false;
        if (email != null ? !email.equals(supplier.email) : supplier.email != null) return false;
        if (addressId != null ? !addressId.equals(supplier.addressId) : supplier.addressId != null) return false;
        if (phone != null ? !phone.equals(supplier.phone) : supplier.phone != null) return false;
        if (fax != null ? !fax.equals(supplier.fax) : supplier.fax != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = supplierId != null ? supplierId.hashCode() : 0;
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
        result = 31 * result + (contactTitle != null ? contactTitle.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (addressId != null ? addressId.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        return result;
    }

    public Collection<Products> getProductsBySupplierId() {
        return productsBySupplierId;
    }

    public void setProductsBySupplierId(Collection<Products> productsBySupplierId) {
        this.productsBySupplierId = productsBySupplierId;
    }
}
