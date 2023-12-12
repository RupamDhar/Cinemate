package com.example.apicall;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CinemateDB 
{
    public CinemateDB(String id, String name, String rating, String year, String cast) 
    {
        String url = "jdbc:mysql://localhost:3306/demo_db";
        String user = "root";
        String password = "your-password";

        String movieName = name.replace("\'", "");
        String movieReleaseYear = year;
        String movieCast = cast;    
        String movieID = id;
        String movieRating = rating;
        
        try{
            //establishing connection with database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful -> "+url);

            //creating query and executing it
            String query = "INSERT INTO movies.movieinfo VALUES ('"+movieID+"','"+movieName+"','"+movieRating+"','"+movieReleaseYear+"','"+movieCast+"');";
            Statement statement = (Statement)connection.createStatement();
            statement.execute(query.replace("\"", ""));
            System.out.println("Query executed!");
        } 
        catch (ClassNotFoundException e){
            e.printStackTrace();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
