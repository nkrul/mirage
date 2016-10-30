package com.kncept.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
	
	Map<K, V> map;
	
	public MapBuilder(Map<K, V> map) {
		this.map = map;
	}
	
	public static<K, V>  MapBuilder<K, V> aMap() {
		return new MapBuilder<>(new HashMap<>());
	}
	
	public MapBuilder<K, V> add(K key, V value) {
		map.put(key, value);
		return this;
	}
	
	public Map<K, V> get() {
		return map;
	}

}
