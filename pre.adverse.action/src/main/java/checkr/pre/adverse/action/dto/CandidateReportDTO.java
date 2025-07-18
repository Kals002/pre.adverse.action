package checkr.pre.adverse.action.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateReportDTO
{
    private String status;
    private String adjudication;
    private String reportPackage;
    private LocalDate createdDate;
    private LocalDate completedDate;
    private String turnAroundTime;

}
