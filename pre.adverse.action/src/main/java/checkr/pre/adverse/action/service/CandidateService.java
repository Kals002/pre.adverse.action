package checkr.pre.adverse.action.service;

import checkr.pre.adverse.action.dto.CandidateCourtSearchDTO;
import checkr.pre.adverse.action.dto.CandidateDTO;
import checkr.pre.adverse.action.dto.CandidateInformationDTO;
import checkr.pre.adverse.action.dto.CandidateReportDTO;
import checkr.pre.adverse.action.entities.Candidate;

import java.util.List;

public interface CandidateService
{
    public Candidate save(Candidate candidate);

    public List<CandidateDTO> fetchAllCandidates(int offset, int limit);

    public CandidateInformationDTO fetchCandidateInformation(Integer candidateId);

    public CandidateReportDTO fetchCandidateReportDTO(Integer candidateId);

    public List<CandidateCourtSearchDTO> fetchCandidateCourtSearches(Integer candidateId);

    public String updateCandidateEngageStatus(Integer candidateId);

}
