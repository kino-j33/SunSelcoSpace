package jp.co.sunselcospace.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import jp.co.sunselcospace.data.DataSourceFactory;
import jp.co.sunselcospace.dto.AccountDto;
import jp.co.sunselcospace.entity.AccountEntity;

public class AccountDao {
	public AccountEntity getAccountByIdAndPassword(AccountDto accountDto)
			throws NamingException, SQLException {
		try (Connection connection = DataSourceFactory.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(
						"select * from account "
								+ "where id = ? and password = ?;");) {

			preparedStatement.setString(1, accountDto.getId());
			preparedStatement.setString(2, accountDto.getPasswordHash());
			try (ResultSet resultSet = preparedStatement.executeQuery();) {
				AccountEntity accountEntity = null;

				if (resultSet.next()) {
					accountEntity = new AccountEntity(
							resultSet.getString("id"), resultSet.getString("password"), resultSet.getString("name"));
				}
				return accountEntity;
			}
		}
	}

	/**
	* @author member
	* IDの重複存在チェック
	* @param id 検査対象ID
	* @return true: 既に存在 / false: 未使用
	*/
	public boolean existsById(String id) throws NamingException, SQLException {
		final String sql = "select 1 from account where id = ? limit 1";
		try (Connection connection = DataSourceFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	/**
	* @author member
	* アカウントレコード挿入
	* テーブル: account
	* 登録項目: id(平文), name(平文), password(ハッシュ化済み)
	*/
	public void insertAccountRecord(AccountDto dto) throws NamingException, SQLException {
		final String sql = "insert into account (id, name, password) values (?, ?, ?)";
		try (Connection connection = DataSourceFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getPasswordHash()); // ハッシュ化済み
			ps.executeUpdate();
		}
	}
}
