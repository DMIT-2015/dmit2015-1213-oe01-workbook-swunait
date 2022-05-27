package contoso.entity;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
public class OnsiteCourse {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CourseID", nullable = false)
    private Integer courseId;
    @Basic
    @Column(name = "Location", nullable = false, length = 50)
    private String location;
    @Basic
    @Column(name = "Days", nullable = false, length = 50)
    private String days;
    @Basic
    @Column(name = "Time", nullable = false)
    private LocalTime time;
    @OneToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "CourseID", nullable = false)
    private Course courseByCourseId;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OnsiteCourse that = (OnsiteCourse) o;
        return Objects.equals(courseId, that.courseId) && Objects.equals(location, that.location) && Objects.equals(days, that.days) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, location, days, time);
    }

    public Course getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(Course courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }
}
