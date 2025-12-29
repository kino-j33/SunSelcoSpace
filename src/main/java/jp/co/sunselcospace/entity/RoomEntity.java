package jp.co.sunselcospace.entity;

/**
 * 施設テーブル用のEntity
 * @author Yamashita
 */
public class RoomEntity {
	private Integer id;
	private String name;
	private String location;
	private Integer capacity;
	private String overview;
	private Integer fee;
	private String image;
	private String introduction;

	/**
	 * @author Yamashita
	 */
	public RoomEntity(Integer id, String name, String location, Integer capacity, String overview, Integer fee,
			String image, String introduction) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.capacity = capacity;
		this.overview = overview;
		this.fee = fee;
		this.image = image;
		this.introduction = introduction;
	}

	public RoomEntity() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @author Yamashita
	 */
	@Override
	public String toString() {
		return "RoomEntity [id=" + id + ", name=" + name + ", location=" + location + ", capacity=" + capacity
				+ ", overview=" + overview + ", fee=" + fee + ", image=" + image + ", introduction=" + introduction
				+ "]";
	}
}