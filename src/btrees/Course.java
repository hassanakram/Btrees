
package btrees;

/**
 *
 * @author hasan
 */
public class Course 
{
    private String courseName;
    private String GradeEarned;
    private int CourseID;
    
    public Course(String c,String g,int id)
    {
        this.CourseID=id;
        this.GradeEarned=g;
        this.courseName=c;
    }
    
    public String getName()
    {
        return this.courseName;
    }
    
    public void setName(String n)
    {
        this.courseName=n;
    }
    
    public String getGrade()
    {
        return this.GradeEarned;
    }
    
    public void setGrade(String g)
    {
        this.GradeEarned=g;
    }
    
    public int getId()
    {
        return this.CourseID;
    }
    
    public void setId(int id)
    {
        this.CourseID=id;
    }
}
