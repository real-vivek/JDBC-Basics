package com.jdbc.basics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DisablingAutoCommit {

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hb_library", "hblibrary",
				"libadmin"); Statement statement = connection.createStatement();) {
			// Setting AutoCommit to false to preserve atomicity property
			// Thus records are not persisted permanently, they are persisted permanently
			// only if committed manually
			connection.setAutoCommit(false);
			
			int updateRowCount = statement.executeUpdate("insert into book values(1002,'Sublte Art', 'Novel', STR_TO_DATE('03-11-2021', '%d-%m-%Y'));");
			
			// We can provide duplicate primary key so that execution fails
			// No record will be persisted as autoCommit is false and if execution fails
			int updateRowCount2 = statement.executeUpdate("insert into book values(1,'Sublte Art', 'Novel', STR_TO_DATE('03-11-2021', '%d-%m-%Y'));");
			
			if(updateRowCount==1 && updateRowCount2==1) {
				// Committing records only if records are updated temporarily successfully
				connection.commit();
				System.out.println(updateRowCount + " rows updated!!");
				System.out.println(updateRowCount + " rows updated!!");
			}
			else {
				// Rolling transaction so that no record is persisted
				connection.rollback();
				System.out.println("Failed to insert records");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to insert records");
		}
	}
}
