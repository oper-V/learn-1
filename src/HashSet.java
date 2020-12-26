import java.util.*;
import java.util.function.Consumer;

public class HashSet<T> implements Set<T> {

    static final Boolean EXIST = true;

    final Map<T, Boolean> elements = new HashMap<>();

    @Override
    public int size() {
        // BEGIN (write your solution here)
        return elements.size();

        // END
    }

    @Override
    public boolean isEmpty() {
        // BEGIN (write your solution here)
        return elements.isEmpty();

        // END
    }

    @Override
    public boolean contains(Object o) {
        // BEGIN (write your solution here)
        return elements.containsKey(o);

        // END
    }

    @Override
    public Iterator<T> iterator() {
        // BEGIN (write your solution here)
        return  elements.keySet().iterator();

        // END
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        return elements.keySet().toArray();

        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        return (T1[]) elements.keySet().toArray(a);

        // END
    }

    @Override
    public boolean add(T t) {
        // BEGIN (write your solution here)
        if(this.contains((T) t)) {
            return false;
        } else {
            elements.put(t,EXIST);
            return  true;
        }


        // END
    }

    @Override
    public boolean remove(Object o) {
        // BEGIN (write your solution here)
        if(!this.contains(o)) {
            return false;
        } else {
            return elements.remove(o);
        }

        // END
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // BEGIN (write your solution here)
        for(Object el: c) {
           if(!elements.containsKey(el));
           return false;
        }

        return true;

        // END
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        // BEGIN (write your solution here)
        if(this.containsAll(c)) {
            return false;
        }
        for(T el: c) {
            elements.put(el, EXIST);

        }
        return true;

        // END
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // BEGIN (write your solution here)
        if(!this.containsAll(c)) {
            return false;
        }
        Map<T, Boolean> newElements = new HashMap<>();
        for(Object el: c) {
            if(!elements.containsKey(el));

            newElements.put((T) el, EXIST);
        }
        elements.clear();
        this.addAll(newElements.keySet());
        return true;
        // END
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // BEGIN (write your solution here)
        if(!this.containsAll(c)) {
            return false;
        }
        for(Object el: c) {
            if(!elements.containsKey(el));
                this.remove(el);
        }
        return true;

        // END
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        elements.clear();

        // END
    }

    @Override
    public boolean equals(Object o) {
        // BEGIN (write your solution here)
        if(o == null & elements == null) return  true;
        if(o.getClass() == this.getClass()) {
            HashSet<T> testObject = (HashSet<T>) o;
            if (testObject.size() == this.size() && this.containsAll((Collection<?>) testObject)) return  true;

        }

        return  false;

        // END
    }

    @Override
    public int hashCode() {
        // BEGIN (write your solution here)
        return 0;


        // END
    }
}