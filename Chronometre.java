package project;

public class Chronometre implements Runnable {
	Thread thread;
	int compteur;
	InterfaceGraphique interfaceGraph;
	boolean running = true;
	boolean threadSuspended = false;

	public Chronometre(InterfaceGraphique interfaceG) {
		interfaceGraph = interfaceG;
		compteur = 0;
	}
	
	public int getCompteur() {
		return compteur;
	}
	
	public void start() {
		if (thread == null)
			thread = new Thread(this);
		thread.setPriority(thread.MAX_PRIORITY);
		thread.start();
	}

	public void suspend() {
		threadSuspended = true;
	}

	public void reset() {
		compteur = 0;
	}

	public boolean isSuspended() {
		return threadSuspended;
	}

	public synchronized void resume() {
		threadSuspended = false;
		this.notify();
	}

	public void run() {
		while (running) {
			try {
				thread.sleep(1000);

				if (threadSuspended) {
					synchronized (this) {
						while (threadSuspended)
							wait();
					}
				}
			} catch (java.lang.InterruptedException e) {
			}
			
			int temps = compteur;
			if(running && temps < 999) {
				compteur = temps+1;
				interfaceGraph.repaint();
			}
		}
	}

}
