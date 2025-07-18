package checkr.pre.adverse.action.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateInformationDTO
{
    private String name;
    private String email;
    private LocalDate dob;
    private Long phone;
    private Integer zipcode;
    private Integer socialSecurity;
    private String driverLicense;
    private LocalDate createdDate;
}
