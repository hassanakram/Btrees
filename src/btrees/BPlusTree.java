/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package btrees;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sun.misc.Queue;

/**
 *
 * @author hasan
 */
public class BPlusTree 
{
    private TreeNode root;
    private int t;
    
    public BPlusTree()
    {
        
    }
    
    public BPlusTree(int t)
    {
        this.t=t;
        this.root=new TreeNode();
    }
    
    public boolean insert(int key,Node<Student> pointer)
    {
        TreeNode parentCarrent=null;
        TreeNode current=root;
        boolean inserted=false;
        while(!inserted)
        {
            if(isFull(current))
            {
                
               current= split(parentCarrent,current,key,pointer);
               
            }
            else
            {
                if(current.isLeaf())
                {
                    TreeNode temp=current;
                    current.insertInOrder(key, pointer);
                    parentCarrent=temp;
                    inserted=true;
                }
                else
                {
                    TreeNode temp=current;
                   current = moveToAppropriateChild(current,key,pointer);
                   parentCarrent=temp;
                }
            }
        }
        return true;
    }
    
    TreeNode moveToAppropriateChild(TreeNode current,int key,Node<Student> pointer)
    {
        int i;
        for(i=0;i<current.values.size();i++)
        {
            if(key<current.values.get(i))
            {
                current= current.children.get(i);
                return current;
            }
        }
        
        current= current.children.get(i);
        return current;
    }
    
    
    public boolean hasPlace(TreeNode t)
    {
        if(t.numberOfKeys()>=this.t)
        {
            return true;
        }
        
        return false;
    }
    
    
    public TreeNode split(TreeNode parentNode, TreeNode node,int key,Node<Student> pointer)
    {
        if(node.equals(this.root))
        {
            if(this.root.isLeaf())
            {
                this.root=new TreeNode();

                TreeNode newNode=new TreeNode();
                int i=0;
                int size=node.values.size()/2;
                for(i=0;i<size;i++)
                {
                    newNode.pointers.add(node.pointers.remove(0));
                    newNode.values.add(node.values.remove(0));
                }

                this.root.values.add(node.values.remove(0));
                this.root.pointers.add(node.pointers.remove(0));

                this.root.children.add(newNode);
                this.root.children.add(node);

                if(key<this.root.values.get(0))
                {
                    return  newNode;
                }
                else
                {
                   return node;
                }
            }
            else
            {
                this.root=new TreeNode();
                
                TreeNode newNode=new TreeNode();
                int i=0;
                int size=node.values.size()/2;
                
                for(i=0;i<size;i++)
                {
                    newNode.pointers.add(node.pointers.remove(0));
                    newNode.values.add(node.values.remove(0));
                    newNode.children.add(node.children.remove(0));
                }
                
                newNode.children.add(node.children.remove(0));
                   
                this.root.values.add(node.values.remove(0));
                this.root.pointers.add(node.pointers.remove(0));

                this.root.children.add(newNode);
                this.root.children.add(node);

                if(key<this.root.values.get(0))
                {
                    return  newNode;
                }
                else
                {
                   return node;
                }
            }
            
        }
        else
        {
            TreeNode newNode=new TreeNode();
            int i=0;
            for(i=0;i<node.values.size()/2;i++)
            {
                newNode.pointers.add(node.pointers.remove(0));
                newNode.values.add(node.values.remove(0));
                if(!node.isLeaf())
                {
                    newNode.children.add(node.children.remove(0));
                }
            
            }
            
            if(!node.isLeaf())
            {
                newNode.children.add(node.children.remove(0));
            }
            
            int val=node.values.remove(0);
            
            parentNode.insertInOrder(val,node.pointers.remove(0));
            
            for(i=0;i<parentNode.values.size();i++)
            {
                if(val>parentNode.values.get(i))
                {
                    
                }
                else
                {
                    break;
                }
            }
            
            parentNode.children.add(i, newNode);
            
            if(key<val)
            {
                return newNode;
            }
            else
            {
                return node;
            }
            
            
        }
    }
    
    
    public boolean isFull(TreeNode current)
    {
        if(current.values.size()==(this.t*2-1))
        {
            return true;
        }
        
        return false;
    }
    
    public boolean isMinimal(TreeNode node)
    {
        if(node.values.size()==t-1)
        {
            return true;
        }
        
        return false;
    }
    
    
    
    public TreeNode deleteFromLeaf(TreeNode parent,TreeNode current,int key)
    {
        if(!this.isMinimal(current))
        {
            current.deleteFromLeafNode(key);
            //no need to update current pointer
            return null;
        }
        else
        {
            if(! lookRight(parent,current,key))
            {
               if(! lookLeft(parent,current,key))
               {
                   current=mergeWithRightOrRight(parent,current);
                   return current;
               }
            }
            
            //no need to update current pointer
            return null;
            
        }
        
    }
    
    public boolean lookRight(TreeNode parent,TreeNode current,int key)
    {
        TreeNode rightSibling;
        for(int i=0;i<parent.values.size();i++)
        {
            if(parent.children.get(i)==current)
            {
                if(i==(parent.children.size()+1))
                {
                    return false; // left child not exist
                }
                
                if(this.isMinimal(rightSibling=parent.children.get(i+1)))
                {
                    return false;
                }
                else
                {
                    // delete the target item
                    current.deleteFromLeafNode(key); 
                    
                    /* rotation from 
                     * left to right 
                     */
                    
                    // moving element from parent to current child
                    current.insertInOrder(parent.values.remove(i), parent.pointers.remove(i));
                    
                    //moving element from left sibling to parent
                    parent.insertInOrder(rightSibling.values.remove(0), rightSibling.pointers.remove(0));
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    public boolean rotateRight(TreeNode parent,TreeNode current)
    {
        TreeNode rightSibling;
        for(int i=0;i<parent.values.size();i++)
        {
            if(parent.children.get(i)==current)
            {
                if(i==(parent.children.size()+1))
                {
                    return false; // left child not exist
                }
                
                if(this.isMinimal(rightSibling=parent.children.get(i+1)))
                {
                    return false;
                }
                else
                {
                    /* rotation from 
                     * left to right 
                     */
                    
                    // moving element from parent to current child
                    current.insertInOrder(parent.values.remove(i), parent.pointers.remove(i));
                    
                    //moving element from left sibling to parent
                    parent.insertInOrder(rightSibling.values.remove(0), rightSibling.pointers.remove(0));
                    
                    if(!current.isLeaf())
                    {
                        current.children.add(rightSibling.children.remove(0));
                    }
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean lookLeft(TreeNode parent,TreeNode current,int key)
    {
        TreeNode leftSibling;
        for(int i=0;i<parent.children.size();i++)
        {
            if(parent.children.get(i)==current)
            {
                if(i==0)
                {
                    return false; // left child not exist
                }
                
                if(this.isMinimal(leftSibling=parent.children.get(i-1)))
                {
                    return false;
                }
                else
                {
                    current.deleteFromLeafNode(key); // delete the target item
                    
                    /* rotation from 
                     * left to right 
                     */
                    
                    // moving element from parent to current child
                    current.insertInOrder(parent.values.remove(i-1), parent.pointers.remove(i-1));
                    
                    //moving element from left sibling to parent
                    
                    parent.insertInOrder(leftSibling.values.remove(leftSibling.values.size()-1), leftSibling.pointers.remove(leftSibling.pointers.size()-1));
                    return true;
                }
                
            }
        }
        
        return false;
    }
    
    
    public boolean rotateLeft(TreeNode parent,TreeNode current)
    {
        TreeNode leftSibling;
        for(int i=0;i<parent.children.size();i++)
        {
            if(parent.children.get(i)==current)
            {
                if(i==0)
                {
                    return false; // left child not exist
                }
                
                if(this.isMinimal(leftSibling=parent.children.get(i-1)))
                {
                    return false;
                }
                else
                {
                    
                    /* rotation from 
                     * left to right 
                     */
                    
                    // moving element from parent to current child
                    current.insertInOrder(parent.values.remove(i-1), parent.pointers.remove(i-1));
                    
                    
                    
                    //moving element from left sibling to parent
                    
                    
                    
                    
                    
                    parent.insertInOrder(leftSibling.values.remove(leftSibling.values.size()-1), leftSibling.pointers.remove(leftSibling.pointers.size()-1));
                    
                    //checking leaf node merging or inner node 
                    if(!current.isLeaf())
                    {
                        current.children.add(0,leftSibling.children.remove(leftSibling.children.size()-1));
                    }
                    
                    return true;
                } 
            }
        }
        
        return false;
    }
    
    public TreeNode mergeWithRightOrRight(TreeNode parent,TreeNode current)
    {
        TreeNode target;
        boolean leftChild=true;
        boolean rightChild=true;
        for(int i=0;i<parent.children.size();i++)
        {
            if(parent.children.get(i)==current)
            {
                if(i==0)
                {
                    //left child not exist 
                    leftChild=false;
                }
                
                if(i==parent.children.size()-1)
                {
                    rightChild=false;
                }
                
                if(leftChild)
                {
                    if(this.isMinimal(target=parent.children.get(i-1)))
                    {
                        parent.children.remove(i);
                        target.insertInOrder(parent.values.remove(i-1),parent.pointers.remove(i-1) );
                        
                        
                        //checking leaf node merging or inner node 
                        if(!current.isLeaf())
                        {
                            target.children.add(current.children.remove(0));
                        }
                        
                        // moving all values from right to left child for merging purpose
                        while(current.values.size()>0)
                        {
                            target.insertInOrder(current.values.remove(0), current.pointers.remove(0));
                            
                            //checking leaf node merging or inner node 
                            if(!current.isLeaf())
                            {
                                target.children.add(current.children.remove(0));
                            }
                        }
                        
                        if(parent.equals(root)&&parent.values.size()==0)
                        {
                            this.root=target;
                        }
                        
                        return target;
                        
                    }
                }
                
                if(rightChild)
                {
                    if(this.isMinimal(target=parent.children.get(i+1)))
                    {
                        parent.children.remove(i+1);
                        current.insertInOrder(parent.values.remove(i),parent.pointers.remove(i));
                        
                        //checking leaf node merging or inner node 
                        if(!current.isLeaf())
                        {
                            current.children.add(target.children.remove(0));
                        }
                        // moving all values from right to left child for merging purpose
                        while(target.values.size()>0)
                        {
                            current.insertInOrder(target.values.remove(0), target.pointers.remove(0));
                            
                            //checking leaf node merging or inner node 
                            if(!current.isLeaf())
                            {
                                current.children.add(target.children.remove(0));
                            }
                        }
                        
                        if(parent.equals(root)&&parent.values.size()==0)
                        {
                            this.root=current;
                        }
                        
                        return current;
                        
                    }
                }
            }
        }
        
        return null;
    }
    
    
    public boolean delete(int rollNo)
    {
        TreeNode parentCarrent=null;
        TreeNode current=root;
        
        boolean deleted=false;
        
        while(!deleted)
        {
            if(current.isLeaf())
            {
                TreeNode tempCurrent=deleteFromLeaf(parentCarrent,current,rollNo);
                    
                if(tempCurrent==null)
                {
                    deleted=true;
                }
                else
                {
                    current=tempCurrent;
                    deleted=false;
                }
            }
            else if(!current.equals(root))
            {
                int i;
                if(current.values.size()==t-1)
                {
                    if(! rotateRight(parentCarrent,current))
                    {
                       if(! rotateLeft(parentCarrent,current))
                       {
                           current=mergeWithRightOrRight(parentCarrent,current);
                       }
                    }
                }
                
                for(i=0;i<current.values.size();i++)
                {
                    if(rollNo<=current.values.get(i))
                    {
                        break;
                    }
                }
                
                
                
                if((i<current.values.size())&&i==current.values.size()&&rollNo==current.values.get(i-1))
                {
                    deletePredecessorOrSuccessor(parentCarrent,current,i-1);
                    deleted=true;
                }
                else if((i<current.values.size())&&rollNo==current.values.get(i))
                {
                    deletePredecessorOrSuccessor(parentCarrent,current,i);
                    deleted=true;
                }
                else
                {
                    TreeNode child=current.children.get(i);
                    TreeNode temp=current;
                    current=child;
                    parentCarrent=temp;
                }
            }
            else
            {
                if(current.equals(root))
                {
                    int i;
                    for(i=0;i<current.values.size();i++)
                    {
                        if(rollNo<=current.values.get(i))
                        {
                            break;
                        }
                    }
                    
                    if((i<current.values.size())&&i==current.values.size()&&rollNo==current.values.get(i-1))
                    {
                        deletePredecessorOrSuccessor(parentCarrent,current,i-1);
                        deleted=true;
                    }
                    else if((i<current.values.size())&&rollNo==current.values.get(i))
                    {
                        deletePredecessorOrSuccessor(parentCarrent,current,i);
                        deleted=true;
                    }
                    else
                    {
                        TreeNode child=current.children.get(i);
                        TreeNode temp=current;
                        current=child;
                        parentCarrent=temp;
                    }

                }
            }
            
        }
        
        
        return true;
    }
    
    
    public boolean deletePredecessorOrSuccessor(TreeNode parent,TreeNode current,int i)
    {
        this.displayTree();
        if(i!=0)
        {
            TreeNode temp=current.children.get(i);
            while(!temp.isLeaf())
            {
                temp=temp.children.get(temp.children.size()-1);
            }
            
            Node data=temp.pointers.get(temp.pointers.size()-1);
            
            int old_key=current.values.get(i);
            
            this.delete(((Student)data.getData()).getId());
            
          
            this.replace(this.root, old_key,((Student)data.getData()).getId(), data);
            
            return true;
        }
        else
        {
            TreeNode temp=current.children.get(i+1);
            while(!temp.isLeaf())
            {
                temp=temp.children.get(0);
            }
            
            Node data=temp.pointers.get(0);
            
            int old_key=current.values.get(i);
            
            this.delete(((Student)data.getData()).getId());
            
            this.replace(this.root, old_key,((Student)data.getData()).getId(), data);
            
            return true;
        }
    }
    
    
    public TreeNode mergeChildren(TreeNode parent,int i)
    {
        TreeNode target=parent.children.get(i);
        TreeNode current=parent.children.get(i+1);
        
        parent.children.remove(i);
        target.insertInOrder(parent.values.remove(i),parent.pointers.remove(i) );

        //checking leaf node leaf or inner node 
        if(!current.isLeaf())
        {
            target.children.add(current.children.remove(0));
        }

        // moving all values from right to left child for merging purpose
        while(current.values.size()>0)
        {
            target.insertInOrder(current.values.remove(0), current.pointers.remove(0));

            //checking leaf node merging or inner node 
            if(!current.isLeaf())
            {
                target.children.add(current.children.remove(0));
            }
        }
        
        return target;
    }
    
    
    public void displayTree()
    {
        this.root.display(0);       
        System.out.println("\n ******************************************************************** \n");
    }
    
    
    
    private Student replace(TreeNode n,int key,int newkey,Node<Student> data)
    {
        int i=0;
        for(i=0;i<n.values.size();i++)
        {
            if(key<=n.values.get(i))
            {
                i++;
                break;
            }
        }
        
        if(key==n.values.get(i-1))
        {
            n.values.remove(i-1);
            n.values.add(i-1,newkey);
            
            n.pointers.remove(i-1);
            n.pointers.add(i-1,data);
            
            return n.pointers.get(i-1).getData();
        }
        else if(n.isLeaf())
        {
            return null;
        }
        
        
        if(key<n.values.get(i-1))
        {
            return replace(n.children.get(i-1),key,newkey,data);
        }
        else
        {
            return replace(n.children.get(i),key,newkey,data);
        }
        
    }
    
    private Student search(TreeNode n,int key)
    {
        int i=0;
        for(i=0;i<n.values.size();i++)
        {
            if(key<=n.values.get(i))
            {
                i++;
                break;
            }
        }
        
        if(key==n.values.get(i-1))
        {
            return n.pointers.get(i-1).getData();
        }
        else if(n.isLeaf())
        {
            return null;
        }
        
        
        if(key<n.values.get(i-1))
        {
            return search(n.children.get(i-1),key);
        }
        else
        {
            return search(n.children.get(i),key);
        }
        
    }
    
    
    public Student searchStudent(int key)
    {
        return search(this.root,key);
    }
    
    public int getT()
    {
        return this.t;
    }
    
}