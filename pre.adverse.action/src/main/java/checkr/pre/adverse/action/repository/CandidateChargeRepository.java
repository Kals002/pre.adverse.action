package checkr.pre.adverse.action.repository;

import checkr.pre.adverse.action.entities.CandidateCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateChargeRepository extends JpaRepository<CandidateCharge, Integer>
{

}
