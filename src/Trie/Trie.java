package Trie;
import java.util.HashMap;
import java.util.Map;

public class Trie {

    public class TrieNode {
        private Map<Character, TrieNode> children;
        private boolean endOfWord;
        public TrieNode() {
            children = new HashMap<>();
            endOfWord = false;
        }
    }

    TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for(Character c : word.toCharArray()) {
            current = current.children.computeIfAbsent(c, k -> new TrieNode());
        }
        current.endOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for(Character c : word.toCharArray()) {
            current = current.children.get(c);
            if(current == null) return false;
        }
        return current.endOfWord;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        System.out.println(trie.search("dog"));
    }

}
