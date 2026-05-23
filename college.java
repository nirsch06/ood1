import java.io.Serializable;
import java.util.HashSet;


public class college implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;

    private HashSet<Lecturer> Lecturers = new HashSet<>();

    private HashSet<Department> departments = new HashSet<>();

    private HashSet<Committee> Committees = new HashSet<>();

    public college(String name)
    {
        this.name = name;
    }

    public void addLecturer(Lecturer l)
    {
        if (l == null) return;
        if (findLecturer(l.getName()) != null) return;
        Lecturers.add(l);
    }

    public void add_department(Department d)
    {
        if (d == null) return;
        if (findDepartment(d.getDepartment_name()) != null) return;
        departments.add(d);
    }

    public void addCommittees(Committee c)
    {
        if (c == null) return;
        if (findCommittee(c.getCommittee_name()) != null) return;
        Committees.add(c);
    }

    public Lecturer findLecturer(String name)
    {
        for (Lecturer l : Lecturers)
        {
            if (l.getName().equalsIgnoreCase(name)) return l;
        }
        return null;
    }

    public Department findDepartment(String name)
    {
        for (Department d : departments)
        {
            if (d.getDepartment_name().equalsIgnoreCase(name)) return d;
        }
        return null;
    }

    public Committee findCommittee(String name)
    {
        for (Committee c : Committees)
        {
            if (c.getCommittee_name().equalsIgnoreCase(name)) return c;
        }
        return null;
    }

    public double averageCollegeSalary()
    {
        if (Lecturers.isEmpty()) return 0;
        int sum = 0;
        for (Lecturer l : Lecturers)
        {
            sum += l.getSalary();
        }
        return sum / (double) Lecturers.size();
    }

    public HashSet<Lecturer> getLecturers() { return Lecturers; }
    public int getNumber_of_lecturers() { return Lecturers.size(); }

    public HashSet<Committee> getCommittees() { return Committees; }
    public int getNumber_of_committees() { return Committees.size(); }

    public HashSet<Department> getDepartments() { return departments; }
    public int getNumber_of_departments() { return departments.size(); }
}

