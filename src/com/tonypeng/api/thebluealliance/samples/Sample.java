/*
 * The Blue Alliance Java API - Sample.java
 * Copyright (c) 2014 Tony "untitled" Peng.  All rights reserved.
 * <http://www.tonypeng.com/>
 * 
 * This file is part of the The Blue Alliance Java API project <http://git.io/JiJQhg>
 * and is licensed under the MIT license <http://git.io/nTMQyw>.
 */

package com.tonypeng.api.thebluealliance.samples;

import com.tonypeng.api.thebluealliance.BLUE;
import com.tonypeng.api.thebluealliance.BLUE.BLUEApiException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Sample {
	public static void main(String[] args)
		throws IOException
	{
		BLUE.setAppId("BLUE_Sample:SampleCode:v1");
		
		boolean run = true;
		
		while(run)
		{
			System.out.println("Enter a request type (team,event,event_teams,event_matches) or type \"exit\".");
			
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			String s = console.readLine();
			
			try
			{
				if(s.equals("team")) {
					handleTeam(console);
				} else if(s.equals("event")) {
					handleEvent(console);
				} else if(s.equals("event_teams")) {
					handleEventTeams(console);
				} else if(s.equals("event_matches")) {
					handleEventMatches(console);
				} else if(s.equals("exit")) {
					run = false;
				} else {
					System.out.println("Unrecognized command.");
				}
			} catch (BLUEApiException e) {
				System.out.println("API exception! " + e.toString());
			} catch (IOException e) {
				System.out.println("Unknown exception. e: " + e.toString());
			} catch (NumberFormatException e) {
                System.out.println(e.getMessage() + " is not a number.");
            }
			
			System.out.println();
		}
	}
	
	private static void handleTeam(BufferedReader console)
		throws IOException, BLUEApiException
	{
		Integer teamNumber = null;
		
		while(teamNumber == null)
		{
			System.out.println("Enter a team number:");
			String teamNumberStr = console.readLine();
			teamNumber = Integer.parseInt(teamNumberStr);
		}
		
		Integer year = null;
		
		while(year == null)
		{
			System.out.println("Enter a year (optional):");
			String yearStr = console.readLine();
			
			if(yearStr.length() == 0) break;
			
			year = Integer.parseInt(yearStr);
		}
		
		BLUE.Teams.Team team = BLUE.Teams.getTeam(teamNumber);
		
		System.out.println(team);
		
		if(year != null)
		{
			BLUE.Events.Event[] events = team.getEvents(year);
			
			for(BLUE.Events.Event e : events)
			{
				System.out.println(e);
			}
		}
	}
	
	private static void handleEvent(BufferedReader console)
			throws IOException, BLUEApiException
	{
		System.out.println("Enter an event code:");
		String eventCode = console.readLine();
		
		Integer year = null;
		
		while(year == null)
		{
			System.out.println("Enter a year:");
			String yearStr = console.readLine();
			year = Integer.parseInt(yearStr);
		}
		
		BLUE.Events.Event event = BLUE.Events.getEvent(eventCode, year);
		
		System.out.println(event);
	}
	
	private static void handleEventTeams(BufferedReader console)
			throws IOException, BLUEApiException
	{
		System.out.println("Enter an event code:");
		String eventCode = console.readLine();
		
		Integer year = null;
		
		while(year == null)
		{
			System.out.println("Enter a year:");
			String yearStr = console.readLine();
			year = Integer.parseInt(yearStr);
		}
		
		BLUE.Events.Event event = BLUE.Events.getEvent(eventCode, year);
		BLUE.Teams.Team[] teams = event.getTeams();
		
		System.out.println(Arrays.toString(teams));
	}
	
	private static void handleEventMatches(BufferedReader console)
			throws IOException, BLUEApiException
	{
		System.out.println("Enter an event code:");
		String eventCode = console.readLine();
		
		Integer year = null;
		
		while(year == null)
		{
			System.out.println("Enter a year:");
			String yearStr = console.readLine();
			year = Integer.parseInt(yearStr);
		}
		
		BLUE.Events.Event event = BLUE.Events.getEvent(eventCode, year);
		BLUE.Matches.Match[] matches = event.getMatches();
		
		System.out.println(Arrays.toString(matches));
	}
}
