/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btrees;

/**
 *
 * @author hasan
 */
public class DoublyLinkedList<Student>
{
    private Node<Student> head;
    private Node<Student> tail;
    private int size;
    
    public DoublyLinkedList()
    {
        head = new Node<Student>(null, null, null);
        tail = new Node<Student>(null, null, null);
        head.setNext(tail);
        tail.setPrevious(head);
        size = 0;
    }
    
    public boolean isEmpty()
    {
        return this.size==0;
    }
    

    private Node<Student> getNext(Node<Student> referenceNode)
    {
        if(referenceNode==null)
        {
                return null;
        }

        return referenceNode.getNext();
    }


    private Node<Student> getPrev(Node<Student> referenceNode) 
    {
        if(referenceNode==null)
        {
                return null;
        }
        return referenceNode.getPrevious();
    }
    
    
    
    public void addBefore(Node<Student> referenceNode, Student data)
    {
        Node<Student> prevNode = this.getPrev(referenceNode);
        Node<Student> newNode = new Node<Student>(data, prevNode, referenceNode);
        referenceNode.setPrevious(newNode);
        prevNode.setNext(newNode);
        size++;
    }

    
    
    public void addAfter(Node<Student> referenceNode, Student data)
    {
        Node<Student> nextNode = this.getNext(referenceNode);
        
        Node<Student> newNode = new Node<Student>(data, referenceNode, nextNode);
        referenceNode.setNext(newNode);
        nextNode.setPrevious(newNode);
        size++;
    }
    

    public void addFirst(Student data)
    {
        this.addAfter(head, data);
    }

    
    public void addLast(Student data)
    {
        this.addBefore(tail, data);
    }

    
    public Student remove(Node<Student> removeNode)
    {
	Node<Student> prevNode = this.getPrev(removeNode);	
	Node<Student> nextNode = this.getNext(removeNode);	
	
	removeNode.setPrevious(null);
	removeNode.setNext(null);
	
	prevNode.setNext(nextNode);
	nextNode.setPrevious(prevNode);	
        
        
        
	size--;
        
        return removeNode.getData();
    }
    
    public Student removeLast()
    {
        
        if(this.isEmpty())
        {
                return null;
        }
        Node<Student> tempNode = this.getPrev(tail);
        return this.remove(tempNode);
    }


    public Student removeFirst() 
    {
        if(this.isEmpty())
        {
                return null;
        }
        Node<Student> tempNode = this.getNext(head);
        return this.remove(tempNode);
    }
    
    public Node<Student> getFirst()
    {
	if(this.isEmpty())
        {
		return null;
        }
	
	Node<Student> temp = this.getNext(head);
	return temp;
    }


    public Node<Student> getLast()
    {
        if(this.isEmpty())
        {
                return null;
        }
        Node<Student> temp = this.getPrev(tail);
        return temp;
    }
}
