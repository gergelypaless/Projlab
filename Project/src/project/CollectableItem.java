package project;

// A CollectableItem-eket nem lehet "felvenni" az inventoryba, ezek egyből felhasználódnak
public interface CollectableItem
{
    void InteractWithCharacter(Character c);
}
