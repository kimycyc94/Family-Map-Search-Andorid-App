package spell;

public class Node implements INode {
    protected Node[] nodeArray;
    protected int frequency;

    public Node() {                     // Node constructor
        nodeArray  = new Node[26];
        frequency = 0;
    }

    @Override
    public int getValue() {
        return frequency;
    }

    @Override
    public void incrementValue() { frequency++; }

    @Override
    public INode[] getChildren() {
        return nodeArray;
    }
}
