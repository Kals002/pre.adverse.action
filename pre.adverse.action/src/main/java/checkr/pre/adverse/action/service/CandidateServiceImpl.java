package checkr.pre.adverse.action.service;

import checkr.pre.adverse.action.dto.*;
import checkr.pre.adverse.action.entities.Adjudication;
import checkr.pre.adverse.action.entities.Candidate;
import checkr.pre.adverse.action.entities.PreAdverseActionNoticeEmail;
import checkr.pre.adverse.action.repository.AdjudicationRepository;
import checkr.pre.adverse.action.repository.CandidateRepository;
import checkr.pre.adverse.action.repository.PreAdverseActionNoticeEmailRepository;
import com.opencsv.CSVWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStreamWriter;
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
    private AdjudicationRepository adjudicationRepository;

    @Autowired
    private JavaMailSender mailSender;

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
    public String savePreAdverseActionNoticeEmail(PreAdverseActionNoticeEmail preAdverseActionNoticeEmail
                                                                       ) throws MessagingException
    {

        /*
            Sends email and saves email data to DB.
            Updates candidate's adjudication status to "Adverse action"
            Email details
         */

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setFrom(preAdverseActionNoticeEmail.getFromEmail());
        helper.setTo(preAdverseActionNoticeEmail.getToEmail());
        helper.setSubject(preAdverseActionNoticeEmail.getSubject());

        String htmlContent = buildEmailContent(preAdverseActionNoticeEmail);
        helper.setText(htmlContent, true);

        mailSender.send(mimeMessage);

        preAdverseActionNoticeEmailRepository.save(preAdverseActionNoticeEmail);

        Candidate candidate = candidateRepository.findById(preAdverseActionNoticeEmail.getCandidateId()).get();
        Adjudication adjudication = candidate.getAdjudication();
        adjudication.setAdjudicationName("Adverse action");
        adjudicationRepository.save(adjudication);
        candidateRepository.save(candidate);


        return "Email sent successfully to " + preAdverseActionNoticeEmail.getToEmail();
    }

    /**
     * Builds a professional HTML email body from the request data.
     * @param preAdverseActionNoticeEmail The email request data.
     * @return A string containing the HTML email body.
     */
    private String buildEmailContent(PreAdverseActionNoticeEmail preAdverseActionNoticeEmail)
    {
        // Using streams to format the list of charges into an HTML list
        String chargesList = preAdverseActionNoticeEmail.getCandidateCharge().stream()
                .map(charge -> "<li>" + charge.getChargeName() + "</li>")
                .collect(Collectors.joining(""));

        return "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head><style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                ".container { max-width: 600px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }" +
                "h2 { color: #d9534f; }" +
                "ul { list-style-type: disc; padding-left: 20px; }" +
                "p { margin-bottom: 10px; }" +
                "strong { color: #555; }" +
                "</style></head>" +
                "<body>" +
                "<div class='container'>" +
                "<h2>" + preAdverseActionNoticeEmail.getSubject() + "</h2>" +
                "<p>Dear " + preAdverseActionNoticeEmail.getCandidateName() + ",</p>" +
                "<p>This notice is to inform you about the results of your recent background check (Candidate ID: " + preAdverseActionNoticeEmail.getCandidateId() + ").</p>" +
                "<p>The following items require your attention:</p>" +
                "<ul>" + chargesList + "</ul>" +
                "<p><strong>Pre-Adverse Action Notice Date:</strong> " + preAdverseActionNoticeEmail.getPreNoticeDate() + "</p>" +
                "<p><strong>Post-Adverse Action Notice Date:</strong> " + preAdverseActionNoticeEmail.getPostNoticeDate() + "</p>" +
                "<p>If you have any questions, please contact us.</p>" +
                "<p>Sincerely,<br>The Hiring Team</p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * @param fromDate
     * @param toDate
     * @return csv file containing List of candidates
     */
    @Override
    public void exportCandidates(LocalDate fromDate, LocalDate toDate, HttpServletResponse response)
            throws IOException
    {
        // Set the content type and header for CSV file download.
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"CandidatesReport.csv\"");

        List<Candidate> candidates = candidateRepository.exportCandidate(fromDate, toDate);

        // Use try-with-resources to ensure the writer is closed automatically.
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()))) {
            // Write the header row to the CSV file.
            writer.writeNext(new String[]{"candidateId", "name", "location", "createdDate", "email",
                                       "dob", "phone", "zipcode", "socialSecurity", "driverLicense" });

            // Write employee data row by row.
            for (Candidate candidate : candidates) {
                writer.writeNext(new String[]{
                        String.valueOf(candidate.getCandidateId()),
                        candidate.getName(),
                        candidate.getLocation(),
                        String.valueOf(candidate.getCreatedDate()),
                        candidate.getEmail(),
                        String.valueOf(candidate.getDob()),
                        String.valueOf(candidate.getPhone()),
                        String.valueOf(candidate.getZipcode()),
                        String.valueOf(candidate.getSocialSecurity()),
                        candidate.getDriverLicense()
                });
            }
        }
    }












}
