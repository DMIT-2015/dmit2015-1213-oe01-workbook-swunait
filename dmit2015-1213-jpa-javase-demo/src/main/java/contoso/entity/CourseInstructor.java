package contoso.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
@IdClass(CourseInstructorPK.class)
public class CourseInstructor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CourseID", nullable = false)
    private Integer courseId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PersonID", nullable = false)
    private Integer personId;
    @ManyToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "CourseID", nullable = false, insertable = false, updatable = false)
    private Course courseByCourseId;
    @ManyToOne
    @JoinColumn(name = "PersonID", referencedColumnName = "PersonID", nullable = false, insertable = false, updatable = false)
    private Person personByPersonId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseInstructor that = (CourseInstructor) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(personId, that.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, personId);
    }

    public Course getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(Course courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }

    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }
}
