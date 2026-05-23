import java.io.Serializable;
import java.util.HashSet;

public class Department implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String Department_name;
    private int Number_of_students;

    private HashSet<Lecturer> Lecturers = new HashSet<>();

    public Department(String Department_name, int Number_of_students)
    {
        this.Department_name = Department_name;
        this.Number_of_students = Number_of_students;
    }
    public HashSet<Lecturer> getLecturers(){
        return Lecturers;
    }

    public String getDepartment_name()
    {
        return Department_name;
    }

    public void Add_leacturers(Lecturer l){
        if (l == null) return;
        if (contains(l)) return;
        this.Lecturers.add(l);
        l.setDepartment(this);
    }

    private boolean contains(Lecturer l)
    {
        return Lecturers.contains(l);
    }

    public double averageSalary()
    {
        if (Lecturers.isEmpty()) return 0;
        int sum = 0;
        for (Lecturer l : Lecturers)
        {
            sum += l.getSalary();
        }
        return sum / (double) Lecturers.size();
    }
    public int getNumber_of_lecturers(){
        return Lecturers.size();
    }

    @Override
    public String toString()
    {
        return "Department \"" + Department_name + "\"  students=" + Number_of_students +
               "  lecturers=" + Lecturers.size();
    }
}




