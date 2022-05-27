package contoso.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
public class Person {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PersonID", nullable = false)
    private Integer personId;
    @Basic
    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;
    @Basic
    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;
    @Basic
    @Column(name = "HireDate", nullable = true)
    private LocalDate hireDate;
    @Basic
    @Column(name = "EnrollmentDate", nullable = true)
    private LocalDate enrollmentDate;
    @Basic
    @Column(name = "Discriminator", nullable = false, length = 50)
    private String discriminator;
    @OneToMany(mappedBy = "personByPersonId")
    private Collection<CourseInstructor> courseInstructorsByPersonId;
    @OneToOne(mappedBy = "personByInstructorId")
    private OfficeAssignment officeAssignmentByPersonId;
    @OneToMany(mappedBy = "personByStudentId")
    private Collection<StudentGrade> studentGradesByPersonId;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId) && Objects.equals(lastName, person.lastName) && Objects.equals(firstName, person.firstName) && Objects.equals(hireDate, person.hireDate) && Objects.equals(enrollmentDate, person.enrollmentDate) && Objects.equals(discriminator, person.discriminator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, lastName, firstName, hireDate, enrollmentDate, discriminator);
    }

    public Collection<CourseInstructor> getCourseInstructorsByPersonId() {
        return courseInstructorsByPersonId;
    }

    public void setCourseInstructorsByPersonId(Collection<CourseInstructor> courseInstructorsByPersonId) {
        this.courseInstructorsByPersonId = courseInstructorsByPersonId;
    }

    public OfficeAssignment getOfficeAssignmentByPersonId() {
        return officeAssignmentByPersonId;
    }

    public void setOfficeAssignmentByPersonId(OfficeAssignment officeAssignmentByPersonId) {
        this.officeAssignmentByPersonId = officeAssignmentByPersonId;
    }

    public Collection<StudentGrade> getStudentGradesByPersonId() {
        return studentGradesByPersonId;
    }

    public void setStudentGradesByPersonId(Collection<StudentGrade> studentGradesByPersonId) {
        this.studentGradesByPersonId = studentGradesByPersonId;
    }
}
