package contoso.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
public class Department {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DepartmentID", nullable = false)
    private Integer departmentId;
    @Basic
    @Column(name = "Name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "Budget", nullable = false)
    private BigDecimal budget;
    @Basic
    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;
    @Basic
    @Column(name = "Administrator", nullable = true)
    private Integer administrator;
    @OneToMany(mappedBy = "departmentByDepartmentId")
    private Collection<Course> coursesByDepartmentId;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Integer administrator) {
        this.administrator = administrator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(departmentId, that.departmentId) && Objects.equals(name, that.name) && Objects.equals(budget, that.budget) && Objects.equals(startDate, that.startDate) && Objects.equals(administrator, that.administrator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId, name, budget, startDate, administrator);
    }

    public Collection<Course> getCoursesByDepartmentId() {
        return coursesByDepartmentId;
    }

    public void setCoursesByDepartmentId(Collection<Course> coursesByDepartmentId) {
        this.coursesByDepartmentId = coursesByDepartmentId;
    }
}
