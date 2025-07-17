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
public class CandidateCharge
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String chargeName;

    @ManyToOne
    @JoinColumn(name = "adverse_id")
    @JsonBackReference
    private PreAdverseActionNoticeEmail preAdverseActionNoticeEmail;

}
