package jp.co.sunselcospace.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jp.co.sunselcospace.entity.BookingEntity;

/**
 *
 * @author Yamashita
 */
public class BookingDto {
	//@formatter:off
	private Integer id;						// 予約番号
	private Integer roomId;				// 施設管理番号
	private String accountId;			// アカウントID
	private LocalDate bookingDate;	// 予約日
	private String purpose;				// 目的
	private String deleted;				// 削除フラグ
	private LocalDateTime recordCreationTimestamp;	// 作成日時
	private LocalDateTime recordUpdateTimestamp;		// 変更日時
	private String roomName;										// 施設名
	//@formatter:on

	public BookingDto(Integer id, Integer roomId, String accountId, LocalDate bookingDate, String purpose,
			String deleted, LocalDateTime recordCreationTimestamp, LocalDateTime recordUpdateTimestamp,
			String roomName) {
		super();
		this.id = id;
		this.roomId = roomId;
		this.accountId = accountId;
		this.bookingDate = bookingDate;
		this.purpose = purpose;
		this.deleted = deleted;
		this.recordCreationTimestamp = recordCreationTimestamp;
		this.recordUpdateTimestamp = recordUpdateTimestamp;
		this.roomName = roomName;
	}

	public BookingDto(BookingEntity bookingEntity, String roomName) {
		this(bookingEntity.getId(), bookingEntity.getRoomId(), bookingEntity.getAccountId(),
				bookingEntity.getBookingDate(), bookingEntity.getPurpose(), bookingEntity.getDeleted(),
				bookingEntity.getRecordCreationTimestamp(), bookingEntity.getRecordUpdateTimestamp(), roomName);
	}

	public BookingDto(BookingEntity bookingEntity) {
		this(bookingEntity.getId(), bookingEntity.getRoomId(), bookingEntity.getAccountId(),
				bookingEntity.getBookingDate(), bookingEntity.getPurpose(), bookingEntity.getDeleted(),
				bookingEntity.getRecordCreationTimestamp(), bookingEntity.getRecordUpdateTimestamp(), "no name");
	}

	public Integer getId() {
		return id;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public String getAccountId() {
		return accountId;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public String getDeleted() {
		return deleted;
	}

	public LocalDateTime getRecordCreationTimestamp() {
		return recordCreationTimestamp;
	}

	public LocalDateTime getRecordUpdateTimestamp() {
		return recordUpdateTimestamp;
	}

	public String getRoomName() {
		return roomName;
	}

	@Override
	public String toString() {
		return "BookingDto [id=" + id + ", roomId=" + roomId + ", accountId=" + accountId + ", bookingDate="
				+ bookingDate + ", purpose=" + purpose + ", deleted=" + deleted + ", recordCreationTimestamp="
				+ recordCreationTimestamp + ", recordUpdateTimestamp=" + recordUpdateTimestamp + ", roomName="
				+ roomName + "]";
	}
}
