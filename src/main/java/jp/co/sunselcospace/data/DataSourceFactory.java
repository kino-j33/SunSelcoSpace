package jp.co.sunselcospace.data;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

public class DataSourceFactory {

	private static DataSource dataSource;

	static {
		try (InputStream input = DataSourceFactory.class
				.getClassLoader()
				.getResourceAsStream("db.properties")) {

			if (input == null) {
				throw new RuntimeException("db.properties が見つかりません");
			}

			Properties prop = new Properties();
			prop.load(input);

			PGSimpleDataSource ds = new PGSimpleDataSource();
			ds.setUrl(prop.getProperty("jdbc.url"));
			ds.setUser(prop.getProperty("jdbc.username"));
			ds.setPassword(prop.getProperty("jdbc.password"));

			dataSource = ds;

		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}