//itay dinachi 216027953
//ofek marko 216249771


import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.util.HashSet;
import java.io.*;

public class Main
{
    private static Scanner sc;
    private static final String DATA_FILE = "college_data.bin";

    public static void main(String[] args)
    {

        sc = new Scanner(System.in);
        college college;

        File dataFile = new File(DATA_FILE);
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                college = (college) ois.readObject();
                System.out.println("College data loaded from file.");
            } catch (Exception e) {
                System.out.println("Failed to load saved data. Starting fresh.");
                college = null;
            }  catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            college = null;
        }

        if (college == null) {
            System.out.print("Enter college name: ");
            college = new college(sc.nextLine().trim());
        }


        while (true)
        {
            printMenu();
            int choice = Integer.parseInt(sc.nextLine().trim());

            if (choice == 0)
            {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                    oos.writeObject(college);
                    System.out.println("Data saved successfully. Bye 😊");
                } catch (IOException e) {
                    System.out.println("Failed to save data.");
                }
                break;
            }


            switch (choice)
            {
                case 1: addLecturerUI(college); break;
                case 2: addCommitteeUI(college); break;
                case 3: assignMemberUI(college); break;
                case 4: changeChairUI(college); break;
                case 5: removeMemberUI(college); break;
                case 6: addDepartmentUI(college); break;
                case 7: System.out.println("College avg salary = " + college.averageCollegeSalary()); break;
                case 8: departmentAvgUI(college); break;
                case 9: listLecturers(college); break;
                case 10: listCommittees(college); break;
                case 11:Add_leaturer_to_department(college);break;
                case 12:Add_article(college);break;
                case 13:equal_art(college);break;
                case 14:equal_department(college);break;
                case 15:copy_committee(college);break;
                default: System.out.println("Wrong option!");
            }
        }
    }

    private static void printMenu()
    {
        System.out.println("\n0-Exit");
        System.out.println("1-Add lecturer");
        System.out.println("2-Add committee");
        System.out.println("3-Add member to committee");
        System.out.println("4-Change committee chair");
        System.out.println("5-Remove member from committee");
        System.out.println("6-Add department");
        System.out.println("7-College average salary");
        System.out.println("8-Department average salary");
        System.out.println("9-List lecturers");
        System.out.println("10-List committees");
        System.out.println("11-Add lecturer to department");
        System.out.println("12-add article");
        System.out.println("13- Comparison based on several articles");
        System.out.println("14- Comparison departments");
        System.out.println("15- duplicate committee");


        System.out.print("Choice: ");
    }

    private static Degree_type chooseDegree()  //תיקון השגיאה של סוג תואר לא נכון
    {
        System.out.print("Degree (1-first 2-second 3-doctor 4-prof): ");
        int opt = Integer.parseInt(sc.nextLine().trim());
        while (true){
            try {
                return Degree_type.values()[opt - 1];

            } catch (Exception e) {
                System.out.println("Wrong option!");
                System.out.print("Degree (1-first 2-second 3-doctor 4-prof): ");
                opt = Integer.parseInt(sc.nextLine().trim());
            }
        }
    }

    private static Lecturer pickLecturer(college col, String prompt)
    {
        System.out.print(prompt);
        String n = sc.nextLine().trim();
        Lecturer l = col.findLecturer(n);
        if (l == null) System.out.println("Lecturer not found.");
        return l;
    }

    private static Committee pickCommittee(college col, String prompt)
    {
        System.out.print(prompt);
        String n = sc.nextLine().trim();
        Committee c = col.findCommittee(n);
        if (c == null) System.out.println("Committee not found.");
        return c;
    }

    private static Department pickDepartment(college col, String prompt)
    {
        System.out.print(prompt);
        String n = sc.nextLine().trim();
        Department d = col.findDepartment(n);
        if (d == null) System.out.println("Department not found.");
        return d;
    }

    private static void addLecturerUI(college col)
    {
        String name = nameExist(col);
        System.out.print("Id: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Degree_type dt = chooseDegree();
        System.out.print("Degree name: ");
        String dname = sc.nextLine().trim();

        System.out.print("Salary: ");
        int sal = Integer.parseInt(sc.nextLine().trim());

        if (dt == Degree_type.first || dt == Degree_type.second) {
            Lecturer l = new Lecturer(name, id, dt, dname, sal);
            col.addLecturer(l);
            System.out.println("Lecturer added.");
        }
        if (dt == Degree_type.doctor){
             doctor l = new doctor(name, id, dt, dname, sal);
            col.addLecturer(l);
            System.out.println("Lecturer added.");

        }
        if (dt == Degree_type.professor){
            System.out.print("institution_Name: ");
            String institution_Name = sc.nextLine().trim();
            professor l = new professor(name, id, dt, dname, sal,institution_Name);
            col.addLecturer(l);
            System.out.println("Lecturer added.");
        }



    }
    public static String nameExist(college col) {
        while (true) {
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            if (col.findLecturer(name) != null) {
                System.out.println("This lecturer already exists!");
            }
            else {
                return name;
            }
        }
    }


    private static void addCommitteeUI(college col) throws Exception // טיפול בהאם יור הוא לא ד"ר או פרופסור
    {
        System.out.print("Committee name: ");
        String cname = sc.nextLine().trim();
        if (col.findCommittee(cname) != null)
        {
            System.out.println("Committee already exists!");
            return;
        }

        Lecturer chair = pickLecturer(col, "Chair name: ");
        if (chair == null) return;
        System.out.print("the type of the committee lectures ");
        Degree_type dt = chooseDegree();
        try {
            check_if_dr_ph(chair);
            Committee c = new Committee(cname, chair,dt);
            col.addCommittees(c);
            System.out.println("Committee added.");
         }
        catch (Exception e) {
            System.out.println("this lecturer is not a doctor or a professor and cannot be a chairman");

        }



    }

    private static void assignMemberUI(college col) {
        Committee c = pickCommittee(col, "Committee name: "); // ווידוי שלא נכנס מרצה שכבר חבר בוועדה
        Lecturer l = pickLecturer(col, "Lecturer name: ");
        Degree_type dtc = c.getDegree_type_of_members();
        Degree_type dtl = l.getDegree_type();
        if (dtl.equals(dtc)) {
            if (c != null && l != null) {
                try {
                    check_exist_in_committee(l, c);
                    c.addCommittee_members(l);
                    System.out.println("Member added.");
                } catch (Exception e) {
                    System.out.println("Member already exists in Committee .");
                }

            }
        }
        else {
            System.out.println("This Committee is only for " + dtc + " degree type lecturers" );
        }
    }

    private static void changeChairUI(college col) throws Exception // טיפול בהאם יור הוא לא ד"ר או פרופסור
    {
        Committee c = pickCommittee(col, "Committee name: ");
        Lecturer l = pickLecturer(col, "New chair: ");
        if (c != null && l != null)
        {
            try{
                check_if_dr_ph(l);
                c.setCommittee_chairman_name(l);
                System.out.println("Chair changed.");}
            catch (Exception e) {
                System.out.println("this lecturer is not a doctor or a professor and cannot be a chairman");
            }
        }
    }

    private static void removeMemberUI(college col)
    {
        Committee c = pickCommittee(col, "Committee name: ");
        Lecturer l = pickLecturer(col, "Lecturer name: ");
        if (c != null && l != null)
        {
            c.removeMember(l);
            System.out.println("Member removed.");
        }
    }

    private static void addDepartmentUI(college col)
    {
        System.out.print("Department name: ");
        String dname = sc.nextLine().trim();
        if (col.findDepartment(dname) != null)
        {
            System.out.println("Department exists!");
            return;
        }
        System.out.print("Students: ");
        int studs = Integer.parseInt(sc.nextLine().trim());

        Department d = new Department(dname, studs);
        col.add_department(d);
        System.out.println("Department added.");
    }

    private static void departmentAvgUI(college col)
    {
        Department d = pickDepartment(col, "Department name: ");
        if (d != null)
        {
            System.out.println("Avg salary = " + d.averageSalary());
        }
    }

    private static void listLecturers(college col)
    {
        for (Lecturer l : col.getLecturers())
        {
            System.out.println(l);
        }
    }

    private static void listCommittees(college col )
    {
        for (Committee c : col.getCommittees())
        {
            System.out.println(c);
        }
    }
    public  static void  Add_leaturer_to_department(college college) // ווידוי שלא נכנס מרצה שכבר חבר במחלקה
    {
        System.out.print("Enter department name: ");
        String department = sc.nextLine().trim();
        System.out.print("Enter lecturer name: ");
        String lecturer = sc.nextLine().trim();
        Department department1 = college.findDepartment(department);
        Lecturer l = college.findLecturer(lecturer);

        try {
            check_exist_in_department(l,department1);
            department1.Add_leacturers(((Lecturer) l));
            System.out.println("Member added.");
        }
        catch (Exception e) {
            System.out.println("Member already exists in department .");
        }



    }
    public static void equal_art(college college)
    {
        System.out.print("Enter name1 : ");
        String name1 = sc.nextLine().trim();
        System.out.print("Enter name2 : ");
        String name2 = sc.nextLine().trim();
        Lecturer lecturer1 = college.findLecturer(name1);
        Lecturer lecturer2 = college.findLecturer(name2);
        if (lecturer1 == null || lecturer2 == null)
        {
            System.out.println("Lecturers not found!");
            return;
        }
        int len1=0;
        int len2=0;

        len1=((doctor) lecturer1).get_Number_of_articles();




        len2=((doctor) lecturer2).get_Number_of_articles();



        if(len1>len2){
            System.out.println(name1 + " has more articles.");

        }
        else if(len2>len1){
            System.out.println(name2 + " has more articles.");
        }
        if(len1==len2){
            System.out.println(name1 +"and "+name2+ " has equal articles.");
        }




    }
    public  static  void Add_article(college college){
        System.out.print("Enter name : ");
        String name1 = sc.nextLine().trim();

        System.out.print("Enter article : ");
        String article = sc.nextLine().trim();
        doctor doctor1 = (doctor) college.findLecturer(name1);
        doctor1.AddArticle(article);

    }
    public  static  void equal_department(college college){
        System.out.print("Enter departemt 1 : ");
        String name1 = sc.nextLine().trim();
        System.out.print("Enter departemt 2 : ");
        String name2 = sc.nextLine().trim();
        Department department1 = college.findDepartment(name1);
        Department department2 = college.findDepartment(name2);
        System.out.print("Enter criterion (1 - Number of team members, 2 - Total articles)");
        int criterion = Integer.parseInt(sc.nextLine().trim());
        int sum1=0;
        int sum2=0;
        if(criterion==1){
            if(department1.getNumber_of_lecturers()>department2.getNumber_of_lecturers()){
                System.out.println(name1 + " has more lecturers.");


            }
            else{
                if(department2.getNumber_of_lecturers()>department1.getNumber_of_lecturers()){
                    System.out.println(name2 + " has more lecturers.");

                }
                else{
                    System.out.println(name1 +name2 +"has equal lecturers. ");
                }
            }
        }
        if(criterion==2){

            for(Lecturer lecturer1 : department1.getLecturers()){
                if (lecturer1 instanceof doctor){
                    sum1+=((doctor) lecturer1).get_Number_of_articles();
                }

            }
            for(Lecturer lecturer2 : department2.getLecturers()){
                if (lecturer2 instanceof doctor){
                    sum2+=((doctor) lecturer2).get_Number_of_articles();
                }

            }
            if(sum1>sum2){
                System.out.println(name1 + " has more articles.");


            }
            else{
                if(sum2>sum1){
                    System.out.println(name2 + " has more articles.");

                }
                else{
                    System.out.println(name1+"and " +name2 +"has equal articles. ");
                }
            }
        }

    }
    public  static  void copy_committee(college college ){
        System.out.print("Enter  committee name  : ");
        String name1 = sc.nextLine().trim();
        Committee committee1 = college.findCommittee(name1);
        String newName = committee1.getCommittee_name();
        Degree_type dt = committee1.getDegree_type_of_members();
        newName="new-"+newName;
        Committee committee2 = new Committee(newName,committee1.getCommittee_chairman(),dt);
        for (Lecturer l : committee1.getCommittee_members()){
            committee2.addCommittee_members(l);
        }

        college.addCommittees(committee2);

    }
    public static void check_if_dr_ph(Lecturer lecturer) throws Exception {
        if (lecturer.getDegree_type()==Degree_type.first || lecturer.getDegree_type()==Degree_type.second ){
            throw new Exception("this lecturer is not a doctor or a professor");

        }
    }
    public static void check_exist_in_department(Lecturer lecturer, Department department) throws Exception {
        if (department.getLecturers().contains(lecturer)) {
            throw new Exception("this lecturer is already in the department");
        }
    }

    public static void check_exist_in_committee(Lecturer lecturer, Committee committee) throws Exception {
        if (committee.getCommittee_members().contains(lecturer)) {
            throw new Exception("this lecturer is already in the committee");
        }
    }



















}

