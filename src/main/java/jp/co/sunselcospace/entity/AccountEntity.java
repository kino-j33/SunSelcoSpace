package jp.co.sunselcospace.entity;

/**
 * アカウントテーブル用のEntity
 * @author Yamashita
 */
public class AccountEntity {
	private String id;
	private String password;
	private String name;

	/**
	 * @author Yamashita
	 *
	 * @param id
	 * @param password
	 * @param name
	 */
	public AccountEntity(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}

	/**
	 * @author Yamashita
	 */
	public AccountEntity(String id, String password) {
		this(id, password, "no Name");
	}

	/**
	 * @author Yamashita
	 */
	public AccountEntity(String id) {
		this(id, "no password", "no Name");
	}

	/**
	 * @author Yamashita
	 */
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * @author Yamashita
	 */
	public String getName() {
		return name;
	}
}
