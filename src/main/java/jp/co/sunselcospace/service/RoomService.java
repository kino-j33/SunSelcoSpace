package jp.co.sunselcospace.service;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.dao.RoomDao;
import jp.co.sunselcospace.entity.RoomEntity;
import jp.co.sunselcospace.exception.DataAccessException;

/**
 * @author Yamashita
 */
public class RoomService {
	//roomDaoをインスタンス化
	private final RoomDao roomDao = new RoomDao();

	/**
	 * 施設管理番号をもとに施設テーブルから検索するようDaoのメソッドを呼び出す。
	 * @author Yamashita
	 *
	 * @param roomId
	 * @return RoomEntity
	 *
	 * @throws DataAccessException
	 * @throws SQLException
	 * @throws NamingException
	 */
	public RoomEntity searchRoomById(Integer roomId) throws DataAccessException {
		try {
			RoomEntity roomEntity = roomDao.findRoomById(roomId);
			if (roomEntity != null) {
				return roomEntity;
			}
			// roomEntityがnullの場合はthrowする
			throw new DataAccessException(ErrorMessage.NOT_FOUND_ROOM_MESSAGE);

		} catch (SQLException e) {
			throw new DataAccessException(ErrorMessage.SQL_ERROR_MESSAGE, e);
		} catch (NamingException e) {
			throw new DataAccessException(ErrorMessage.DATABASE_ERROR_MESSAGE, e);
		}
	}

	/**
	 * @author member
	 */

	//RoomServiceはroomDaoに施設一覧取得の実行を依頼する。

	public List<RoomEntity> getRoomList() throws NamingException, SQLException {
		//戻り値をList<RoomEntity> roomListのリストに格納
		List<RoomEntity> roomList = roomDao.getRoomList();
		//RoomServiceは施設情報を含むリストをspaceListInitServletに返す
		return roomList;
	}

}