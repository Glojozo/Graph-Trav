import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

public class GraphTest {
    /*
     *  Builds a sample directed graph for testing:
     * A -> B
     * A -> C
     * B -> D
     * C -> D
     * D -> E
     * This is a directed graph, represented as an adjacency list.
     */
    private Graph<String> buildSampleGraph() {
        Graph<String> graph = new Graph<>(5);
        String[] labels = {"A", "B", "C", "D", "E"};

        for (int i = 0; i < labels.length; i++) {
            graph.setLabel(i, labels[i]);
        }

        graph.addEdge(0, 1); // A -> B
        graph.addEdge(0, 2); // A -> C
        graph.addEdge(1, 3); // B -> D
        graph.addEdge(2, 3); // C -> D
        graph.addEdge(3, 4); // D -> E

        return graph;
    }

    /*
     * Tests setting and getting labels for vertices.
     */
    @Test
    public void testSetAndGetLabel() {
        Graph<String> graph = new Graph<>(3);
        graph.setLabel(0, "X");
        graph.setLabel(1, "Y");
        assertEquals("X", graph.getLabel(0));
        assertEquals("Y", graph.getLabel(1));
    }

    /*
     * Tests that accessing a label with an invalid index throws an exception.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testInvalidLabelAccess() {
        Graph<String> graph = new Graph<>(2);
        graph.getLabel(5); // Should throw
    }

    /*
     * Tests adding and removing an edge between two vertices.
     */
    @Test
    public void testAddAndRemoveEdge() {
        Graph<String> graph = new Graph<>(2);
        graph.setLabel(0, "A");
        graph.setLabel(1, "B");
        graph.addEdge(0, 1);
        assertTrue(graph.isEdge(0, 1));
        graph.removeEdge(0, 1);
        assertFalse(graph.isEdge(0, 1));
    }

    /*
     * Tests retrieving neighbors of a vertex.
     */
    @Test
    public void testNeighbors() {
        Graph<String> graph = new Graph<>(3);
        graph.setLabel(0, "A");
        graph.setLabel(1, "B");
        graph.setLabel(2, "C");

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        int[] neighbors = graph.neighbors(0);
        Arrays.sort(neighbors);
        assertArrayEquals(new int[]{1, 2}, neighbors);
    }

    /*
     * Tests BFS traversal from vertex "A" in a sample graph.
     */
    @Test
    public void testBreadthFirstTraversal() {
        Graph<String> graph = buildSampleGraph();
        QueueInterface<String> result = graph.getBreadthFirstTraversal("A");

        List<String> output = new ArrayList<>();
        while (!result.isEmpty()) {
            output.add(result.dequeue());
        }

        // Possible BFS order: A, B, C, D, E
        assertEquals(Arrays.asList("A", "B", "C", "D", "E"), output);
    }

    /*
     * Tests DFS traversal from vertex "A" in a sample graph.
     * DFS order may vary, so we check for a valid known sequence.
     */
    @Test
    public void testDepthFirstTraversal() {
        Graph<String> graph = buildSampleGraph();
        QueueInterface<String> result = graph.getDepthFirstTraversal("A");

        List<String> output = new ArrayList<>();
        while (!result.isEmpty()) {
            output.add(result.dequeue());
        }

        // DFS order may vary but this is one valid path
        assertEquals(Arrays.asList("A", "B", "D", "E", "C"), output);
    }

    /*
     * Tests that setting a label in an empty graph throws an exception.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testEmptyGraphAccess() {
        Graph<String> graph = new Graph<>(0);
        graph.setLabel(0, "A"); // Should throw
    }

    /*
     * Tests that adding a duplicate edge does not cause issues.
     */
    @Test
    public void testDuplicateEdge() {
        Graph<String> graph = new Graph<>(2);
        graph.setLabel(0, "A");
        graph.setLabel(1, "B");

        graph.addEdge(0, 1);
        graph.addEdge(0, 1); // Duplicate

        assertTrue(graph.isEdge(0, 1)); // Still true
    }

    /*
     * Tests BFS and DFS starting from an isolated vertex.
     * Only the starting node should be returned.
     */
    @Test
    public void testTraversalFromIsolatedNode() {
        Graph<String> graph = new Graph<>(3);
        graph.setLabel(0, "A");
        graph.setLabel(1, "B");
        graph.setLabel(2, "C");

        graph.addEdge(0, 1); // A -> B
        // C is isolated

        QueueInterface<String> bfs = graph.getBreadthFirstTraversal("C");
        assertEquals("C", bfs.dequeue());
        assertTrue(bfs.isEmpty());

        QueueInterface<String> dfs = graph.getDepthFirstTraversal("C");
        assertEquals("C", dfs.dequeue());
        assertTrue(dfs.isEmpty());
    }

    /*
     * Tests that adding an edge with an invalid index throws an exception.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddInvalidEdge() {
        Graph<String> graph = new Graph<>(2);
        graph.setLabel(0, "A");
        graph.setLabel(1, "B");
        graph.addEdge(0, 5); // Invalid target index
    }

    /*
    * Tests traversal in a graph with a cycle to ensure no infinite loops.
    */
    @Test
    public void testTraversalWithCycle() {
        Graph<String> graph = new Graph<>(3);
        graph.setLabel(0, "A");
        graph.setLabel(1, "B");
        graph.setLabel(2, "C");

        graph.addEdge(0, 1); // A -> B
        graph.addEdge(1, 2); // B -> C
        graph.addEdge(2, 0); // C -> A (cycle)

        QueueInterface<String> bfs = graph.getBreadthFirstTraversal("A");
        List<String> bfsOrder = new ArrayList<>();
        while (!bfs.isEmpty()) bfsOrder.add(bfs.dequeue());
        assertTrue(bfsOrder.containsAll(Arrays.asList("A", "B", "C")));

        QueueInterface<String> dfs = graph.getDepthFirstTraversal("A");
        List<String> dfsOrder = new ArrayList<>();
        while (!dfs.isEmpty()) dfsOrder.add(dfs.dequeue());
        assertTrue(dfsOrder.containsAll(Arrays.asList("A", "B", "C")));
    }

    /*
     * This test checks for no issues when a vertex is relabeled and retrieves new label.
     */
    @Test
    public void testRelabelVertex() {
        Graph<String> graph = new Graph<>(1);
        graph.setLabel(0, "A");
        graph.setLabel(0, "B");
        assertEquals("B", graph.getLabel(0));
    }
}
