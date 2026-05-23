import java.io.Serializable;


public class professor extends doctor implements Serializable{
    private static final long serialVersionUID = 1L;

    String institution_Name  ;
    public professor(String Name, int Id, Degree_type degree_type, String Degree_name, int Salary, String institution_Name) {
        super(Name, Id, degree_type, Degree_name, Salary);
        this.institution_Name=institution_Name;
    }

    public String get_institution_name() {
        return institution_Name;
    }
}
