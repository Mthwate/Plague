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
	
}
