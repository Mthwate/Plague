package com.mthwate.plague.command;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;

import com.mthwate.plague.disease.Disease;
import com.mthwate.plague.lib.DiseaseHelper;

public class CommandListd extends CommandBase {

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "command.listd.usage";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] params) {
		EntityPlayer targetEntity = null;
		
		//if there are no parameters, target the sender
		if (params.length == 0) {
			targetEntity = getCommandSenderAsPlayer(icommandsender);

		//if there is one parameter, target the specified player
		} else if (params.length == 1) {
			targetEntity = icommandsender.getEntityWorld().getPlayerEntityByName(params[0]);
		}

		if (targetEntity != null) {
			List<Disease> diseases = DiseaseHelper.getActiveDiseases(targetEntity);
			
			if (diseases.size() > 0) {
				String allDiseaseNames = "";
				
				for (Disease disease : diseases) {
					allDiseaseNames += ", " + disease.getName();
				}
				
				//removes the initial ", "
				allDiseaseNames = allDiseaseNames.substring(2);
				
				icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(targetEntity.getDisplayName() + " has " + allDiseaseNames + "."));
				
			} else {
				icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(targetEntity.getDisplayName() + " has no diseases."));
			}
		} else {
			this.error();
		}
	}

	@Override
	public List<?> addTabCompletionOptions(ICommandSender icommandsender, String[] params) {
		return null;
	}
	
	private void error() {
		throw(new WrongUsageException(getCommandUsage(null), new Object[0]));
	}

}
