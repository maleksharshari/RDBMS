package org.i2i.datadito.CHF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

public class InsertBooks {
    public static void main(String[] args) {
        // Database connection details
        String jdbcUrl = "jdbc:oracle:thin:@34.107.104.195:1521:xe";
        String username = "SYSTEM";
        String password = "ORACLE";

        String insertSQL = "INSERT INTO BOOK (ID, NAME, ISBN, CREATE_DATE) VALUES (?, ?, ?, SYSDATE)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            Random random = new Random();

            for (int i = 1; i <= 100; i++) {
                String name = "Book_" + i;
                String isbn = String.format("%013d", random.nextLong() & Long.MAX_VALUE); // 13-digit random ISBN

                preparedStatement.setInt(1, i); // Set ID
                preparedStatement.setString(2, name); // Set Name
                preparedStatement.setString(3, isbn); // Set ISBN

                preparedStatement.addBatch(); // Add to batch

                if (i % 10 == 0 || i == 100) { // Execute in batches of 10
                    preparedStatement.executeBatch();
                }
            }

            System.out.println("100 records inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
