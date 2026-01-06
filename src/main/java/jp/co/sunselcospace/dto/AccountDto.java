package jp.co.sunselcospace.dto;

public class AccountDto {

	private String name;
	//member追記

	private String id;
	private String password;
	private String passwordHash;

	public String getName() {
		return name;
	}
	//member追記

	public void setName(String name) {
		this.name = name;
	}
	//member追記

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public String toString() {
		//return "AccountDto [id=" + id + ", password=" + password + ", passwordHash=" + passwordHash + "]";

		return "AccountDto [name=" + name + ", id=" + id + ", password=" + password + ", passwordHash=" + passwordHash
				+ "]";
		//member追記
	}

}
