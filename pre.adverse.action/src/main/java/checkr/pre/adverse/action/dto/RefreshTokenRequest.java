package checkr.pre.adverse.action.dto;

public class RefreshTokenRequest 
{
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RefreshTokenRequest() {
		super();
	}

	public RefreshTokenRequest(String token) {
		super();
		this.token = token;
	}

	@Override
	public String toString() {
		return "RefreshTokenRequest [token=" + token + "]";
	}
	
	

}
