import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Employee implements Serializable {
    private ArrayList<String> skillset;
    private String name;
    private String sLevels[];

    public Employee(String nametag){
        name = nametag;
        skillset = new ArrayList();
        sLevels = new String[3];
        sLevels[0] = "weak";
        sLevels[1] = "intermediate";
        sLevels[2] = "master";
    }

    public void addskill(String newskill, int x){
        if(x<sLevels.length & x>0)
            {}
        else if(x<0)
            {x = 0;}
        else
            {x = 0;}
        String temp = newskill + "|" + sLevels[x];
            skillset.add(temp);
            System.out.println("Skill added");
            
        
    }
    public void removeskill(String skill){
        String removal = null;
        for (Iterator<String> all = skillset.iterator(); all.hasNext(); ) {
            String str = (String) all.next();
            if(str.contains(skill))
                {removal = str;}
        }
        if(removal!=null)
            {skillset.remove(removal);}
    }

    public String SendSkillLevel(String skill){
        String notfound = null;
        for (Iterator<String> all = skillset.iterator(); all.hasNext(); ) {
            String str = (String) all.next();
            
            if(str.startsWith(skill))
                {String[] temp = new String[2];
                    temp = str.split("\\|", 2);
                return temp[1];
                }
        }
        return notfound;         
    }
    
    public String Printskills(){
        String skills = "";
    for (Iterator<String> all = skillset.iterator(); all.hasNext(); ) {
        String sk = (String) all.next();
        skills+= sk + " ";
    }
    return skills;
    }
    public String toString(){
        return name;
    }
}