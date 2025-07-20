package checkr.pre.adverse.action.service;

import checkr.pre.adverse.action.dto.*;
import checkr.pre.adverse.action.entities.Candidate;
import checkr.pre.adverse.action.entities.PreAdverseActionNoticeEmail;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface CandidateService
{
    public Candidate save(Candidate candidate);

    public List<CandidateDTO> fetchAllCandidates(int offset, int limit);

    public CandidateInformationDTO fetchCandidateInformation(Integer candidateId);

    public CandidateReportDTO fetchCandidateReportDTO(Integer candidateId);

    public List<CandidateCourtSearchDTO> fetchCandidateCourtSearches(Integer candidateId);

    public String updateCandidateEngageStatus(Integer candidateId);

    public List<CandidateAdverseActionsDTO> fetchAdverseActions(int offset, int limit);

    public String savePreAdverseActionNoticeEmail(PreAdverseActionNoticeEmail preAdverseActionNoticeEmail)
            throws MessagingException;

    public void exportCandidates(LocalDate fromDate, LocalDate toDate, HttpServletResponse response)
            throws IOException;
}
