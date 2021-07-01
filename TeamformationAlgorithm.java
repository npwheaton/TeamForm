import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;



public class TeamformationAlgorithm {
    public static void main(String[] args) {
        int generations = 0;
        int teamsize = 0;
        int initpop = 0;
        int lifetime = 1;
        double mutationchance = 0.0;
        int prevtask = 0;
        int nowtask = 0;
        double prevqual = 0.0;
        double nowqual = 0.0;
        int toptasks = 0;
        double topqual = 0.90;
        int halt = 0;
        int increment= 0;
        String filename = "";
        Scanner scan = new Scanner(System.in);
        Roster rost;
        LinkedList<String> tasklist = new LinkedList<String>();
        

        try {   System.out.println("Enter file name for Roster");
                filename = scan.nextLine();
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                rost = (Roster) in.readObject();
                in.close();
                fileIn.close();
             } catch (IOException i) {
                System.out.println("Load in file error");
                i.printStackTrace();
                return;
             } catch (ClassNotFoundException c) {
                System.out.println("Roster class not found");
                c.printStackTrace();
                return;
             }
        
            try {System.out.println("Enter file name for tasklist");
                filename = scan.nextLine();
                File myObj = new File(filename);
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                  tasklist.add(myReader.nextLine());
                  
                }
                myReader.close();
              } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
                System.exit(1);
              }
        //inputs
        
        System.out.println("Enter the team size. The team size has to be less than the number of employees in roster: " + rost.RosterSize() + " and higher than 1");
        teamsize = scan.nextInt();
        scan.nextLine();
        if(teamsize>=rost.RosterSize() ||teamsize<=1)
              {System.out.println("teamsize out of bounds");
              System.exit(1);
              }
        System.out.println("Enter number of generations");
        generations = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the population");
        initpop = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the mutation chance");
        mutationchance = scan.nextDouble();
        scan.nextLine();
        System.out.println("Enter the lifetime");
        lifetime = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter an optimum tasks done by the team");
        toptasks = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter an optimum quality of the team");
        topqual = scan.nextDouble();
        scan.nextLine();
        scan.close();
        if(lifetime<1)
              {lifetime =1;}

        String jobslist = "";
        for (int i = 0; i<tasklist.size(); i++)
              {jobslist += tasklist.get(i) + ", ";}
        System.out.println("Tasklist: \n " + jobslist);
        System.out.println("\n");
        System.out.println("Roster: \n" + rost.toString());
        System.out.println("\n");

        if(generations<=100)
            {halt = generations/5;}
          else{ halt = 25;}
        TeamsManager team = new TeamsManager(initpop, rost, teamsize, tasklist, mutationchance, lifetime);
        long starttime = System.nanoTime();
        //loop for program
        for (int i = 0; i<generations; i++)
            {
              if(i==0)
                {team.generatepopulation();}
                //kill old chromosomes
                team.killOldchromos(i);

                //calculate average fit, highest fit
                team.FindAvg();
                team.findhighfit();
                nowtask = team.Sendhightaskchromo().SendTasksdone();
                nowqual = team.Sendhightaskchromo().SendTaskQuality();
                if((nowtask == prevtask) & (nowqual==prevqual))
                  {increment++;}
                else{increment = 0;}
                prevtask = nowtask;
                prevqual = nowqual;
                
                if((team.Sendhightaskchromo().SendTasksdone()>=toptasks) & (team.Sendhightaskchromo().SendTaskQuality()>=topqual))
                {break;}
                else if(increment == halt)
                  {break;}
            //reproduce
            team.reproduce(i);

            }
            
            long endtime = System.nanoTime();
            long duration = (endtime - starttime)/1000000;
            MemberChromosome taskschromo = team.Sendhightaskchromo();
            MemberChromosome qualitychromo = team.Sendhighqualitychromo();
            System.out.println("Highest fit chromosome for tasks completed: "+ taskschromo.toString());
            System.out.println(" tasks done: "+ taskschromo.SendTasksdone() + " quality: " + taskschromo.SendTaskQuality());
            System.out.println("Highest fit chromosome for quality: "+qualitychromo.toString());
            System.out.println(" tasks done: "+ qualitychromo.SendTasksdone() + " quality: " + qualitychromo.SendTaskQuality());
            System.out.println(duration);
            //System.out.println(team.toString());
}
}