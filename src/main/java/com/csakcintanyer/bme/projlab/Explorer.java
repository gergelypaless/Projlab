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
    
        IceBlock selectedBlock = Game.get().getSelectedIceBlock();
        HashMap<Direction, IceBlock> neighbours = block.getNeighbours();
        if (neighbours.get(Direction.UP) == selectedBlock ||
            neighbours.get(Direction.DOWN) == selectedBlock ||
            neighbours.get(Direction.LEFT) == selectedBlock ||
            neighbours.get(Direction.RIGHT) == selectedBlock)
        {
            selectedBlock.setChecked();
            System.out.println("Stability: " + selectedBlock.getStability());
            
            // a képesség használata egy munka
            changeEnergy(-1);
            return true;
        }
        return false;
    }
    
    public void draw(int x, int y)
    {
        View view = View.get();
        if(!isInWater) view.draw(view.explorerIcon, x, y);
        else {
            view.draw(view.explorerInWaterIcon, x, y+5);
            if(isDrowning()) view.draw(view.drowningIcon, x, y-10);
        }
    
        if (Game.get().getCurrentlyMovingCharacter() == this)
            view.draw(view.littleArrow, x + 3, y - 7);
    }
    
    // kiíráshoz kell
    public String toString()
    {
        return "explorer";
    }
}
