import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class TeamsManager {
    private ArrayList<MemberChromosome> pop;
    private int popsize;
    private int avgtasksdone;
    private Double avgquality;
    private MemberChromosome hightasks;
    private MemberChromosome highquality;
    private int size;
    private LinkedList<String> tasks;
    private Double chance;
    private Roster rost;
    private int lifetime;


    public TeamsManager(int popsize, Roster r,int size, LinkedList<String> tasks, Double chance, int lifetime ){
    this.popsize = popsize;
    rost = r;
    this.size = size;
    this.tasks = tasks;
    this.chance = chance;
    this.lifetime = lifetime;
    avgtasksdone = 0;
    avgquality = 0.0;
    pop = new ArrayList<MemberChromosome>();
    
    
    }
    public void generatepopulation(){
        for (int i = 0; i<popsize; i++){
           
            MemberChromosome ancestor = new MemberChromosome(rost, size, tasks, chance, 0);
            pop.add(ancestor);
        }
    }
    public void FindAvg() {
        int task = 0;
        Double quality = 0.0;
           for (int i = 0; i<pop.size(); i++ ) {
            MemberChromosome chromo = (MemberChromosome) pop.get(i);
            task+= chromo.SendTasksdone();
            quality+= chromo.SendTaskQuality();
            } 
        avgtasksdone= task/pop.size();
        avgquality= quality/pop.size();
    
    }

    public void findhighfit(){
    int task = 0;
    Double quality = 0.0;
    for (int i = 0; i<pop.size(); i++  ) {
        MemberChromosome chromo = (MemberChromosome) pop.get(i);
        if(chromo.SendTasksdone()>task)
            {task = chromo.SendTasksdone();
            hightasks = chromo;
            }
        else if((chromo.SendTasksdone()==task) & chromo.SendTaskQuality()>hightasks.SendTaskQuality())
            {task = chromo.SendTasksdone();
            hightasks = chromo;
            }
        if(chromo.SendTaskQuality()>=quality)
            {quality = chromo.SendTaskQuality();
                highquality = chromo;
            }
        } 
    }
    public void killOldchromos(int currentgen){
        for (int i = 0; i<pop.size(); i++ ) {//remove chromosomes tht are old
            MemberChromosome chromo = (MemberChromosome) pop.get(i);
              if(((currentgen-chromo.Sendbirthgeneration())>lifetime)  & (chromo==hightasks) || pop.size()<3)
                  {}
            else if(((currentgen-chromo.Sendbirthgeneration())>lifetime)  & pop.size()>2)
                  {pop.remove(i);}
            }
    }
    public void reproduce(int currentgen){
        LinkedList<MemberChromosome> breeders = new LinkedList<MemberChromosome>();
        LinkedList<MemberChromosome> qualbreeders = new LinkedList<MemberChromosome>();
        LinkedList<MemberChromosome> newgen = new LinkedList<MemberChromosome>();
        MemberChromosome dad, mom;
        for (int i = 0; i<pop.size(); i++ ) {//remove chromosomes tht are below average
            MemberChromosome chromo = (MemberChromosome) pop.get(i);
              if((chromo.SendTasksdone()<avgtasksdone))
                  {pop.remove(i);}
                else if((chromo.SendTasksdone()>avgtasksdone))
                    {breeders.add(pop.get(i));}

                  
            }
            for (int i = 0; i<breeders.size(); i++ ) {
                MemberChromosome chromo = (MemberChromosome) breeders.get(i);
                    if((chromo.SendTaskQuality()>avgquality))
                        {qualbreeders.add(breeders.get(i));
                        breeders.remove(i);}
    
                      
                }
        
        for (int i= 0; i<(popsize-pop.size()); i++){//replace the deleted chromosomes with new ones
            Random rand = new Random();
            if(qualbreeders.size()>1)
            {int temp2 = rand.nextInt(qualbreeders.size());
                 dad = qualbreeders.get(temp2);
                int temp3;
                do{
                    temp3 = rand.nextInt(qualbreeders.size());
                   }while(temp2==temp3);
                 mom = qualbreeders.get(temp3);
            }
            else if(qualbreeders.size()==1 & breeders.size()>0)
            {
                dad = qualbreeders.get(0);
               int temp3;
                temp3 = rand.nextInt(breeders.size());
                mom = breeders.get(temp3);

            }
            else if(qualbreeders.size()==1 & breeders.size()==0)
            {
                dad = qualbreeders.get(0);
                int temp3;
                do{temp3 = rand.nextInt(pop.size());
                 mom = pop.get(temp3);

                }while(qualbreeders.get(0)==pop.get(temp3));

            }
            else if((breeders.size()>1) & qualbreeders.size()== 0 )
                {int temp2 = rand.nextInt(breeders.size());
                     dad = breeders.get(temp2);
                    int temp3;
                    do{
                        temp3 = rand.nextInt(breeders.size());
                       }while(temp2==temp3);
                     mom = breeders.get(temp3);
                }
            else if(breeders.size()==1 & qualbreeders.size()== 0)
                {
                    dad = breeders.get(0);
                   int temp3;
                   do{temp3 = rand.nextInt(pop.size());
                    mom = pop.get(temp3);

                   }while(breeders.get(0)==pop.get(temp3));


                }
            else{
                int temp2 = rand.nextInt(pop.size());
             dad = pop.get(temp2);
            int temp3;
            do{
                temp3 = rand.nextInt(pop.size());
               }while(temp2==temp3);
             mom = pop.get(temp3);

            }
            
            MemberChromosome child = new MemberChromosome(rost, size, tasks, chance, currentgen+1);
            child.Inheritance(dad, mom);
            child.mutate();
            newgen.add(child);
            
            }
        int babies = newgen.size();
        for (int i= 0; i<babies; i++)
            {pop.add(newgen.get(i));}
        }
    
    public MemberChromosome Sendhightaskchromo(){
        return hightasks;
    }
    public MemberChromosome Sendhighqualitychromo(){
        return highquality;
    }
    public int Sendpopsize(){
        return pop.size();
    }
    public String toString(){
        String population = "";
    for (int i = 0; i<pop.size(); i++ ) {
        MemberChromosome chromo = (MemberChromosome) pop.get(i);
    population+= chromo + " Tasks done: "+ chromo.SendTasksdone()+ " Quality: " + chromo.SendTaskQuality();
    }
    return population;
    }
}