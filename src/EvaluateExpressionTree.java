public class EvaluateExpressionTree {

    public static class TreeNode {
        private String value;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(String value) {
            this.value = value;
        }

        public TreeNode(String value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public String getValue() {
            return value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode root = createTree();
        Integer result = evaluateTree(root);
        System.out.println(result);
    }

    private static int evaluateTree(TreeNode root) {
        if (root == null) {
            throw new IllegalArgumentException("Expression tree node cannot be null");
        }

        if (root.getLeft() == null && root.getRight() == null) {
            return Integer.parseInt(root.value);
        }

        int leftValue = evaluateTree(root.getLeft());
        int rightValue = evaluateTree(root.getRight());
        return switch (root.getValue()) {
            case "+" -> leftValue + rightValue;
            case "-" -> leftValue - rightValue;
            case "*" -> leftValue * rightValue;
            case "/" -> {
                if (rightValue == 0) throw new ArithmeticException("Division by zero");
                yield leftValue / rightValue;
            }
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }

    private static TreeNode createTree() {
        TreeNode root = new TreeNode("*");
        TreeNode n1 = new TreeNode("+");
        TreeNode n2 = new TreeNode("2");
        root.setLeft(n1);
        root.setRight(n2);
        TreeNode n3 = new TreeNode("6", null, null);
        TreeNode n4 = new TreeNode("-1", null, null);
        n1.setLeft(n3);
        n1.setRight(n4);
        return root;
    }
}
