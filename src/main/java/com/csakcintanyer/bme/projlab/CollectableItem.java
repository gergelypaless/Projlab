package com.csakcintanyer.bme.projlab;

// A CollectableItem-eket nem lehet "felvenni" az inventoryba, ezek egyből felhasználódnak
public interface CollectableItem
{
    // felvettünk egy itemet.
    void interactWithCharacter(Character c);
}
