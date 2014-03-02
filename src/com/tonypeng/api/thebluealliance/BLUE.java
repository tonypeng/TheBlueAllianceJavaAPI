/*
 * The Blue Alliance Java API - BLUE.java
 * Copyright (c) 2014 Tony "untitled" Peng.  All rights reserved.
 * <http://www.tonypeng.com/>
 * 
 * This file is part of the The Blue Alliance Java API project <http://git.io/JiJQhg>
 * and is licensed under the MIT license <http://git.io/nTMQyw>.
 */

package com.tonypeng.api.thebluealliance;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Provides an interface for the The Blue Alliance REST API.
 */
public class BLUE {
	
	/**
	 * Describes an API exception.
	 */
	@SuppressWarnings("serial")
	public static class BLUEApiException extends Exception {
		Exception _innerException;
		
		/**
		 * Creates an instance of BLUEApiException.
		 * 
		 * @param message The exception message.
		 * @param inner The underlying message.
		 */
		public BLUEApiException(String message, Exception inner) {
			super(message);
			
			_innerException = inner;
		}
	
		/**
		 * Gets the underlying exception.
		 * 
		 * @return The underlying exception.
		 */
		public Exception getInnerException() {
			return _innerException;
		}
	}
	
	private static final String API_BASE = "http://www.thebluealliance.com/api/v2";
	private static String X_TBA_APP_ID = "";
	private static final JSONParser parser = new JSONParser();
	
	/**
	 * Sets the App ID for the application.
	 * 
	 * @param appId The application ID, in the format <team/person id>:<app description>:<version>
	 */
	public static void setAppId(String appId) {
		X_TBA_APP_ID = appId;
	}
	
	private static boolean isInitialized() {
		return X_TBA_APP_ID.length() > 0 && X_TBA_APP_ID.split(":").length == 3;
	}
	
	/**
	 * Makes an API call to The Blue Alliance.
	 * 
	 * @param apiReq The REST endpoint to make a request to.
	 * @return The parsed JSON data.
	 */
	public static Object api(String apiReq)
		throws BLUEApiException
	{
		if(!isInitialized()) throw new BLUEApiException("BLUE was not initialized.", null);
		
		String endpoint = API_BASE + apiReq;
		
		URL endpointUrl;
		
		try {
			endpointUrl = new URL(endpoint);
		} catch (Exception e) {
			throw new BLUEApiException("Malformed API request.", e);
		}
		
		HttpURLConnection conn;
		
		try {
			conn = (HttpURLConnection)endpointUrl.openConnection();
		} catch (Exception e) {
			throw new BLUEApiException("Could not open connection.", e);
		}
		
		try {
			conn.setRequestMethod("GET");
		} catch (Exception e) {
			throw new BLUEApiException("Could not set the request type.", e);
		}
		
		conn.setRequestProperty("X-TBA-App-Id", X_TBA_APP_ID);
		conn.setUseCaches(false);
		
		InputStream is;
		BufferedReader reader;
		
		try {
			is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is));
		} catch (Exception e) {
			throw new BLUEApiException("Error getting the input stream.", e);
		}
		
		String jsonString = "";
		String respLine = "";
		
		try {
			while((respLine = reader.readLine()) != null) {
				jsonString += respLine;
			}
			
			reader.close();
		} catch (Exception e) {
			throw new BLUEApiException("Error reading the response.", e);
		}
		
		Object obj;
		
		try {
			obj = parser.parse(jsonString);
		} catch (Exception e) {
			throw new BLUEApiException("Malformed response received.", e);
		}
		
		return obj;
	}
	
	/**
	 * Provides functionality for requesting team data.
	 */
	public static class Teams {
		
		/**
		 * Describes an alliance's color.
		 */
		public static enum AllianceColor {
			RED,
			BLUE
		}
		
		/**
		 * Represents a team.
		 */
		public static class Team {
			
			String _website;
			String _name;
			String _locality;
			String _region;
			String _country;
			String _location;
			int _teamNumber;
			String _key;
			String _nickName;
			
			private Team(String website, String name, String locality,
					String region, String country, String location, int teamNumber,
					String key, String nickName) {
				_website = website;
				_name = name;
				_locality = locality;
				_region = region;
				_country = country;
				_location = location;
				_teamNumber = teamNumber;
				_key = key;
				_nickName = nickName;
			}
			
			/**
			 * Gets the team's website.
			 * 
			 * @return The team's website.
			 */
			public String getWebsite() {
				return _website;
			}
			
			/**
			 * Gets the team's name.
			 * 
			 * @return The team's name.
			 */
			public String getName() {
				return _name;
			}
			
			/**
			 * Gets the team's locality.
			 * 
			 * @return The team's locality.
			 */
			public String getLocality() {
				return _locality;
			}
			
			/**
			 * Gets the team's region.
			 * 
			 * @return The team's region.
			 */
			public String getRegion() {
				return _region;
			}
			
			/**
			 * Gets the team's locality.
			 * 
			 * @return The team's region.
			 */
			public String getCountry() {
				return _country;
			}
			
			/**
			 * Gets the team's location.
			 * 
			 * @return The team's location.
			 */
			public String getLocation() {
				return _location;
			}
			
			/**
			 * Gets the team's number.
			 * 
			 * @return The team's number.
			 */
			public int getTeamNumber() {
				return _teamNumber;
			}
			
			/**
			 * Gets the team's API reference key.
			 * 
			 * @return The team's API reference key.
			 */
			public String getTeamKey() {
				return _key;
			}
			
			/**
			 * Gets the team's nickname.
			 * 
			 * @return The team's nickname.
			 */
			public String getNickName() {
				return _nickName;
			}
			
			/**
			 * Gets the <code>String</code> representation of this <code>Team</code>.
			 */
			public String toString() {
				return String.format("{ %s, %s, %s, %s, %s, %s, %d, %s, %s }", getWebsite(), getName(), getLocality(),
						getRegion(), getCountry(), getLocation(), getTeamNumber(), getTeamKey(), getNickName());
			}
			
			/**
			 * Gets the events attended by this team in <code>year</code>
			 * @param year The requested year.
			 * @return Events that this team attended in <code>year</code>
			 * @throws BLUEApiException
			 */
			@SuppressWarnings("rawtypes")
			public Events.Event[] getEvents(int year)
				throws BLUEApiException
			{
				HashMap obj;
				
				try {				
					obj = (HashMap)BLUE.api("/team/" + _key + "/" + year);
				} catch (Exception e) {
					throw new BLUEApiException("Invalid response.", e);
				}
				
				JSONArray arr = (JSONArray)obj.get("events");
				
				Events.Event[] events = new Events.Event[arr.size()];
				
				for(int i = 0; i < events.length; i++)
				{
					JSONObject event = (JSONObject)arr.get(i);
					
					events[i] = Events.parseEvent(event);
				}
				
				return events;
			}
		}
		
		/**
		 * Gets the team with team number <code>teamNumber</code>
		 * @param teamNumber The requested team number.
		 * @return The team with team number <code>teamNumber</code>, represented as a <code>Team</code>
		 * @throws BLUEApiException
		 */
		@SuppressWarnings("rawtypes")
		public static Team getTeam(int teamNumber)
			throws BLUEApiException
		{
			String teamKey = "frc" + teamNumber;
			
			HashMap obj;
			
			try {				
				obj = (HashMap)BLUE.api("/team/" + teamKey);
			} catch (Exception e) {
				throw new BLUEApiException("Invalid response.", e);
			}
			
			return parseTeam(obj);
		}
		
		/**
		 * Returns the <code>Team</code> representation from a hashmap with data.
		 * @param obj The hashmap with data.
		 * @return The hashmap represented as a <code>Team</code>
		 */
		@SuppressWarnings("rawtypes")
		public static Team parseTeam(HashMap obj) {
			return new Team((String)obj.get("website"), (String)obj.get("name"), (String)obj.get("locality"),
					(String)obj.get("region"), (String)obj.get("country_name"), (String)obj.get("location"),
					(int)(long)(Long)obj.get("team_number"), (String)obj.get("key"), (String)obj.get("nickname"));
		}
	}
	
	/**
	 * Provides functionality for requesting event data.
	 */
	public static class Events {
		
		/**
		 * Represents an event.
		 */
		public static class Event {
			
			private String _key;
			private String _name;
			private String _shortName;
			private String _eventCode;
			private String _eventType;
			private int _eventTypeCode;
			private int _year;
			private String _location;
			private boolean _official;
			
			private Event(String key, String name, String shortName,
					String eventCode, String eventType, int eventTypeCode, int year, String location,
					boolean official) {
				_key = key;
				_name = name;
				_shortName = shortName;
				_eventCode = eventCode;
				_eventType = eventType;
				_eventTypeCode = eventTypeCode;
				_year = year;
				_location = location;
				_official = official;
			}
			
			/**
			 * Gets the event key.
			 * 
			 * @return The event key.
			 */
			public String getEventKey() {
				return _key;
			}
			
			/**
			 * Gets the event name.
			 * 
			 * @return The event name.
			 */
			public String getName() {
				return _name;
			}
			
			/**
			 * Gets the event short name.
			 * 
			 * @return The event short name.
			 */
			public String getShortName() {
				return _shortName;
			}
			
			/**
			 * Gets the event code.
			 * @return The event code.
			 */
			public String getEventCode() {
				return _eventCode;
			}
			
			/**
			 * Gets the <code>String</code> representation of the event type.
			 * 
			 * @return The <code>String</code> representation of the event type.
			 */
			public String getEventTypeString() {
				return _eventType;
			}
			
			/**
			 * Gets the event type.
			 * 
			 * @return The event type.
			 */
			public int getEventType() {
				return _eventTypeCode;
			}
			
			/**
			 * Gets the year of this event.
			 * 
			 * @return The year of this event.
			 */
			public int getYear() {
				return _year;
			}
			
			/**
			 * Gets the event location.
			 * 
			 * @return The event location.
			 */
			public String getLocation() {
				return _location;
			}
			
			/**
			 * Determines if this event is an official event.
			 *  
			 * @return <code>true</code> if the event is an official event; <code>false</code> otherwise.
			 */
			public boolean isOfficial() {
				return _official;
			}
			
			/**
			 * Returns the <code>String</code> representation of this <code>Event</code>.
			 */
			public String toString() {
				return String.format("{ %s, %s, %s, %s, %s, %d, %d, %s, %s }", getEventKey(), getName(), getShortName(),
						getEventCode(), getEventTypeString(), getEventType(), getYear(), getLocation(), (isOfficial() ? "true" : "false"));
			}
			
			/**
			 * Gets a list of all teams that attended this event.
			 * 
			 * @return An array of the teams that attended this event.
			 * @throws BLUEApiException
			 */
			public Teams.Team[] getTeams()
				throws BLUEApiException
			{
				String eventKey = getEventKey();
				
				JSONArray arr;
				
				try {				
					arr = (JSONArray)BLUE.api("/event/" + eventKey + "/teams");
				} catch (Exception e) {
					throw new BLUEApiException("Invalid response.", e);
				}
				
				Teams.Team[] teams = new Teams.Team[arr.size()];
				
				for(int i = 0; i < teams.length; i++) {
					teams[i] = Teams.parseTeam((JSONObject)arr.get(i));
				}
				
				return teams;
			}
			
			/**
			 * Gets a list of matches played at this event.
			 * 
			 * @return An array of the matches played at this event.
			 * @throws BLUEApiException
			 */
			public Matches.Match[] getMatches()
					throws BLUEApiException
			{
				String eventKey = getEventKey();
				
				JSONArray arr;
				
				try {				
					arr = (JSONArray)BLUE.api("/event/" + eventKey + "/matches");
				} catch (Exception e) {
					throw new BLUEApiException("Invalid response.", e);
				}
				
				Matches.Match[] matches = new Matches.Match[arr.size()];
				
				for(int i = 0; i < matches.length; i++) {
					matches[i] = Matches.parseMatch((JSONObject)arr.get(i));
				}
				
				return matches;
			}
		}
		
		/**
		 * Gets the event with event code <code>eventCode</code> in <code>year</code>
		 * @param eventCode The eventCode of the requested event.
		 * @param year The requested year.
		 * @return The event with event code <code>eventCode</code> in <code>year</code>.
		 * @throws BLUEApiException
		 */
		@SuppressWarnings("rawtypes")
		public static Event getEvent(String eventCode, int year)
				throws BLUEApiException
		{
			String eventKey = year + eventCode;
			
			HashMap obj;
			
			try {				
				obj = (HashMap)BLUE.api("/event/" + eventKey);
			} catch (Exception e) {
				throw new BLUEApiException("Invalid response.", e);
			}
			
			return parseEvent(obj);
		}
		
		/**
		 * Returns the <code>Event</code> representation from a hashmap with data.
		 * @param obj The hashmap with data.
		 * @return The hashmap represented as an <code>Event</code>
		 */
		@SuppressWarnings("rawtypes")
		public static Event parseEvent(HashMap event) {
			return new Event((String)event.get("key"), (String)event.get("name"), (String)event.get("short_name"),
					(String)event.get("event_code"), (String)event.get("event_type_string"), (int)(long)(Long)event.get("event_type"),
					(int)(long)(Long)event.get("year"), (String)event.get("location"), (Boolean)event.get("official"));
		}
		
	}
	
	/**
	 * Provides functionality for requesting match data.
	 */
	public static class Matches {
		
		/**
		 * Represents a match.
		 */
		public static class Match {
			
			/**
			 * Represents an alliance in a match.
			 */
			public static class Alliance {
				Teams.AllianceColor _color;
				String[] _teams;
				
				private Alliance(Teams.AllianceColor color, String[] teams) {
					_color = color;
					_teams = teams;
				}
				
				/**
				 * Gets the teams in this alliance.
				 * 
				 * @return The teams in this alliance.
				 */
				public String[] getTeams() {
					String[] copy = new String[_teams.length];
					
					System.arraycopy(_teams, 0, copy, 0, copy.length);
					
					return copy;
				}
				
				/**
				 * Returns the <code>String</code> representation of this <code>Alliance</code>.
				 */
				public String toString() {
					return String.format("{ %s, %s }", _color.name(), Arrays.toString(_teams));
				}
			}
			
			String _key;
			String _compLevel;
			int _setNumber;
			int _matchNumber;
			String _eventKey;
			Alliance _redAlliance;
			int _redAllianceScore;
			Alliance _blueAlliance;
			int _blueAllianceScore;
			
			private Match(String key, String compLevel, int setNumber,
					int matchNumber, String eventKey, Alliance redAlliance,
					int redAllianceScore, Alliance blueAlliance, int blueAllianceScore) {
				_key = key;
				_compLevel = compLevel;
				_setNumber = setNumber;
				_matchNumber = matchNumber;
				_eventKey = eventKey;
				_redAlliance = redAlliance;
				_redAllianceScore = redAllianceScore;
				_blueAlliance = blueAlliance;
				_blueAllianceScore = blueAllianceScore;
			}
			
			/**
			 * Gets the match key.
			 * 
			 * @return The match key.
			 */
			public String getMatchKey() {
				return _key;
			}
			
			/**
			 * Gets the competition level.
			 * 
			 * @return The competition level.
			 */
			public String getCompLevel() {
				return _compLevel;
			}
			
			/**
			 * Gets the set number.
			 * 
			 * @return The set number.
			 */
			public int getSetNumber() {
				return _setNumber;
			}
			
			/**
			 * Gets the match number.
			 * 
			 * @return The match number.
			 */
			public int getMatchNumber() {
				return _matchNumber;
			}
			
			/**
			 * Gets the event key.
			 * 
			 * @return The event key.
			 */
			public String getEventKey() {
				return _eventKey;
			}
			
			/**
			 * Gets the red alliance <code>Alliance</code> object.
			 * 
			 * @return The red alliance <code>Alliance</code> object.
			 */
			public Alliance getRedAlliance() {
				return _redAlliance;
			}
			
			/**
			 * Gets the red alliance score.
			 * 
			 * @return The red alliance score.
			 */
			public int getRedAllianceScore() {
				return _redAllianceScore;
			}
			
			/**
			 * Gets the blue alliance <code>Alliance</code> object.
			 * 
			 * @return The blue alliance <code>Alliance</code> object.
			 */
			public Alliance getBlueAlliance() {
				return _blueAlliance;
			}
			
			/**
			 * Gets the blue alliance score.
			 * 
			 * @return The blue alliance score.
			 */
			public int getBlueAllianceScore() {
				return _blueAllianceScore;
			}
			
			/**
			 * Returns the <code>String</code> representation of this <code>Match</code>
			 */
			public String toString() {
				return String.format("{ %s, %s, %d, %d, %s, %s, %d, %s, %d }", getMatchKey(), getCompLevel(), getSetNumber(), getMatchNumber(),
						getEventKey(), getRedAlliance().toString(), getRedAllianceScore(), getBlueAlliance().toString(), getBlueAllianceScore());
			}
		}
		
		/**
		 * Returns the <code>Match</code> representation from a hashmap with data.
		 * @param obj The hashmap with data.
		 * @return The hashmap represented as a <code>Match</code>
		 */
		@SuppressWarnings("rawtypes")
		public static Match parseMatch(HashMap obj) {
			JSONObject alliances = (JSONObject)obj.get("alliances");
			
			JSONObject blueAllianceJson = (JSONObject)alliances.get("blue");
			JSONObject redAllianceJson = (JSONObject)alliances.get("red");
			
			JSONArray blueTeams = (JSONArray)blueAllianceJson.get("teams");
			JSONArray redTeams = (JSONArray)redAllianceJson.get("teams");
			
			int blueScore = (int)(int)(long)(Long)blueAllianceJson.get("score");
			int redScore = (int)(int)(long)(Long)redAllianceJson.get("score");
			
			Match.Alliance blueAlliance = new Match.Alliance(Teams.AllianceColor.BLUE, new String[] { (String)blueTeams.get(0), (String)blueTeams.get(1), (String)blueTeams.get(2) });
			Match.Alliance redAlliance = new Match.Alliance(Teams.AllianceColor.RED, new String[] { (String)redTeams.get(0), (String)redTeams.get(1), (String)redTeams.get(2) });
			
			return new Match((String)obj.get("key"), (String)obj.get("comp_level"), (int)(long)(Long)obj.get("set_number"),
					(int)(long)(Long)obj.get("match_number"), (String)obj.get("event_key"), redAlliance, redScore, blueAlliance, blueScore);
			
		}
	}
}
