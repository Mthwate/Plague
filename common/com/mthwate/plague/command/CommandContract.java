package com.mthwate.plague.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

import com.mthwate.plague.Plague;
import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.lib.DiseaseHelper;

public class CommandContract extends CommandBase {

	@Override
	public String getCommandName() {
		return "contract";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "command.contract.usage";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {

		// if one arg, apply to sender
		if (astring.length == 1) {
			String diseaseName = astring[0];
			Disease disease = DiseaseHelper.getDiseaseFromString(diseaseName);
			EntityPlayer senderEntity = getCommandSenderAsPlayer(icommandsender);
			if (disease != null) {
				DiseaseHelper.addDisease(senderEntity, disease);
				icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(senderEntity.getCommandSenderName() + " contractred " + disease.getName().toLowerCase() + "."));
			} else {
				error();
			}
			
		// if 2 args, apply to player with name matching arg
		} else if (astring.length == 2) {
			String diseaseName = astring[0];
			String targetName = astring[1];
			EntityPlayer targetEntity = icommandsender.getEntityWorld().getPlayerEntityByName(targetName);
			if (targetEntity != null) {
				Disease disease = DiseaseHelper.getDiseaseFromString(diseaseName);
				if (disease != null) {
					DiseaseHelper.addDisease(targetEntity, disease);
					icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(targetEntity.getCommandSenderName() + " contractred " + disease.getName().toLowerCase() + "."));
				} else {
					error();
				}
			} else {
				error();
			}
			
		// if 3 args, apply to player with name matching arg
		} else if (astring.length == 3) {
			String diseaseName = astring[0];
			String targetName = astring[1];
			String duration = astring[2];
			EntityPlayer targetEntity = icommandsender.getEntityWorld().getPlayerEntityByName(targetName);
			if (targetEntity != null) {
				Disease disease = DiseaseHelper.getDiseaseFromString(diseaseName);
				if (disease != null) {
					DiseaseHelper.addDisease(targetEntity, disease);
					DiseaseHelper.setDiseaseDuration(targetEntity, disease, Integer.parseInt(duration));
					icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(targetEntity.getCommandSenderName() + " contractred " + disease.getName().toLowerCase() + "."));
				} else {
					error();
				}
			} else {
				error();
			}

			// else fail
		} else {
			error();
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
		if(astring.length == 1) {
			String[] diseases = new String[Plague.diseases.size()];
			for (int i=0; i<Plague.diseases.size(); i++) {
				diseases[i] = Plague.diseases.get(i).getUnlocalizedName().toLowerCase();
			}
			return getListOfStringsMatchingLastWord(astring, diseases);
		} else if(astring.length == 2) {
			return getListOfStringsMatchingLastWord(astring, MinecraftServer.getServer().getAllUsernames());
		}
		return null;
	}
	
	private void error() {
		throw(new WrongUsageException("command.contract.usage", new Object[0]));
	}

}