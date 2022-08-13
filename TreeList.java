// moodle name : rabeeaazreik , ID : 211710124 , name : rabeea azreik
// moodle name : guygavriely , ID : 323832204 , name : guy gavriely
/**
 *
 * Tree list
 *
 * An implementation of a Tree list with  key and info
 *
 */

public class TreeList{
    // this is the only attribute of this class
    private AVLTree avl;

    public AVLTree getAVL() // a function that return the avl attribute
    {
        return this.avl;
    }

    /**
     * public Item retrieve(int i)
     *
     * returns the item in the ith position if it exists in the list.
     * otherwise, returns null
     */

    // O(log n)
    public Item retrieve(int i) // returns the item on the i'th index of the list
    {	// calls the function tree select, the item on index i is in the node that has the i+1 smallest key
        // we make an item with the key and value of the i+1'th smallest node(smallest by key) and we return this item
        Item item = new Item(this.avl.TreeSelect(i+1).getKey(),this.avl.TreeSelect(i+1).getValue());
        return item;
    }

    /**
     * public int insert(int i, int k, String s)
     *
     * inserts an item to the ith position in list  with key k and  info s.
     * returns -1 if i<0 or i>n otherwise return 0.
     */

    // O(log n)
    public int insert(int i, int k, String s) // this function inserts a node with key = k and info = s to our treelist
    {
        if(this.avl==null)
        {
            if(i!=0) // if the tree is empty and i!=0 then we return -1 because we are getting an illegal index
            {
                return -1;
            }
            //if we got here that mean that the tree is null and the index is 0 therefore we create a tree and we insert the node to it's first index
            this.avl = new AVLTree();
            this.avl.insertFirstToList(k, s);
            return 0;
        }
        // if we got here that means the tree isn't null and we just call the function that actually inserts (we check if i is legal in the other function)
        return this.avl.insertToList(i, k, s);
    }

    /**
     * public int delete(int i)
     *
     * deletes an item in the ith posittion from the list.
     * returns -1 if i<0 or i>n-1 otherwise returns 0.
     */

    // O(log n)
    public int delete(int i)
    {
        // if the index is illegal we return -1
        if (i < 0 || i > this.avl.size() - 1)
        {
            return -1;
        }
        // if we got here that means that the index is legal and we call the function that actually deletes
        this.avl.deleteFromList(i);
        return 0;
    }
}