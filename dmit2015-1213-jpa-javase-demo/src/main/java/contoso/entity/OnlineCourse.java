package contoso.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
public class OnlineCourse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CourseID", nullable = false)
    private Integer courseId;
    @Basic
    @Column(name = "URL", nullable = false, length = 100)
    private String url;
    @OneToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "CourseID", nullable = false)
    private Course courseByCourseId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnlineCourse that = (OnlineCourse) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, url);
    }

    public Course getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(Course courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }
}
