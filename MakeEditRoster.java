import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class MakeEditRoster {
    public static void main(String[] args) {
        Roster rost;
        Scanner answer = new Scanner(System.in);
        String temp = "";
        int response;
        String filename = null;
        System.out.println("This program edits a roster object and saves it");
        System.out.println("Press y to Load in Roster object from file otherwise press n");
        temp = answer.nextLine();
        if(temp.equals("y")|| temp.equals("Y"))
            {try {System.out.println("Enter file name");
                filename = answer.nextLine();
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

            }
        else{
            rost = new Roster();
        }
        do{
        System.out.println("Press:\n1: Add Employee to Roster\n2: Remove Emmployee from Roster\n3: Edit skills on Employee\n 4: See complete Roster\n 5: Exit");
        response = answer.nextInt();
        answer.nextLine();
        switch(response)
            {case 1:
                System.out.println("Enter Employee name");
                temp = answer.nextLine();
                Employee newE = new Employee(temp);
                    do{
                    System.out.println("Enter new skill");
                    String nskill = answer.nextLine();
                    System.out.println("Add Skill Level. Press:\n 0: To add 'weak'\n 1: To add 'intermediate'\n 2: To add master");
                    int Lskill = answer.nextInt();
                    answer.nextLine();
                    newE.addskill(nskill, Lskill);
                    System.out.println("Press y to add another skill, otherwise press n");
                    temp = answer.nextLine();
                    }while(temp.equals("y")||temp.equals("Y"));
                rost.AddEmployees(newE);
                System.out.println(newE.toString());
                newE.Printskills();
                break;
            case 2:
                if(rost.RosterSize()>0)
                    {System.out.println("Enter Employee name");
                    temp = answer.nextLine();
                    rost.RemoveEmployees(temp);
                    }
                break;

            case 3:
                System.out.println("Enter Employee name");
                temp = answer.nextLine();
                Employee e = rost.SendEmployee(temp);
                if(e!=null)
                    {System.out.println("Press 1 to add skill or 2 to remove skill");
                     temp = answer.nextLine();
                     if(temp.equals("1"))
                        {System.out.println("Enter new skill");
                        temp = answer.nextLine();
                        System.out.println("Add Skill Level. Press:\n 0: To add 'weak'\n 1: To add 'intermediate'\n 2: To add master");
                    int Lskill = answer.nextInt();
                    answer.nextLine();
                    e.addskill(temp, Lskill);
                        System.out.println(e.Printskills());
                        }
                    else{
                        System.out.println("Enter skill you want to remove");
                        temp = answer.nextLine();
                        e.removeskill(temp);
                        System.out.println(e.Printskills());
                        }

                    }
                break;

            case 4:
            System.out.println(rost);
                break;
            
            default:
                break;


            }

        }while(response!=5);
        if(filename == null)
            {try {
                System.out.println("Enter  a Filename to save roster");
                filename = answer.nextLine();
                FileOutputStream fileOut = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(rost);
                out.close();
                fileOut.close();
                System.out.printf("Serialized data is saved to " + filename);
             } catch (IOException i) {
                i.printStackTrace();
             }

            }
        else{
            try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(rost);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + filename);
         } catch (IOException i) {
            i.printStackTrace();
         }

        }
        answer.close();

        

    }

}