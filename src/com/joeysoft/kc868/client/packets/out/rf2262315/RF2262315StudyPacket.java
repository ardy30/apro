package com.joeysoft.kc868.client.packets.out.rf2262315;

import com.joeysoft.kc868.client.packets.OutPacket;
import com.joeysoft.kc868.client.support.Protocol;
import com.joeysoft.kc868.common.GlobalConst;

public class RF2262315StudyPacket extends OutPacket{

	/** 无线信号 */
	private int sensorId;
	
	public RF2262315StudyPacket(int sensorId) {
		super(Protocol.CMD_RFSTUY_315M_STUDY, Protocol.MSG_RFSTUY_315M_STUDY);
		this.sensorId = sensorId;
	}

	@Override
	protected void putBody() {
		sbMessage.append(sensorId);
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}
}
