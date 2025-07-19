package checkr.pre.adverse.action.config;

import java.util.Optional;

import checkr.pre.adverse.action.entities.UserInfo;
import checkr.pre.adverse.action.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class UserInfoUserDetailsService implements UserDetailsService
{

	@Autowired
	private UserInfoRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Optional<UserInfo> userInfo = repository.findByName(username);
		return userInfo.map(e -> new UserInfoUserDetails(e))
						.orElseThrow(() -> new UsernameNotFoundException("User not found"));
				
	}

}
