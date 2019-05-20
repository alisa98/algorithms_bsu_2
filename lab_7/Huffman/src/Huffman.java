import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Huffman {
    private Map<String, Integer> frequency;
    private List<Tree.Node> nodeList;
    private Tree huffmanTree;
    private Map<String, String> charCodes;
    private StringBuilder encodedByteString;

    public Huffman() {
        frequency = new HashMap<>();
        nodeList = new ArrayList<>();
    }

    public void encode(String name, String encodedName) throws IOException {
        readBaseFile(name);
        makeTreeOfSymbols();
        setCodeMapFromTree();
        encodeFile(name, encodedName);
    }

    public void decode(String name, String decodedName) throws IOException {
        readEncodedFile(name);
        decodeStringToFile(decodedName);
    }

    private void readBaseFile(String name) throws IOException {
        try (FileReader r = new FileReader(name)) {
            int c;
            while ((c = r.read()) != -1) {
                frequency.merge(String.valueOf((char) c), 1, Integer::sum);
            }
        }
        frequency.forEach((key, value) -> nodeList.add(new Tree.Node(key, value)));
    }

    private void readEncodedFile(String name) throws IOException {
        List<StringBuilder> encodedBytes = new ArrayList<>();
        StringBuilder codeTreeString = new StringBuilder();
        try (ObjectInputStream r = new ObjectInputStream(new FileInputStream(name))) {
            int c;
            char last = '0';
            boolean readCodeTree = true;
            while ((c = r.readUnsignedByte()) != -1) {
                if (readCodeTree) {
                    if ((char) c == '.' && (last != '0') && (last != '1')) {
                        readCodeTree = false;
                    } else {
                        codeTreeString.append((char) c);
                        last = (char) c;
                    }
                } else {
                    encodedBytes.add(new StringBuilder(Integer.toBinaryString(c)));
                }
            }
        } catch (EOFException e) {
            huffmanTree = generateTreeFromCodeString(codeTreeString.reverse().toString());
            setCodeMapFromTree();
            huffmanTree.generateTreeDictionary();

            for (int i = 1; i < encodedBytes.size() - 1; ++i) {
                while (encodedBytes.get(i).length() < 8) {
                    encodedBytes.get(i).insert(0, "0");
                }
            }
            for (int i = 0; i < Integer.parseInt(encodedBytes.get(0).toString(), 2); ++i) {
                encodedBytes.get(encodedBytes.size() - 1).insert(0, "0");
            }
            encodedByteString = new StringBuilder();
            for (int i = 1; i < encodedBytes.size(); ++i) {
                encodedByteString.append(encodedBytes.get(i));
            }
        }
    }

    private Tree generateTreeFromCodeString(String codeString) {
        LinkedList<Tree.Node> nodeLinkedList = new LinkedList<>();
        char[] charArray = codeString.toCharArray();
        char lastChar = '0';
        for (char ch : charArray) {
            if (ch == '1') {
                nodeLinkedList.addFirst(new Tree.Node(String.valueOf(lastChar)));
            } else if (ch == '0') {
                Tree.Node firstNode = nodeLinkedList.removeFirst();
                Tree.Node secondNode = nodeLinkedList.removeFirst();

                nodeLinkedList.addFirst(new Tree.Node(firstNode, secondNode));
            } else {
                lastChar = ch;
            }
        }
        return new Tree(nodeLinkedList.removeFirst());
    }

    private void decodeStringToFile(String name) throws IOException {
        StringBuilder sb = new StringBuilder();
        String decodedChar;
        try (FileWriter w = new FileWriter(name)) {
            for (int i = 0; i < encodedByteString.length(); ++i) {
                sb.append(encodedByteString.charAt(i));
                decodedChar = getDecodedChar(sb.toString());
                if (decodedChar != null) {
                    w.write(decodedChar);
                    sb = new StringBuilder();
                }
            }
        }
    }

    private String getDecodedChar(String code) {
        for (Map.Entry<String, String> entry : charCodes.entrySet()) {
            if (entry.getValue().equals(code)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void encodeFile(String name, String encodedName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileReader r = new FileReader(name); ObjectOutputStream w = new ObjectOutputStream(new FileOutputStream(encodedName))) {
            int c;
            while ((c = r.read()) != -1) {
                sb.append(charCodes.get(String.valueOf((char) c)));
            }
            huffmanTree.generateTreeDictionary();
            w.writeBytes(huffmanTree.traverseDictionary.toString());
            w.writeByte((byte)'.');
            writeBinary(sb, w);
        }
    }

    private void writeBinary(StringBuilder sb, ObjectOutputStream w) throws IOException {
        int i = 0;
        int rem = 8 - sb.length() % 8;

        int zeros = countZerosAtTheBeginning(sb.subSequence(sb.length() - (8 - rem), sb.length()).toString());

        w.write((byte) Integer.parseInt(Integer.toBinaryString(zeros), 2));
        while (i < sb.length() - 8) {
            String str = sb.subSequence(i, i = i + 8).toString();
            w.write((byte) Integer.parseInt(str, 2));
        }

        if (rem != 0) {
            String str = sb.subSequence(i, sb.length()).toString();
            w.write((byte) Integer.parseInt(str, 2));
        }

    }

    private int countZerosAtTheBeginning(String str) {
        int i = 0;
        for (char c : str.toCharArray()) {
            if (c == '0') {
                i++;
            } else {
                return i;
            }
        }
        return i;
    }

    private void makeTreeOfSymbols() {
        while (nodeList.size() != 1) {
            sortNodes();
            concatTwoSmallest();
        }
        huffmanTree = new Tree(nodeList.get(0));
    }

    private void setCodeMapFromTree() {
        charCodes = huffmanTree.setCodes();
    }

    private void sortNodes() {
        nodeList = nodeList.stream().sorted((o1, o2) -> {
                    if (o1.frequency.equals(o2.frequency)) {
                        return o2.value.length() - o1.value.length();
                    }
                    return o1.frequency - o2.frequency;
                }).collect(Collectors.toList());
    }

    private void concatTwoSmallest() {
        Tree.Node firstNode = nodeList.remove(0);
        Tree.Node secondNode = nodeList.remove(0);
        nodeList.add(new Tree.Node(firstNode, secondNode));
    }

    public static class Tree {

        private Node root;
        private Map<String, String> codeMap;
        private StringBuilder traverseDictionary;

        public Tree(Node root) {
            this.root = root;
            codeMap = new HashMap<>();
        }

        public Map<String, String> setCodes() {
            inOrder(root, "");
            return codeMap;
        }

        private void inOrder(Node n, String code) {
            if (n != null) {
                if (n.isLeaf) {
                    codeMap.put(n.value, code + n.codeValue);
                }
                inOrder(n.left, code + n.codeValue);
                inOrder(n.right, code + n.codeValue);
            }
        }

        public void generateTreeDictionary() {
            traverseDictionary = new StringBuilder();
            traverseDictionary(root);
        }

        private void traverseDictionary(Node n) {
            if (n != null) {
                if (n.isLeaf) {
                    traverseDictionary.append(1).append(n.value);
                } else {
                    traverseDictionary.append(0);
                }
                traverseDictionary(n.left);
                traverseDictionary(n.right);
            }
        }

        private static class Node {
            private String value;
            private Integer frequency;
            private Node left;
            private Node right;
            private boolean isLeaf;
            private String codeValue;

            public Node(String value) {
                this.value = value;
                this.frequency = 0;
                this.isLeaf = true;
            }

            public Node(String value, Integer frequency) {
                this.value = value;
                this.frequency = frequency;
                this.isLeaf = true;
            }

            public Node(Node left, Node right) {
                this.value = left.value + right.value;
                this.frequency = left.frequency + right.frequency;
                this.codeValue = "";
                left.codeValue = "0";
                right.codeValue = "1";
                this.left = left;
                this.right = right;
            }

        }
    }

    public static void main(String[] args) {
        Huffman huffman = new Huffman();
        try {
            huffman.encode("text.txt", "encoded");

            huffman.decode("encoded", "decoded");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}