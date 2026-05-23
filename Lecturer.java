import java.io.Serializable;


public class Lecturer implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String Name;
    private int Id;
    private Degree_type degree_type;
    private String Degree_name;
    private int Salary;
    private Department department=null;

    public Lecturer(String Name, int Id, Degree_type degree_type,
                    String Degree_name, int Salary)
    {
        this.Name = Name;
        this.Id = Id;
        this.degree_type = degree_type;
        this.Degree_name = Degree_name;
        this.Salary = Salary;

    }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }

    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public Degree_type getDegree_type() { return degree_type; }
    public void setDegree_type(Degree_type d) { degree_type = d; }

    public String getDegree_name() { return Degree_name; }
    public void setDegree_name(String n) { Degree_name = n; }

    public int getSalary() { return Salary; }
    public void setSalary(int s) { Salary = s; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department d) { department = d; }


}
