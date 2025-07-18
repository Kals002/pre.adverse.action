package checkr.pre.adverse.action.repository;

import checkr.pre.adverse.action.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer>
{
    @Query(value = "SELECT * FROM candidate WHERE created_date >= :fromDate and created_date <= :toDate ",
            nativeQuery = true)
    public List<Candidate> exportCandidate(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);


}
