package coronatree;

public class AVLTree {

    AVLNode root; 	// The tree root.
    int size;		// The size of the tree.
    /**
     * Construct an empty tree.
     */
    public AVLTree() {
       	size = 0;
    }
    
    /**
     * Returns the size of the tree.
     * 
     * @return the size of the tree.
     */
    public int size(){
    	return size;
    }
    
    /**
     * Returns the height of the tree.
     * Returns -1 if the tree is empty.
     * 
     * @return the height of the tree.
     */
    public int height(){
    	if(root==null){
    	    return -1;
        }
        return root.height;
    }

    /**
     * Inserts into the tree; You may assume that every person has a unique ID number.
     * That is, no person will appear twice.
     * 
     * @param p - the person to insert.
     */
    public void insert(Person p) {
        root = insert(root, p);
        size++;
    }
    private static AVLNode insert(AVLNode subTreeRoot,Person p) {
        if (subTreeRoot == null) {
            subTreeRoot = new AVLNode(p);
            return subTreeRoot;
        }
        AVLNode child;
        if(p.id > subTreeRoot.data.id) {
            child= insert(subTreeRoot.right, p);
            subTreeRoot.right = child;
        }
        else{
            child= insert(subTreeRoot.left, p);
            subTreeRoot.left = child;
        }
        child.parent = subTreeRoot;
        subTreeRoot.updateHeight();
        int balance = subTreeRoot.getBalance();
        if (balance > 1 && p.id < subTreeRoot.left.data.id) {
            return rightRotate(subTreeRoot);
        }
        if (balance < -1 && p.id > subTreeRoot.right.data.id){
            return leftRotate(subTreeRoot);
        }
        if (balance > 1 && p.id > subTreeRoot.left.data.id){
            leftRotate(subTreeRoot.left);
            return rightRotate(subTreeRoot);
        }
        if(balance < -1 && p.id < subTreeRoot.right.data.id) {
            rightRotate(subTreeRoot.right);
            return leftRotate(subTreeRoot);
        }
        return subTreeRoot;
    }

    private static AVLNode leftRotate(AVLNode q){
        AVLNode p= q.right;
        q.right = p.left;
        p.left = q;
        q.updateHeight();
        p.updateHeight();
        updateRootParent(q,p);
        return p;
    }

    private static AVLNode rightRotate(AVLNode q){
        AVLNode p= q.left;
        q.left = p.right;
        p.right = q;
        q.updateHeight();
        p.updateHeight();
        updateRootParent(q,p);
        return p;
    }

    private static void updateRootParent(AVLNode oldRoot,AVLNode newRoot){
        AVLNode s= oldRoot.parent;
        oldRoot.parent = newRoot;
        newRoot.parent = s;
        if(s != null){
            if(s.left==oldRoot){
                s.left =newRoot;
            }
            else{
                s.right=newRoot;
            }
            s.updateHeight();
        }
    }
    /**
     * Search for a person in the tree.
     * 
     * @param p the person to search for.
     * @return true iff 'p' is an element in the tree.
     */
    public boolean search(Person p) {
        AVLNode currentNode= root;
        while (currentNode !=null){
            if(currentNode.data.id == p.id){
                return true;
            }
            if(p.id > currentNode.data.id){
                currentNode = currentNode.right;
            }
            else {
                currentNode = currentNode.left;
            }
        }
        return false;
    }
    /**
     * Traverse the contents of this tree in an 'inorder' manner and return and array
     * containing the traversal of the tree.
     * 
     * @return a sorted array of the tree's content.
     */
    public Person[] inorder(){
        Person[] persons = new Person[size];
        inorder(root,persons,0);
        return persons;
    }

    private static int inorder(AVLNode root,Person[] persons,int pos){
        if(root == null)return pos;
        pos = inorder(root.left,persons,pos);
        persons[pos++] = root.data;
        pos = inorder(root.right,persons,pos);
        return pos;
    }
}
