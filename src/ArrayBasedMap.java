import java.util.*;

public class ArrayBasedMap<K, V> implements Map<K, V> {

    private List<Pair> KeyAndValues = new ArrayList<>();

    @Override
    public int size() {
        // BEGIN (write your solution here)
//        int size = 0;
//        for (Pair p : KeyAndValues) size++;
//        return size;
        return KeyAndValues.size();

        // END
    }

    @Override
    public boolean isEmpty() {
        // BEGIN (write your solution here)
        return KeyAndValues.size() == 0;

        // END
    }

    @Override
    public boolean containsKey(Object key) {
        // BEGIN (write your solution here)
        K forTestKey = (K) key;
        for (Pair p : KeyAndValues) {
            if(forTestKey==null ? p.key==null : p.key.equals(forTestKey)) return true;
        }

        return false;
        // END
    }

    @Override
    public boolean containsValue(Object value) {
        // BEGIN (write your solution here)
        V forTestValue = (V) value;
        for (Pair p : KeyAndValues) {
            if(forTestValue==null ? p.value==null : p.value.equals(forTestValue)) return true;
        }

        return false;

        // END
    }

    @Override
    public V get(Object key) {
        // BEGIN (write your solution here)
        K forTestKey = (K) key;
        for (Pair p : KeyAndValues) {
            if(forTestKey==null ? p.key==null : p.key.equals(forTestKey)) {
                return p.getValue();
            }
        }
        return null;

        // END
    }

    @Override
    public V put(K key, V value) {
        // BEGIN (write your solution here)
        K forTestKey = (K) key;
        if(this.containsKey(forTestKey)) {
            for (Pair p : KeyAndValues) {
                if(forTestKey==null ? p.key==null : p.key.equals(forTestKey)) return p.setValue(value);
            }

        } else {
            Pair newPair = new Pair(key, value);
            KeyAndValues.add(newPair);
        }
        return  null;

        // END
    }

    @Override
    public V remove(Object key) {
        // BEGIN (write your solution here)
        K forTestKey = (K) key;
        if(!this.containsKey(forTestKey)) return null;
        for (Pair p : KeyAndValues) {
            if(forTestKey==null ? p.key==null : p.key.equals(forTestKey)) {
                final V oldValue = p.value;
                KeyAndValues.remove(p);
                return oldValue;
            }
        }
        return null;
        // END
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<K, V> e : (Set<Entry<K, V>>)(Set)m.entrySet())
            put(e.getKey(), e.getValue());
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        KeyAndValues.clear();

        // END
    }

    @Override
    public Set<K> keySet() {
        final Set<K> keys = new HashSet<K>();
        for (Pair p : KeyAndValues) keys.add(p.getKey());
        return keys;
    }

    @Override
    public Collection<V> values() {
        // BEGIN (write your solution here)
//        if(KeyAndValues.isEmpty()) {
//            return null;
//        } else {
//            return (Collection<V>) KeyAndValues.subList(0,KeyAndValues.size()-1);
            Collection<V> collectionForReturn = new ArrayList<>();
            for (Pair p : KeyAndValues) {
                collectionForReturn.add(p.value);
            }
            return collectionForReturn;
//        }

        // END
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return (Set<Entry<K, V>>)(Set)new HashSet<>(KeyAndValues);
    }

    private class Pair implements Entry<K, V> {

        private final K key;

        private V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            final V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            Entry<K, V> pair = (Entry<K, V>) o;


            if (key != null ? !key.equals(pair.getKey()) : pair.getKey() != null) return false;
            return !(value != null ? !value.equals(pair.getValue()) : pair.getValue() != null);

        }

        @Override
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                    (value == null ? 0 : value.hashCode());
        }
    }
}