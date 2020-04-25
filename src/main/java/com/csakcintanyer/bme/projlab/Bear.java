package com.csakcintanyer.bme.projlab;

import java.io.Serializable;

public class Bear extends Entity
{
	public boolean move(Direction d)
	{
		IceBlock newBlock = block.getNeighbours().get(d);
		while (newBlock == null)
			newBlock = block.getNeighbours().get(d);
		
		block.remove(this);
		newBlock.accept(this);
		setIceBlock(newBlock);
		
		if (newBlock.getEntities().size() > 1 && !newBlock.hasIgloo()) // there is somebody else on the iceblock and no igloo
			Game.get().lose();
		
		return true;
	}
}
