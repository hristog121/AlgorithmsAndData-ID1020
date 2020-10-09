/**
 * A data structure to hold mapping between Item and an Index. This is backed by a hash table.
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */
import java.util.NoSuchElementException;

// A data structure to hold a mapping between an Item and an index
// Backed by a hash table for Item to index mapping and an index array for index to Item mapping
public class IndexTable<Item> {
    // Holds the total size of the hash table and index array
    private int countOfElements;
    // Holds the current number of items
    private int currentIndex;
    // Holds the hash table for mapping between an Item and an index
    private Bag<ItemIndex<Item>>[] hashTable;
    // Holds the array mapping between an index and an Item
    private Item[] indexTable;

    // Creates a new index table with pre-defined max size
    public IndexTable(int countOfElements) {
        // Initialize the hash table and index table
        this.hashTable = (Bag<ItemIndex<Item>>[]) new Bag[countOfElements];
        this.indexTable = (Item[]) new Object[countOfElements];
        this.countOfElements = countOfElements;
    }

    // Adds a new item to the index table and returns its index
    // Throws an exception if we try to add more elements in the index table than the pre-defined size
    public int add(Item item) {
        // Get the hash of the item
        int hash = hash(item);

        // If the hash is not found in the hash table, add a new Bag in the hash table to hold all the elements with
        // this hash
        if (hashTable[hash] == null) {
            // If there are more elements than the predefined size, throw an exception
            if (currentIndex > countOfElements) {
                throw new IndexOutOfBoundsException();
            }
            hashTable[hash] = new Bag<>();
        }

        // If the hash is found in the hash table, iterate over the items with the same hash in the Bag to find if the
        // item already exists. If so, return the index of the item without adding it again
        for (ItemIndex<Item> next : hashTable[hash]) {
            if (next.item.equals(item)) {
                return next.index;
            }
        }

        // If there are more elements than the pre-defined size of the index table, throw and exception
        if (currentIndex > countOfElements) {
            throw new IndexOutOfBoundsException();
        }

        // If the item does not exist and the count of elements does not exceed the pre-defined size, add the element
        // to the Bag holding the elements with the same hash and add a mapping between the Item and the index in the
        // index table
        hashTable[hash].add(new ItemIndex(item, currentIndex));
        indexTable[currentIndex] = item;
        return currentIndex++;
    }

    // Gets the index of the specified Item in the index table
    // Returns an exception of the item is not present in the index table
    public int getIndex(Item item) {
        // Hash the item
        int hash = hash(item);
        // If the hash table does not contain a Bag for this item, throw an exception
        if (hashTable[hash] == null) {
            throw new NoSuchElementException(String.format("Item %snot found", item));
        }

        // Iterate over the items in the Bag, which holds the same hash and search for the item
        // If the item is found the index is directly returned
        for (ItemIndex<Item> next : hashTable[hash]) {
            if (next.item.equals(item)) {
                return next.index;
            }
        }

        // If we reach here, that means that the item is not in the index table
        throw new NoSuchElementException(String.format("Item %s not found", item));
    }

    // Gets the item that corresponds to the specified index
    // Returns an exception if the specified index does not exist in the index table
    public Item getItem(int index) {
        if (index >= indexTable.length) {
            throw new NoSuchElementException(String.format("No item on index %d", index));
        }
        return indexTable[index];
    }

    // Hashing function that will produce indexes from 0 to the pre-defined size of the hash table
    private int hash(Item item) {
        return (item.hashCode() & 0x7fffffff) % countOfElements;
    }

    // A structure to hold the mapping between an Item and a numeric index
    private static class ItemIndex<Item> {
        private Item item;
        private int index;

        public ItemIndex(Item item, int index) {
            this.item = item;
            this.index = index;
        }
    }
}