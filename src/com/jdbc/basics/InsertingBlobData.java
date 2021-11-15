package com.jdbc.basics;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class InsertingBlobData {

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "userName",
				"password");
				PreparedStatement preparedStatement = connection
						.prepareStatement("insert into profile_pics values(?,?);");) {

			// Pass path of file you want to save in db
			File file = new File("E:/Profile Pic.jpeg");
			FileInputStream fileInputStream = new FileInputStream(file);
			preparedStatement.setInt(1, 1012);
			preparedStatement.setBinaryStream(2, fileInputStream, file.length());

			
			// Using execute method to show example
			// Use execute when we don't know what query we have(select or non select) i.e. if query is dynamic
			// execute will return true if query is select type or else it will return false
			boolean selectQuery = preparedStatement.execute();
			if (selectQuery) {
				ResultSet resultSet = preparedStatement.getResultSet();
				while (resultSet.next()) {
					// we can use getString(colIndex) instead of all the getXXX methods
					System.out.print(resultSet.getInt(1) + "\t");
					System.out.print(resultSet.getString(2) + "\t");
					System.out.print(resultSet.getString(3) + "\t");
					// Retrieving date values from table the output will be in java.sql.Date format
					System.out.print(resultSet.getDate(4) + "\t");
					System.out.println();
				}
			} else {
				int updatedRowCount = preparedStatement.getUpdateCount();
				System.out.println(updatedRowCount + " rows updated!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
