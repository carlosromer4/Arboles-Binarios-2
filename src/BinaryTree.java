import java.util.*;

public class BinaryTree {
    TreeNode root;

    public BinaryTree() { this.root = null; }
    public BinaryTree(TreeNode root) { this.root = root; }

    public void insertRoot(Object data) {
        if (root == null) root = new TreeNode(data);
    }

    public TreeNode search(Object data) {
        return searchRec(root, data);
    }
    private TreeNode searchRec(TreeNode node, Object data) {
        if (node == null) return null;
        if (Objects.equals(node.data, data)) return node;
        TreeNode found = searchRec(node.left, data);
        return found != null ? found : searchRec(node.right, data);
    }

    public boolean insertLeft(Object parent, Object data) {
        TreeNode p = search(parent);
        if (p == null || p.left != null) return false;
        p.left = new TreeNode(data);
        return true;
    }

    public boolean insertRight(Object parent, Object data) {
        TreeNode p = search(parent);
        if (p == null || p.right != null) return false;
        p.right = new TreeNode(data);
        return true;
    }

    public void buildTree(Scanner scanner) {
        root = null;
        System.out.print("Raíz: ");
        if (!scanner.hasNextLine()) return;
        String val = scanner.nextLine().trim();
        if (val.isEmpty() || val.equals("-") || val.equals(".")) return;
        insertRoot(val);

        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode curr = q.poll();
            System.out.print("Hijo izquierdo de " + curr.data + ": ");
            if (scanner.hasNextLine()) {
                String l = scanner.nextLine().trim();
                if (!l.isEmpty() && !l.equals("-") && !l.equals(".")) {
                    curr.left = new TreeNode(l);
                    q.add(curr.left);
                }
            }
            System.out.print("Hijo derecho de " + curr.data + ": ");
            if (scanner.hasNextLine()) {
                String r = scanner.nextLine().trim();
                if (!r.isEmpty() && !r.equals("-") && !r.equals(".")) {
                    curr.right = new TreeNode(r);
                    q.add(curr.right);
                }
            }
        }
        System.out.println("¡Árbol construido!");
    }

    public void printTree() {
        if (root == null) { System.out.println("Árbol vacío."); return; }
        printRec("", root, false);
    }
    private void printRec(String prefix, TreeNode node, boolean isRight) {
        if (node != null) {
            System.out.println(prefix + (isRight ? "└── " : "├── ") + node.data);
            printRec(prefix + (isRight ? "    " : "│   "), node.left, false);
            printRec(prefix + (isRight ? "    " : "│   "), node.right, true);
        }
    }

    public String preorder() {
        List<String> l = new ArrayList<>();
        preRec(root, l);
        return String.join(" ", l);
    }
    private void preRec(TreeNode n, List<String> l) {
        if (n == null) return;
        l.add(String.valueOf(n.data));
        preRec(n.left, l);
        preRec(n.right, l);
    }

    public String inorder() {
        List<String> l = new ArrayList<>();
        inRec(root, l);
        return String.join(" ", l);
    }
    private void inRec(TreeNode n, List<String> l) {
        if (n == null) return;
        inRec(n.left, l);
        l.add(String.valueOf(n.data));
        inRec(n.right, l);
    }

    public String postorder() {
        List<String> l = new ArrayList<>();
        postRec(root, l);
        return String.join(" ", l);
    }
    private void postRec(TreeNode n, List<String> l) {
        if (n == null) return;
        postRec(n.left, l);
        postRec(n.right, l);
        l.add(String.valueOf(n.data));
    }

    public int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int balanceFactor(TreeNode node) {
        if (node == null) return 0;
        return height(node.right) - height(node.left);
    }

    public boolean isBalanced() {
        return isBalRec(root);
    }
    private boolean isBalRec(TreeNode n) {
        if (n == null) return true;
        int bf = balanceFactor(n);
        if (bf < -1 || bf > 1) return false;
        return isBalRec(n.left) && isBalRec(n.right);
    }

    public List<Object> unbalancedNodes() {
        List<Object> list = new ArrayList<>();
        unbalRec(root, list);
        return list;
    }
    private void unbalRec(TreeNode n, List<Object> list) {
        if (n == null) return;
        unbalRec(n.left, list);
        int bf = balanceFactor(n);
        if (bf < -1 || bf > 1) list.add(n.data);
        unbalRec(n.right, list);
    }

    public void printBalanceFactors() {
        if (root == null) { System.out.println("Árbol vacío."); return; }
        printBFRec(root);
    }
    private void printBFRec(TreeNode n) {
        if (n != null) {
            printBFRec(n.left);
            System.out.println("Nodo: " + n.data + " | FE: " + balanceFactor(n));
            printBFRec(n.right);
        }
    }

    public TreeNode rotateRight(TreeNode node) {
        TreeNode pivot = node.left;
        node.left = pivot.right;
        pivot.right = node;
        return pivot;
    }

    public TreeNode rotateLeft(TreeNode node) {
        TreeNode pivot = node.right;
        node.right = pivot.left;
        pivot.left = node;
        return pivot;
    }

    public TreeNode rotateLeftRight(TreeNode node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    public TreeNode rotateRightLeft(TreeNode node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    public void balance() {
        System.out.println("\n--- ANTES DE BALANCEAR ---");
        printBalanceFactors();
        root = balanceAVL(root);
        System.out.println("\n--- DESPUÉS DE BALANCEAR ---");
        printBalanceFactors();
    }

    private TreeNode balanceAVL(TreeNode node) {
        if (node == null) return null;
        node.left = balanceAVL(node.left);
        node.right = balanceAVL(node.right);

        int bf = balanceFactor(node);
        if (bf < -1) {
            if (balanceFactor(node.left) <= 0) return rotateRight(node);
            return rotateLeftRight(node);
        }
        if (bf > 1) {
            if (balanceFactor(node.right) >= 0) return rotateLeft(node);
            return rotateRightLeft(node);
        }
        return node;
    }
}