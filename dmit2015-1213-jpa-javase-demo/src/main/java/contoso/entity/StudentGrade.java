package contoso.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
public class StudentGrade {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EnrollmentID", nullable = false)
    private Integer enrollmentId;
    @Basic
    @Column(name = "CourseID", nullable = false, insertable = false, updatable = false)
    private Integer courseId;
    @Basic
    @Column(name = "StudentID", nullable = false, insertable = false, updatable = false)
    private Integer studentId;
    @Basic
    @Column(name = "Grade", nullable = true, precision = 2)
    private BigDecimal grade;
    @ManyToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "CourseID", nullable = false)
    private Course courseByCourseId;
    @ManyToOne
    @JoinColumn(name = "StudentID", referencedColumnName = "PersonID", nullable = false)
    private Person personByStudentId;

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGrade that = (StudentGrade) o;
        return Objects.equals(enrollmentId, that.enrollmentId) && Objects.equals(courseId, that.courseId) && Objects.equals(studentId, that.studentId) && Objects.equals(grade, that.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, courseId, studentId, grade);
    }

    public Course getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(Course courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }

    public Person getPersonByStudentId() {
        return personByStudentId;
    }

    public void setPersonByStudentId(Person personByStudentId) {
        this.personByStudentId = personByStudentId;
    }
}
