package checkr.pre.adverse.action.controller;

import checkr.pre.adverse.action.dto.*;
import checkr.pre.adverse.action.entities.*;
import checkr.pre.adverse.action.service.CandidateService;
import checkr.pre.adverse.action.service.JwtService;
import checkr.pre.adverse.action.service.RefreshTokenService;
import checkr.pre.adverse.action.service.UserInfoService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import checkr.pre.adverse.action.repository.CandidateRepository;

import java.io.IOException;
import java.time.LocalDate;
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

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<Candidate> saveCandidate(@RequestBody Candidate candidate)
    {
           Candidate savedCandidate =  candidateService.save(candidate);
           return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);
    }

    @GetMapping("/candidates/{offset}/{limit}")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<List<CandidateDTO>> fetchAllCandidates(@PathVariable int offset, @PathVariable int limit)
    {
        List<CandidateDTO> candidates = candidateService.fetchAllCandidates(offset, limit);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/info/{candidateId}")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<CandidateInformationDTO> fetchCandidateInformationDTO(@PathVariable Integer candidateId)
    {
        CandidateInformationDTO candidateInformationDTO = candidateService.fetchCandidateInformation(candidateId);
        return new ResponseEntity<>(candidateInformationDTO, HttpStatus.OK);
    }

    @GetMapping("/report/{candidateId}")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<CandidateReportDTO> fetchCandidateReportDTO(@PathVariable Integer candidateId)
    {
        CandidateReportDTO candidateReportDTO = candidateService.fetchCandidateReportDTO(candidateId);
        return new ResponseEntity<>(candidateReportDTO, HttpStatus.OK);
    }

    @GetMapping("/courtSearch/{candidateId}")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<List<CandidateCourtSearchDTO>> fetchCandidateCourtSearchDTO(@PathVariable Integer candidateId)
    {
        List<CandidateCourtSearchDTO> candidateCourtSearches = candidateService.fetchCandidateCourtSearches(candidateId);
        return new ResponseEntity<>(candidateCourtSearches, HttpStatus.OK);
    }

    @PutMapping("/engage/{candidateId}")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<String> updateCandidateEngageStatus(@PathVariable Integer candidateId)
    {
        String status = candidateService.updateCandidateEngageStatus(candidateId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/adverseActions/{offset}/{limit}")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<List<CandidateAdverseActionsDTO>> fetchAllAdverseActions
                                                            (@PathVariable int offset, @PathVariable int limit)
    {
        List<CandidateAdverseActionsDTO> candidateAdverseActionsDTOS =
                                                            candidateService.fetchAdverseActions(offset, limit);
        return new ResponseEntity<>(candidateAdverseActionsDTOS, HttpStatus.OK);

    }

    @PostMapping("/adverseAction/save")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<String> savePreAdverseActionNoticeEmail(
            @RequestBody PreAdverseActionNoticeEmail preAdverseActionNoticeEmail
    ) throws MessagingException
    {
        String emailSentNotification = candidateService.
                                                    savePreAdverseActionNoticeEmail(preAdverseActionNoticeEmail);
        return new ResponseEntity<>(emailSentNotification, HttpStatus.OK);
    }

    @GetMapping("/export/report")
    @PreAuthorize("hasAuthority('ROLES_ADMIN')")
    public ResponseEntity<Void> exportReport(@RequestParam LocalDate fromDate,
                                                        @RequestParam LocalDate toDate,
                                                     HttpServletResponse response) throws IOException {
           candidateService.exportCandidates(fromDate, toDate, response);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    // JWT

    @PostMapping("/register")
    public UserInfo register(@RequestBody UserInfo userInfo)
    {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/authenticate")    // or Login
    public JwtResponse crateandGetJWTToken(@RequestBody AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setAccessToken(jwtService.generateToken(authRequest.getUsername()));
            jwtResponse.setToken(refreshToken.getToken());
            return jwtResponse;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponse createRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService :: verifyExpiration)
                .map(RefreshToken :: getUserInfo)
                .map(userInfo ->
                {
                    String accessToken = jwtService.generateToken(userInfo.getName());

                    JwtResponse jwtResponse = new JwtResponse();
                    jwtResponse.setAccessToken(accessToken);
                    jwtResponse.setToken(refreshTokenRequest.getToken());

                    return jwtResponse;
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }

}
