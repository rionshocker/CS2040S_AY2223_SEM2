/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        if (child == child.LEFT) {
            if (node.left != null) {
                return 1 + countNodes(node.left, child.LEFT) + countNodes(node.left, child.RIGHT);
            } else {
                return 0;
            }
        } else {
            if (node.right != null) {
                return 1 + countNodes(node.right, child.LEFT) + countNodes(node.right, child.RIGHT);
            } else {
                return 0;
            }
        }
    }

    public int enumerateNodesHelper(TreeNode node, TreeNode[] nodeArr, int index) {
        if (node == null) {
            return index;
        } else {
            int mid = enumerateNodesHelper(node.left, nodeArr, index);
            nodeArr[mid++] = node;
            int last = enumerateNodesHelper(node.right, nodeArr, mid);
            return last;
        }
    }

    /**
     * Builds an array of nodes in the specified subtree
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
        int numberOfNodes = countNodes(node, child);
        TreeNode[] nodeArr = new TreeNode[numberOfNodes];

        if (child == child.LEFT) {
            enumerateNodesHelper(node.left, nodeArr, 0);
        } else {
            enumerateNodesHelper(node.right, nodeArr, 0);
        }
        return nodeArr;
    }

    /**
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        int numberOfNodes = nodeList.length;
        
        if (numberOfNodes == 0) {
            return null;
        } else {
            int start = 0;
            int mid = 0 + (numberOfNodes - start) / 2;
            TreeNode[] leftNodeList = new TreeNode[mid];
            TreeNode[] rightNodeList = new TreeNode[numberOfNodes - mid - 1];
            TreeNode root = nodeList[mid];

            for (int i = 0; i < mid; i++) {
                leftNodeList[i] = nodeList[i];
            }
            for (int i = mid + 1; i < numberOfNodes; i++) {
                rightNodeList[i - mid - 1] = nodeList[i];
            }

            root.left = buildTree(leftNodeList);
            root.right = buildTree(rightNodeList);

            return root;
        }

    }

    /**
    * Rebuilds the specified subtree of a node
    * 
    * @param node the part of the subtree to rebuild
    * @param child specifies which child is the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
    * Inserts a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }
    }


    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        for (int i = 0; i < 100; i++) {
            tree.insert(i);
        }
        tree.rebuild(tree.root, Child.RIGHT);
    }
}
