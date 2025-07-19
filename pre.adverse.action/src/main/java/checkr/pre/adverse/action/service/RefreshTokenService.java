package checkr.pre.adverse.action.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import checkr.pre.adverse.action.entities.RefreshToken;
import checkr.pre.adverse.action.repository.RefreshTokenRepository;
import checkr.pre.adverse.action.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RefreshTokenService 
{
	 @Autowired
	    private RefreshTokenRepository refreshTokenRepository;
	    @Autowired
	    private UserInfoRepository userInfoRepository;

	    public RefreshToken createRefreshToken(String username)
	    {
	    	RefreshToken refreshToken = new RefreshToken();
	       
	    	refreshToken.setUserInfo(userInfoRepository.findByName(username).get());
	    	refreshToken.setToken(UUID.randomUUID().toString());
	    	refreshToken.setExpiryDate(Instant.now().plusMillis(600000));//10
	    	
	        return refreshTokenRepository.save(refreshToken);
	    }


	    public Optional<RefreshToken> findByToken(String token) 
	    {
	        return refreshTokenRepository.findByToken(token);
	    }


	    public RefreshToken verifyExpiration(RefreshToken token) 
	    {
	        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
	            refreshTokenRepository.delete(token);
	            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
	        }
	        return token;
	    }

}
