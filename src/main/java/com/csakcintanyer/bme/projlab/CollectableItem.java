package com.csakcintanyer.bme.projlab;

import java.io.Serializable;

/**
 * @author zsoko
 *A CollectableItem-eket nem lehet "felvenni" az inventoryba, ezek egyből felhasználódnak
 */

public abstract class CollectableItem implements Serializable, Drawable
{
    public CollectableItem(IceBlock block)
    {
        this.block = block;
    }
    
    // felvettünk egy itemet.
    public abstract void interactWithCharacter(Character c) throws IllegalArgumentException;
    
    // melyik blockon van
    protected IceBlock block;
    
}
