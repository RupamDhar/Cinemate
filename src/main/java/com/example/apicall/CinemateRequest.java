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

        //clearing previous result content for next data to be displayed
        result.setLength(0);

        //building http request with movie name
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://imdb8.p.rapidapi.com/auto-complete?q="+movieRequest))
		.header("X-RapidAPI-Key", "your-rapidAPI-key")
		.header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        HttpResponse<String> response;
        
        //try-catch since response might generate null value
        try 
        {
            String movieName;
            String movieReleaseYear;
            String movieCast;
            String movieID;
            String movieRating;

            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //parsing the HTTP response as Json object
            JsonObject jsonResponse = (JsonObject)JsonParser.parseString(response.body());
            int n = jsonResponse.get("d").getAsJsonArray().size();

            //iterating through the movies
            for(int i=0; i<n; i++)
            {
                movieName = getMovieName(jsonResponse, i);
                movieReleaseYear = getMovieReleaseYear(jsonResponse, i);
                movieCast = getMovieCast(jsonResponse, i);
                movieID = getMovieID(jsonResponse, i);
                movieRating = getMovieRating(jsonResponse, i, movieID);

                //updating local database
                new CinemateDB(movieID,movieName,movieRating,movieReleaseYear,movieCast);
                
                result.append("["+movieRating+"] "+movieName+" :\n"+
                              "Release Year => "+movieReleaseYear+"\n"+
                              "Cast => "+movieCast+"\n\n");
                System.out.println((movieID+":"+movieName+"->"+movieRating).replace("\"", ""));
            }
        } 
        catch (IOException | InterruptedException e) {
            result.append("Some error occurred");
        }
    }

    //method to return the response to the Cinemate class
    public StringBuffer getMovieDetails(){ return result; }

    private String getMovieName(JsonObject jsonResponse, int index){
        try{
            return jsonResponse.get("d").getAsJsonArray().get(index).getAsJsonObject().get("l").toString();
        }
        catch(Exception e){
            return "no data available";
        }
    }

    private String getMovieReleaseYear(JsonObject jsonResponse, int index){
        try{
            return jsonResponse.get("d").getAsJsonArray().get(index).getAsJsonObject().get("y").toString();
        }
        catch(Exception e){
            return "no data available";
        }
    }

    private String getMovieCast(JsonObject jsonResponse, int index){
        try{
            return jsonResponse.get("d").getAsJsonArray().get(index).getAsJsonObject().get("s").toString();
        }
        catch(Exception e){
            return "no data available";
        }
    }

    private String getMovieID(JsonObject jsonResponse, int index){
        try{
            return jsonResponse.get("d").getAsJsonArray().get(index).getAsJsonObject().get("id").toString();
        }
        catch(Exception e){
            return "no data available";
        }
    }

    private String getMovieRating(JsonObject jsonResponse, int i, String movieID) {

        //creating new http request to get movie rating
        HttpRequest request = HttpRequest.newBuilder()
		.uri(URI.create("https://imdb8.p.rapidapi.com/title/get-ratings?tconst="+movieID.replace("\"", "")))
		.header("X-RapidAPI-Key", "your-rapidAPI-key")
		.header("X-RapidAPI-Host", "imdb8.p.rapidapi.com")
		.method("GET", HttpRequest.BodyPublishers.noBody())
		.build();
        
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonRatingResponse = (JsonObject)JsonParser.parseString(response.body());

            //some responses might not have a rating(null)
            if(jsonRatingResponse.get("rating")==null) return "0.0";
            else return jsonRatingResponse.get("rating").toString();
        } 
        catch(Exception e) {
            return "0.0";
        }
    }
}