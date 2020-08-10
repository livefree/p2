package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V>  {
    public static final int LEFT_CHILD_INDEX = 0;
    public static final int RIGHT_CHILD_INDEX = 1;

    public AVLTree() {
        root = null;
        size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // nothing is in the tree yet
        V oldValue = find(key);
        root = insertHelper((AVLNode) root, key, value);
        return oldValue;
    }


    // Takes in an AVLNode, Key and value as parameters
    // traverses the AVL tree and determine which direction we need to traverse
    // based on the given key value
    // inserts a new node into the tree if the key isn't in the tree
    // otherwise replace the old value of the tree
    private AVLNode insertHelper(AVLNode node, K key, V value) {
        if (node == null) { // new key that we never saw before
            size++;
            return new AVLNode(key, value);
        }

        int direction = Integer.signum(key.compareTo(node.key));
        int childNodeIndex;
        if (direction == 0) { // this node's key matches the key we are looking for
            node.value = value;
            return node;
        } else {
            // direction + 1 = {0, 2} -> {0, 1}
            childNodeIndex = Integer.signum(direction + 1);
            AVLNode child = insertHelper((AVLNode) node.children[childNodeIndex], key, value);
            node.children[childNodeIndex] = child;
        }
        updateHeight(node); // update this current node's height
        return balanceTree(node);
    }

    // takes an AVL node as parameter
    // update the height of the given node
    private void updateHeight(AVLNode node) {
        int leftChildHeight = getHeight((AVLNode) node.children[0]);
        int rightChildHeight = getHeight((AVLNode) node.children[1]);
        node.height = Math.max(leftChildHeight, rightChildHeight) + 1;
    }

    // Takes an AVLNode as parameter
    // returns the height of the given node
    private int getHeight(AVLNode node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    // Takes a node as a parameter
    // Balance this node if the tree becomes too heavy on one side
    private AVLNode balanceTree(AVLNode node) {
        AVLNode leftChild = (AVLNode) node.children[LEFT_CHILD_INDEX];
        AVLNode rightChild = (AVLNode) node.children[RIGHT_CHILD_INDEX];
        int heightDiff = calculateHeightDifference(node);
        if (heightDiff < -1) { // left subtree is shorter than right subtree
            int subHeightDiff = calculateHeightDifference(rightChild);
            if (subHeightDiff > 0) { // left, right (kink) case
                node.children[RIGHT_CHILD_INDEX] = rotateRight(rightChild);
            }
            node = rotateLeft(node);
        } else if (heightDiff > 1) {
            int subHeightDiff = calculateHeightDifference(leftChild);
            if (subHeightDiff < 0) { // right, left (kink) case
                node.children[LEFT_CHILD_INDEX] = rotateLeft(leftChild);
            }
            node = rotateRight(node);
        }
        updateHeight(node);
        return node;
    }

    // Takes in an AVLNode as parameter
    // returns the height difference of this node's two children
    private int calculateHeightDifference(AVLNode node) {
        AVLNode leftChild = (AVLNode) node.children[LEFT_CHILD_INDEX];
        AVLNode rightChild = (AVLNode) node.children[RIGHT_CHILD_INDEX];
        return getHeight(leftChild) - getHeight(rightChild);
    }

    /*
    Rotate left example
        1                       2
         \                     / \
          2         ->       1    3
           \
            3
      Takes an AVL node as a parameter
      Rearranges the pointers of the nodes as see above
      Returns the new root of this subtree after rotation
     */
    private AVLNode rotateLeft(AVLNode node) {
        AVLNode rightChild = (AVLNode) node.children[RIGHT_CHILD_INDEX];
        node.children[RIGHT_CHILD_INDEX] = rightChild.children[LEFT_CHILD_INDEX];
        rightChild.children[LEFT_CHILD_INDEX] = node;
        updateHeight(rightChild);
        updateHeight(node);
        return rightChild;
    }

    /*
    Rotate left example
         3                       2
        /                       / \
       2         ->           1    3
       /
      1
      Takes an AVL node as a parameter
      Rearranges the pointers of the nodes as see above
      Returns the new root of this subtree after rotation
     */
    private AVLNode rotateRight(AVLNode node) {
        AVLNode leftChild = (AVLNode) node.children[LEFT_CHILD_INDEX];
        node.children[LEFT_CHILD_INDEX] = leftChild.children[RIGHT_CHILD_INDEX];
        leftChild.children[RIGHT_CHILD_INDEX] = node;
        updateHeight(leftChild);
        updateHeight(node);
        return leftChild;
    }

    public class AVLNode extends BSTNode {
        public int height;
        public AVLNode(K key, V value) {
            super(key, value);
            height = 0;
        }
    }
}
