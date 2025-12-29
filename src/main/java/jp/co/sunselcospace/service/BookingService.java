package jp.co.sunselcospace.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.dao.BookingDao;
import jp.co.sunselcospace.entity.BookingEntity;
import jp.co.sunselcospace.exception.DataAccessException;

/**
 * @author Yamashita
 */
public class BookingService {
	private final BookingDao bookingDao = new BookingDao();

	/**
	 * 予約番号をもとに予約テーブルを検索するようDAOのメソッドを呼び出し、BookingEntityを返す。
	 * @author Yamashita
	 *
	 * @param bookingId 予約ID
	 * @return BookingEntity
	 *
	 * @throws DataAccessException
	 * @throws SQLException
	 * @throws NamingException
	 */
	public BookingEntity searchBookingById(String bookingId) throws DataAccessException {
		try {
			int parsedBookingId = Integer.valueOf(bookingId); // 予約番号をintに変換

			// 予約番号をもとに予約テーブルを検索するようDAOのメソッドを呼び出します。
			BookingEntity bookingEntity = bookingDao.findBookingById(parsedBookingId);

			if (bookingEntity != null) {
				return bookingEntity;
			}
			// 予約番号での予約が確認できなかった場合
			throw new DataAccessException(ErrorMessage.NOT_FOUND_BOOKING_MESSAGE);

		} catch (SQLException | NamingException e) {
			throw new DataAccessException(ErrorMessage.DATABASE_ERROR_MESSAGE, e);
		} catch (NumberFormatException e) {
			throw new DataAccessException(ErrorMessage.INVALID_BOOKING_ID_MESSAGE, e);
		}
	}

	/**
	 * 予約番号をもとに予約テーブルでキャンセル処理を行うようDAOのメソッドを呼び出します。
	 * @author Yamashita
	 *
	 * @param id 予約番号
	 *
	 * @throws DataAccessException
	 * @throws SQLException
	 * @throws NamingException
	 */
	public void cancelBookingById(int id) throws DataAccessException {
		int updateCount = 0;
		try {
			// Daoのキャンセル処理を行うメソッドの呼び出し
			updateCount = bookingDao.cancelBookingRecordById(id);

		} catch (SQLException e) {
			throw new DataAccessException(ErrorMessage.SQL_ERROR_MESSAGE, e);
		} catch (NamingException e) {
			throw new DataAccessException(ErrorMessage.DATABASE_ERROR_MESSAGE, e);
		}

		if (updateCount != 1) { // 実行結果が1件では無かった場合throwする
			throw new DataAccessException(ErrorMessage.NOT_FOUND_BOOKING_MESSAGE);
		}
	}

	/**
	 * アカウントIDをもとにbookingテーブルから予約情報を検索するようDAOのメソッドを呼び出します。
	 * @author Yamashita
	 *
	 * @param id アカウントID
	 * @return List<BookingEntity>
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	public List<BookingEntity> searchBookingByAccountId(String id)
			throws DataAccessException {

		try {
			// 予約情報の取得
			List<BookingEntity> bookingEntities = bookingDao.findBookingByAccountId(id);

			if (bookingEntities != null) {
				return bookingEntities;
			}
			throw new DataAccessException(ErrorMessage.NOT_FOUND_BOOKING_MESSAGE);

		} catch (SQLException | NamingException e) {
			throw new DataAccessException(ErrorMessage.SQL_ERROR_MESSAGE, e);
		} catch (NumberFormatException e) { // DAO内でID変換を行うためNumberFormatExceptionをcatch
			throw new DataAccessException(ErrorMessage.INVALID_BOOKING_ID_MESSAGE, e);
		}
	}

	/**
	 * bookingテーブルから予約対象となった施設IDの予約日取得をDAOに依頼
	 * @author member
	 * @param roomid
	 * @return bookedDateList
	 * @throws DataAccessException
	 */
	public List<LocalDate> searchBookedDatesByRoomId(Integer roomid) throws DataAccessException, NamingException {

		BookingDao bookingDao = new BookingDao();

		List<LocalDate> bookedDateList = new ArrayList<LocalDate>();

		try {
			bookedDateList = bookingDao.findBookedDatesByRoomId(roomid);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(ErrorMessage.SQL_ERROR_MESSAGE);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new NamingException(ErrorMessage.DATABASE_ERROR_MESSAGE);
		}
		return bookedDateList;
	}

	/**
	 * 対象施設の予約日が重複していないか調べる
	 * @author member
	 * @param roomId
	 * @param bookingDate
	 * @return
	 * @throws DataAccessException
	 * @throws NamingException
	 */
	public boolean validateBookingDateAvailability(Integer roomId, LocalDate bookingDate)
			throws DataAccessException, NamingException {

		BookingDao bookingDao = new BookingDao();

		int bookingCount = -1;

		try {
			bookingCount = bookingDao.countBookingsByRoomIdAndDate(roomId, bookingDate);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(ErrorMessage.SQL_ERROR_MESSAGE);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new NamingException(ErrorMessage.DATABASE_ERROR_MESSAGE);
		}

		if (bookingCount == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 予約情報（BookingEntity）のDB登録をDAOに依頼
	 * @author member
	 * @param bookingEntity
	 * @throws DataAccessException
	 * @throws NamingException
	 */
	public void createBooking(BookingEntity bookingEntity) throws DataAccessException, NamingException {

		BookingDao bookingDao = new BookingDao();
		try {
			bookingDao.insertBooking(bookingEntity);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(ErrorMessage.SQL_ERROR_MESSAGE);
		} catch (NamingException e) {
			e.printStackTrace();
			throw new NamingException(ErrorMessage.DATABASE_ERROR_MESSAGE);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DataAccessException(ErrorMessage.SQL_ERROR_MESSAGE);
		}
	}

}
