package jp.co.sunselcospace.util;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.naming.NamingException;

import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.dto.BookingDto;
import jp.co.sunselcospace.entity.BookingEntity;
import jp.co.sunselcospace.entity.RoomEntity;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.service.RoomService;

/**
 * @author Yamashita
 */
public class Convertor {
	/**
	 * SQLのDateをLocalDateに変換し返す。
	 * @author Yamashita
	 *
	 * @param sqlDate
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(Date sqlDate) {
		if (sqlDate == null) {
			return null;
		}

		return sqlDate.toLocalDate();
	}

	/**
	 * StringのdateをLocalDateに変換し返す。
	 * @author Yamashita
	 *
	 * @param date
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(String date) {
		return LocalDate.parse(date);
	}

	/**
	 * SQLのTimestampをLocalDateTimeに変換し返す。
	 * @author Yamashita
	 *
	 * @param timestamp
	 * @return LocalDateTime
	 */
	public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp.toLocalDateTime();
	}

	/**
	 * bookingEntityを、bookingEntityの情報と施設名を持ったbookingDtoに詰め替えて返します
	 * @param bookingEntities
	 * @return List<BookingDto>
	 * @throws DataAccessException
	 */
	public static List<BookingDto> bookingEntityToBookingDto(List<BookingEntity> bookingEntities)
			throws DataAccessException {
		// 予約情報の施設名を追加したリストを作成するためにインスタンス化
		List<BookingDto> bookingStatusList = new ArrayList<BookingDto>();

		// 施設一覧情報を取得
		List<RoomEntity> roomList;
		try {
			roomList = new RoomService().getRoomList();

			// 施設管理番号とRoomEntityでMapを作成
			Map<Integer, RoomEntity> roomMap = roomList.stream()
					.collect(Collectors.toMap(RoomEntity::getId, Function.identity()));

			for (BookingEntity booking : bookingEntities) {
				// キー（施設管理番号）が一致していたらBookingEntityを返す
				RoomEntity room = roomMap.get(booking.getRoomId());
				if (room != null) { // 一致していた場合
					bookingStatusList.add(new BookingDto(booking, room.getName()));
				} else { // 一致していなかった場合、施設名はno nameにしています
					bookingStatusList.add(new BookingDto(booking));
				}
			}
		} catch (NamingException | SQLException e) {
			e.printStackTrace(); // デバッグ用
			throw new DataAccessException(ErrorMessage.DATABASE_ERROR_MESSAGE, e);
		}

		return bookingStatusList;
	}
}
