package gwtdb.server;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

abstract public class MemcacheLayer extends RemoteServiceServlet {
	private static final long serialVersionUID = -5073780553052923451L;
	protected static final MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();

	protected static <T> T getFromMemcache(final String key, final T initialValue) {
		if (!memcacheService.contains(key)) {
			memcacheService.put(key, initialValue);
		}
		try {
			return (T) memcacheService.get(key);
		} catch (RuntimeException e) {
			// If memcache still contains an entry of a wrong type a ClassCastException would occur. in this case we just return an empty list.
			return initialValue;
		}
	}

	protected String getGetAllIsValidKey(final String kind) {
		return "getAll_isValid_" + kind;
	}

	protected String getGetAllKey(final String kind) {
		return "getAll_" + kind;
	}
}