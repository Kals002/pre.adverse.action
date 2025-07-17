package checkr.pre.adverse.action.repository;

import checkr.pre.adverse.action.entities.CandidateCourtSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateCourtSearchRepository extends JpaRepository<CandidateCourtSearch, Integer>
{

}
