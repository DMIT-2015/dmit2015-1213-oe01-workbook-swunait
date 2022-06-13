package dmit2015.entity;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "Categories", schema = "WestWind", catalog = "DMIT2015_1213_E01_swu2015")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CategoryID", nullable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "CategoryName", nullable = false, length = 15)
    private String categoryName;
    @Basic
    @Column(name = "Description", nullable = true, length = -1)
    private String description;
    @OneToMany(mappedBy = "category")
    private Collection<Products> productsByCategoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

        if (categoryId != null ? !categoryId.equals(category.categoryId) : category.categoryId != null) return false;
        if (categoryName != null ? !categoryName.equals(category.categoryName) : category.categoryName != null)
            return false;
        if (description != null ? !description.equals(category.description) : category.description != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = categoryId != null ? categoryId.hashCode() : 0;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Collection<Products> getProductsByCategoryId() {
        return productsByCategoryId;
    }

    public void setProductsByCategoryId(Collection<Products> productsByCategoryId) {
        this.productsByCategoryId = productsByCategoryId;
    }
}
