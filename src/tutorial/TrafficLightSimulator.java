package tutorial;

public class TrafficLightSimulator implements Runnable {
	
	private TrafficLightColor tlc;
	private boolean stop = false;
	private boolean changed = false;
	
	TrafficLightSimulator(TrafficLightColor init){
		tlc = init;
	}
	
	TrafficLightSimulator(){
		tlc = TrafficLightColor.RED;
	}
	
	public void run() {
		while(!stop) {
			try {
				switch(tlc) {
					case GREEN:
						Thread.sleep(10000);
						break;
					case YELLOW:
						Thread.sleep(2000);
						break;
					case RED:
						Thread.sleep(12000);
						break;
				}
			} catch (InterruptedException e) {
				System.out.println(e);
			}
			changeColor();
		}
	}
	
	synchronized void changeColor() {
		switch(tlc) {
		case RED:
			tlc = TrafficLightColor.GREEN;
			break;
		case YELLOW:
			tlc = TrafficLightColor.RED;
			break;
		case GREEN:
			tlc = TrafficLightColor.YELLOW;
			break;
		}
		changed = true;
		notify();
	}
	
	synchronized void waitForChange() {
		try {
			while(!changed)
				wait();
			changed = false;
		} catch(InterruptedException e) {
			System.out.println(e);
		}
	}
	
	synchronized TrafficLightColor getColor() {
		return tlc;
	}
	
	synchronized void cancel() {
		stop = true;
	}

}
