package project;

import java.util.Date;
import java.util.logging.*;

public class Main
{
	
	
	public static void main(String[] args)
	{
		setLogLevel(Level.FINE);
		
		Game game = Game.get();
		
	}
	
	private static void setLogLevel(Level level)
	{
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(level);
		for (Handler h : rootLogger.getHandlers())
		{
			h.setLevel(level);
			h.setFormatter(new SimpleFormatter() {
				private static final String format = "[%1$tF %1$tT] [%2$-7s] [%3$s]: %4$s %n";
				
				@Override
				public synchronized String format(LogRecord lr) {
					return String.format(format,
							new Date(lr.getMillis()),
							lr.getLevel().getLocalizedName(),
							lr.getSourceClassName() + "." + lr.getSourceMethodName(),
							lr.getMessage()
					);
				}
			});
		}
	}
	
	
	
}
