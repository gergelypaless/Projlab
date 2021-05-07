package com.csakcintanyer.bme.projlab;

import java.util.HashMap;


public class Explorer extends Character
{
    public Explorer(int ID)
    {
        super(ID);
        maxHealth = health = 4; // 4 testhője van
    }
    
    // az Explorer képessége, hogy meg tudja nézni egy szomszédos block hány karaktert bír el.
    public boolean useAbility()
    {
        if (energy == 0) // ha nincs elég energiája a játékosnak akkor nem sikerül
            return false;
        
        // lekerjuk a selectedBlockot.
        IceBlock selectedBlock = Game.get().getSelectedIceBlock();
        // lekérjük a szomszédait
        HashMap<Direction, IceBlock> neighbours = block.getNeighbours();
        if (neighbours.get(Direction.UP) == selectedBlock ||
            neighbours.get(Direction.DOWN) == selectedBlock ||
            neighbours.get(Direction.LEFT) == selectedBlock ||
            neighbours.get(Direction.RIGHT) == selectedBlock) // ha a selectedBlock melletti van kijelölve
        {
            selectedBlock.setChecked(); // ez az iceblock már meg lett nézve egy explorer által
            
            // a képesség használata egy munka
            changeEnergy(-1);
            return true; // sikeres
        }
        return false;
    }
    
    // explorer kirajzolása
    public void draw(int x, int y)
    {
        View view = View.get();
        // ha nincs vizben akkor más textúra
        if(!isInWater) view.draw(view.explorerIcon, x, y);
        else {
            // ha vizben van akkor InWaterIcon
            view.draw(view.explorerInWaterIcon, x, y+5);
            if(isDrowning()) view.draw(view.drowningIcon, x, y-10); // ha fuldoklik akkor drawingIcon
        }
    
        // ha ez a currently moving character akkor egy nyilacskát is kirajzolunk
        if (Game.get().getCurrentlyMovingCharacter() == this)
            view.draw(view.littleArrow, x + 3, y - 7);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "explorer";
    }
}
