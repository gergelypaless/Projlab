package com.csakcintanyer.bme.projlab;
import java.io.IOException;
import java.util.ArrayList;

public class Rope extends CollectableItem implements UsableItem
{
    
    public Rope(IceBlock block)
    {
        super(block);
    }
    
    // Item használata
    public boolean use(IceBlock savingTo)
    {
        
        // lekérjük azt az IceBlockot ahonnan ki kell mentenünk valakit
        IceBlock savingFrom = null;
        try {
            savingFrom = savingTo.getNeighbours().get(IOLanguage.GetDirection());
        } catch (IOException e) { e.printStackTrace(); }
        
        // lekérjük az IceBlockról a karaktereket
        ArrayList<Entity> characters = savingFrom.getEntities();
        // kiválasztunk egyet, mondjuk a 0. helyen lévő karaktert
        Entity characterInTrouble = characters.get(0);
        // mozgatjuk a karaktert
        savingFrom.remove(characterInTrouble);
        savingTo.accept(characterInTrouble);
        // beállítjuk a karakternek az új helyét
        characterInTrouble.setIceBlock(savingTo);
    
        // meghívjhuk a save függvényt mivel a karakter megmenekült/nem fulladt meg
        characterInTrouble.save();
        
        return false; // a kötelet nem kell törölni használat után
    }
    
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
}
