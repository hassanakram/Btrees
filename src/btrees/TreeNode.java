/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btrees;

import java.util.ArrayList;

/**
 *
 * @author hasan
 */

public class TreeNode 
{
    public ArrayList<Integer> values;
    public ArrayList<Node<Student>> pointers;
    public  ArrayList<TreeNode> children;
    
    
    public TreeNode()
    {
        this.values=new ArrayList<>();
        this.pointers=new ArrayList<>();
        this.children=new ArrayList<>();
    }
    
    
    
    public boolean isLeaf()
    {
        return children.isEmpty();
    }
    
    public int numberOfKeys()
    {
        return values.size();
    }
    
    public boolean insertInOrder(int key,Node<Student> pointer)
    {
        
        for (int i = 0; i < this.values.size(); i++) 
        {
            if (this.values.get(i) < key)
            {
                continue;
            }
            if (this.values.get(i) == key) 
            {
                return false;
            }
            
            this.values.add(i, key);
            this.pointers.add(i,pointer);
            
            return true;
        }
        
        this.values.add(key);
        this.pointers.add(pointer);
        
        return true;
    }
    
    
    public void deleteFromLeafNode(int key)
    {
        int i=0;
        
        while(this.values.get(i)!=key)
        {
            i++;
        }
        
        this.values.remove(i);
        this.pointers.remove(i);
        
    }
    
    public  void display(int spaces)
    {
        for(int i=0;i<spaces;i++)
        {
            System.out.print("  ");
        }
        
        
        System.out.print(this.values);
        
        
        System.out.println("");
        
        for(int k=0;k<this.children.size();k++)
        {
            this.children.get(k).display(spaces+4);
        }
    }
}
