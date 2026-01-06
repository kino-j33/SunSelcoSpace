package jp.co.sunselcospace.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 予約テーブル用のEntity
 * @author Yamashita
 */
public class BookingEntity {
	private Integer id;
	private Integer roomId;
	private String accountId;
	private LocalDate bookingDate;
	private String purpose;
	private String deleted;
	private LocalDateTime recordCreationTimestamp;
	private LocalDateTime recordUpdateTimestamp;

	/**
	 * @author Yamashita
	 * @param id
	 * @param roomId
	 * @param accountId
	 * @param bookingDate
	 * @param purpose
	 * @param deleted
	 * @param recordCreationTimestamp
	 * @param recordUpdateTimestamp
	 */
	// @formatter:off
	public BookingEntity(Integer id
								 , Integer roomId
								 , String accountId
								 , LocalDate bookingDate
								 , String purpose
								 , String deleted
								 , LocalDateTime recordCreationTimestamp
								 , LocalDateTime recordUpdateTimestamp) {
		// @formatter:on
		this.id = id;
		this.roomId = roomId;
		this.accountId = accountId;
		this.bookingDate = bookingDate;
		this.purpose = purpose;
		this.deleted = deleted;
		this.recordCreationTimestamp = recordCreationTimestamp;
		this.recordUpdateTimestamp = recordUpdateTimestamp;
	}

	/**
	 * @author Yamashita
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @author Yamashita
	 */
	public Integer getRoomId() {
		return roomId;
	}

	/**
	 * @author Yamashita
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @author Yamashita
	 */
	public LocalDate getBookingDate() {
		return bookingDate;
	}

	/**
	 * @author Yamashita
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @author Yamashita
	 */
	public String getDeleted() {
		return deleted;
	}

	/**
	 * @author Yamashita
	 */
	public LocalDateTime getRecordCreationTimestamp() {
		return recordCreationTimestamp;
	}

	/**
	 * @author Yamashita
	 */
	public LocalDateTime getRecordUpdateTimestamp() {
		return recordUpdateTimestamp;
	}

	/**
	 * @author Yamashita
	 */
	@Override
	public String toString() {
		return "BookingEntity [id=" + id + ", roomId=" + roomId + ", accountId=" + accountId + ", bookingDate="
				+ bookingDate + ", purpose=" + purpose + ", deleted=" + deleted + ", recordCreationTimestamp="
				+ recordCreationTimestamp + ", recordUpdateTimestamp=" + recordUpdateTimestamp + "]";
	}
}
