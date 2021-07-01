import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class MemberChromosome {
    private ArrayList<String> nameslist;
    private int Skillfitness;
    private Double Levelfitness;
    private String teamNames[];
    private Employee teamobj[];
    private Roster rost;
    private LinkedList<String> tasklist;
    private static int arraysize;
    private static Double mutatechance;
    private int birthgen;

    public MemberChromosome(Roster r,int size, LinkedList<String> tasks, Double chance, int birthday) {
    rost = r;
    arraysize = size;
    tasklist = tasks;
    nameslist = rost.SendNamesList();
    Skillfitness = 0;
    Levelfitness = 0.0;
    mutatechance = chance;
    birthgen = birthday;
    teamNames = new String[arraysize];
    teamobj = new Employee[arraysize];
    initializeTeam();
    

    }
    public String RandomNameFromList(){
        String name;
        Random rand = new Random();
        int temp = rand.nextInt(nameslist.size());
        name = nameslist.get(temp);
        nameslist.remove(temp);
    
        return name;
    }
    public void initializeTeam(){
        nameslist = rost.SendNamesList();
        for(int i = 0; i< arraysize; i++)
            {teamNames[i] = RandomNameFromList();}
    }
    public void initializeTeamObj(){
        for( int i = 0; i<arraysize; i++)
            {teamobj[i] = rost.SendEmployee(teamNames[i]);}
    }
    public void Inheritance(MemberChromosome Dad, MemberChromosome Mom)
        {Random rand = new Random();
        int temp = rand.nextInt(arraysize - 1);
        for (int i = 0; i<=temp; i++)
            {teamNames[i] = Dad.Sendgenes(i);}
        for (int i = temp+1; i<arraysize; i++)
            {teamNames[i] = Mom.Sendgenes(i);}
        
        
        }
    public String Sendgenes(int target){
        return teamNames[target];
    }
    public void mutate(){
        Random rand = new Random();
        Double temp;
        for (int i = 0; i<arraysize; i++)
            { temp = rand.nextDouble();
                if(temp < mutatechance)
                    {nameslist.add(teamNames[i]);
                    teamNames[i] = RandomNameFromList();
                    }

            }
        
    }
    public void FindSkill(String skill){
        String temp = "";
        String top = "empty";
        for(int i = 0; i<arraysize; i++)
            {temp = rost.SendEmployee(teamNames[i]).SendSkillLevel(skill);
            if(temp!=null)
                {if(temp.equals("master"))
                    {top = temp;
                    break;
                    }
                else if(temp.equals("intermediate") & (top.equals("empty") || top.equals("weak")))
                        {top = temp;}
                else if(temp.equals("weak") & top.equals("empty"))
                        {top = temp;}
                }

            }

        if(top.equals("master"))
            {Skillfitness++;
            Levelfitness++;}
        else if(top.equals("intermediate"))
            {Skillfitness++;
            Levelfitness+=0.75;}
        else if(top.equals("weak"))
            {Skillfitness++;
            Levelfitness+= 0.3;}
    }
    public void CalculateFitness(){
        for( int i = 0; i<tasklist.size(); i++)
            {FindSkill(tasklist.get(i));}
        Levelfitness= Levelfitness/ Skillfitness;
    }
    public int SendTasksdone(){
    Skillfitness = 0;
    Levelfitness = 0.0;
    CalculateFitness();
    return Skillfitness;
    }
    public Double SendTaskQuality(){
        Skillfitness = 0;
        Levelfitness = 0.0;
        CalculateFitness();
        return Levelfitness;
        }
    public Employee teammember(int x){
        return rost.SendEmployee(teamNames[x]);
    }
    public String toString(){
        String temp = "";
        for (int i =0; i<arraysize; i++)
            {temp+= teamNames[i]+ " ";}
        return temp;
    }
    public int Sendbirthgeneration(){
        return birthgen;
    }
}