package checkr.pre.adverse.action.repository;

import checkr.pre.adverse.action.entities.PreAdverseActionNoticeEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreAdverseActionNoticeEmailRepository extends JpaRepository<PreAdverseActionNoticeEmail, Integer>
{

}
