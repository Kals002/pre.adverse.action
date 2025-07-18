package checkr.pre.adverse.action.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer candidateId;
    private String name;
    private String location;
    private LocalDate createdDate;
    private String email;
    private LocalDate dob;
    private Long phone;
    private Integer zipcode;
    private Integer socialSecurity;
    private String driverLicense;

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Adjudication adjudication;

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL)
    @JsonManagedReference
    private CandidateReport candidateReport;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CandidateCourtSearch> candidateCourtSearch;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PreAdverseActionNoticeEmail> preAdverseActionNoticeEmail;
















}
