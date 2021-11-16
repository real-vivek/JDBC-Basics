package com.jdbc.basics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RetrievingBlobData {

	public static void main(String[] args) {
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_name", "userName",
				"password");
				PreparedStatement preparedStatement = connection
						.prepareStatement("select pic from profile_pics where id=?");) {

			preparedStatement.setInt(1, 1012);
			// Using execute method to show example
			// Use execute when we don't know what query we have(select or non select) i.e.
			// if query is dynamic
			// execute will return true if query is select type or else it will return false
			boolean selectQuery = preparedStatement.execute();
			System.out.println(selectQuery);
			if (selectQuery) {
				ResultSet resultSet = preparedStatement.getResultSet();
				if(resultSet.next()) {
				// Pass location where you want to save your image
				File file = new File("E:/Retrieved Pic.jpeg");
				fileOutputStream = new FileOutputStream(file);
				inputStream = resultSet.getBinaryStream(1);
				System.out.println(inputStream);
				int val = inputStream.read();
				while (val != -1) {
					fileOutputStream.write(val);
					val = inputStream.read();
				}
				}
			} else {
				int updatedRowCount = preparedStatement.getUpdateCount();
				System.out.println(updatedRowCount + " rows updated!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
