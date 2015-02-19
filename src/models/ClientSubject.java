package models;

import java.util.LinkedList;
import java.util.List;

public class ClientSubject implements Subject {

	private List<ClientObserver> clients;
	
	public ClientSubject() {
		clients = new LinkedList<>();
	}
	
	@Override
	public boolean add(Observer o) {
		ClientObserver p = (ClientObserver) o;
		if ( !this.clients.contains(p) ) {
			System.out.println("Adding new client! " + o.toString());
			this.clients.add(p);
			return true;
		}
		return false;
	}

	@Override
	public boolean remove(Observer o) {
		ClientObserver p = (ClientObserver) o;
		return this.clients.remove(p);
	}

	@Override
	public void notifyObservers(Object o) {
		for ( ClientObserver c : this.clients ) {
			System.out.println(clients.size() + "  " + c.toString());
			c.update(o);
		}
	}
}
