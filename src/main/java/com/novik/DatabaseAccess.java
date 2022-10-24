package com.novik;

// Loading required libraries

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


public class DatabaseAccess extends HttpServlet {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String SELECT_ALL_USERS1_QUERY = "select * from users1";
    private static final String SELECT_ALL_COUNTER_QUERY = "select * from counter";
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("update counter set count=count+1 where id=1");
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS1_QUERY);
            while (resultSet.next()) {
                writer.println("<h1> " + resultSet.getInt("id") + ", name: "
                        + resultSet.getString("name") + ", course: "
                        + resultSet.getInt("course") + "</h1>");
            }
            ResultSet resultSet1 = statement.executeQuery(SELECT_ALL_COUNTER_QUERY);
            while (resultSet1.next()) {
                writer.println("<h1> This page was visited " + resultSet1.getInt("count") + " times</h1>");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
