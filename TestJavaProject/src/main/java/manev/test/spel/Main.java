package manev.test.spel;

public class Main {
    // java code addiiton
    static class Node {
        char data;

        boolean isEndOfString;

        Node left, eq, right;

        public Node(char data) {
            this.data = data;
            this.isEndOfString = false;
            this.left = null;
            this.eq = null;
            this.right = null;
        }
    }

    public static Node insert(Node root, String word) {
        if (root == null) {
            root = new Node(word.charAt(0));
        }
        if (word.charAt(0) < root.data) {
            root.left = insert(root.left, word);
        } else if (word.charAt(0) > root.data) {
            root.right = insert(root.right, word);
        } else {
            if (word.length() > 1) {
                root.eq = insert(root.eq, word.substring(1));
            } else {
                root.isEndOfString = true;
            }
        }
        return root;
    }

    public static void traverseTSTUtil(Node root, char[] buffer, int depth) {
        if (root != null) {
            traverseTSTUtil(root.left, buffer, depth);
            buffer[depth] = root.data;
            if (root.isEndOfString) {
                System.out.println(new String(buffer, 0, depth + 1));
            }
            traverseTSTUtil(root.eq, buffer, depth + 1);
            traverseTSTUtil(root.right, buffer, depth);
        }
    }

    public static void traverseTST(Node root) {
        char[] buffer = new char[50];
        traverseTSTUtil(root, buffer, 0);
    }

    public static boolean searchTST(Node root, String word) {
        if (root == null) {
            return false;
        }
        if (word.charAt(0) < root.data) {
            return searchTST(root.left, word);
        } else if (word.charAt(0) > root.data) {
            return searchTST(root.right, word);
        } else {
            if (word.length() > 1) {
                return searchTST(root.eq, word.substring(1));
            } else {
                return root.isEndOfString;
            }
        }
    }

    public static void main(String[] args) {
        Node root = new Node(' ');
        root = insert(root, "cat");
        root = insert(root, "cats");
        root = insert(root, "up");
        root = insert(root, "bug");

        System.out.println("Following is traversal of ternary search tree:");
        traverseTST(root);

        System.out.println("\nFollowing are search results for 'cats', 'bu', and 'up':");
        System.out.println(searchTST(root, "cats") ? "Found" : "Not Found");
        System.out.println(searchTST(root, "bu") ? "Found" : "Not Found");
        System.out.println(searchTST(root, "up") ? "Found" : "Not Found");
    }
}

// The code is contributed by Arushi Goel.
