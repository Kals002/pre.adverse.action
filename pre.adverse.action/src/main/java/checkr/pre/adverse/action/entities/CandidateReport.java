package checkr.pre.adverse.action.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateReport
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String status;
    private String reportPackage;
    private LocalDate createdDate;
    private LocalDate completedDate;
    private String turnAroundTime;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    @JsonBackReference
    private Candidate candidate;


}
