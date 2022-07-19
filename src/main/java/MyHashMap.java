import java.util.Collection;

public interface MyHashMap<K, V> {
    V put(K key, V value);

    V get(K key);

    V remove(K key);

    boolean remove(K key, V value);

    int size();
}
