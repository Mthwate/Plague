package com.mthwate.plague;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.gson.GsonDiseases;
import com.mthwate.plague.lib.DiseaseHelper;

public class EventHooksPlague {
	
	List<EntityPlayer> playersToCheck = new ArrayList<EntityPlayer>();;

	@ForgeSubscribe
	// called when an entity is attacked
	public void onEntityAttack(LivingAttackEvent event) {
		for (Disease disease : Plague.diseases) {
			disease.entityAttack(event);
		}
		
		if (event.entityLiving instanceof EntityPlayer) {
			DiseaseHelper.reduceDamage(event.entityLiving);
		}
	}

	@ForgeSubscribe
	// called when an entity spawns
	public void onEntityDeath(LivingDeathEvent event) {
		for (Disease disease : Plague.diseases) {
			disease.entityDeath(event);
		}
		
		if ((!event.source.equals(DamageSourcePlague.disease) || !DiseaseHelper.wasDamaged(event.entityLiving)) && event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			
			GsonDiseases gsonDiseases = new GsonDiseases();
			gsonDiseases.diseases = new HashMap<String, Integer>();
			
			for (Disease disease : DiseaseHelper.getActiveDiseases(player)) {
				gsonDiseases.diseases.put(disease.getUnlocalizedName(), DiseaseHelper.getDiseaseDuration(player, disease));
			}
			
			File worldDir = ((SaveHandler) player.worldObj.getSaveHandler()).getWorldDirectory();
			File plagueDir = new File(worldDir.getAbsolutePath() + "/plague");
			if (!plagueDir.exists()) {
				plagueDir.mkdir();
			}
			writeGson(plagueDir.getAbsolutePath() + "/" + player.getEntityName() + ".json", gsonDiseases);
		}
	}

	@ForgeSubscribe
	// called when an entity spawns
	public void onEntitySpawn(LivingSpawnEvent event) {
		for (Disease disease : Plague.diseases) {
			disease.entitySpawn(event);
		}
	}

	@ForgeSubscribe
	// called when an entity updates (every tick)
	public void onEntityUpdate(LivingUpdateEvent event) {
		for (Disease disease : Plague.diseases) {
			disease.entityUpdate(event);
		}

		// increases the duration of all active diseases
		DiseaseHelper.count(event.entity);
		
		if (event.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.entity;
			if (playersToCheck.contains(player)) {
				File worldDir = ((SaveHandler) player.mcServer.worldServerForDimension(0).getSaveHandler()).getWorldDirectory();
				File jsonFile = new File(worldDir.getAbsolutePath() + "/plague/" + player.getEntityName() + ".json");
				if (jsonFile.exists()) {
					GsonDiseases gsonDiseases = readGson(jsonFile.getAbsolutePath());
					for (Entry<String, Integer> entry : gsonDiseases.diseases.entrySet()) {
						Disease disease = DiseaseHelper.getDiseaseFromString(entry.getKey());
						DiseaseHelper.addDisease(player, disease);
						DiseaseHelper.setDiseaseDuration(player, disease, entry.getValue());
					}
					jsonFile.delete();
				}
				playersToCheck.remove(player);
			}
		}
	}

	@ForgeSubscribe
	// called when an entity spawns
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.entity;
			File worldDir = ((SaveHandler) player.mcServer.worldServerForDimension(0).getSaveHandler()).getWorldDirectory();
			File jsonFile = new File(worldDir.getAbsolutePath() + "/plague/" + player.getEntityName() + ".json");
			if (jsonFile.exists()) {
				playersToCheck.add(player);
			}
		}
	}

	private void writeGson(String file, GsonDiseases gsonDiseases) {
		try {
			Writer writer = new FileWriter(new File(file));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(gsonDiseases, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private GsonDiseases readGson(String file) {
		try {
			Reader reader = new FileReader(new File(file));
			Gson gson = new GsonBuilder().create();
			GsonDiseases gsonDiseases = gson.fromJson(reader, GsonDiseases.class);
			reader.close();
			return gsonDiseases;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
