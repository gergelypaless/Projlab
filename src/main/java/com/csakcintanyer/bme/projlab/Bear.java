package com.csakcintanyer.bme.projlab;

// A medve osztálya
public class Bear extends Entity
{

	// a medve lép egyet a jégmezőn a paraméterként átvett irányba.
	public boolean move(Direction d)
	{
		IceBlock newBlock = block.getNeighbours().get(d);

		//ha az adott irányban nincs jégtábla, akkor a lépés sikertelen
		if (newBlock == null)
			return false;

		// mozgatjuk a medvét az IceBlock accept és remove függvényeivel
		block.remove(this);
		newBlock.accept(this);
		setIceBlock(newBlock);

		/*
		* Itt ellenőrizzük. hogy a medvén kívül van-e valaki(játékos) a jégtáblán és
		* ha igen, akkor van-e ott iglu. Ha van játékos és nincs iglu -> Game over
		* */
		if (newBlock.getEntities().size() > 1 && !newBlock.hasIgloo())
			Game.get().lose();

		return true; //sikeres lépés
	}
	
	public void draw(int x, int y)
	{
		View view = View.get();
		
		int xmod = (x - 20) % 55;
		int ymod = (y - 120) % 55;
		x -= xmod;
		y -= ymod;
		
		view.draw(view.bearIcon, x, y);
	}
	
	// kiiráshoz kell
	public String toString()
	{
		return "bear";
	}
}
