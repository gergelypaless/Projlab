package com.csakcintanyer.bme.projlab;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Rope extends CollectableItem implements UsableItem
{
    public Rope(IceBlock block)
    {
        this.block = block;
    }
    
    // Item használata
    public boolean use(IceBlock savingTo)
    {
        IceBlock savingFrom = Game.get().getSelectedIceBlock();
        HashMap<Direction, IceBlock> neighbours = block.getNeighbours();
        if (neighbours.get(Direction.UP) == savingFrom ||
                neighbours.get(Direction.DOWN) == savingFrom ||
                neighbours.get(Direction.LEFT) == savingFrom ||
                neighbours.get(Direction.RIGHT) == savingFrom)
        {
            // lekérjük az IceBlockról a karaktereket
            ArrayList<Entity> characters = savingFrom.getEntities();
            
            if (characters.isEmpty())
                throw new IllegalArgumentException("Cannot use rope");
            // kiválasztunk egyet, mondjuk a 0. helyen lévő karaktert
            Entity characterInTrouble = characters.get(0);
            
            // mozgatjuk a karaktert
            savingFrom.remove(characterInTrouble);
            savingTo.accept(characterInTrouble);
            
            // beállítjuk a karakternek az új helyét
            characterInTrouble.setIceBlock(savingTo);
    
            // meghívjhuk a save függvényt mivel a karakter megmenekült/nem fulladt meg
            characterInTrouble.save();
        }
        else
        {
            throw new IllegalArgumentException("Cannot use rope");
        }
        return false; // a kötelet nem kell törölni használat után
    }
    
    // rope kirajzolása
    public void draw(int x, int y)
    {
        View view = View.get();
        view.draw(view.ropeIcon, x, y);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "rope";
    }

    public void interactWithCharacter(Character c)
    {
        c.addItem(this); // hizzáadjuk az inventoryhoz mivel UsableItem
    }
    
    private IceBlock block;
}
