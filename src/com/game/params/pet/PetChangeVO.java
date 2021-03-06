package com.game.params.pet;

import com.game.params.*;

//宠物切换(工具自动生成，请勿手动修改！）
public class PetChangeVO implements IProtocol {
	public int playerId;//玩家ID
	public int petId;//宠物ID
	public boolean hasMutate;//是否变异过


	public void decode(BufferBuilder bb) {
		this.playerId = bb.getInt();
		this.petId = bb.getInt();
		this.hasMutate = bb.getBoolean();
	}

	public void encode(BufferBuilder bb) {
		bb.putInt(this.playerId);
		bb.putInt(this.petId);
		bb.putBoolean(this.hasMutate);
	}
}
