import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Graph<E>{
    private boolean[][] edges; //edges[i][j] is true if there is a vertex from i to j
    private E[] labels; //labels[i] contains the label for vertex i
    private boolean[] visited;
    private int[] bfsParent;
    private int[] dfsParent;

    private List<String> bfsTreeEdges = new ArrayList<>();
    private List<String> dfsTreeEdges = new ArrayList<>();
    /**Creates an empty Graph. */
    @SuppressWarnings("unchecked")
    public Graph(int n){
        edges = new boolean[n][n]; //All values initially false - cast, but warning suppressed
        labels = (E[]) new Object[n]; //All values initially null
    } //end Graph constructor

    /**Retrieves the label of a vertex of this Graph.
     * @return label of vertex.
     * @param vertex integer correlating to the label.
     */
    public E getLabel(int vertex){
        checkVertexIndex(vertex); //Check if vertex is in bounds
        return labels[vertex];
    } //end getLabel

    /**Tests whether an edge exists.
     * @return True if edge exists, false otherwise.
     * @param source integer where the edge starts.
     * @param target integer where the edge points to.
     */
    public boolean isEdge(int source, int target){
        checkVertexIndex(source); //Check if source is in bounds
        checkVertexIndex(target); //Check if target is in bounds
        return edges[source][target];
    } //end isEdge

    /* Checks if source/target is in bounds of Graph
     * @param index integer index of the vertex
     */ 
    private void checkVertexIndex(int index) {
        if (index < 0 || index >= labels.length) {
            throw new IndexOutOfBoundsException("Invalid vertex index: " + index);
        }
    }
    
    /**Adds an edge.
     * @param source integer where the edge starts.
     * @param target integer where the edge points to.
     */
    public void addEdge(int source, int target){
        checkVertexIndex(source);
        checkVertexIndex(target);
        edges[source][target] = true;
    } //end addEdge

    /**Obtains a list of neighbors of a specified vertex of this Graph.
     * @return integer array of all vertices vertex connects to.
     * @param vertex integer index of vertex in this Graph.
     */
    public int[] neighbors(int vertex){
        checkVertexIndex(vertex); //Check if vertex is in bounds
        int count = 0;
        int[] answer;
        
        for(int i = 0; i < labels.length; i++){
            if(edges[vertex][i]){
                count++;
            } //end if
        } //end for
        answer = new int[count];
        count = 0;
        for(int i = 0; i < labels.length; i++){
            if(edges[vertex][i]){
                answer[count++] = i;
            } //end if
        } //end for
        return answer;

    } //end neighbors

    /**Removes an edge.
     * @param source integer where the edge starts.
     * @param target integer where the edge ends.
     */
    public void removeEdge(int source, int target){
        checkVertexIndex(source); //Check if source is in bounds
        checkVertexIndex(target); //Check if target is in bounds
        edges[source][target] = false;
    } //end removeEdge

    /**Changes the label of a vertex of this Graph. 
    * @param vertex integer index of the vertex.
    * @param newLabel label to assign to the vertex.
    */
    public void setLabel(int vertex, E newLabel){
        checkVertexIndex(vertex); //Check if vertex is in bounds
        if (newLabel == null) {
            throw new IllegalArgumentException("Label cannot be null.");
        }
        
        labels[vertex] = newLabel;
    } //end setLabel

    /**Determines the number of vertices in this Graph.
     * @return integer number on vertices in this Graph.
     */
    public int size(){
        return labels.length;
    } //end size

    /**Resets the visited array
     * reset visited arrays to false
    */
    public void resetVertices(){
       if (visited == null) {
        visited = new boolean[labels.length];
       }
       Arrays.fill(visited, false);
   } //end resetVertices
    

    /**Performs a breadth-first search traversal on this Graph.
     * @return Queue of labels in the order they were visited.
     * @param origin vertex search will begin at.
     */
    public QueueInterface<E> getBreadthFirstTraversal(E origin){
        visited = new boolean[labels.length];
        bfsParent = new int[labels.length];
        Arrays.fill(bfsParent, -1);
        bfsTreeEdges.clear();

        resetVertices();

        QueueInterface<E> traversalOrder = new LinkedQueue<>();
        QueueInterface<Integer> vertexQueue = new LinkedQueue<>();

        int originIndex = -1;
        for(int i = 0; i < labels.length; i++){
            if(labels[i].equals(origin)){
                originIndex = i;
                break;
            } //end if
        } //end for

        if (originIndex == -1) {
            throw new IllegalArgumentException("Label not found: " + origin);
        }        

        visited[originIndex] = true;
        traversalOrder.enqueue(origin);
        vertexQueue.enqueue(originIndex);

        while(!vertexQueue.isEmpty()){
            int frontIndex = vertexQueue.dequeue();
            for(int neighbor : neighbors(frontIndex)){
                if(!visited[neighbor]){
                    visited[neighbor] = true;
                    bfsParent[neighbor] = frontIndex;
                    traversalOrder.enqueue(labels[neighbor]);
                    vertexQueue.enqueue(neighbor);
                    bfsTreeEdges.add("(" + labels[frontIndex] + "," + labels[neighbor] + ")");
                } //end if
            } //end for each
        } //end while

        return traversalOrder;
    } //end getBreadthFirstTraversal

    //Performs depth-first search traversal on this Graph.
    public QueueInterface<E> getDepthFirstTraversal(E origin){
        visited = new boolean[labels.length];
        dfsParent = new int[labels.length];
        Arrays.fill(dfsParent, -1);
        dfsTreeEdges.clear();


        resetVertices();

        QueueInterface<E> traversalOrder = new LinkedQueue<>();
        Stack<Integer> vertexStack = new Stack<>();

        int originIndex = -1;
        for (int i = 0; i < labels.length; i++) {
        if (labels[i].equals(origin)) {
            originIndex = i;
            break;
            }
        }

        if (originIndex == -1) {
            throw new IllegalArgumentException("Label not found: " + origin);
        }

        visited[originIndex] = true;
        traversalOrder.enqueue(origin);
        vertexStack.push(originIndex);

        while (!vertexStack.isEmpty()) {
            int topIndex = vertexStack.peek(); // Peek without removing
            boolean foundUnvisited = false;

            for (int neighbor : neighbors(topIndex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    dfsParent[neighbor] = topIndex;
                    traversalOrder.enqueue(labels[neighbor]);
                    vertexStack.push(neighbor);
                    foundUnvisited = true;
                    dfsTreeEdges.add("(" + labels[topIndex] + "," + labels[neighbor] + ")");
                    break; // Important: go deeper
                }
            }

            if (!foundUnvisited) {
                vertexStack.pop(); // Backtrack
            }
        }

        return traversalOrder;
    }//end getDepthFirstTraversal

    public void printBFSTreeEdgesInOrder() {
        System.out.print("BFS Tree edges: { ");
        for (int i = 0; i < bfsTreeEdges.size(); i++) {
            if (i > 0)
                System.out.print(", ");
            System.out.print(bfsTreeEdges.get(i));
        }
        System.out.println(" }");
    }
    
    public void printDFSTreeEdgesInOrder() {
        System.out.print("DFS Tree edges: { ");
        for (int i = 0; i < dfsTreeEdges.size(); i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(dfsTreeEdges.get(i));
        }
        System.out.println(" }");
    }

    public static void main(String[] args) {
        Graph<String> graph = new Graph<>(9);

        //Set vertex labels A to I
        String[] nodes = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
        for(int i = 0; i < nodes.length; i++){
            graph.setLabel(i, nodes[i]);
        } //end for

        //Add directed edges
        graph.addEdge(0, 1); //A -> B
        graph.addEdge(0, 3); //A -> D
        graph.addEdge(0, 4); //A -> E
        graph.addEdge(1, 4); //B -> E
        graph.addEdge(3, 6); //D -> G
        graph.addEdge(4, 5); //E -> F
        graph.addEdge(4, 7); //E -> H
        graph.addEdge(6, 7); //G -> H
        graph.addEdge(5, 2); //F -> C
        graph.addEdge(5, 7); //F -> H
        graph.addEdge(7, 8); //H -> I
        graph.addEdge(2, 1); //C -> B
        graph.addEdge(8, 5); //I -> F

        QueueInterface<String> breadthResult = graph.getBreadthFirstTraversal("A");
        System.out.print("Breadth-First Traversal Result: ");
        while(!breadthResult.isEmpty()){
            System.out.print(breadthResult.dequeue() + " ");
        } //end while
        System.out.println();
        graph.printBFSTreeEdgesInOrder();


        QueueInterface<String> depthResult = graph.getDepthFirstTraversal("A");
        System.out.print("Depth-First Traversal Result: ");
        while (!depthResult.isEmpty()) {
            System.out.print(depthResult.dequeue() + " ");
        }
        System.out.println();
        graph.printDFSTreeEdgesInOrder();

    } //end main

} //end Graph