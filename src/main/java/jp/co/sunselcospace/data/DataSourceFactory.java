package jp.co.sunselcospace.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceFactory {

	private static DataSource dataSource;

	static {
		try {
			// Tomcatの設定（context.xml）を参照するためのコンテキストを生成
			InitialContext context = new InitialContext();

			// JNDI名でDataSourceを照会
			// Tomcat側で設定したコネクションプールが利用可能になる
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/sun_selco_space");
		} catch (NamingException e) {
			// 起動時に設定ミスがあればすぐわかるように例外を投げる
			throw new ExceptionInInitializerError("JNDIリソース 'jdbc/sun_selco_space' の取得に失敗しました: " + e.getMessage());
		}
	}

	/**
	 * コネクションプールから接続を取得する
	 */
	public static Connection getConnection() throws SQLException {
		if (dataSource == null) {
			throw new SQLException("DataSource が初期化されていません。");
		}
		return dataSource.getConnection();
	}
}