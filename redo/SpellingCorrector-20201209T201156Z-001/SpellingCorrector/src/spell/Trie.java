package spell;


//Note: node.getChildren()[] vs node.nodeArray[]  겟칠드런과 노드어레이는 테크니컬리 같지만 Node node; 이 베리어블에 값을 주거나 return 노드값 할 때
// 겟칠드런은 (Node) 를 앞에 써줘서 스테팈오브젝트 해줘야함. 노드어레이는 그냥 ㄱㅊ. 왜 스테팈오브젴 해줘야하냐? 몰라. 아마 겟칠드런이 INode 라서...?

public class Trie implements ITrie {                   // Trie class
    protected Node rootNode;
    protected int countNode;
    protected int countWord;
    //protected Set<String> wordList;

    public Trie() {                                    // Trie constructor
        rootNode = new Node();
        countNode = 1;
        countWord = 0;
    }

    @Override
    public void add(String word) {
        Node node = rootNode;
        int currIndex;
        for (int i = 0; i < word.length(); i++) {
            currIndex = word.charAt(i) - 'a';        //get current index by character - 'a'
            node = addHelper(node, currIndex);
        }
        if (node.getValue() == 0) {                  //Word iteration is done. if it has 0 frequency, countWord.
            countWord++;
        }
        node.incrementValue();                       //Frequency++
    }

    private Node addHelper (Node currNode, int currIndex) {
        if (currNode.getChildren()[currIndex] == null) {       // If current node has no children
            currNode.getChildren()[currIndex] = new Node();    // Create new node
            countNode++;                                       // Then countNode
        }
        return (Node) currNode.getChildren()[currIndex];       // return current Node's children node by using current index number
    }

    @Override
    public INode find(String word) {
        Node node = rootNode;
        int currIndex;
        for (int i = 0; i < word.length(); i++) {
            currIndex = word.charAt(i) - 'a';
            if (node.getChildren()[currIndex] == null) {      // if there is no char, return null
                return null;
            }
            node = (Node) node.getChildren()[currIndex];            // if the char exists, update the node (Tree traverse)
        }
        if (node.getValue() != 0) {      // word char 끝까지 다 돌고 for loop 나온거임. Frequency 가 0이아니면 지금 이 노드값 리턴
            return node;
        }
        return null;                     //아니라면 그냥 null 리턴
    }

    @Override
    public String toString() {
        StringBuilder current = new StringBuilder();
        StringBuilder outPut = new StringBuilder();
        toStringHelper(rootNode, current, outPut);
        return outPut.toString();
    }

    private void toStringHelper(Node node, StringBuilder curr, StringBuilder out) {
        if (node.getValue() != 0) {
            out.append(curr);
            out.append("\n");
        }
        for (int i = 0; i < node.getChildren().length; i++) {
            Node child = (Node) node.getChildren()[i];
            if (child != null) {
                char letter;
                letter = (char) ('a' + i);
                curr.append(letter);
                toStringHelper(child, curr, out);
                curr.deleteCharAt(curr.length() - 1);
            }
        }
    }

    @Override
    public int hashCode() {
        int code = 0;
        Node node = rootNode;
        for (int i = 0; i < node.getChildren().length; i++) {
            if (node.getChildren()[i] != null) {
                code += (i + countNode) * 31;
            }
        }
        return countWord * code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() == obj.getClass()) {    // 이 class 와 object 로 받아오는 애가 같은건지
            Trie trie = (Trie) obj;                 // cast obj to (Trie). c++의 Static_cast 비슷한거임. 오브젝트를 트라이클래스로 바꿔줌
            if (trie.countNode != countNode || trie.countWord != countWord) {
                return false;
            }
            return equalsHelper(trie.rootNode, rootNode);          //Check value, nullity, all the children nodes.
        }
        else {
            return false;
        }
    }

    //helper function
    private boolean equalsHelper (INode node1, INode node2) {
        //check value of the node, nullity, equality, check every child node are equal
        if ((node1 == null && node2 != null) || (node1 != null && node2 == null)) {
            return false;
        }
        if (node1 == null && node2 == null) {         //This statement is necessary to prevent out of range error in the for-loop below.
            return true;
        }
        if (node1.getValue() != node2.getValue()) {
            return false;
        }
        for (int i = 0; i < 26; i++) {       // 26: The maximum nodes that each node can possess. (number of alphabets)
            if (!equalsHelper(node1.getChildren()[i], node2.getChildren()[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getWordCount() { return countWord; }

    @Override
    public int getNodeCount() { return countNode; }
}
