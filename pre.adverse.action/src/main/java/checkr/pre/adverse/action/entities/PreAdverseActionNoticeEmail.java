package checkr.pre.adverse.action.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class PreAdverseActionNoticeEmail
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fromEmail;
    private String toEmail;
    private String subject;
    private String status;
    private LocalDate preNoticeDate;
    private LocalDate postNoticeDate;
    private Integer candidateId;
    private String candidateName;

    @OneToMany(mappedBy = "preAdverseActionNoticeEmail", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CandidateCharge> candidateCharge;


}
