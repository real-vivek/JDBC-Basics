package com.jdbc.basics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertingDateData {

	public static void main(String[] args) {
		// Using try with resources
		// Resource must implement java.lang.AutoCloseable interface if we want to use
		// it in try with resources
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "userName",
				"password");
				// In case of PreparedStatement db engine will execute Query tokenization,
				// parsing and optimization only once but db will execute query number of times
				// it will improve application performance because query execution plan(buffer)
				// is created on db side
				// Also preparedStatement object is created on java side with positional params
				// to which we set values
				// Here we create template for query
				PreparedStatement preparedStatement = connection
						.prepareStatement("insert into book values(?,?,?,?);");) {
			preparedStatement.setInt(1, 1011);
			preparedStatement.setString(2, "The Alchemist");
			preparedStatement.setString(3, "Philosophy");

			// Code to insert current(today's) date in sql table
			// java.util.Date date = new java.util.Date();
			// System.out.println(date); ==> Tue Nov 09 15:34:52 IST 2021
			// long time = date.getTime();
			// System.out.println(time); ==> 1636452292523
			// java.sql.Date sqlDate = new java.sql.Date(time);
			// System.out.println(sqlDate); ==> 2021-11-09
			// preparedStatement.setDate(4, sqlDate);

			// Code to insert any date in sql
			// Remember to give date in form of YYYY-MM-DD or we get
			// IllegalArgumentException
			java.sql.Date sqlDate = java.sql.Date.valueOf("2021-11-12");
			preparedStatement.setDate(4, sqlDate);

			// Using exeuteUpdate as it is a non select sql query
			int updatedRowCount = preparedStatement.executeUpdate();
			System.out.println(updatedRowCount + " rows updated!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
