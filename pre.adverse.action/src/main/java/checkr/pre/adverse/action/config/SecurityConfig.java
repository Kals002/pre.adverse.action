package checkr.pre.adverse.action.config;

import checkr.pre.adverse.action.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig 
{
	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	//Authentication
	public UserDetailsService userDetailsService()  //Pass "PasswordEncoder encoder" for in memory DB
	{
		return new UserInfoUserDetailsService();     // Fetching from DB
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	//Authorization
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{

		return http.csrf(e -> e.disable())
		.authorizeHttpRequests(e -> e.requestMatchers( "/candidate/register",
				"/candidate/authenticate", "/candidate/refreshToken").permitAll())
		.authorizeHttpRequests(e -> e.requestMatchers("/candidate/**").authenticated())
		.sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider())
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
		
		
	}
	
	 @Bean
	    public AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());   // encode password and match with DB encoded password.
	        return authenticationProvider;
	    }
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

}
