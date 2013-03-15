package com.joeysoft.kc868.client.packets.in;

import com.joeysoft.kc868.client.packets.InPacket;
import com.joeysoft.kc868.client.packets.PacketParseException;

public class UnknownInPacket extends InPacket{
	public UnknownInPacket(){
		super();
	}
	
	public UnknownInPacket(char command, String message) throws PacketParseException{
		super(command, message);
	}
	
	@Override
	protected void parseBody() throws PacketParseException {
		
	}

}
