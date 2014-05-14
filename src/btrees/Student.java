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
public class Student 
{
    private String studentName;
    private int Id;
    ArrayList<Course> courses;
    
    public Student(int id,String name,ArrayList<Course> listOfCourses)
    {
        this.Id=id;
        this.studentName=name;
        this.courses=listOfCourses; 
    }
    
    public void setName(String name)
    {
        this.studentName=name;
    }
    
    public void setId(int id)
    {
        this.Id=id;
    }
    
    public void setCourses(ArrayList<Course> listOfCourses)
    {
        this.courses=listOfCourses;
    }
    
    public ArrayList<Course> getCourses()
    {
        ArrayList<Course> temp=new ArrayList<>(this.courses);
        return temp;
    }
    
    public void addCourse(Course c)
    {
        this.courses.add(c);
    }
    
    public String getName()
    {
        return this.studentName;
    }
    
    public int getId()
    {
        return this.Id;
    }
    
    public void displayStudent()
    {
        System.out.println();
        System.out.println("Roll# : "+this.Id);
        System.out.println("Name : "+this.studentName);
        System.out.println("****************** Courses Details ************************");
        for(int i=0;i<this.courses.size();i++)
        {
            System.out.println("ID of Course : "+this.courses.get(i).getId());
            System.out.println("Name of Course : "+this.courses.get(i).getName());
            System.out.println("Grade of Course : "+this.courses.get(i).getGrade());
        }
        System.out.println("****************** Courses Details End ************************");
        System.out.println();
    }
    
}
