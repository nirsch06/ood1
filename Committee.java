import java.io.Serializable;
import java.util.HashSet;

public class Committee implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String Committee_name;
    private Lecturer Committee_chairman;

    private HashSet<Lecturer> Committee_members = new HashSet<>();
    private Degree_type Degree_type_of_members;

    public Committee(String Committee_name, Lecturer Committee_chairman, Degree_type Degree_type_of_members)
    {
        this.Committee_name = Committee_name;
        this.Degree_type_of_members=Degree_type_of_members;
        setCommittee_chairman_name(Committee_chairman);
        addMemberIfMissing(Committee_chairman);
    }
    public  int getNumber_of_members(){return Committee_members.size();}

    public String getCommittee_name()
    {
        return Committee_name;
    }

    public Lecturer getCommittee_chairman()
    {
        return Committee_chairman;
    }

    public HashSet<Lecturer> getCommittee_members() {
        return Committee_members;
    }

    public Degree_type getDegree_type_of_members() {return Degree_type_of_members;}

    public void setCommittee_chairman_name(Lecturer chairman)
    {
        if (chairman == null) return;
        Degree_type t = chairman.getDegree_type();
        if (t == Degree_type.doctor || t == Degree_type.professor)
        {
            Committee_chairman = chairman;
        }
    }

    public void setDegree_type_of_members(Degree_type degree_type_of_members) {
        Degree_type_of_members = degree_type_of_members;
    }

    public void addCommittee_members(Lecturer l)
    {
        if (l == null) return;
        Committee_members.add(l);
    }

    public void removeMember(Lecturer l)
    {
        if (l == null) return;
        if (Committee_members.remove(l)) {
            if (Committee_chairman == l)
            {
                Committee_chairman = null;
            }
        }
    }

    private boolean contains(Lecturer l)
    {
        return Committee_members.contains(l);
    }

    private void addMemberIfMissing(Lecturer l)
    {
        if (!contains(l)) addCommittee_members(l);
    }

    @Override
    public String toString()
    {
        String chair = (Committee_chairman != null) ? Committee_chairman.getName() : "none";
        return "Committee \"" + Committee_name + "\"  chair=" + chair + "  members=" + Committee_members.size();
    }
}

