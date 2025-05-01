import java.util.NoSuchElementException;
/**
 * A class that implements a queue of objects by using a chain of linked nodes.
 * @author Zoey Yung
 */
public class LinkedQueue<T> implements QueueInterface<T>{
    private Node firstNode; //references node at front of queue
    private Node lastNode; //references node at back of queue

    /**Creates an empty LinkedQueue. */
    public LinkedQueue(){
        firstNode = null;
        lastNode = null;
    } //end default constructor

    /**Adds a new entry to the back of the queue.
     * @param newEntry entry to be added.
     */
    @Override
    public void enqueue(T newEntry){
        Node newNode = new Node(newEntry, null);
        if (isEmpty()){
            firstNode = newNode;
        }
        else{
            lastNode.setNextNode(newNode);
        } //end if-else
        lastNode = newNode;
    } //end enqueue

    /**Removes and returns the object at the front of the queue.
     * @return Object at the front of the queue.
     */
    @Override
    public T dequeue(){
        T front = getFront();
        firstNode.setData(null);
        firstNode = firstNode.getNextNode();
        
        if(firstNode == null){
            lastNode = null;
        } //end if
        return front;
    } //end dequeue

    /**Retrieves entry at the front of the queue.
     * @return Object at the front of the queue.
     */
    @Override
    public T getFront(){
        if(isEmpty()){
            throw new NoSuchElementException("Queue is empty");
        }
        else{
            return firstNode.getData();
        } //end if-else
    } //end getFront
    
    /**Detects whether this queue is empty.
     * @return True if the queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty(){
        return (firstNode == null) && (lastNode == null);
    } //end isEmpty

    /**Removes all entries from this queue. */
    @Override
    public void clear(){
        firstNode = null;
        lastNode = null;
    } //end clear

    /**Node class for LinkedQueue. */
    private class Node {
        private T data; //entry in queue
        private Node next; //link to next node

        /**Creates new node with data. */
        private Node(T dataPortion){
            this(dataPortion, null);
        } //end constructor
        
        /**Creates new node with data and link ot the next node. */
        private Node(T dataPortion, Node nextNode){
            data = dataPortion;
            next = nextNode;
        } //end constructor

        /**Retrieves data from node.
         * @return data in node.
         */
        private T getData(){
            return data;
        } //end getData

        /**Sets data of node. */
        private void setData(T newData){
            data = newData;
        } //end setData

        /**Retrieves next node.
         * @return next node.
         */
        private Node getNextNode(){
            return next;
        } //end getNextNode

        /**Sets next linked node. */
        private void setNextNode(Node nextNode){
            next = nextNode;
        } //end setNextNode

    } //end Node

} //end LinkedQueue