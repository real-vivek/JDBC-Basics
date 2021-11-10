package com.jdbc.basics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BatchUpdation {

	public static void main(String[] args) {
		// Using try with resources
		// Resource must implement java.lang.AutoCloseable interface if we want to use
		// it in try with resources
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "userName",
				"password");
				PreparedStatement preparedStatement = connection
						.prepareStatement("insert into book values(?,?,?,?);");) {
			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, "Shiva Triology");
			preparedStatement.setString(3, "Mythological");
			java.sql.Date sqlDate = java.sql.Date.valueOf("2021-11-12");
			preparedStatement.setDate(4, sqlDate);

			// Adding preparedStatement to batch for statement object use
			// statement.addbatch("sql query") to add query to batch
			preparedStatement.addBatch();

			preparedStatement.setInt(1, 2);
			preparedStatement.setString(2, "Revolution 2020");
			preparedStatement.setString(3, "Novel");
			java.sql.Date sqlDate2 = java.sql.Date.valueOf("2021-11-13");
			preparedStatement.setDate(4, sqlDate2);

			preparedStatement.addBatch();

			// It is not recommend to mix select select and non select
			// Using executeBatch to execute all the queries in batch we can iterate over
			// int[] to get updatedRowCount
			int[] batchUpdateArray = preparedStatement.executeBatch();
			for (int updateCount : batchUpdateArray) {
				System.out.println(updateCount + " rows updated!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
