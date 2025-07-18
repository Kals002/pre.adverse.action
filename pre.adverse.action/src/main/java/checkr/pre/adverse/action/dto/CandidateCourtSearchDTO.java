package checkr.pre.adverse.action.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateCourtSearchDTO
{
    private String offenseName;
    private String status;
    private LocalDate date;
}
