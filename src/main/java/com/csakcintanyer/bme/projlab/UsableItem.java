package com.csakcintanyer.bme.projlab;

import java.io.Serializable;

// az használható Item-eknek van Use függvényük is, erre van a UsableItem
public interface UsableItem
{
	// item használata
	boolean use(IceBlock block);
}
