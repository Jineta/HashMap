public class MyHashMapImplementation<K, V> implements MyHashMap<K, V> {
    private final float LOAD_FACTOR = 0.75f;
    private int capacity = 16;
    private int size;
    private int threshold = (int) (capacity * LOAD_FACTOR);
    private Node<K, V>[] entry = new Node[capacity];

    private class Node<K, V> {
        private int hashcode;
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(int hashcode, K key, V value, Node<K, V> next) {
            this.hashcode = hashcode;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private void addEntry(int hashcode, K key, V value, int index) {
        if (size >= threshold) {
            resize();
        }
        Node<K, V> currentNode = entry[index];
        entry[index] = new Node(hashcode, key, value, currentNode);
        System.out.println("Capacity =" + capacity + " threshold =" + threshold + " Size =" + size + " Index= " + index + " Key = " + key + " hash = " + hashcode + " Add: " + value + " " + entry[index] + " " + entry[index].next);
        size++;
    }

    private void resize() {
        System.out.println("resize started");
        size = 0;
        capacity = capacity * 2;
        threshold = (int) (capacity * LOAD_FACTOR);
        Node<K, V>[] oldEntry = entry;
        Node<K, V>[] newEntry = new Node[capacity];
        entry = newEntry;

        for (int i = 0; i < oldEntry.length; i++) {
            Node<K, V> currentNode = oldEntry[i];
            if (currentNode != null) {
                for (currentNode = oldEntry[i]; currentNode != null; currentNode = currentNode.next) {
                    put(currentNode.key, currentNode.value);
                }
            }
        }
    }

    private int getIndex(K key) {
        int index = getHash(key) & (capacity - 1);
        return index;
    }

    private int getHash(K key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public V get(K key) {
        int index = getIndex(key);
        for (Node<K, V> e = entry[index]; e != null; e = e.next) {
            if ((e.key == null) && (key == null)) {
                return e.value;
            }
            if ((e.key != null) && e.key.equals(key)) {
                return e.value;
            }
        }
        return null;
    }

    public V remove(K key) {
        int index = getIndex(key);
        Node<K, V> prevNode = null;
        for (Node<K, V> currentNode = entry[index]; currentNode != null; currentNode = currentNode.next) {
            if ((currentNode.key == null) && (key == null)) {
                V removedValue = currentNode.value;
                if (prevNode == null) {
                    entry[index] = currentNode.next;
                } else {
                    prevNode.next = currentNode.next;
                }
                size--;
                return removedValue;
            }
            if ((currentNode.key != null) && currentNode.key.equals(key)) {
                V removedValue = currentNode.value;
                if (prevNode == null) {
                    entry[index] = currentNode.next;
                } else {
                    prevNode.next = currentNode.next;
                }
                size--;
                return removedValue;
            }
            prevNode = currentNode;
        }
        return null;
    }

    public boolean remove(K key, V value) {
        int index = getIndex(key);
        Node<K, V> prevNode = null;

        for (Node<K, V> currentNode = entry[index]; currentNode != null; currentNode = currentNode.next) {
            if ((currentNode.key == null) && (key == null)) {
                if ((currentNode.value == null) && (value == null)) {
                    if (prevNode == null) {
                        entry[index] = currentNode.next;
                    } else {
                        prevNode.next = currentNode.next;
                    }
                    size--;
                    return true;
                } else if ((currentNode.value != null)&&currentNode.value.equals(value)) {
                    if (prevNode == null) {
                        entry[index] = currentNode.next;
                    } else {
                        prevNode.next = currentNode.next;
                    }
                    size--;
                    return true;
                }
            }

            if ((currentNode.key != null)&&currentNode.key.equals(key)) {
                if ((currentNode.value == null) && (value == null)) {
                    if (prevNode == null) {
                        entry[index] = currentNode.next;
                    } else {
                        prevNode.next = currentNode.next;
                    }
                    size--;
                    return true;
                } else if ((currentNode.value != null) && currentNode.value.equals(value)) {
                    if (prevNode == null) {
                        entry[index] = currentNode.next;
                    } else {
                        prevNode.next = currentNode.next;
                    }
                    size--;
                    return true;
                }
            }
            prevNode = currentNode;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public V put(K key, V value) {
        int index = getIndex(key);
        for (Node<K, V> e = entry[index]; e != null; e = e.next) {
            if ((e.key == null) && (key == null)) {
                V oldValue = e.value;
                e.value = value;
                System.out.println("Size =" + size + " Index= " + index + " Key = " + e.key + " hash = " + getHash(key) + " Rewrite: " + oldValue + " to " + e.value);
                return oldValue;
            }
            if ((e.key != null) && e.key.equals(key)) {
                V oldValue = e.value;
                e.value = value;
                System.out.println("Size =" + size + " Index= " + index + " Key = " + e.key + " hash = " + getHash(key) + " Rewrite: " + oldValue + " to " + e.value);
                return oldValue;
            }
        }
        addEntry(getHash(key), key, value, index);
        return null;
    }
}
