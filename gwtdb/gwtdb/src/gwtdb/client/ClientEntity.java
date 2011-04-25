package gwtdb.client;

import java.io.Serializable;
import java.util.HashMap;

public class ClientEntity implements Serializable {
	private static final long serialVersionUID = 5558840959621781786L;

	private long id;
	private String kind;
	private HashMap<String, Object> data = new HashMap<String, Object>();

	public ClientEntity() {
	}

	public ClientEntity(final String kind) {
		this.kind = kind;
	}

	public ClientEntity(final String kind, final long id) {
		this.kind = kind;
		this.id = id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	private void setKind(final String kind) {
		this.kind = kind;
	}

	public String getKind() {
		return kind;
	}

	public void setProperty(final String key, final Object value) {
		data.put(key, value);
	}

	public Object get(final String key) {
		return data.get(key);
	}

	public String[] keys() {
		return data.keySet().toArray(new String[0]);
	}

	public static ClientEntity fromUpdateDescription(final String updateDescription) {
		final String entityDescription = updateDescription.substring("update?".length(), updateDescription.length());
		final ClientEntity entity = new ClientEntity();

		for (final String keyValuePair : entityDescription.split("&")) {
			final String[] singlePair = keyValuePair.split("=");
			final String key = singlePair[0];
			final String value = singlePair[1];

			if ("id".equals(key)) {
				entity.setId(Long.parseLong(value));
			} else if ("kind".equals(key)) {
				entity.setKind(value);
			} else {
				entity.setProperty(key, value);
			}
		}

		return entity;
	}

	@Override
	public String toString() {
		// TODO escape characters if necessary
		String s = "";
		s += "id=" + id + "&";
		s += "kind=" + kind + "&";

		for (String k : data.keySet()) {
			s += k + "=" + data.get(k) + "&";
		}

		return s;
	}
}
