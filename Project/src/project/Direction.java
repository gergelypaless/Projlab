package project;

public enum Direction
{
	LEFT,
	RIGHT,
	UP,
	DOWN,
	INVALID;
	
	
	
	public static Direction opposite(Direction d)
	{
		if (d == Direction.LEFT) return Direction.RIGHT;
		if (d == Direction.RIGHT) return Direction.LEFT;
		if (d == Direction.UP) return Direction.DOWN;
		if (d == Direction.DOWN) return Direction.UP;
		return Direction.INVALID;
	}
}
