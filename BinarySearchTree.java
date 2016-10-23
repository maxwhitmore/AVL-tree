/*
 * This is an implementation of an AVL tree (which is a Binary Search Tree that performs
 * rotations upon insertion to automatically balance).
 */

public class BinarySearchTree<E extends Comparable<E>> {
	private E element;
	private BinarySearchTree <E> parentTree;
	private BinarySearchTree <E> leftTree;
	private BinarySearchTree <E> rightTree;
  
	public BinarySearchTree(E data) {
		this.element = data;
		this.parentTree = null;
		this.leftTree = null;
		this.rightTree = null;
	}

	public boolean hasLeft() {
		return this.leftTree != null;		
	}

	public boolean hasRight() {
		return this.rightTree != null;
	}

	public boolean isLeaf() {
		return !hasRight() && !hasLeft();	
	}

	public boolean isEmpty() {
		return element == null;
	}

	public boolean isRoot() {
		return parentTree == null;
	}

	public boolean isLeftChild() {
		if (!isRoot()) {
			return this == parentTree.leftTree;
		} else {
			return false;
		}		
	}

	public boolean isRightChild() {
		if (!isRoot()) {
			return this == parentTree.rightTree;
		} else {
			return false;
		}
	}

	public boolean hasParent() {
		return !(isRoot());		
	}

	public BinarySearchTree<E> findNode(E e) {
		if (isEmpty()) {
			return null;
		} else if (e.compareTo(this.element) == 0) { 
			return this;
		} else if ((e.compareTo(this.element) > 0) && (this.rightTree != null)) {
			return this.rightTree.findNode(e);
		} else if ((e.compareTo(this.element) < 0) && (this.leftTree != null)) {
			return this.leftTree.findNode(e);
		} else {
			return null;
		}
	}

	public BinarySearchTree<E> findMin() {
		if (this.isEmpty()) {
			return null;
		}
		if (this.leftTree == null) { 
			return this;
		} else {
			return this.leftTree.findMin(); 
		}
	}

	public BinarySearchTree<E> findSuccessor() {
		if (this.isEmpty() || (this.rightTree == null)) { 
			return null;
		}
		return this.rightTree.findMin(); 
	}

	public BinarySearchTree<E> addRoot(E e) {
		if (!isEmpty()) {
			throw new IllegalArgumentException("Error: tree is not empty."); 
		}
		this.element = e; 
		return this;		
	}

	public BinarySearchTree<E> insert(E e) {
		int x = this.element.compareTo(e); 
		if (x >= 0) { 
			if (hasLeft()) {
				this.leftTree = this.leftTree.insert(e); 
			} else {
				BinarySearchTree<E> newBST = new BinarySearchTree<E>(e); 
				newBST.parentTree = this; 
				this.leftTree = newBST; 
			}
		} else { 
			if (hasRight()) {
				this.rightTree = this.rightTree.insert(e); 
			} else {
				BinarySearchTree<E> newBST = new BinarySearchTree<E>(e); 
				newBST.parentTree = this; 
				this.rightTree = newBST; 
			}
		}
		this.balance(); 
		return this;
	}

	public BinarySearchTree<E> search(E e) {
		return findNode(e);
	}

	public void removeChild() {
		if (!hasParent()) { 
			return; 
		} 
		if (isLeftChild()) {
			parentTree.leftTree = null; 
		} else {
			parentTree.rightTree = null;
		}
		return;
	}

	public BinarySearchTree<E> delete(E e) {
		if (isEmpty()) { 
			System.out.print("Error: tree is not empty.");
			return this; // simply return
		 }
		int x = this.element.compareTo(e); 
		if (x < 0) { 
			if (!hasRight()) { 
				System.out.println("Error: node does not have vale to delete.");
			} else { 
				this.rightTree = this.rightTree.delete(e);
			}
			return this;
		}
		 if (x > 0) { 
			 if (!hasLeft()) {
				 System.out.println("Error: node does not have vale to delete.");
			 } else { 
				 this.leftTree = this.leftTree.delete(e);
			 }
			 return this;
		 }
		 if (isLeaf()) {
			 if (isRoot()) { 
				 return this;
			 }
			 this.removeChild();
			 return null;
		 }
		 if ((hasLeft()) && (!hasRight())) { 
			 BinarySearchTree<E> newBST = this.leftTree; 
			 this.element = newBST.element; 
			 this.leftTree = this.leftTree.leftTree; 
			 this.rightTree = this.leftTree.rightTree; 
			 return this;
		 }
		 if (((!hasLeft() && hasRight()))) { 
			 BinarySearchTree<E> newBST = this.rightTree; 
			 this.element = newBST.element; 
			 this.leftTree = this.rightTree.leftTree; 
			 this.rightTree = this.rightTree.rightTree; 
			 return this;
		 }
		 BinarySearchTree<E> successor = this.findSuccessor(); 
		 BinarySearchTree<E> newBST = successor.parentTree;
		 if (element.compareTo(newBST.element) == 0) { 
			 this.element = successor.element; 
			 this.rightTree = this.rightTree.rightTree; 
			 return this;
		 } else { 
			 this.element = successor.element; 
			 successor.removeChild();
			 return this;
		 }
	}

	public int size() {
		if (isEmpty()) { 
			return -1;
		}
		int leftHeight;
		int rightHeight;
		if (hasLeft()) {
			leftHeight = this.leftTree.size(); 
		} else {
			leftHeight = 0;
		}
		if (hasRight()) {
			rightHeight = this.rightTree.size(); 
		} else {
			rightHeight = 0;
		}
		return (leftHeight + rightHeight + 1); 
	}

	public int balanceFactor() {
		if ((isEmpty()) || (isLeaf())) { 
			return 0;
		} 
		int leftHeight;
		int rightHeight;
		if (hasLeft()) {
			leftHeight = this.leftTree.height(); 
		} else {
			leftHeight = -1;
		} 
		if (hasRight()) {
			rightHeight = this.rightTree.height(); 
		} else {
			rightHeight = -1;
		}
		return (leftHeight - rightHeight); 
	}
	
	public int height() {
		if (isEmpty()) {
			return -1;
		}
		int leftHeight;
		int rightHeight;
		if (hasLeft()) {
			leftHeight = this.leftTree.height(); 
		} else {
			leftHeight = -1;
		}
		if (hasRight()) {
			rightHeight = this.rightTree.height(); 
		} else {
			rightHeight = -1;
		}
		return Math.max(leftHeight , rightHeight) + 1; 
	}

	public int depth() {
		if (isEmpty()) { 
			return -1;
		}
		if (isRoot()) { 
			return 0;
		}
		return (this.parentTree.depth() + 1); 
	}

	public void balance() {
		int balanceFactor = this.balanceFactor();
		if (balanceFactor >= 2) {
			if (this.leftTree.balanceFactor() < 0) {
				this.leftTree.leftRotation(); 
			}
			this.rightRotation(); 
		}
		if (balanceFactor <= -2) { 
			if (this.rightTree.balanceFactor() > 0) { 
				this.rightTree.rightRotation(); 
			}
			this.leftRotation(); 
		}
	}

	public void rightRotation() {
		BinarySearchTree<E> newBST = this.leftTree; 
		this.leftTree = newBST.rightTree; 
		newBST.rightTree = new BinarySearchTree<E>(this.element); 
		newBST.rightTree.parentTree = newBST; 
		newBST.rightTree.leftTree = this.leftTree; 
		newBST.rightTree.rightTree = this.rightTree; 
		this.leftTree = newBST.leftTree; 
		this.rightTree = newBST.rightTree; 
		this.element = newBST.element; 
	}

	public void leftRotation() {
		BinarySearchTree<E> newBST = this.rightTree; 
		this.rightTree = newBST.leftTree;
		newBST.leftTree = new BinarySearchTree<E>( this.element ); 
		newBST.leftTree.parentTree = newBST; 
		newBST.leftTree.leftTree = this.leftTree; 
		newBST.leftTree.rightTree = this.rightTree; 
		this.leftTree = newBST.leftTree; 
		this.rightTree = newBST.rightTree;  
		this.element = newBST.element; 
	}

	public String postorder() {
		String postOrder = ""; 
		if (isEmpty()) {
			return postOrder;
		}
		if (hasLeft()) { 
			postOrder += this.leftTree.postorder();
		}
		if (hasRight()) { 
			postOrder += this.rightTree.postorder();
		}
		postOrder += this.element.toString(); 
		return postOrder; 
	}
}