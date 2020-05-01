package com.csakcintanyer.bme.projlab;

// az használható Item-eknek van Use függvényük is, erre van a UsableItem
public interface UsableItem
{
	// item használata
	boolean use(IceBlock block);
}
