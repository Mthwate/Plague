package com.mthwate.plague.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

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
				}
			} else {
				throw new WrongUsageException("command.contract.usage", new Object[0]);
			}

			// else fail
		} else {
			throw new WrongUsageException("command.contract.usage", new Object[0]);
		}
	}

}