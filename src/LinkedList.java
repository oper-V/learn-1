import java.util.*;

public class LinkedList<T> implements List<T> {

    private Item<T> firstInList = null;

    private Item<T> lastInList = null;

    private int size;

    @Override
    public int size() {
        return this.size;
    }



    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        // BEGIN (write your solution here)
        Item <T> forCheck = this.firstInList;
        for (int i = 0; i < size; i++) {
            if(o == null & forCheck.element == null) {
                return true;
//            } else if (o.equals(forCheck.element)) {
//                return true;
//            } else forCheck = forCheck.nextItem;

            } else if (o == null) {
                forCheck = forCheck.nextItem;
                continue;
            } else if (o.equals(forCheck.element)) {
                return true;
            }
            forCheck = forCheck.nextItem;

        }
        return false;


        // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator(0);
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        final Object[] linkedListArray = new Object[this.size()];
        Item <T> elementsForArray = this.firstInList;
        for (int i = 0; i < size; i++) {
            linkedListArray[i] = elementsForArray;
            elementsForArray = elementsForArray.nextItem;
        }
        return linkedListArray;
        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        Item <T> elementsForArray = this.firstInList;
        if (a.length >= size) {

            for (int i = 0; i < size; i++) {
                 a[i] = (T1) elementsForArray.element;
                elementsForArray = elementsForArray.nextItem;
            }
        } else {
//            final T1[] oldArray = a;
//            a = (T1[]) new Object[this.size];
            a = (T1[]) Arrays.copyOf(a, size, a.getClass());
//            System.arraycopy(oldArray, 0, a, 0, size);

            for (int i = 0; i < size; i++) {
                a[i] = (T1) elementsForArray.element;
                if (elementsForArray.equals(lastInList)) {
                    break;
                } else {
                    elementsForArray = elementsForArray.nextItem;
                }
            }
        }
        return a;

        // END
    }

    @Override
    public boolean add(final T newElement) {
         //BEGIN (write your solution here)
        Item<T> newItem;
        if (this.size == 0) {
            newItem = new Item<>(newElement, null, null);
            firstInList = newItem;
        } else {
            newItem = new Item<>(newElement, lastInList, null);
            //firstInList = newItem;
            newItem.prevItem.nextItem = newItem;
        }
        lastInList = newItem;
        size++;
        return true;

        // END
    }

    @Override
    public void add(final int index, final T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(final Object o) {
        // BEGIN (write your solution here)
        if (Objects.equals(o,this.firstInList.element)) {
            Item <T> newFirst = this.firstInList.nextItem;
            if (newFirst == null) {
                this.clear();
            } else {
//            Item <T> newFirst = new Item<>(this.firstInList.nextItem.element,this.firstInList.nextItem.prevItem,this.firstInList.nextItem.nextItem);
                newFirst.prevItem = null;
                this.firstInList.nextItem = null;
                this.firstInList = newFirst;
                this.size--;
            }
        } else if (Objects.equals(o, this.lastInList.element)) {


                Item<T> newLast = this.lastInList.prevItem;
                newLast.nextItem = null;
                this.lastInList.prevItem = null;
                this.lastInList = newLast;
                this.size--;

        } else {

            Item<T> forCheck = this.firstInList;
            for (int i = 0; i < size; i++) {


                if (Objects.equals(o, forCheck.element)) {
                    forCheck.prevItem.nextItem = forCheck.nextItem;
                    forCheck.nextItem.prevItem = forCheck.prevItem;
                    forCheck.nextItem = null;
                    forCheck.prevItem = null;
                    this.size--;
                    return true;
                }
                forCheck = forCheck.nextItem;
            }
        }
        return false;
        // END
    }

    @Override
    public T remove(final int index) throws IndexOutOfBoundsException {
        // BEGIN (write your solution here)
        if (index < 0 || index >= size ) throw new IndexOutOfBoundsException();
        if (index == 0 & size == 1) {
            T oldFirstElement = firstInList.element;
            this.firstInList = null;
            this.lastInList = null;
            size = 0;
            return oldFirstElement;
        }
        if (index == 0 ) {
            Item <T> newFirst = this.firstInList.nextItem;
            T oldFirstElement = firstInList.element;
            newFirst.prevItem = null;
            this.firstInList.nextItem = null;
            this.firstInList = newFirst;
            this.size--;
            return oldFirstElement;
        }

        if (index == size - 1 ) {
            Item <T> newLast = this.lastInList.prevItem;
            T oldLastElement = lastInList.element;
            newLast.nextItem = null;
            this.lastInList.prevItem = null;
            this.lastInList = newLast;
            this.size--;
            return oldLastElement;
        }

        Item <T> elementForRemove = firstInList;
        for (int i = 0; i < index; i++) {
            elementForRemove = elementForRemove.nextItem;
        }
        elementForRemove.prevItem.nextItem = elementForRemove.nextItem;
        elementForRemove.nextItem.prevItem = elementForRemove.prevItem;
        elementForRemove.nextItem = null;
        elementForRemove.prevItem = null;
        this.size--;
        return elementForRemove.element;

        // END
    }

    private void remove(final Item<T> current) {
        // BEGIN (write your solution here)
        if (Objects.equals(current,this.firstInList)) {
            Item <T> newFirst = this.firstInList.nextItem;
            newFirst.prevItem = null;
            this.firstInList.nextItem = null;
            this.firstInList = newFirst;
            this.size--;
        }

        if (Objects.equals(current,this.lastInList)) {
            Item <T> newLast = this.lastInList.prevItem;
            newLast.nextItem = null;
            this.lastInList.prevItem = null;
            this.lastInList = newLast;
            this.size--;
        }
        Item <T> elementForRemove = this.firstInList;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(current,elementForRemove)) {
                elementForRemove.prevItem.nextItem = elementForRemove.nextItem;
                elementForRemove.nextItem.prevItem = elementForRemove.prevItem;
                elementForRemove.nextItem = null;
                elementForRemove.prevItem = null;
                this.size--;
            }

            elementForRemove = elementForRemove.nextItem;

        }



        // END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        this.removeIf(item -> !c.contains(item));
        return true;
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        this.size = 0;
        this.lastInList = null;
        this.firstInList = null;
        // END
    }

    @Override
    public List<T> subList(final int start, final int end) {
        return null;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object o) {
        // BEGIN (write your solution here)
        Item <T> forCheck = this.firstInList;
        for (int i = 0; i < size; i++) {
            if (o == null & forCheck.element == null) {
                return i;
            } else if (o == null) {
                forCheck = forCheck.nextItem;
                continue;
            } else if (o.equals(forCheck.element)) {
                return i;
            }
            forCheck = forCheck.nextItem;
        }
            return -1;
        // END
    }

    @Override
    public T set(final int index, final T element) {
        // BEGIN (write your solution here)
        T elementForReturn = getItemByIndex(index).element;
        getItemByIndex(index).element = element;
        return elementForReturn;
        // END
    }

    @Override
    public T get(final int index) {
        // BEGIN (write your solution here)
        return getItemByIndex(index).element;
        // END
    }

    private Item<T> getItemByIndex(final int index) {
        // BEGIN (write your solution here) An auxiliary method for searching for node by index.
        if (index < 0 || index > LinkedList.this.size()) {
            throw new IndexOutOfBoundsException();
        }
        Item <T> itemForFind = firstInList;
        for (int i = 0; i < index; i++) {
            itemForFind = itemForFind.nextItem;
        }
        return itemForFind;
        // END
    }

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> currentItemInIterator;

        private Item<T> lastReturnedItemFromIterator;

        private int index;

        ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            if (index < 0 || index > LinkedList.this.size()) {
                throw new IndexOutOfBoundsException();
            }
            this.index = index;
            if (index == LinkedList.this.size()) {
                currentItemInIterator = null;
            } else {
                currentItemInIterator = getItemByIndex(index);
            }
//            if (index == 0) {
//
//            }


            // END
        }

        @Override
        public boolean hasNext() {
            // BEGIN (write your solution here)
            return LinkedList.this.size() > index  ;
            // END
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            if (!hasNext()) {
                throw new NoSuchElementException();
            }


//            Item <T> itemForReturn = currentItemInIterator;
            Item <T> itemForReturn = new Item<>(currentItemInIterator.element, currentItemInIterator.prevItem,currentItemInIterator.nextItem);
//            Item <T> itemForReturn = getItemByIndex(index);
            lastReturnedItemFromIterator = currentItemInIterator;
            index++;
            currentItemInIterator = currentItemInIterator.nextItem;
            return itemForReturn.element;

            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            return (index > 0 && index <= LinkedList.this.size());
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            Item <T> itemForReturn = getItemByIndex(--index);
            currentItemInIterator =  lastReturnedItemFromIterator = itemForReturn;
            return itemForReturn.element;
            // END
        }

        @Override
        public void add(final T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if (lastReturnedItemFromIterator == null) {
                throw new IllegalStateException();
            }
            lastReturnedItemFromIterator.element = element;

            // END
        }

        @Override
        public int previousIndex() {
            // BEGIN (write your solution here)
            if (!hasPrevious()) {
                return -1;
            }
            else return index - 1 ;
            // END
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            if (!hasNext()) {
                return LinkedList.this.size();
            }
            //else return lastIndex+1;
            else return index;
            // END
        }

        @Override
        public void remove() {
            // BEGIN (write your solution here)
            if (lastReturnedItemFromIterator == null) {
                throw new IllegalStateException();
            }
            LinkedList.this.remove(lastReturnedItemFromIterator.element);
            lastReturnedItemFromIterator = null;
            index--;


            // END
        }
    }

    private static class Item<T> {

        private T element;

        private Item<T> nextItem;

        private Item<T> prevItem;

        Item(final T element, final Item<T> prevItem, final Item<T> nextItem) {
            this.element = element;
            this.nextItem = nextItem;
            this.prevItem = prevItem;
        }

        public Item<T> getNextItem() {
            return nextItem;
        }

        public Item<T> getPrevItem() {
            return prevItem;
        }
    }
}
