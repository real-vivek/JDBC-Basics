package com.jdbc.basics;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.zaxxer.hikari.HikariDataSource;

public class AppUsingHikariDataSource {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try (
			// This is a very lightweight (at roughly 130Kb) and lightning-fast JDBC connection pooling framework
			HikariDataSource dataSource = new HikariDataSource()) 
		{
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/db_name");
			dataSource.setUsername("userName");
			dataSource.setPassword("password");
			dataSource.setMaximumPoolSize(5);
			Statement statement = dataSource.getConnection().createStatement();
			ResultSet rs = statement.executeQuery("select * from Book");
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				System.out.print(metaData.getColumnName(i) + "\t");
			}
			System.out.println();
			System.out.println("-----------------------------------------------------");
			while (rs.next()) {
				System.out.print(rs.getString(1) + "\t");
				System.out.print(rs.getString(2) + "\t");
				System.out.print(rs.getString(3) + "\t");
				System.out.print(rs.getString(4) + "\t");
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
