package jp.co.sunselcospace.form;

public class AccountForm {
	private String name;
	//member追記
	private String id;
	private String password;

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
}
