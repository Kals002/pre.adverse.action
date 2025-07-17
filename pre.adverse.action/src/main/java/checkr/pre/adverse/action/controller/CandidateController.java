package checkr.pre.adverse.action.controller;

import checkr.pre.adverse.action.entities.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import checkr.pre.adverse.action.repository.CandidateRepository;

@RestController
@RequestMapping("/candidate")
public class CandidateController
{
    @Autowired
    private CandidateRepository candidateRepository;

    @PostMapping("/save")
    public ResponseEntity<?> saveCandidate(@RequestBody Candidate candidate)
    {
           Candidate savedCandidate =  candidateRepository.save(candidate);
           return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public String get()
    {
        return "Hello";
    }
}
