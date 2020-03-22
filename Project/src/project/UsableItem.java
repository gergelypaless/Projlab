package project;

// az használható Item-eknek van Use függvényük is, erre van a UsableItem
public interface UsableItem
{
	// item használata
	void use(IceBlock block);
}
