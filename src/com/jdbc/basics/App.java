package com.jdbc.basics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			// Step 1: Loading and registering the driver
			// Inside Driver class implementation DriverManger.registerDriver() will be
			// called in static block
			// With JDBC 4.0, applications no longer need to issue a Class.forName() on the
			// driver name;
			// Instead, the DriverManager will find an appropriate JDBC driver when the
			// application requests a Connection
			// Class.forName("com.mysql.cj.jdbc.Driver");

			// Step 2: Getting Connection object
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hb_library", "username", "passsword");

			// Step 3: Creating statement object
			// Creating statement object as we have to execute each and every query
			// individually
			statement = con.createStatement();

			// Step 4: Executing query
			// Using executeQuery as it is a select query
			resultSet = statement.executeQuery("select * from book");

			// Step : Getting the data from query
			ResultSetMetaData metaData = resultSet.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				System.out.print(metaData.getColumnName(i) + "\t");
			}
			System.out.println();
			System.out.println("-------------------------------------------");
			while (resultSet.next()) {
				// we can use getString(colIndex) instead of all the getXXX methods
				System.out.print(resultSet.getInt(1) + "\t");
				System.out.print(resultSet.getString(2) + "\t");
				System.out.print(resultSet.getString(3) + "\t");
				// Retrieving date values from table the output will be in java.sql.Date format
				System.out.print(resultSet.getDate(4) + "\t");
				System.out.println();
			}
		}
		// To handle ClassNotFoundException and SQLException
		catch (Exception exception) {
			exception.printStackTrace();
			try {
				statement.close();
				resultSet.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				statement.close();
				resultSet.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
