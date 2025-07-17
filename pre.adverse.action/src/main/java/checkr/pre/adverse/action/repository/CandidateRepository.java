package checkr.pre.adverse.action.repository;

import checkr.pre.adverse.action.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer>
{

}
