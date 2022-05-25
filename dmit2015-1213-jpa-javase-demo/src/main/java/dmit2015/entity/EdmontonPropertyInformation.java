package dmit2015.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "PropertyInformation")
@Getter @Setter
public class EdmontonPropertyInformation implements Serializable {

    @Id
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "year_built")
    private Integer yearBuilt;


}
