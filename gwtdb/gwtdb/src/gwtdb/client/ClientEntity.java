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
	
	public long getId() {
		return id;
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
}
