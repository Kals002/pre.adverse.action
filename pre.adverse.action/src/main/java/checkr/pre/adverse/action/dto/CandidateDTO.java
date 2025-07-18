package checkr.pre.adverse.action.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO
{
    private String name;
    private String adjudication;
    private String status;
    private String location;
    private LocalDate date;

}
