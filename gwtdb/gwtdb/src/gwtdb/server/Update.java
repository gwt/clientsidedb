package gwtdb.server;

import gwtdb.client.ClientEntity;

public class Update {
	private long id;
	private ClientEntity entity;

	public Update(final long id, final ClientEntity entity) {
		this.id = id;
		this.entity = entity;
	}

	public long getId() {
		return id;
	}

	public ClientEntity getEntity() {
		return entity;
	}
}
