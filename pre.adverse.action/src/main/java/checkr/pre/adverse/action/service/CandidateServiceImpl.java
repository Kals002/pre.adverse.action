package checkr.pre.adverse.action.service;

import checkr.pre.adverse.action.dto.*;
import checkr.pre.adverse.action.entities.Candidate;
import checkr.pre.adverse.action.entities.PreAdverseActionNoticeEmail;
import checkr.pre.adverse.action.repository.CandidateRepository;
import checkr.pre.adverse.action.repository.PreAdverseActionNoticeEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService
{
    private CandidateRepository candidateRepository;
    private PreAdverseActionNoticeEmailRepository preAdverseActionNoticeEmailRepository;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                PreAdverseActionNoticeEmailRepository preAdverseActionNoticeEmailRepository)
    {
        this.candidateRepository = candidateRepository;
        this.preAdverseActionNoticeEmailRepository = preAdverseActionNoticeEmailRepository;
    }

    /**
     * @param candidate
     * @return Candidate
     */
    @Override
    public Candidate save(Candidate candidate)
    {
        return candidateRepository.save(candidate);
    }

    /**
     * @param offset
     * @param limit
     * @return List of CandidateDTO
     */
    @Override
    public List<CandidateDTO> fetchAllCandidates(int offset, int limit)
    {
       Page<Candidate> candidates = candidateRepository.findAll(PageRequest.of(offset, limit));

       return candidates.stream()
               .map(candidate -> new CandidateDTO(candidate.getName(),
                                                           candidate.getAdjudication().getAdjudicationName(),
                                                           candidate.getCandidateReport().getStatus(),
                                                           candidate.getLocation(),
                                                           candidate.getCreatedDate()
                                                           ))
               .toList();
    }

    /**
     * @param candidateId
     * @return CandidateInformationDTO
     */
    @Override
    public CandidateInformationDTO fetchCandidateInformation(Integer candidateId)
    {
        Candidate candidate = candidateRepository.findById(candidateId).get();

        return new CandidateInformationDTO(
                                            candidate.getName(),
                                            candidate.getEmail(),
                                            candidate.getDob(),
                                            candidate.getPhone(),
                                            candidate.getZipcode(),
                                            candidate.getSocialSecurity(),
                                            candidate.getDriverLicense(),
                                            candidate.getCreatedDate());
    }

    /**
     * @param candidateId
     * @return CandidateReportDTO
     */
    @Override
    public CandidateReportDTO fetchCandidateReportDTO(Integer candidateId)
    {
        Candidate candidate = candidateRepository.findById(candidateId).get();

        return new CandidateReportDTO(candidate.getCandidateReport().getStatus(),
                                      candidate.getAdjudication().getAdjudicationName(),
                                      candidate.getCandidateReport().getReportPackage(),
                                      candidate.getCandidateReport().getCreatedDate(),
                                      candidate.getCandidateReport().getCompletedDate(),
                                      candidate.getCandidateReport().getTurnAroundTime());
    }

    /**
     * @param candidateId
     * @return List of CandidateCourtSearchDTO
     */
    @Override
    public List<CandidateCourtSearchDTO> fetchCandidateCourtSearches(Integer candidateId)
    {
        Candidate candidate = candidateRepository.findById(candidateId).get();

        return candidate.getCandidateCourtSearch()
                        .stream()
                .map(candidateCourtSearch ->
                        new CandidateCourtSearchDTO(
                                candidateCourtSearch.getOffenseName(),
                                candidateCourtSearch.getCandidate().getCandidateReport().getStatus(),
                                candidateCourtSearch.getCandidate().getCreatedDate()))
                .toList();
    }

    /**
     * @param candidateId
     * @return success
     */
    @Override
    public String updateCandidateEngageStatus(Integer candidateId)
    {
        Candidate candidate = candidateRepository.findById(candidateId).get();
        candidate.getCandidateReport().setStatus("ENGAGE");
        candidateRepository.save(candidate);
        return "updated status";
    }

    /**
     * @param offset
     * @param limit
     * @return List of CandidateAdverseActionsDTO
     */
    @Override
    public List<CandidateAdverseActionsDTO> fetchAdverseActions(int offset, int limit)
    {
        Page<PreAdverseActionNoticeEmail> preAdverseActionNoticeEmails = preAdverseActionNoticeEmailRepository
                                                                         .findAll(PageRequest.of(offset, limit));
        return preAdverseActionNoticeEmails.stream()
                                           .map(preAdverseActionNoticeEmail ->
                                new CandidateAdverseActionsDTO(preAdverseActionNoticeEmail.getCandidateName(),
                                preAdverseActionNoticeEmail.getStatus(),
                                preAdverseActionNoticeEmail.getPreNoticeDate(),
                                preAdverseActionNoticeEmail.getPostNoticeDate()))
                .toList();
    }

    /**
     * @param preAdverseActionNoticeEmail
     * @return preAdverseActionNoticeEmail
     */
    @Override
    public PreAdverseActionNoticeEmail savePreAdverseActionNoticeEmail(PreAdverseActionNoticeEmail preAdverseActionNoticeEmail)
    {
        return preAdverseActionNoticeEmailRepository.save(preAdverseActionNoticeEmail);
    }

    /**
     * @param fromDate
     * @param toDate
     * @return List of candidate
     */
    @Override
    public List<Candidate> exportCandidates(LocalDate fromDate, LocalDate toDate)
    {
        return candidateRepository.exportCandidate(fromDate, toDate);
    }


}
