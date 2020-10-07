package coronatree;

public class AVLNode {	
	Person data;		// The data in the node
	AVLNode parent;		// The parent
	AVLNode left;       // Left child
	AVLNode right;      // Right child
	int height;       	// Height

	/**
	 * A standard constructor, sets all pointers to null.
	 * 
	 * @param p - the data of the node.
	 */
	AVLNode(Person p) {
         this(p,null,null,null);
    }
    
    /**
     * A standard constructor
     * 
     * @param p - the data of the node.
     * @param lt - the left child.
     * @param rt - the right child.
     * @param parent - the parent.
     */
    AVLNode(Person p, AVLNode lt, AVLNode rt, AVLNode parent){
    	this.data= p;
		this.parent = parent;
		this.left = lt;
		this.right = rt;
		updateHeight();
    }
	protected void updateHeight(){
		int lh = -1, rh = -1;
		if (left != null) {
			lh = left.height;
		}
		if (right != null) {
			rh = right.height;
		}
    	height = Math.max(rh, lh) + 1;
	}

    protected int getBalance(){
    	int lh=-1,rh=-1;
    	if(left!=null){
    		lh=left.height;
		}
		if(right!=null){
			rh=right.height;
		}
    	return lh-rh;
	}

    public String toString(){
    	return this.data.toString();
    }
}
