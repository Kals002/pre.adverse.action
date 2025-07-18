package checkr.pre.adverse.action.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateAdverseActionsDTO
{
    private String name;
    private String status;
    private LocalDate preNoticeDate;
    private LocalDate postNoticeDate;
}
