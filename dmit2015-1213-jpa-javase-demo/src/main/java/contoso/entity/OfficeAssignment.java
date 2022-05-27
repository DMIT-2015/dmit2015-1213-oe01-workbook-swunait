package contoso.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(schema = "ContosoUniversity")
public class OfficeAssignment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "InstructorID", nullable = false)
    private Integer instructorId;
    @Basic
    @Column(name = "Location", nullable = false, length = 50)
    private String location;
    @Basic
    @Column(name = "Timestamp", nullable = false)
    private LocalDateTime timestamp;
    @OneToOne
    @JoinColumn(name = "InstructorID", referencedColumnName = "PersonID", nullable = false)
    private Person personByInstructorId;

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeAssignment that = (OfficeAssignment) o;
        return Objects.equals(instructorId, that.instructorId) && Objects.equals(location, that.location) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructorId, location, timestamp);
    }

    public Person getPersonByInstructorId() {
        return personByInstructorId;
    }

    public void setPersonByInstructorId(Person personByInstructorId) {
        this.personByInstructorId = personByInstructorId;
    }
}
