package jp.co.sunselcospace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jp.co.sunselcospace.data.DataSourceFactory;
import jp.co.sunselcospace.entity.RoomEntity;

/**
 * @author Yamashita
 */
public class RoomDao {
	/**
	 * 施設管理番号をもとに施設テーブルを検索しRoomEntityを返す
	 * @author Yamashita
	 *
	 * @param roomId 施設管理番号
	 * @return RoomEntity
	 *
	 * @throws NamingException
	 * @throws SQLException
	 */
	public RoomEntity findRoomById(int roomId) throws NamingException, SQLException {
		String sql = "select * from room where id = ?;";

		// @formatter:off
		try (Connection connection = DataSourceFactory.getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql.toString());) {
		// @formatter:on
			ps.setInt(1, roomId);
			try (ResultSet resultSet = ps.executeQuery();) {
				if (resultSet.next()) {
					return getRoomEntity(resultSet);
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @author Yamashita
	 *
	 * @param resultSet
	 * @return RoomEntity
	 *
	 * @throws SQLException
	 */
	private RoomEntity getRoomEntity(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String name = resultSet.getString("name");
		String location = resultSet.getString("location");

		Integer capacity = resultSet.getInt("capacity");
		if (resultSet.wasNull()) {
			capacity = null;
		}

		String overview = resultSet.getString("overview");
		Integer fee = resultSet.getInt("fee");
		String image = resultSet.getString("image");
		String introduction = resultSet.getString("introduction");

		// @formatter:off
		return new RoomEntity(id
										 , name
										 , location
										 , capacity
										 , overview
										 , fee
										 , image
										 , introduction);
		// @formatter:on
	}

	/*
	 * @author member
	*RoomDaoがデータベースから施設の情報を取得する
	*テーブル：施設（room）
	*条件　　：全件取得
	*取得項目：画像・施設名・場所・人数・概要・料金・施設管理番号
	*/

	//roomテーブルから全件取得しroomListに格納
	public List<RoomEntity> getRoomList() throws NamingException, SQLException {
		List<RoomEntity> roomList = new ArrayList<>();

		//roomテーブルから全件取得
		String sql = "SELECT * FROM room";

		//DBに接続、SQLを実行して結果を受け取る
		try (Connection con = DataSourceFactory.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			//一つずつ取り出し各列の値をEntityに詰め替える
			while (rs.next()) {
				RoomEntity room = new RoomEntity();
				room.setId(rs.getInt("id"));
				room.setName(rs.getString("name"));
				room.setLocation(rs.getString("location"));
				room.setCapacity(rs.getInt("capacity"));
				room.setOverview(rs.getString("overview"));
				room.setFee(rs.getInt("fee"));
				room.setImage(rs.getString("image"));

				//実行結果をインスタンス化したRoomEntityに詰め替え、リストに追加
				roomList.add(room);
			}

		}

		//リストをRoomServiceに返す
		return roomList;
	}

}
