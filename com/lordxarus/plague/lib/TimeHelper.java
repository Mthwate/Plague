package com.lordxarus.plague.lib;

public class TimeHelper {
	
	// time to tick
	
	public static int timeToTick(int sec) {
		return(timeToTick(0, 0, sec));
	}
	
	public static int timeToTick(int min, int sec) {
		return(timeToTick(0, min, sec));
	}
	
	//converts time to ticks
	public static int timeToTick(int hour, int min, int sec) {
		return((hour * 60 * 60 * 20) + (min * 60 * 20) + (sec * 20));
	}
	
	// tick to time
	
	//converts ticks to real life time in seconds
	public static int tickToTime(int ticks) {
		return(ticks/20);
	}
	
	// mc to time
	
	public static double mcToTime(int sec) {
		return(mcToTime(0, 0, sec));
	}
	
	public static double mcToTime(int min, int sec) {
		return(mcToTime(0, min, sec));
	}
	
	//converts Minecraft time to real life time in seconds
	public static double mcToTime(int hour, int min, int sec) {
		return(((hour/72.0)*(60*60)) + ((min/72.0)*60) + (sec/72.0));
	}
	
	// time to mc
	
	public static int timeToMc(int sec) {
		return(timeToMc(0, 0, sec));
	}
	
	public static int timeToMc(int min, int sec) {
		return(timeToMc(0, min, sec));
	}
	
	//converts real life time to Minecraft time in seconds
	public static int timeToMc(int hour, int min, int sec) {
		return((hour * 60 * 60 * 72) + (min * 60 * 72) + (sec * 72));
	}
	
	// mc to tick
	
	public static double mcToTick(int sec) {
		return(mcToTick(0, 0, sec));
	}
	
	public static double mcToTick(int min, int sec) {
		return(mcToTick(0, min, sec));
	}

	//converts Minecraft time to ticks
	public static double mcToTick(int hour, int min, int sec) {
		return((((hour*20)/72.0)*(60*60)) + (((min*20)/72.0)*60) + ((sec*20)/72.0));
	}
	
	// tick to mc

	//converts ticks to Minecraft time in seconds
	public static double tickToMc(int ticks) {
		return((ticks * 72) / 20.0);
	}
		
	
}
