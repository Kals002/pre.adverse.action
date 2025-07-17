package checkr.pre.adverse.action.repository;

import checkr.pre.adverse.action.entities.CandidateReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateReportRepository extends JpaRepository<CandidateReport, Integer>
{

}
