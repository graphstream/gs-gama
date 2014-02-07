package org.graphstream.gama.extension.receiver;

import java.util.HashMap;
import java.util.Map;

import msi.gama.util.GamaList;


/**
 * Helper class.
 *
 * Stocks list of values for each attribute. Provides methods to add a value to
 * a list and to retrieve a list from attribute name. Handles the conversion
 * from NetStream types to NetLogo types.
 * 
 *  This class is almost a copy of the one from gs-netlogo but updated to run with Gama
 *  
 * @author Stefan Balev, modified by Thibaut Démare
 *
 */
public class Attributes {
	Map<String, GamaList> map;

	public Attributes() {
		map = new HashMap<String, GamaList>();
	}

	public GamaList get(String attribute) {
		return map.remove(attribute);
	}

	public void add(String attribute, Object value) {
		Object gamaValue = netStreamToGama(value);
		if (gamaValue == null)
			return;
		GamaList list = map.get(attribute);
		if (list == null) {
			list = new GamaList();
			map.put(attribute, list);
		}
		list.add(gamaValue);
	}

	protected static Object netStreamToGama(Object o) {
		Object result = simpleNetStreamToGama(o);
		if (result != null)
			return result;
		if (!o.getClass().isArray())
			return null;
		GamaList list = new GamaList();
		for (Object element : (Object[]) o) {
			Object gamaElement = simpleNetStreamToGama(element);
			if (gamaElement == null)
				return null;
			list.add(gamaElement);
		}
		return list;
	}

	protected static Object simpleNetStreamToGama(Object o) {
		if (o instanceof Boolean || o instanceof String || o instanceof Double)
			return o;
		if (o instanceof Number) {
			return new Double(((Number) o).doubleValue());
		}
		return null;
	}
}