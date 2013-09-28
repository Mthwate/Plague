package com.lordxarus.plague;

public class TimeHelper {
	
	public static int timeToTick(int sec) {
		return(timeToTick(0, 0, sec));
	}
	
	public static int timeToTick(int min, int sec) {
		return(timeToTick(0, min, sec));
	}
	
	public static int timeToTick(int hour, int min, int sec) {
		return((hour * 60 * 60 * 20) + (min * 60 * 20) + (sec * 20));
	}
	
	public static int tickToTime(int ticks) {
		return(ticks/20);
	}
	
	public static double mcToTime(int sec) {
		return(mcToTime(0, 0, sec));
	}
	
	public static double mcToTime(int min, int sec) {
		return(mcToTime(0, min, sec));
	}
	
	public static double mcToTime(int hour, int min, int sec) {
		return((hour * 50) + (min * (50.0/60.0)) + (sec * (50.0/(60.0 * 60.0))));
	}
	
	public static int timeToMc(int sec) {
		return(timeToMc(0, 0, sec));
	}
	
	public static int timeToMc(int min, int sec) {
		return(timeToMc(0, min, sec));
	}
	
	public static int timeToMc(int hour, int min, int sec) {
		return((hour * 60 * 60 * 72) + (min * 60 * 72) + (sec * 72));
	}
	
}
