package com.csakcintanyer.bme.projlab;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/*
Saját AutoResetEvent osztályt alkottunk azzal a céllal,
hogy a játék logikája addig várjon a NextRound hívással,
amíg az adott játékosunk energiája 0-ra nem csökken.
 */

public class AutoResetEvent
{
	private final Semaphore event;
	private final Integer mutex;
	
	public AutoResetEvent(boolean signalled)
	{
		event = new Semaphore(signalled ? 1 : 0);
		mutex = -1;
		
	}

	//Ezzel adunk jelet az AutoResetEvent-nek (true-ra állítjuk).
	public void set()
	{
		synchronized (mutex)
		{
			if (event.availablePermits() == 0)
			{
				event.release();
			}
		}
	}

	//Ezzel állítjuk vissza false-ra az event értékét.
	public void reset()
	{
		event.drainPermits();
	}

	//Itt fog várni az AutoReset-eventünk amíg jelet nem kap.
	public void waitOne() throws InterruptedException
	{
		event.acquire();
	}

	//A waitOne függvény tarthat egy megadott ideig, ha ez letelik Interrupt-ot dob
	public boolean waitOne(int timeout, TimeUnit unit) throws InterruptedException
	{
		return event.tryAcquire(timeout, unit);
	}


	public boolean isSignalled()
	{
		return event.availablePermits() > 0;
	}

	//A waitOne függvény tarthat egy megadott ideig, ha ez letelik Interrupt-ot dob
	public boolean waitOne(int timeout) throws InterruptedException
	{
		return waitOne(timeout, TimeUnit.MILLISECONDS);
	}
}


