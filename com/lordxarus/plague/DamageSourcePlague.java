package com.lordxarus.plague;

import net.minecraft.util.DamageSource;

public class DamageSourcePlague extends DamageSource {
	
	public static DamageSource disease = new DamageSourcePlague("disease").setDamageBypassesArmor();
	public static DamageSource syringe = new DamageSourcePlague("syringe").setDamageBypassesArmor();
	
	DamageSourcePlague(String name) {
		super(name);
	}
}