package jp.co.sunselcospace.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.co.sunselcospace.data.DataSourceFactory;
import jp.co.sunselcospace.entity.BookingEntity;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.util.Convertor;

/**
 * @author Yamashita
 */
public class BookingDao {
	/**
	 * 予約番号をもとに予約テーブルを検索し、BookingEntityを返す
	 * @author Yamashita
	 *
	 * @param bookingID
	 * @return BookingEntity
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public BookingEntity findBookingById(int bookingID) throws NamingException, SQLException {
		// connectionの取得
		try (Connection connection = DataSourceFactory.getConnection();) {
			// キャンセル（論理削除）されていない予約番号を探す
			PreparedStatement ps = connection.prepareStatement(
					"select * from booking where id = ? and deleted = '0';");
			ps.setInt(1, bookingID);

			ResultSet resultSet = ps.executeQuery();

			// resultSetでBookingEntityのインスタンス化
			if (resultSet.next()) {
				return getBookingEntity(resultSet);
			}
			return null;
		}
	}

	/**
	 * 予約番号をもとに予約テーブルでキャンセル処理を行う
	 * @author Yamashita
	 *
	 * @param id
	 * @return int 更新したレコード数
	 *
	 * @throws SQLException
	 * @throws NamingException
	 */
	public int cancelBookingRecordById(int id) throws SQLException, NamingException {
		try (Connection connection = DataSourceFactory.getConnection();) {
			StringBuffer sb = new StringBuffer();
			// @formatter:off
			sb.append("update booking set deleted = 1 ") // 削除フラグを1に変更
			   .append(",record_update_timestamp = now() ") // 予約変更日時を実行時刻に修正
			   .append("where id = ? and deleted = '0';"); // キャンセル（論理削除）されていない予約番号の予約が対象
			// @formatter:on

			PreparedStatement ps = connection.prepareStatement(sb.toString());
			ps.setInt(1, id);

			return ps.executeUpdate();
		}
	}

	/**
	 * アカウントIDをもとにbookingテーブルから予約情報を検索し、List<BookingEntity>を返す
	 * @author Yamashita
	 *
	 * @param id アカウントID
	 * @return List<BookingEntity>
	 * @throws NamingException
	 * @throws SQLException
	 */
	public List<BookingEntity> findBookingByAccountId(String id) throws SQLException, NamingException {
		List<BookingEntity> bookingEntities = new ArrayList<BookingEntity>();

		// connectionの取得
		try (Connection connection = DataSourceFactory.getConnection();) {
			// ログインしているアカウントのキャンセル（論理削除）されていない予約を探す
			PreparedStatement ps = connection.prepareStatement(
					"select * from booking where account_id = ? and deleted = '0';");
			ps.setString(1, id);
			ResultSet resultSet = ps.executeQuery();
			// resultSetでBookingEntityのインスタンス化
			while (resultSet.next()) {
				bookingEntities.add(getBookingEntity(resultSet));
			}
		}
		return bookingEntities;
	}

	/**
	 * resultSetから1件のBookingEntityをインスタンス化し返す
	 * @author Yamashita
	 *
	 * @param resultSet
	 * @return BookingEntity
	 *
	 * @throws SQLException
	 */
	private BookingEntity getBookingEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id"); // 予約ID
		Integer roomId = resultSet.getInt("room_id"); // 施設管理番号
		String accountId = resultSet.getString("account_id"); // アカウントID

		Date bookingDate = resultSet.getDate("booking_date"); // 予約日
		LocalDate convertBookingDate = Convertor.toLocalDate(bookingDate); // 予約日をLocalDateに変換

		String purpose = resultSet.getString("purpose"); // 利用目的
		String deleted = resultSet.getString("deleted"); // 削除フラグ

		// @formatter:off
		Timestamp recordCreationTimestamp
			= resultSet.getTimestamp("record_creation_timestamp"); // 予約作成日時
		LocalDateTime convertRecordCreationTimestamp
			= Convertor.toLocalDateTime(recordCreationTimestamp); // 予約作成日時をLocalDateTimeに変換

		Timestamp recordUpdateTimestamp
			= resultSet.getTimestamp("record_update_timestamp"); // 予約変更日時
		LocalDateTime convertRecordUpdateTimestamp
			= Convertor.toLocalDateTime(recordUpdateTimestamp); // 予約変更日時LocalDateTimeに変換
		// @formatter:on

		// @formatter:off
		return  new BookingEntity(id
											   , roomId
											   , accountId
											   , convertBookingDate
											   , purpose
											   , deleted
											   , convertRecordCreationTimestamp
											   , convertRecordUpdateTimestamp);
		// @formatter:on
	}

	/**
	 * BookingDao：対象施設の既に予約されている日をbookingから取得
	 * @author member
	 * @param id
	 * @return List<LocalDate>
	 * @throws SQLException
	 */
	public List<LocalDate> findBookedDatesByRoomId(Integer id) throws SQLException, NamingException {
		List<LocalDate> bookedDateList = new ArrayList<LocalDate>();

		// connectionの取得
		try (Connection connection = DataSourceFactory.getConnection();) {
			// 施設管理番号（roomId）で予約テーブルから対象施設の予約日(booking_date)を取得
			PreparedStatement ps = connection.prepareStatement(
					"SELECT booking_date " +
							"FROM booking " +
							"WHERE room_id = ? " +
							"AND deleted = '0'"); //deteledはchar型
			ps.setInt(1, id);
			ResultSet resultSet = ps.executeQuery();
			// resultSetでbookedDateListに日付を追加
			while (resultSet.next()) {
				bookedDateList.add(resultSet.getDate("booking_date").toLocalDate());
			}
		}
		return bookedDateList;
	}

	/**
	 * 対象の施設に指定した予約日が既に存在しているかbookingから取得
	 * @author member
	 * @param roomId
	 * @param bookingDate
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public int countBookingsByRoomIdAndDate(Integer roomId, LocalDate bookingDate)
			throws SQLException, NamingException {
		// connectionの取得
		try (Connection connection = DataSourceFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"SELECT COUNT(*) AS cnt " +
								"FROM booking " +
								"WHERE room_id = ? " +
								"AND booking_date = ? " +
								"AND deleted = '0'");) {
			ps.setInt(1, roomId);
			ps.setDate(2, java.sql.Date.valueOf(bookingDate));
			try (ResultSet resultSet = ps.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("cnt");
				}
				return 0;
			}
		}
		//"SELECT COUNT(*)" + だめ？
	}

	/**
	 * 予約情報（BookingEntitiy）を元にDBに登録
	 * @author member
	 * @param bookingEntity
	 * @throws SQLException
	 * @throws NamingException
	 * @throws DataAccessException
	 */
	public void insertBooking(BookingEntity bookingEntity) throws SQLException, NamingException, DataAccessException {
		// connectionの取得
		try (Connection connection = DataSourceFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO booking (" +
								" room_id, account_id, booking_date, purpose, deleted, " +
								" record_creation_timestamp, record_update_timestamp" +
								") VALUES (?, ?, ?, ?, '0', ?, ?)");) {
			ps.setInt(1, bookingEntity.getRoomId());
			ps.setString(2, bookingEntity.getAccountId());
			ps.setDate(3, java.sql.Date.valueOf(bookingEntity.getBookingDate()));
			ps.setString(4, bookingEntity.getPurpose());
			ps.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
			ps.setTimestamp(6, java.sql.Timestamp.valueOf(LocalDateTime.now()));

			int result = ps.executeUpdate();

			if (result == 0) {
				throw new SQLException();
			}
		}
	}

}
