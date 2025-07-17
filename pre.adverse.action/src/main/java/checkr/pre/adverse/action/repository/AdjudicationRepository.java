package checkr.pre.adverse.action.repository;

import checkr.pre.adverse.action.entities.Adjudication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdjudicationRepository extends JpaRepository<Adjudication, Integer>
{

}
