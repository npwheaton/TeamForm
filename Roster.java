import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Roster implements Serializable {
private ArrayList<Employee> allEmploys;

public Roster(){allEmploys = new ArrayList();}


public void AddEmployees(Employee emp){allEmploys.add(emp);}
public void RemoveEmployees(String name){
    Employee fired = SendEmployee(name);
    if(fired!=null)
        {allEmploys.remove(fired);}
}
public Employee SendEmployee(String name){
    for (Iterator<Employee> all = allEmploys.iterator(); all.hasNext(); ) {
        Employee emp = (Employee) all.next();
        if(name.equals(emp.toString()))
            {return emp;}
    }
return null;
}
public ArrayList<String> SendNamesList(){
    ArrayList<String> names = new ArrayList();
    for (Iterator<Employee> all = allEmploys.iterator(); all.hasNext(); ) {
        Employee emp = (Employee) all.next();
        names.add(emp.toString());
    }
    return names;
}
public int RosterSize(){return allEmploys.size();}

public String toString(){
    String roster = "";
    for (Iterator<Employee> all = allEmploys.iterator(); all.hasNext(); ) {
        Employee emp = (Employee) all.next();
        roster+= emp + ": "+ emp.Printskills() + "\n";
    }
    return roster;
}
}