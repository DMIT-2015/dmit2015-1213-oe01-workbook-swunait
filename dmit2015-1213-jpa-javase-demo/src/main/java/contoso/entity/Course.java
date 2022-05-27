package contoso.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CourseID", nullable = false)
    private Integer courseId;
    @Basic
    @Column(name = "Title", nullable = false, length = 100)
    private String title;
    @Basic
    @Column(name = "Credits", nullable = false)
    private Integer credits;
    @Basic
    @Column(name = "DepartmentID", nullable = false, insertable = false, updatable = false)
    private Integer departmentId;
    @ManyToOne
    @JoinColumn(name = "DepartmentID", referencedColumnName = "DepartmentID", nullable = false)
    private Department departmentByDepartmentId;
    @OneToMany(mappedBy = "courseByCourseId")
    private Collection<CourseInstructor> courseInstructorsByCourseId;
    @OneToOne(mappedBy = "courseByCourseId")
    private OnlineCourse onlineCourseByCourseId;
    @OneToOne(mappedBy = "courseByCourseId")
    private OnsiteCourse onsiteCourseByCourseId;
    @OneToMany(mappedBy = "courseByCourseId")
    private Collection<StudentGrade> studentGradesByCourseId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) && Objects.equals(title, course.title) && Objects.equals(credits, course.credits) && Objects.equals(departmentId, course.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, title, credits, departmentId);
    }

    public Department getDepartmentByDepartmentId() {
        return departmentByDepartmentId;
    }

    public void setDepartmentByDepartmentId(Department departmentByDepartmentId) {
        this.departmentByDepartmentId = departmentByDepartmentId;
    }

    public Collection<CourseInstructor> getCourseInstructorsByCourseId() {
        return courseInstructorsByCourseId;
    }

    public void setCourseInstructorsByCourseId(Collection<CourseInstructor> courseInstructorsByCourseId) {
        this.courseInstructorsByCourseId = courseInstructorsByCourseId;
    }

    public OnlineCourse getOnlineCourseByCourseId() {
        return onlineCourseByCourseId;
    }

    public void setOnlineCourseByCourseId(OnlineCourse onlineCourseByCourseId) {
        this.onlineCourseByCourseId = onlineCourseByCourseId;
    }

    public OnsiteCourse getOnsiteCourseByCourseId() {
        return onsiteCourseByCourseId;
    }

    public void setOnsiteCourseByCourseId(OnsiteCourse onsiteCourseByCourseId) {
        this.onsiteCourseByCourseId = onsiteCourseByCourseId;
    }

    public Collection<StudentGrade> getStudentGradesByCourseId() {
        return studentGradesByCourseId;
    }

    public void setStudentGradesByCourseId(Collection<StudentGrade> studentGradesByCourseId) {
        this.studentGradesByCourseId = studentGradesByCourseId;
    }
}
