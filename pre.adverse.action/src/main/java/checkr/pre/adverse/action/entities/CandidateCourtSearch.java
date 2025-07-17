package checkr.pre.adverse.action.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateCourtSearch
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String ssnVerification;
    private String sexOffender;
    private String globalWatchList;
    private String federalCharges;
    private String countryCriminal;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    @JsonBackReference
    private Candidate candidate;

}
