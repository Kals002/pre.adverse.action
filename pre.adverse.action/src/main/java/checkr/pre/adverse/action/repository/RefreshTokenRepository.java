package checkr.pre.adverse.action.repository;

import java.util.Optional;

import checkr.pre.adverse.action.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>
{

	Optional<RefreshToken> findByToken(String token);

}
