package models;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Message implements Type {

	private String author;
	private String message;
	private String time;
	
	public Message() {
		
	}
	
	public Message(String author, String message, String time) {
		this.author = author;
		this.message = message;
		this.time = time;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public String toJson() {
		return new Gson().toJson(this);
		
	}
	
	public Message fromJson(String json) {
		JsonParser parser = new JsonParser();
		JsonObject element = parser.parse(json).getAsJsonObject();
		String author = element.getAsJsonPrimitive("author").getAsString();
		String message = element.getAsJsonPrimitive("message").getAsString();
		String time = element.getAsJsonPrimitive("time").getAsString();
		return new Message(author, message, time);
	}
	
	@Override
	public boolean equals(Object o) {
		Message m = (Message) o;
		if ( m.getMessage().equals(this.getMessage()) && m.getAuthor().equals(this.getAuthor()) 
				&& m.getTime() == this.getTime()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "[" + this.getTime() + "] " +  this.getAuthor() + ": " + this.getMessage();
	}
}
