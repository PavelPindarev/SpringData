package _04.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String address;

    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private byte[] picture;

    @Column(name = "have_medical_insurance")
    private boolean haveMedicalInsurance;

    @OneToMany(targetEntity = Visitation.class)
    private Set<Visitation> visitation;

    @OneToMany(targetEntity = Diagnose.class)
    private Set<Diagnose> diagnoses;

    @OneToMany(targetEntity = Medicament.class)
    private Set<Medicament> medicaments;
}
