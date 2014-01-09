package com.mthwate.plague;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.mthwate.plague.packet.Packet;
import com.mthwate.plague.packet.PacketEnergy;
import com.mthwate.plague.packet.PacketParticle;
import com.mthwate.plague.packet.PacketSound;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {

		if (packet.channel.equals("Plague")) {
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			try {
				String type = inputStream.readUTF();
				
				//TODO clean up names and move elsewhere
				List<Class<? extends Packet>> test = new ArrayList<Class<? extends Packet>>();
				
				test.add(PacketSound.class);
				test.add(PacketParticle.class);
				test.add(PacketEnergy.class);
				
				for(Class<? extends Packet> a : test) {
					Packet b = a.newInstance();
					if(b.getName().equals(type)) {
						b.process(inputStream);
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
