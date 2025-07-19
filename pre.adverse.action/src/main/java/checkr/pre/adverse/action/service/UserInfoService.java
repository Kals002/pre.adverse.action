package checkr.pre.adverse.action.service;

import checkr.pre.adverse.action.entities.UserInfo;
import checkr.pre.adverse.action.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService 
{
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	public UserInfo addUser(UserInfo userInfo)
	{
		userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
		return userInfoRepository.save(userInfo);
	}
}
