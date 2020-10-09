/**
 * Interface implementation for the graph structure
 * @author Hristo Georgiev - 1c3r00t
 * @param <Item>
 */
public interface Graph<Item> {
    int getIndex(Item item);
    Item getItem(int index);
    Bag<Integer>[] getEdges();
    int getCountVertices();
}
