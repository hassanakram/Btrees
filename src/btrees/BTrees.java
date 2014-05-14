/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package btrees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author hasan
 */

public class BTrees 
{
    
    private static DoublyLinkedList<Student> sortredListOfStudents=new DoublyLinkedList<>();
    
    private static BPlusTree b;
    
    private static DoublyLinkedList<Student> listOfStudents=new DoublyLinkedList<>();
    
    
    public static int InputFromFile(DoublyLinkedList<Student> listOfStudents)
    {
        try 
        {
            String filename=JOptionPane.showInputDialog("Please Enter file name : ");
            
            FileReader reader = new FileReader(new File(filename).getAbsolutePath());
            BufferedReader buffReader = new BufferedReader(reader);
            String s;
            s = buffReader.readLine();
            int t=Integer.parseInt(s);
            while((s = buffReader.readLine()) != null)
            {
                ArrayList<Course> courses=new ArrayList<>();
                
                Student std=new Student(1,"",null);
                String[] parts = s.split(",");
                
                std.setId(Integer.parseInt(parts[0]));
                std.setName(parts[1]);
                
                for(int i=2;i<parts.length;i++)
                {
                    String temp=parts[i];
                    String[] courseDetail=temp.split("#");
                    courses.add(new Course(courseDetail[0],courseDetail[1],i));
                }
                
                std.setCourses(courses);
                listOfStudents.addFirst(std);
                
                insertInOrder(std);
            }
            
            return t;
        }
        catch(IOException e)
        {
            System.out.println("File not Found !");
            return -1;
        }
    }
    
    public static void display(DoublyLinkedList<Student> listOfStudents)
    {
        Node<Student> n=listOfStudents.getLast();
        
        while(n.getPrevious()!=null)
        {
            System.out.println("ID : "+n.getData().getId()+",  Name : "+n.getData().getName());
            n=n.getPrevious();
        }
    }
    
    
    
    
    public static void main(String[] args) 
    {
        
//        InputFromFile(listOfStudents);
//        
//        //display(listOfStudents);
//        
//        /*********** Insertion testing **************************** */
//        b=new BPlusTree(2);
//        
//        Node<Student> n=listOfStudents.getLast();
//        
//        while(n.getPrevious()!=null)
//        {
//            b.insert(n.getData().getId(), n);
//            n=n.getPrevious();
//            b.displayTree();
//        }
//        
//        b.delete(25);
//        
//        b.displayTree();
//        
//        b.displayTree();
//        
//        b.delete(23);
//        
//        b.displayTree();
        
        int input=0;
        String inputOption=JOptionPane.showInputDialog("Enter 1 to enter file name : \n Enter 2 to give value of 't' to build empty tree \n"
            + " Enter 3 to insert a new Student in tree : \n Enter 4 to delete a student from tree \n Enter 5 to store tree on file \n"
            + "Enter 6 to display tree \n Enter 7 to search a value in tree \n Enter 8 to update a Student Data in tree \n Enter -1 to exit " );
        input=Integer.parseInt(inputOption);
        while(input!=-1)
        {
            if(input==-1)
            {
                break;
            }
            else if(input==1)
            {
                int t=InputFromFile(listOfStudents);
                if(t>1)
                {
                    b=new BPlusTree(t);
                    Node<Student> n=listOfStudents.getFirst();
                    while(n.getNext()!=null)
                    {
                        b.insert(n.getData().getId(), n);
                        n=n.getNext();
                        b.displayTree();
                    }
                }
                else if(t==-1)
                {
                    System.out.println("File opening problem !");
                }
                else
                {
                    System.out.println("Invalid t value !");
                }
                
            }
            else if(input==2)
            {
                String key=JOptionPane.showInputDialog("Enter value of 't' : ");
                int t=Integer.parseInt(key);
                    if(t>1)
                    {
                        b=new BPlusTree(t);
                    }
                    else
                    {
                        System.out.println("Invalid value of 't'");
                    }
            }
            else if(input==3)
            {
                
                ArrayList<Course> list=new ArrayList<>();
                String ID=JOptionPane.showInputDialog("Enter ID of course : \n ( -1 to stop entering courses) ");
                int id=Integer.parseInt(ID);
                while(id!=-1)
                {
                    String name=JOptionPane.showInputDialog("Enter name of course :  ");
                    String grade=JOptionPane.showInputDialog("Enter grade of course :  ");
                    
                    list.add(new Course(name,grade,id));
                    
                    ID=JOptionPane.showInputDialog("Enter ID of course : \n ( -1 to stop entering courses) ");
                    id=Integer.parseInt(ID);
                    
                }
                
                ID=JOptionPane.showInputDialog("Enter ID of Student :  ");
                id=Integer.parseInt(ID);
                while(b.searchStudent(id)!=null)
                {
                    ID=JOptionPane.showInputDialog("Enter ID of course : \n ( Student already exist with id = "+  id +") ");
                    id=Integer.parseInt(ID);
                }
                
                String name=JOptionPane.showInputDialog("Enter name of Student :  ");
                Student s=new Student(id,name,list);
                //inserting in sorted list
                insertInOrder(s);
                
                listOfStudents.addLast(s);
                
                b.insert(id, listOfStudents.getLast() );
                
            }
            else if(input==4)
            {
                String ID=JOptionPane.showInputDialog("Enter ID of Student :  ");
                int id=Integer.parseInt(ID);
                while(b.searchStudent(id)==null)
                {
                    ID=JOptionPane.showInputDialog("Enter ID of student : \n ( Student dos not exist with id = "+  id +") ");
                    id=Integer.parseInt(ID);
                }
                
                b.delete(id);
                deleteFromLists(id);
                
            }
            else if(input==5)
            {
                try
                {
                    String fileName=JOptionPane.showInputDialog("Enter File name to save (without extension) : "+".txt");
                    File file=new File(fileName);       
                    if(!file.exists())
                    {
                        file.createNewFile();
                    }
                    
                    FileWriter fw=new FileWriter(file,true);
                    
                    fw.append(b.getT()+"\n");
                    
                    Node<Student> n=listOfStudents.getLast();
                    
                    while(n.getPrevious()!=null)
                    {
                       fw.append( n.getData().getId() + "," );
                       fw.append( n.getData().getName() ); 
                       for(int i=0;i<n.getData().getCourses().size();i++)
                       {
                           fw.append(","+ n.getData().getCourses().get(i).getName()+"#"+n.getData().getCourses().get(i).getGrade() );
                       }
                       
                       fw.append("\n");
                       
                       n=n.getPrevious();
                    }
                    
                    fw.close();
                }
                catch(Exception e)
                {
                    System.out.print(e.toString());;
                }
            }
            else if(input==6)
            {
                b.displayTree();
            }
            else if(input==7)
            {
                String key=JOptionPane.showInputDialog("Enter key to search : ");
                int id=Integer.parseInt(key);
                    
                Student s= b.searchStudent(id);
                if(s!=null)
                {
                    s.displayStudent();
                }
                else
                {
                    System.out.println("Not Found");
                }
                
            }
            else if(input==8)
            {
                String key=JOptionPane.showInputDialog("Enter Roll to find and update student : ");
                
                int id=Integer.parseInt(key);
                
                
                Student s= b.searchStudent(id);
                
                ArrayList<Course> listOfCourses=s.courses;
                String option=JOptionPane.showInputDialog("Enter 1 to add course : \n Enter 2 to update name of student : \n Enter -1 to cancel :");
                int option_id=Integer.parseInt(option);
                if(option_id==1)
                {
                    String ID=JOptionPane.showInputDialog("Enter ID of course : \n ( -1 to stop entering courses) ");
                    id=Integer.parseInt(ID);
                    while(id!=-1)
                    {
                        String name=JOptionPane.showInputDialog("Enter name of course :  ");
                        String grade=JOptionPane.showInputDialog("Enter grade of course :  ");

                        listOfCourses.add(new Course(name,grade,id));

                        ID=JOptionPane.showInputDialog("Enter ID of course : \n ( -1 to stop entering courses) ");
                        id=Integer.parseInt(ID);

                    }
                
                }
                else if(option_id==2)
                {
                    
                    String name=JOptionPane.showInputDialog("Enter new name of Student : ");
                    s.setName(name);
                } 
                else
                {
                    
                }
                
            }
            
            inputOption=JOptionPane.showInputDialog("Enter 1 to enter file name : \n Enter 2 to give value of 't' to build empty tree \n"
            + " Enter 3 to insert a new Student in tree : \n Enter 4 to delete a student from tree \n Enter 5 to store tree on file \n"
            + "Enter 6 to display tree \n Enter 7 to search a value in tree \n Enter 8 to update a Student Data in tree \n Enter -1 to exit " );
            input=Integer.parseInt(inputOption);
        }   
    }
    
    
    public static void insertInOrder(Student s)
    {
         Node<Student> current= sortredListOfStudents.getFirst();
         
         if(sortredListOfStudents.isEmpty())
         {
             sortredListOfStudents.addFirst(s);
         }
         else
         { 
            Node<Student> prev=current.getPrevious();
             
            while((current!=null)&&(!(current.equals(sortredListOfStudents.getLast())))&&current.getData().getId()<s.getId())
            {
                Node<Student> temp=current;
                current=current.getNext();
                prev=temp;
            }
            if(!current.equals(sortredListOfStudents.getLast()))
            {
                sortredListOfStudents.addAfter(prev, s);
            }
            else
            {
                sortredListOfStudents.addAfter(current, s);
            }
            
         }
    }
    
    public static void deleteFromLists(int key)
    {
        Node<Student> n=listOfStudents.getFirst();
        while(n.getData().getId()!=key)
        {
            n=n.getNext();
        }
        
        listOfStudents.remove(n);
        
        n=sortredListOfStudents.getFirst();
        while(n.getData().getId()!=key)
        {
            n=n.getNext();
        }
        
        sortredListOfStudents.remove(n);
        
        
    }
    
}
