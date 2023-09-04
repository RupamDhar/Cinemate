package com.example.apicall;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CinemateRequest
{
    static StringBuffer result = new StringBuffer("");

    //response generated when constructor called with movie name
    CinemateRequest(String movieRequest)
    {
        System.out.print("\033[H\033[2J");
        //clearing result content for next data to be displayed
        result.setLength(0);

        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://imdb8.p.rapidapi.com/auto-complete?q="+movieRequest))
		.header("X-RapidAPI-Key", "your-api-key")
		.header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response;

        //try-catch since response might generate null value
        try {
            String movieName;
            String movieReleaseYear;
            String movieCast;

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //parsing the HTTP response to Json object
            JsonObject jsonResponse = (JsonObject)JsonParser.parseString(response.body());
            int n = jsonResponse.get("d").getAsJsonArray().size();

            //iterating through the movies
            for(int i=0; i<n; i++)
            {
                movieName=jsonResponse.get("d").getAsJsonArray().get(i).getAsJsonObject().get("l").toString();
                //release year might not be present in database
                try{
                    movieReleaseYear=jsonResponse.get("d").getAsJsonArray().get(i).getAsJsonObject().get("y").toString();
                }
                catch(Exception e){
                    movieReleaseYear="no data available";
                }
                movieCast=jsonResponse.get("d").getAsJsonArray().get(i).getAsJsonObject().get("s").toString();
                
                result.append(movieName+" => "+movieReleaseYear+" : "+movieCast+"<br>");
            }
        } 
        catch (IOException | InterruptedException e) {
            result.append("Some error occurred");
        }
    }

    //method to return the response to the main class
    public StringBuffer getMovieDetails()
    {
        return result;
    }
}