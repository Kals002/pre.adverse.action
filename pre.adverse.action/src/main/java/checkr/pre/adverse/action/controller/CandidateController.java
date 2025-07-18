package checkr.pre.adverse.action.controller;

import checkr.pre.adverse.action.dto.CandidateCourtSearchDTO;
import checkr.pre.adverse.action.dto.CandidateDTO;
import checkr.pre.adverse.action.dto.CandidateInformationDTO;
import checkr.pre.adverse.action.dto.CandidateReportDTO;
import checkr.pre.adverse.action.entities.Candidate;
import checkr.pre.adverse.action.entities.CandidateCourtSearch;
import checkr.pre.adverse.action.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import checkr.pre.adverse.action.repository.CandidateRepository;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController
{
    private CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService)
    {
        this.candidateService = candidateService;
    }

    @PostMapping("/save")
    public ResponseEntity<Candidate> saveCandidate(@RequestBody Candidate candidate)
    {
           Candidate savedCandidate =  candidateService.save(candidate);
           return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
    }

    @GetMapping("/candidates/{offset}/{limit}")
    public ResponseEntity<List<CandidateDTO>> fetchAllCandidates(@PathVariable int offset, @PathVariable int limit)
    {
        List<CandidateDTO> candidates = candidateService.fetchAllCandidates(offset, limit);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/info/{candidateId}")
    public ResponseEntity<CandidateInformationDTO> fetchCandidateInformationDTO(@PathVariable Integer candidateId)
    {
        CandidateInformationDTO candidateInformationDTO = candidateService.fetchCandidateInformation(candidateId);
        return new ResponseEntity<>(candidateInformationDTO, HttpStatus.OK);
    }

    @GetMapping("/report/{candidateId}")
    public ResponseEntity<CandidateReportDTO> fetchCandidateReportDTO(@PathVariable Integer candidateId)
    {
        CandidateReportDTO candidateReportDTO = candidateService.fetchCandidateReportDTO(candidateId);
        return new ResponseEntity<>(candidateReportDTO, HttpStatus.OK);
    }

    @GetMapping("/courtSearch/{candidateId}")
    public ResponseEntity<List<CandidateCourtSearchDTO>> fetchCandidateCourtSearchDTO(@PathVariable Integer candidateId)
    {
        List<CandidateCourtSearchDTO> candidateCourtSearches = candidateService.fetchCandidateCourtSearches(candidateId);
        return new ResponseEntity<>(candidateCourtSearches, HttpStatus.OK);
    }

    @PutMapping("/engage/{candidateId}")
    public ResponseEntity<String> updateCandidateEngageStatus(@PathVariable Integer candidateId)
    {
        String status = candidateService.updateCandidateEngageStatus(candidateId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
