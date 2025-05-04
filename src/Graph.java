import java.util.Stack;

public class Graph<E>{
    private boolean[][] edges; //edges[i][j] is true if there is a vertex from i to j
    private E[] labels; //labels[i] contains the label for vertex i
    private boolean[] visited;

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
        return labels[vertex];
    } //end getLabel

    /**Tests whether an edge exists.
     * @return True if edge exists, false otherwise.
     * @param source integer where the edge starts.
     * @param target integer where the edge points to.
     */
    public boolean isEdge(int source, int target){
        return edges[source][target];
    } //end isEdge

    /**Adds an edge.
     * @param source integer where the edge starts.
     * @param target integer where the edge points to.
     */
    public void addEdge(int source, int target){
        edges[source][target] = true;
    } //end addEdge

    /**Obtains a list of neighbors of a specified vertex of this Graph.
     * @return integer array of all vertices vertex connects to.
     * @param vertex integer index of vertex in this Graph.
     */
    public int[] neighbors(int vertex){
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
        edges[source][target] = false;
    } //end removeEdge

    /**Changes the label of a vertex of this Graph. 
    * @param vertex integer index of the vertex.
    * @param newLabel label to assign to the vertex.
    */
    public void setLabel(int vertex, E newLabel){
        labels[vertex] = newLabel;
    } //end setLabel

    /**Determines the number of vertices in this Graph.
     * @return integer number on vertices in this Graph.
     */
    public int size(){
        return labels.length;
    } //end size

    /**Resets the visited array */
    public void resetVertices(){
       for(int i = 0; i < visited.length; i++){
        visited[i] = false;
       }
   } //end restVertices
    

    /**Performs a breadth-first search traversal on this Graph.
     * @return Queue of labels in the order they were visited.
     * @param origin vertex search will begin at.
     */
    public QueueInterface<E> getBreadthFirstTraversal(E origin){
        visited = new boolean[labels.length];
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

        if(originIndex == -1){
            return traversalOrder; //origin not found
        } //end if

        visited[originIndex] = true;
        traversalOrder.enqueue(origin);
        vertexQueue.enqueue(originIndex);

        while(!vertexQueue.isEmpty()){
            int frontIndex = vertexQueue.dequeue();
            for(int neighbor : neighbors(frontIndex)){
                if(!visited[neighbor]){
                    visited[neighbor] = true;
                    traversalOrder.enqueue(labels[neighbor]);
                    vertexQueue.enqueue(neighbor);
                } //end if
            } //end for each
        } //end while

        return traversalOrder;
    } //end getBreadthFirstTraversal

    //Performs depth-first search traversal on this Graph.
    public QueueInterface<E> getDepthFirstTraversal(E origin){
        visited = new boolean[labels.length];
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
            return traversalOrder; // origin not found
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
                    traversalOrder.enqueue(labels[neighbor]);
                    vertexStack.push(neighbor);
                    foundUnvisited = true;
                    break; // Important: go deeper
                }
            }

            if (!foundUnvisited) {
                vertexStack.pop(); // Backtrack
            }
        }

        return traversalOrder;
    }//end getDepthFirstTraversal

    public void printTreeEdges(E origin){
        //add code here to print the edges of the tree formed by the traversal
    }//end printTreeEdges

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

        QueueInterface<String> depthResult = graph.getDepthFirstTraversal("A");
        System.out.print("Depth-First Traversal Result: ");
        while (!depthResult.isEmpty()) {
            System.out.print(depthResult.dequeue() + " ");
        }
            System.out.println();

    } //end main

} //end Graph