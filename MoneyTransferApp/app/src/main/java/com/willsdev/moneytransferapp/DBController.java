package com.willsdev.moneytransferapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBController {
    private static Connection connection;
    private static Statement statement;
    private Map<String, List> data = new HashMap<>();

    DBController(String host, String login, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(host, login, password);
            statement = connection.createStatement();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public List resultSetToArrayList(ResultSet rs) throws SQLException{
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String,String>> list = new ArrayList();
        int count = 0;
        while (rs.next()){
            HashMap row = new HashMap(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
            count++;
            if (count == 50) break;
        }
        return list;
    }

    public Map getData() throws SQLException {
        String query = "select * from users";
        ResultSet result;
//        try {
//            result = statement.executeQuery(query);
//        } catch (SQLException sqlEx) {
//            result = null;
//            sqlEx.printStackTrace();
//        }
//
//        data.put("users", resultSetToArrayList(result));

        query = "select * from phrases where languages_language_id=5";
        try {
            result = statement.executeQuery(query);
        } catch (SQLException sqlEx) {
            result = null;
            sqlEx.printStackTrace();
        }
        data.put("phrases",resultSetToArrayList(result));
        return data;
    }
    public void update(){
        //todo
    }
}
