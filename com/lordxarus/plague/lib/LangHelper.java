package com.lordxarus.plague.lib;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class LangHelper {

	public static void register(String lang) {
		LanguageRegistry.instance().loadLocalization("/assets/plague/lang/" + lang + ".lang", lang, false);
	}
	
	public static String getLocalization(String key) {
		return(LanguageRegistry.instance().getStringLocalization(key));
	}
	
}
