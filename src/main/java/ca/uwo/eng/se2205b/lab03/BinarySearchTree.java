package ca.uwo.eng.se2205b.lab03;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * Binary Search Tree
 */
@ParametersAreNonnullByDefault
public class BinarySearchTree<E extends Comparable<E>> implements Tree<E> {
    private BinaryNode<E> root;

    /**
     * Internal Node structure used for the BinaryTree
     * @param <E>
     */
    static class BinaryNode<E> implements Tree.Node<E> {

        private E element;	        // The data in the node
        private BinaryNode<E> left;	        // Pointer to the left child
        private BinaryNode<E> right;	        // Pointer to the right child
        private BinaryNode<E> parent;


        BinaryNode(E elem, @Nullable BinaryNode<E> parent) {
            this.element = elem;
            this.parent = parent;
        }

        /**
         * Does it have a left child?
         */
        public boolean hasLeft() {
            return left != null;
        }

        /**
         * Does it have a right child?
         */
        public boolean hasRight() {
            return right != null;
        }

        @Nullable BinaryNode<E> getLeft() {
            return left;
        }

        @Nullable BinaryNode<E> getRight() {
            return right;
        }

        @Nullable BinaryNode<E> getParent() {
            return parent;
        }
        public void setLeft(BinaryNode<E> newLeft)
        {
            left = newLeft;
        }

        public void setRight(BinaryNode<E> newRight)
        {
            right = newRight;
        }

        @Override
        public int size() {
            return 1 + (hasLeft() ? left.size() : 0) + (hasRight() ? right.size() : 0);
        }

        @Override
        public boolean isEmpty() {
            return (element == null);
        }

        @Override
        public int height() {
            if (isLeaf())
                return 0;
            else
                return 1 + Math.max(hasLeft() ? left.height() : 0, hasRight() ? right.height() : 0);
        }

        @Override
        public boolean isProper() {
            if (isEmpty())
                return true;
            if (left.height() != right.height())
                return false;

            return left.isProper() && right.isProper();
        }

        @Override
        public boolean isBalanced() {

            int lh; /* for height of left subtree */

            int rh; /* for height of right subtree */

        /* If tree is empty then return true */
            if (isEmpty())
                return true;

        /* Get the height of left and right sub trees */
            lh = left.height();
            rh = right.height();

            if (Math.abs(lh - rh) <= 1 )
                return true;

        /* If we reach here then tree is not height-balanced */
            return false;

        }

        @Override
        public E getElement() {
            return element;
        }

        @Override
        public Collection<? extends Node<E>> children() {

            Collection<Node<E>> child = new ArrayList();
            child.add(left);
            child.add(right);

            return child;
        }

        @Override
        public boolean isInternal() {
            return hasLeft() || hasRight();
        }

        @Override
        public boolean isLeaf() {
            return !hasLeft() && !hasRight();
        }
    }

    public BinarySearchTree() {

        root = new BinaryNode<E>(null, null);
    }

    @Override
    public int size() {
        int leftNumber = 0;
        int rightNumber = 0;

        if (root.getLeft() != null)
            leftNumber = root.getLeft().size();

        if (root.getRight() != null)
            rightNumber = root.getRight().size();

        return 1 + leftNumber + rightNumber;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int height() {
        return root.height();
    }

    @Override
    public boolean isProper() {
        return root.isProper();
    }

    @Override
    public boolean isBalanced() {
        return root.isBalanced();
    }
    
    @Override
    public Iterator<E> iterator(Traversal how) {
        return new IteratorForBST(how);
    }

    private class IteratorForBST implements Iterator<E> {
        Stack<BinaryNode<E>> s;
        BinaryNode<E> currentNode;
        Traversal traversal;

        private IteratorForBST(Traversal how) {
            traversal = how;
            currentNode = root;
            s = new Stack<BinaryNode<E>>();
        }

        @Override
        public boolean hasNext() {
            if (traversal == Traversal.InOrder || traversal == Traversal.PostOrder)
                return (!s.isEmpty() || (currentNode != null));
            else
                return !s.isEmpty();
        }

        @Override
        public E next() {

            //InOrder
            if (traversal == Traversal.InOrder){

                BinaryNode<E> nextNode = null;

                // Find leftmost node with no left child
                while (currentNode != null)
                {
                    s.push(currentNode);
                    currentNode = currentNode.getLeft();
                } // end while

                // Get leftmost node, then move to its right subtree
                if (!s.isEmpty())
                {
                    nextNode = s.pop();
                    assert nextNode != null; // Since nodeStack was not empty
                    // before the pop
                    currentNode = nextNode.getRight();
                }
                else
                    throw new NoSuchElementException();

                return nextNode.getElement();
            }

            //PreOrder
            if (traversal == Traversal.PreOrder){
                BinaryNode<E> nextNode;

                if (hasNext())
                {
                    nextNode = s.pop();
                    BinaryNode<E> leftChild = nextNode.getLeft();
                    BinaryNode<E> rightChild = nextNode.getRight();

                    // Push into stack in reverse order of recursive calls
                    if (rightChild != null)
                        s.push(rightChild);

                    if (leftChild != null)
                        s.push(leftChild);
                }
                else
                {
                    throw new NoSuchElementException();
                }

                return nextNode.getElement();
            }

            //PostOrder
            else {
                BinaryNode<E> leftChild = null;
                BinaryNode<E> nextNode = null;

                // Find leftmost leaf
                while (currentNode != null)
                {
                    s.push(currentNode);
                    leftChild = currentNode.getLeft();
                    if (leftChild == null)
                        currentNode = currentNode.getRight();
                    else
                        currentNode = leftChild;
                }

                if (!s.isEmpty())
                {
                    nextNode = s.pop();

                    BinaryNode<E> parent = null;
                    if (!s.isEmpty())
                    {
                        parent = s.peek();
                        if (nextNode == parent.getLeft())
                            currentNode = parent.getRight();
                        else
                            currentNode = null;
                    }
                    else
                        currentNode = null;
                }
                else
                {
                    throw new NoSuchElementException();
                }

                return nextNode.getElement();
            }
        }
    }

    @Override
    public boolean contains(E element) {
        BinaryNode<E> current = root;
        int comparison = 0;
        if (root.isEmpty()) {
            return false;
        }

        while (true)  {
            comparison = current.getElement().compareTo(element);
            if (comparison == 0) { // key found
                return true;
            }
            if (comparison == 1) {
                if (current.getLeft() == null) {
                    // Key not found
                    return false;
                }
                current = current.getLeft();
            } else if (comparison == -1) {
                if (current.getRight() == null) {
                    // Key not found
                    return false;
                }
                current = current.getRight();
            }
        }
    }

    @Override
    public boolean put(E element) {

        int comparison = 0;

        if (root.isEmpty()) {
            root = new BinaryNode<E>(element, root);
            return true;
        }

        else {
            BinaryNode<E> currentNode = root;
            while (true) {
                comparison = element.compareTo(root.getElement());
                if (comparison == 0) {
                    throw new UnsupportedOperationException();
                }
                if (comparison == 1) {

                    if (currentNode.hasLeft()) {
                        currentNode = currentNode.getLeft();
                    } else {
                        currentNode.setLeft(new BinaryNode<E>(element, root));
                        return true;
                    }
                } else if (comparison == -1) {

                    if (currentNode.hasRight()) {
                        currentNode = currentNode.getRight();
                    } else {
                        currentNode.setRight(new BinaryNode<E>(element, root));
                        return true;
                    }
                }
            }
        }
    }

    @Override
    public boolean remove(E element) {
        BinaryNode<E> currentNode = root;
        BinaryNode<E> temp = null;
        int comparison = 0, priorComparison = 0;

        if (root == null || root.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        else {
            currentNode = root;
            while (true) {
                comparison = element.compareTo(root.getElement());
                if (comparison == 0) {
                    // key found
                    break;
                }
                priorComparison = comparison;
                // parent = current;
                if (comparison == 1) {
                    if (currentNode.hasLeft()) {
                        currentNode = currentNode.getLeft();
                    }
                    else {
                        throw new UnsupportedOperationException();
                    }
                }
                else if (comparison == -1) {

                    if (currentNode.hasRight()) {
                        currentNode = currentNode.getRight();
                    }
                    else {
                        throw new UnsupportedOperationException();
                    }
                }
            }
            // if i am here that means we have found the node
			/* deleting leaf node */
            if (!currentNode.hasRight()) {
                if (currentNode == root && !currentNode.hasLeft()) {
                    root = new BinaryNode<E>(null, null);
                    return true;
                }
                else if (currentNode == root) {
                    root = currentNode.getLeft();
                    return true;
                }

                if (priorComparison == 1) {
                    currentNode.getParent().setLeft(currentNode.getLeft());
                }
                else if (priorComparison == -1) {
                    currentNode.getParent().setRight(currentNode.getRight());
                }
            }
            else {
				/* delete node with right child */
                temp = currentNode.getRight();
                if (!temp.hasLeft()) {
                    temp.setLeft(currentNode.getLeft());
                    if (currentNode == root) {
                        root = temp;
                        return true;
                    }

                    if (priorComparison == 1)
                        currentNode.getParent().setLeft(temp);
                    else if (priorComparison == -1)
                        currentNode.getParent().setRight(temp);

                }
                else {
					/* delete node with two children */
                    BinaryNode<E> successor = null;
                    while (true) {
                        successor = temp.getLeft();
                        if (!successor.hasLeft())
                            break;
                        temp = successor;
                    }
                    temp.setLeft(successor.getRight());
                    successor.setLeft(currentNode.getLeft());
                    successor.setRight(currentNode.getRight());
                    if (currentNode == root) {
                        root = successor;
                        return true;
                    }
                    if (priorComparison == 1)
                        currentNode.getParent().setLeft(successor);
                    else if (priorComparison == -1)
                        currentNode.getParent().setRight(successor);
                }
            }
        }
        return false;
    }


    @Nullable
    @Override
    public BinaryNode<E> getRoot() {
        return root;
    }


}
