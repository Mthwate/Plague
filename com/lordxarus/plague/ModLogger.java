package com.lordxarus.plague;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

public class ModLogger {
	private static Logger logger = Logger.getLogger(Plague.modid);

	public static void init() {
		logger.setParent(FMLLog.getLogger());
	}
	
	public static void log(Level level, String message) {
		logger.log(level, message);
	}
	
	public static void log(Level level, String message, boolean verbose) {
		if (verbose == true) {
			if (Plague.verbose) {
				logger.log(level, message);
			}
		} else {
			logger.log(level, message);
		}
	}

}
