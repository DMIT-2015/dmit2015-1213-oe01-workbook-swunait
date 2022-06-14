package dmit2015.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "Categories", schema = "WestWind", catalog = "DMIT2015_1213_E01_swu2015")
public class Category implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CategoryID", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "CategoryName", nullable = false, length = 15)
    private String categoryName;
    @Basic
    @Column(name = "Description", nullable = true, length = -1)
    private String description;
    @OneToMany(mappedBy = "category")
    private Collection<Product> productsByCategoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer categoryId) {
        this.id = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (categoryName != null ? !categoryName.equals(category.categoryName) : category.categoryName != null)
            return false;
        if (description != null ? !description.equals(category.description) : category.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Collection<Product> getProductsByCategoryId() {
        return productsByCategoryId;
    }

    public void setProductsByCategoryId(Collection<Product> productsByCategoryId) {
        this.productsByCategoryId = productsByCategoryId;
    }
}
