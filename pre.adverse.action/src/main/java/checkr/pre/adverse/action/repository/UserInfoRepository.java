package checkr.pre.adverse.action.repository;

import java.util.Optional;

import checkr.pre.adverse.action.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>
{

	Optional<UserInfo> findByName(String username);

}
