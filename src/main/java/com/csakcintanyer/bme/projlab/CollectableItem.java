package com.csakcintanyer.bme.projlab;

import java.io.Serializable;

/**
 * @author zsoko
 *A CollectableItem-eket nem lehet "felvenni" az inventoryba, ezek egyből felhasználódnak
 */

public abstract class CollectableItem implements Serializable, Drawable
{
    // felvettünk egy itemet.
    public abstract void interactWithCharacter(Character c) throws IllegalArgumentException;
}
