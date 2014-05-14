/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btrees;

/**
 *
 * @author hasan
 */
public class Node<Student> 
{
    private Node<Student> previous=null;
    private Node<Student> next=null;
    private Student data;
    
    public Node(Student s,Node<Student> previous,Node<Student> next)
    {
        this.data=s;
        this.next=next;
        this.previous=previous;
    }
    
    public Node getPrevious()
    {
        return this.previous;
    }
    
    public Node getNext()
    {
        return this.next;
    }
    
    public Student getData()
    {
        return this.data;
    }
    
    public void setPrevious(Node<Student> p)
    {
        this.previous=p;
    }
    
    public void setNext(Node<Student> n)
    {
        this.next=n;
    }
    
    public void setData(Student s)
    {
        this.data=s;
    }
    
    
}
