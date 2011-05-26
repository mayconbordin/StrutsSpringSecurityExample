package com.strutstool.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author maycon
 */
public class DataCollection<K, V> extends HashMap<K, V>{

    public List<V> listValues() {
        List<V> list = new ArrayList();

        for (Map.Entry<K, V> object : this.entrySet()) {
            V value = object.getValue();
            list.add(value);
        }

        return list;
    }

    public List<K> listKeys() {
        List<K> list = new ArrayList();

        for (Map.Entry<K, V> object : this.entrySet()) {
            K key = object.getKey();
            list.add(key);
        }

        return list;
    }

    public V getValue(K key) {
        if (key instanceof String) {
            key = (K) ((String)key).toLowerCase();
        }

        for (Map.Entry<K, V> object : this.entrySet()) {
            K index = object.getKey();
            V value = object.getValue();

            if (index.equals(key)) {
                return value;
            }
        }

        return null;
    }

    public boolean isValid(K key) {
        if (key instanceof String) {
            key = (K) ((String)key).toLowerCase();
        }

        for (Map.Entry<K, V> object : this.entrySet()) {
            K index = object.getKey();

            if (index.equals(key)) {
                return true;
            }
        }

        return false;
    }
}
