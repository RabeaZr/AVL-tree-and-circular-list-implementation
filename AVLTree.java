// moodle name : rabeeaazreik , ID : 211710124 , name : rabeea azreik
// moodle name : guygavriely , ID : 323832204 , name : guy gavriely
/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */

public class AVLTree {
    //this is the only attribute of this class which is a pointer to the root node
    private IAVLNode root;

    // O(1)
    public AVLTree() // initializes an empty AVLTree
    {
        this.root=null;
    }

    /**
     * public boolean empty()
     *
     * returns true if and only if the tree is empty
     *
     */

    // O(1)
    public boolean empty() {
        if(this.root==null) // if root pointer is null, that means we have no nodes in the tree and therefore its empty
        {
            return true;
        }
        return false; // root!=null => tree is not empty
    }

    /**
     * public String search(int k)
     *
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     */

    // O(log n)
    public String search(int k)
    {	// searches a node with the key k, if such node exits - returns its value, if not - return null
        IAVLNode tmp = root;
        while(tmp!=null)
        {
            if(tmp.getKey()==k) // we found a node with the key k.
            {
                return tmp.getValue(); // returns its value
            }
            else if(tmp.getKey()<k) // keep searching in sub right tree
            {
                tmp = tmp.getRight();
            }
            else // keep searching in left sub tree
            {
                tmp = tmp.getLeft();
            }
        }
        return null;
    }

    /**
     * public int insert(int k, String i)
     *
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
     * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
     * returns -1 if an item with key k already exists in the tree.
     */

    // O(log n)
    public int insert(int k, String i) {
        IAVLNode node = new AVLNode(k,i); // this is now the node we need to insert
        if(this.root==null) // check if the tree is empty
        {
            root = node;
            return 0;
        }
        IAVLNode y = null;
        IAVLNode tmp = this.root;
        while(tmp!=null) // in this loop we check if theres already a node with the key k, if there is, we return -1
        {
            y = tmp;
            if(tmp.getKey()==k) // a node with the key k is already in the tree
            {
                return -1;
            }
            else if(tmp.getKey()<k) // keep searching in right sub tree
            {
                tmp = tmp.getRight();
            }
            else // this searching in left sub tree
            {
                tmp = tmp.getLeft();
            }
        }
        // now y should be the parent of the inserted node
        if(y.getKey()<k) // this means we need to insert x to the right of y
        {
            y.setRight(node);
            node.setParent(y);
        }
        else // this means we need to insert x to the left of y
        {
            y.setLeft(node);
            node.setParent(y);
        }

        tmp = y;
        while(tmp!=null) // we update the size of all the nodes from the inserted parent to the root
        {
            ((AVLNode) (tmp)).setSize(((AVLNode) (tmp)).getSize() + 1); // size of all sub trees on the path to the root is bigger by one because of the insert
            tmp=tmp.getParent();
        }
        int BF; // this is going to keep balance factors of nodes
        int cnt = 0; // this is going to count the amount of rotations we did
        while(y!=null)
        {	// this loop iterates on the path from the parent of the inserted node to the root
            y.setHeight(((AVLNode)(y)).NewHeight()); //update the height of nodes beginning at the parent of the inserted node all the way to the root
            BF = ((AVLNode) (y)).BalanceFactor(); // the balance factor of y

            // in the next conditions we check the balance factor of the node and it's left/right son and then we decide what kind of rotation is needed
            // in addition we update the cnt(counter) for each rotation done
            if (BF <= 1 && BF >= -1)
            {
                y = y.getParent();
            }
            else
            {
                if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y);
                    cnt++;
                }
                else if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==1))
                {
                    this.RotateRight(y.getRight());
                    this.RotateLeft(y);
                    cnt=cnt+2;
                }
                else if((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y.getLeft());
                    this.RotateRight(y);
                    cnt=cnt+2;
                }
                else
                { // this means ((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==1))
                    this.RotateRight(y);
                    cnt++;
                }
            }
        }
        return cnt;	// returns how many rotations were done.
    }

    /**
     * public int delete(int k)
     *
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
     * returns -1 if an item with key k was not found in the tree.
     */

    // O(log n)
    public int delete(int k) {
        if (this.empty()) // if the tree is empty that means that the key k is not in the tree therefore we return -1
        {
            return -1;
        }
        if (this.isLeaf(root)) // we need to take care of this special case, if the tree is of size 1
        {
            if (this.root.getKey() == k) { // if the single key in the tree is k we delete it otherwise we return -1
                this.root = null;
                return 0;
            }
            return -1;
        }
        if (this.root.getRight() == null && this.root.getLeft() != null) {
            if (this.root.getKey() == k) {
                //the case where the root doesn't have a right son and also the root's key is k
                this.root = this.root.getLeft();
                this.root.setParent(null);
                return 0;
            }
        }
        if (this.root.getLeft() == null && this.root.getRight() != null) {
            if (this.root.getKey() == k) {
                // the case where the root doesn't have a left son and also the root's key is k
                this.root = this.root.getRight();
                this.root.setParent(null);
                return 0;
            }
        }
        IAVLNode x = this.root;
        IAVLNode succ;
        while (x != null) { // this loop starts from the root and goes to the place that k is in or the place it should be in
            if (x.getKey() == k) {
                break; // if we found a node with k as a key we break and that means that x is the node with key k
            }
            else if (k < x.getKey()) {
                x = x.getLeft();
            }
            else {
                x = x.getRight();
            }
        }

        if (x == null) { // if this is true that means that the above loop never found a node with key k therefore we return -1
            return -1;
        }

        // now x is the node we want to delete
        IAVLNode y;

        if (this.isLeaf(x)) //case 1 - x is a leaf
        {
            y = x.getParent();
            if (x.getParent() != null) {
                if (x.getParent().getRight() == x) { //if the node we deleted was a right son we update the right son of the parent to null
                    x.getParent().setRight(null);
                }
                if (x.getParent().getLeft() == x) { //if the node we deleted was a left son we update the left son of the parent to null
                    x.getParent().setLeft(null);
                }
            }
            else { // if we are deleting the root
                this.root = null;
            }
        }
        else if ((x.getLeft() != null) && (x.getRight() == null)) //case 2 - the node we are trying to delete has no right son
        {
            y = x.getParent();
            if (x.getParent() != null) {
                if (x.getParent().getRight() == x) { // if x is a right son of it's parent
                    x.getParent().setRight(x.getLeft());
                }
                if (x.getParent().getLeft() == x) { // if x is a left son of it's parent
                    x.getParent().setLeft(x.getLeft());
                }
                x.getLeft().setParent(x.getParent());
            } else {
                this.root = null;
            }

        }
        else if ((x.getLeft() == null) && (x.getRight() != null)) //still case 2 - symmetric to the one above it
        {
            y = x.getParent();
            if (x.getParent() != null) {
                if (x.getParent().getRight() == x) { // if x is a right son of it's parent
                    x.getParent().setRight(x.getRight());
                }
                if (x.getParent().getLeft() == x) { // if x is a left son of it's parent
                    x.getParent().setLeft(x.getRight());
                }
                x.getRight().setParent(x.getParent());
            } else {
                this.root = null;
            }
        }
        else // case 3, x has two children
        {
            // in this case we need to delete the successor physically and replace it with the node we are trying to delete
            succ = Successor(x);
            // there are two cases :
            // 1) the successor is the right child of the node that we want to delete .
            // 2) the successor is in the left subtree of the right child of the node that we want to delete.

            if (succ != x.getRight()) //this is case 2
            {
                y = succ.getParent(); // here the path up to the root will start (the path where we update the size/height and do rotations)
                succ.getParent().setLeft(succ.getRight()); //adjusting pointers in order to delete the successor physically
                if (succ.getRight() != null) // adjusting pointers in case the right child isn't null
                {
                    succ.getRight().setParent(succ.getParent());
                }
                succ.setParent(x.getParent()); // adjusting more pointers in order to put the successor in the place of the node we wanted to delete
                succ.setRight(x.getRight());
                succ.setLeft(x.getLeft());
                succ.getLeft().setParent(succ);
                succ.getRight().setParent(succ);
                if (succ.getParent() != null) { // if the the node we were trying to delete was the root
                    if (succ.getParent().getLeft() == x) { //x was a left child so we set succ to be also a left child
                        succ.getParent().setLeft(succ);
                    }
                    else { // x was a right child so we set succ to be also a right child
                        succ.getParent().setRight(succ);
                    }
                }
                else { // if we try to delete the root of the tree
                    this.root = succ;
                }
                succ.setHeight(x.getHeight()); // we set the height of succ to be the height of x because we put succ in the place of x
                ((AVLNode) (succ)).setSize(((AVLNode) (x)).getSize()); // we set the size of succ to be the size of x because we put succ in the place of x
            }
            else { // case 2 - this means that succ = x.getright
                succ.getParent().setRight(succ.getRight()); // adjusting pointers in order to delete the successor physically
                if (succ.getRight() != null) {
                    succ.getRight().setParent(succ.getParent());
                }
                succ.setLeft(x.getLeft()); // adjusting mor epointers in order to put succ in the place of x
                succ.setRight(x.getRight());
                succ.setParent(x.getParent());
                succ.getLeft().setParent(succ);
                if (succ.getRight() != null) {
                    succ.getRight().setParent(succ);
                }
                if (succ.getParent() != null) {
                    if (succ.getParent().getLeft() == x) { // x was a left child so we set succ to be also a left child
                        succ.getParent().setLeft(succ);
                    }
                    else { // x was a right child so we set succ to be also a right child
                        succ.getParent().setRight(succ);
                    }
                }
                else { // if we try to delete the root of the tree
                    this.root = succ;
                }
                succ.setHeight(x.getHeight()); // we set the height of succ to be the height of x because we put succ in the place of x
                ((AVLNode) (succ)).setSize(((AVLNode) (x)).getSize()); // we set the size of succ to be the size of x because we put succ in the place of x
                y = succ; // because the successor was the right child of x that means that the path that goes up to the root in order to update the
                // height/size and do rotations should start at x succ after we put it in the place of x
            }
        }
        // we deleted x from the tree therefore we set it's pointers to null
        x.setLeft(null);
        x.setRight(null);
        x.setParent(null);
        IAVLNode tmp = y;
        while(tmp!=null) // a loop that goes from y to the root and subtracts 1 from the sizes of the nodes
        {
            ((AVLNode) (tmp)).setSize(((AVLNode) (tmp)).getSize() - 1); // size of all sub trees on the path to the root is smaller by one because of the deletion
            tmp=tmp.getParent();
        }
        int BF;
        int cnt = 0; // a counter of the number of rotations
        while(y!=null) // a loop that goes from y to the root and changes the heights and does rotations if needed
        {
            y.setHeight(((AVLNode)(y)).NewHeight()); // updates the height if needed
            BF = ((AVLNode) (y)).BalanceFactor(); // calculates the balance factor of the node
            if (BF <= 1 && BF >= -1) // a "good" balance factor
            {
                y = y.getParent();
            }
            else // a "bad" balance factor therefore we have 6 cases of rotations
            {
                if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y);
                    cnt++;
                }
                else if ((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==0))
                {
                    this.RotateLeft(y);
                    cnt++;
                }
                else if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==1))
                {
                    this.RotateRight(y.getRight());
                    this.RotateLeft(y);
                    cnt=cnt+2;
                }
                else if((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y.getLeft());
                    this.RotateRight(y);
                    cnt=cnt+2;
                }
                else if ((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==0))
                {
                    this.RotateRight(y);
                    cnt++;
                }
                else {
                    this.RotateRight(y);
                    cnt++;
                }
            }
        }
        return cnt; // returns the number of rotations done
    }

    /**
     * public String min()
     *
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */

    // O(log n)
    public String min() // returns the info of the node in the tree with the minimum key
    {
        if(this.root==null) // if the tree is empty we return null
        {
            return null;
        }
        IAVLNode tmp = this.root;
        while(tmp.getLeft()!=null) // we go all the way to the left to find the min
        {
            tmp = tmp.getLeft();
        }
        return tmp.getValue();
    }

    /**
     * public String max()
     *
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */

    // O(log n)
    public String max() // returns the info of the node in the tree with the maximum key
    {
        if(this.root==null) // if the tree is empty we return null
        {
            return null;
        }
        IAVLNode tmp = this.root;
        while(tmp.getRight()!=null) // we go all the way to the right to find the max
        {
            tmp = tmp.getRight();
        }
        return tmp.getValue();
    }


    /**
     * public int[] keysToArray()
     *
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */

    // O(n)
    public int[] keysToArray()
    {
        if (this.root == null) { // if it's an empty tree we return an empty array
            return new int[0];
        }
        int[] arr = new int[this.size()]; // initializing the array that we will return and it's length is the number of nodes in the tree (size)
        inPlace(arr,this.root,0); // this function puts the right values in the array using recursion
        return arr;
    }

    // a function that keysToArray uses
    public void inPlace(int[] arr,IAVLNode node,int i) {
        // a recursive function that does kind of an in order traversal on the tree and adds the values to the array in the right index
        if (node == null) { // when we get to null we stop
            return;
        }
        inPlace(arr,node.getLeft(),i); // call the recursion on the left subtree
        int s; // a variable that helps us determine the right index that we should add to
        if (node.getLeft() == null) {
            s = 0;
        }
        else {
            s = ((AVLNode) node.getLeft()).getSize();
        }
        arr[s + i] = node.getKey(); // adds the key of the current node to the array
        inPlace(arr,node.getRight(),(s + i + 1)); // calls the recursion on the right subtree
    }

    /**
     * public String[] infoToArray()
     *
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */

    // O(n)
    public String[] infoToArray()
    {
        //this function works in the same way keys to array did
        if (this.root == null) { // if it is an empty tree we return an empty array
            return new String[0];
        }
        String[] arr = new String[this.size()]; // initializing the array that we will return and it's length is the number of nodes in the tree (size)
        inPlace2(arr,this.root,0); // this function puts the right values in the array using recursion
        return arr;
    }

    // a function that infoToArray uses
    public void inPlace2(String[] arr,IAVLNode node,int i) {
        // a recursive function that does kind of an in order traversal on the tree and adds the values to the array in the right index
        if (node == null) { // when we get to null we stop
            return;
        }
        inPlace2(arr,node.getLeft(),i); // a recursive call to the left subtree
        int s; // a variable that helps us determine the right index that we should add to
        if (node.getLeft() == null) {
            s = 0;
        }
        else {
            s = ((AVLNode) node.getLeft()).getSize();
        }
        arr[s + i] = node.getValue(); // adds the info of the current node to the array
        inPlace2(arr,node.getRight(),(s + i + 1)); // a recursive call to the right subtree
    }

    /**
     * public int size()
     *
     * Returns the number of nodes in the tree.
     *
     * precondition: none
     * postcondition: none
     */

    // O(1)
    public int size() // returns the amount of nodes in the tree
    {
        if(this.root==null) // if it's an empty tree then it's size is 0
        {
            return 0;
        }
        return ((AVLNode)this.root).getSize(); // if it's not empty then we just return the size of the root
    }

    /**
     * public int getRoot()
     *
     * Returns the root AVL node, or null if the tree is empty
     *
     * precondition: none
     * postcondition: none
     */

    // O(1)
    public IAVLNode getRoot()
    {	// returns the root of the tree
        return this.root;
    }

    // O(1)
    public void RotateLeft(IAVLNode x)
    {
        //most of the work in this function is just changing pointers
        IAVLNode y = x.getRight();
        x.setRight(y.getLeft());
        if(y.getLeft()!=null) // if y has a left subtree (not null)
        {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent()); // pointers changing
        if(x.getParent()==null) // changes the root to y in case that x's parent is null
        {
            this.root = y;
        }
        else if (x == x.getParent().getLeft()) // if x is a left child
        {
            x.getParent().setLeft(y);
        }
        else // x is a right child
        {
            x.getParent().setRight(y);
        }
        y.setLeft(x); // changing pointers
        x.setParent(y);

        ((AVLNode)(x.getParent())).setSize(((AVLNode)(x)).getSize()); // we update the size of x's parent to be x's size

        if((x.getRight()!=null)&&(x.getLeft()!=null)) // x has both a right subtree and a left subtree
        {
            // changing the size to be the sum of the sizes of the subtrees + 1
            ((AVLNode)(x)).setSize(((AVLNode)(x.getRight())).getSize()+((AVLNode)(x.getLeft())).getSize()+1);
        }
        else if (x.getRight()!=null)
        {
            // changing the size to be the size of the right subtree + 1
            ((AVLNode)(x)).setSize(((AVLNode)(x.getRight())).getSize()+1);

        }
        else if (x.getLeft()!=null)
        {
            // changing the size to be the size of the left subtree + 1
            ((AVLNode)(x)).setSize(((AVLNode)(x.getLeft())).getSize()+1);
        }
        else
        {
            // a leaf
            ((AVLNode)(x)).setSize(1);
        }

        x.setHeight(((AVLNode) (x)).NewHeight()); // update the height
        x.getParent().setHeight(((AVLNode) (x)).NewHeight()); // update the height
    }

    // O(1)
    public void RotateRight(IAVLNode x)
    {
        //this function is symmetric to the one above it
        IAVLNode y = x.getLeft();
        x.setLeft(y.getRight());
        if(y.getRight()!=null) // if y has a right subtree (not null)
        {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent()); //changing pointers
        if(x.getParent()==null) // changes the root to y in case that x's parent is null
        {
            this.root = y;
        }
        else if (x == x.getParent().getRight()) // if x is a right child
        {
            x.getParent().setRight(y);
        }
        else // if x is a left child
        {
            x.getParent().setLeft(y);
        }
        y.setRight(x); // changing pointers
        x.setParent(y);

        ((AVLNode)(x.getParent())).setSize(((AVLNode)(x)).getSize());// we update the size of x's parent to be x's size

        if((x.getLeft()!=null)&&(x.getRight()!=null)) // x has both a right subtree and a left subtree
        {
            // updating the size to the sum of the sizes of the subtrees + 1
            ((AVLNode)(x)).setSize(((AVLNode)(x.getLeft())).getSize()+((AVLNode)(x.getRight())).getSize()+1);
        }
        else if (x.getLeft()!=null)
        {
            // updating the size to be the left subtree's size + 1
            ((AVLNode)(x)).setSize(((AVLNode)(x.getLeft())).getSize()+1);

        }
        else if (x.getRight()!=null)
        {
            // updating the size to be the right subtree's size + 1
            ((AVLNode)(x)).setSize(((AVLNode)(x.getRight())).getSize()+1);
        }
        else
        {
            // a leaf
            ((AVLNode)(x)).setSize(1);
        }
        x.setHeight(((AVLNode) (x)).NewHeight()); // update the height
        x.getParent().setHeight(((AVLNode) (x)).NewHeight());// update the height
    }

    // O(log(n))
    public IAVLNode TreeSelect(int k) // finds the node with k'th smallest key
    { // calls the recursive function TreeSelectRec with the tree's root and k
        // returns the output of the recursive function
        return TreeSelectRec(this.root,k);
    }

    // O(log(n))
    public IAVLNode TreeSelectRec(IAVLNode x, int k)
    {	// recursive function thats used to find the node with k'th smallest key
        int r;
        if(x.getLeft()==null)
        {
            r = 1;
        }
        else
        {
            r =  ((AVLNode) (x.getLeft())).getSize()+1;
        }
        if(k==r) // means we found the requested node
        {
            return x;
        }
        else if (k<r) // this means we need to keep searching in the left sub tree
        {
            return TreeSelectRec(x.getLeft(),k);
        }
        else // this means we need to keep searching at the right sub tree
        {
            return TreeSelectRec(x.getRight(),k-r);
        }
    }

    // O(log n)
    public IAVLNode Successor(IAVLNode x)
    { // returns the Successor of x if it exists, otherwise(x has the maximum key) returns null
        IAVLNode succ = x;
        if(x.getRight()!=null) // if x has a right son we go to it and then we go all the way to the left and this is going to be the successor
        {
            x = x.getRight();
            while(x!=null)
            {
                succ = x;
                x=x.getLeft();
            }
            return succ; // we return the min in the right subtree
        }
        succ = x.getParent();
        while(succ!=null && x == succ.getRight()) // we go up until the first right turn or until we get to null
        {
            x = succ;
            succ = x.getParent();
        }
        return succ;
    }

    // O(log n)
    public IAVLNode Predecessor(IAVLNode x)
    { // returns the Predecessor of x if it exists, otherwise(x has the minimum key) returns null
        IAVLNode pre = x;
        if(x.getLeft()!=null) // if x has a left son we go to it and then we go all the way to the right and this is going to be the predecessor
        {
            x = x.getLeft();
            while(x!=null)
            {
                pre = x;
                x=x.getRight();
            }
            return pre; // we return the max in the left subtree
        }
        pre = x.getParent();
        while(pre!=null && x == pre.getLeft()) // we go up until the first left turn or until we get to null
        {
            x = pre;
            pre = x.getParent();
        }
        return pre;
    }

    // O(1)
    public boolean isLeaf(IAVLNode node) // checks if a given node in a tree is a leaf
    {
        if((node.getRight()==null)&&(node.getLeft()==null)) // if the node doesn't have children then it's a leaf
        {
            return true;
        }
        return false;
    }

    // O(1)
    public int insertFirstToList(int k, String s)
    // this function inserts a node with key = k and info = s to the first index in the TreeList
    {
        IAVLNode node = new AVLNode(k,s); // new node to insert
        this.root = node;
        return 0;
    }

    // O(log n)
    public int insertToList(int i , int k, String s)
    // this function inserts a node with key = k and info = s to the i'th index in the TreeList
    {
        if((i<0)||(i>this.size())) // if the index is illegal then we return -1
        {
            return -1;
        }
        IAVLNode node = new AVLNode(k,s); // new node to insert
        IAVLNode y=this.root;
        if(i==this.size()) // i=n => we need to find the maximum and make node its right child
        {
            while(y.getRight()!=null) // we go all the way to the right to find the max (we know that y in the beginning isn't null since we have insertFirstToList)
            {
                y = y.getRight();
            }
            y.setRight(node);
        }
        else // i<n
        {
            y = this.TreeSelect(i+1); // y is the node of rank i+1
            if(y.getLeft()==null)
            { // if y has no left child, make node its left child
                y.setLeft(node);
            }
            else
            {
                y = this.Predecessor(y);
                y.setRight(node);
            }

        }
        node.setParent(y);

        // now we need to fix the tree
        IAVLNode tmp = y;
        while(tmp!=null) // we update the size of all the nodes from the inserted parent to the root
        {
            ((AVLNode) (tmp)).setSize(((AVLNode) (tmp)).getSize() + 1); // size of all sub trees on the path to the root is bigger by one because of the insert
            tmp=tmp.getParent();
        }
        int BF; // this is going to keep balance factors of nodes
        while(y!=null)
        {	// this loop iterates on the path from the parent of the inserted node to the root
            y.setHeight(((AVLNode)(y)).NewHeight()); //update the height of nodes beginning at the parent of the inserted node all the way to the root
            BF = ((AVLNode) (y)).BalanceFactor(); // the balance factor of y

            // in the next conditions we check the balance factor of the node and it's left/right son and then we decide what kind of rotation is needed
            if (BF <= 1 && BF >= -1)
            {
                y = y.getParent();
            }
            else
            {
                if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y);
                }
                else if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==1))
                {
                    this.RotateRight(y.getRight());
                    this.RotateLeft(y);
                }
                else if((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y.getLeft());
                    this.RotateRight(y);
                }
                else
                { // this means ((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==1))
                    this.RotateRight(y);
                }
            }
        }
        return 0;
    }

    // O(log n)
    public int deleteFromList(int i)
    {
        // this function deletes the node in the i'th index in the TreeList
        IAVLNode x = this.TreeSelect(i+1); // we find the node that we want to delete
        IAVLNode succ;
        IAVLNode y;
        if (this.isLeaf(x)) //case 1 - x is a leaf
        {
            y = x.getParent();
            if (x.getParent() != null) {
                if (x.getParent().getRight() == x) { //if the node we deleted was a right son we update the right son of the parent to null
                    x.getParent().setRight(null);
                }
                if (x.getParent().getLeft() == x) { //if the node we deleted was a left son we update the left son of the parent to null
                    x.getParent().setLeft(null);
                }
            }
            else { // if we are deleting the root
                this.root = null;
            }
        }
        else if ((x.getLeft() != null) && (x.getRight() == null)) //case 2 - the node we are trying to delete has no right son
        {
            y = x.getParent();
            if (x.getParent() != null) {
                if (x.getParent().getRight() == x) { // if x is a right son of it's parent
                    x.getParent().setRight(x.getLeft());
                }
                if (x.getParent().getLeft() == x) { // if x is a left son of it's parent
                    x.getParent().setLeft(x.getLeft());
                }
                x.getLeft().setParent(x.getParent());
            }
            else {
                this.root = x.getLeft();
            }

        }
        else if ((x.getLeft() == null) && (x.getRight() != null)) //still case 2 - symmetric to the one above it
        {
            y = x.getParent();
            if (x.getParent() != null) {
                if (x.getParent().getRight() == x) { // if x is a right son of it's parent
                    x.getParent().setRight(x.getRight());
                }
                if (x.getParent().getLeft() == x) { // if x is a left son of it's parent
                    x.getParent().setLeft(x.getRight());
                }
                x.getRight().setParent(x.getParent());
            }
            else {
                this.root = x.getRight();
            }
        }
        else // case 3, x has two children
        {
            // in this case we need to delete the successor physically and replace it with the node we are trying to delete
            succ = Successor(x);
            // there are two cases :
            // 1) the successor is the right child of the node that we want to delete .
            // 2) the successor is in the left subtree of the right child of the node that we want to delete.

            if (succ != x.getRight()) //this is case 2
            {
                y = succ.getParent(); // here the path up to the root will start (the path where we update the size/height and do rotations)
                succ.getParent().setLeft(succ.getRight()); //adjusting pointers in order to delete the successor physically
                if (succ.getRight() != null) // adjusting pointers in case the right child isn't null
                {
                    succ.getRight().setParent(succ.getParent());
                }
                succ.setParent(x.getParent()); // adjusting more pointers in order to put the successor in the place of the node we wanted to delete
                succ.setRight(x.getRight());
                succ.setLeft(x.getLeft());
                succ.getLeft().setParent(succ);
                succ.getRight().setParent(succ);
                if (succ.getParent() != null) { // if the the node we were trying to delete was the root
                    if (succ.getParent().getLeft() == x) { //x was a left child so we set succ to be also a left child
                        succ.getParent().setLeft(succ);
                    }
                    else { // x was a right child so we set succ to be also a right child
                        succ.getParent().setRight(succ);
                    }
                }
                else { // if we try to delete the root of the tree
                    this.root = succ;
                }
                succ.setHeight(x.getHeight()); // we set the height of succ to be the height of x because we put succ in the place of x
                ((AVLNode) (succ)).setSize(((AVLNode) (x)).getSize()); // we set the size of succ to be the size of x because we put succ in the place of x
            }
            else { // case 2 - this means that succ = x.getright
                succ.getParent().setRight(succ.getRight()); // adjusting pointers in order to delete the successor physically
                if (succ.getRight() != null) {
                    succ.getRight().setParent(succ.getParent());
                }
                succ.setLeft(x.getLeft()); // adjusting mor epointers in order to put succ in the place of x
                succ.setRight(x.getRight());
                succ.setParent(x.getParent());
                succ.getLeft().setParent(succ);
                if (succ.getRight() != null) {
                    succ.getRight().setParent(succ);
                }
                if (succ.getParent() != null) {
                    if (succ.getParent().getLeft() == x) { // x was a left child so we set succ to be also a left child
                        succ.getParent().setLeft(succ);
                    }
                    else { // x was a right child so we set succ to be also a right child
                        succ.getParent().setRight(succ);
                    }
                }
                else { // if we try to delete the root of the tree
                    this.root = succ;
                }
                succ.setHeight(x.getHeight()); // we set the height of succ to be the height of x because we put succ in the place of x
                ((AVLNode) (succ)).setSize(((AVLNode) (x)).getSize()); // we set the size of succ to be the size of x because we put succ in the place of x
                y = succ; // because the successor was the right child of x that means that the path that goes up to the root in order to update the
                // height/size and do rotations should start at x succ after we put it in the place of x
            }
        }

        x.setLeft(null);
        x.setRight(null);
        x.setParent(null);
        IAVLNode tmp = y;
        while(tmp!=null) // a loop that goes from y to the root and subtracts 1 from the sizes of the nodes
        {
            ((AVLNode) (tmp)).setSize(((AVLNode) (tmp)).getSize() - 1); // size of all sub trees on the path to the root is smaller by one because of the deletion
            tmp=tmp.getParent();
        }
        int BF;
        int cnt = 0; // a counter of the number of rotations
        while(y!=null) // a loop that goes from y to the root and changes the heights and does rotations if needed
        {
            y.setHeight(((AVLNode)(y)).NewHeight()); // updates the height if needed
            BF = ((AVLNode) (y)).BalanceFactor(); // calculates the balance factor of the node
            if (BF <= 1 && BF >= -1) // a "good" balance factor
            {
                y = y.getParent();
            }
            else // a "bad" balance factor therefore we have 6 cases of rotations
            {
                if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y);
                    cnt++;
                }
                else if ((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==0))
                {
                    this.RotateLeft(y);
                    cnt++;
                }
                else if((BF == -2)&&(((AVLNode) (y.getRight())).BalanceFactor()==1))
                {
                    this.RotateRight(y.getRight());
                    this.RotateLeft(y);
                    cnt=cnt+2;
                }
                else if((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==-1))
                {
                    this.RotateLeft(y.getLeft());
                    this.RotateRight(y);
                    cnt=cnt+2;
                }
                else if ((BF == 2)&&(((AVLNode) (y.getLeft())).BalanceFactor()==0))
                {
                    this.RotateRight(y);
                    cnt++;
                }
                else {
                    this.RotateRight(y);
                    cnt++;
                }
            }
        }
        return 0;
    }

    /**
     * public interface IAVLNode
     * ! Do not delete or modify this - otherwise all tests will fail !
     */

    public interface IAVLNode{
        public int getKey(); //returns node's key
        public String getValue(); //returns node's value [info]
        public void setLeft(IAVLNode node); //sets left child
        public IAVLNode getLeft(); //returns left child (if there is no left child return null)
        public void setRight(IAVLNode node); //sets right child
        public IAVLNode getRight(); //returns right child (if there is no right child return null)
        public void setParent(IAVLNode node); //sets parent
        public IAVLNode getParent(); //returns the parent (if there is no parent return null)
        public void setHeight(int height); // sets the height of the node
        public int getHeight(); // Returns the height of the node
    }

    /**
     * public class AVLNode
     *
     * If you wish to implement classes other than AVLTree
     * (for example AVLNode), do it in this file, not in
     * another file.
     * This class can and must be modified.
     * (It must implement IAVLNode)
     */

    public class AVLNode implements IAVLNode{
        //these are the attributes of this class
        private int key;
        private String value;
        private IAVLNode parent;
        private IAVLNode right;
        private IAVLNode left;
        private int size;
        private int height;

        // O(1)
        public AVLNode(int key, String value)
        {	// constructor of an AVLNode
            this.key = key;
            this.value = value;
            this.parent = null;
            this.right = null;
            this.left = null;
            this.size = 1;
            this.height = 0;
        }

        // O(1)
        public int getKey() // returns key
        {
            return this.key;
        }

        // O(1)
        public String getValue() // returns value
        {
            return this.value;
        }

        // O(1)
        public void setLeft(IAVLNode node) // sets the left son to be node
        {
            this.left = node;
        }

        // O(1)
        public IAVLNode getLeft()
        {
            return this.left; // returns the left son
        }

        // O(1)
        public void setRight(IAVLNode node) // sets the right son to be node
        {
            this.right = node;
        }

        // O(1)
        public IAVLNode getRight() // returns the right son
        {
            return this.right;
        }

        // O(1)
        public void setParent(IAVLNode node) // sets the parent to be node
        {
            this.parent = node;
        }

        // O(1)
        public IAVLNode getParent() // returns the parent of a node
        {
            return this.parent;
        }

        // O(1)
        public void setHeight(int height) // sets the height of a node
        {
            this.height = height;
        }

        // O(1)
        public int getHeight() // returns the height of a tree
        {
            return this.height;
        }

        // O(1)
        public void setSize(int size)  // sets the size of a node(how many nodes are descendents of a node)
        {
            this.size = size;
        }

        // O(1)
        public int getSize()
        {	 // returns the size of a node(how many nodes are descendents of a node)
            return this.size;
        }

        // O(1)
        public int BalanceFactor()
        {	// computes the balance factor of a node, that is the height of its left son - the height of its right son
            int h1;
            int h2;
            // calculating the height of the left subtree
            if (this.getLeft() == null) {
                h1 = -1;
            }
            else {
                h1 = this.getLeft().getHeight();
            }
            //calculating the height of the right subtree
            if (this.getRight() == null) {
                h2 = -1;
            }
            else {
                h2 = this.getRight().getHeight();
            }
            return h1 - h2; // return the balance factor of the node
        }

        // O(1)
        public int NewHeight()
        {	// computes the new height of a node, that is the maximum height between its right son and its left son + 1
            int h1;
            int h2;
            //calculating the height of the left subtree
            if (this.getLeft() == null) {
                h1 = -1;
            }
            else {
                h1 = this.getLeft().getHeight();
            }
            //calculating the height of the right subtree
            if (this.getRight() == null) {
                h2 = -1;
            }
            else {
                h2 = this.getRight().getHeight();
            }
            return Math.max(h1,h2)+1; //returns the height of the node
        }
    }
}