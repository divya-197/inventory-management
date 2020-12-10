import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

public class InventoryManagementSystem{
static LocalDate ldt=LocalDate.now();
static int tid=0;
 static String t_date=ldt.getDayOfMonth()+"-"+ldt.getMonth()+"-"+ldt.getYear()+" ("+ldt.getDayOfWeek()+")";
static Scanner s=new Scanner(System.in);

private static void add()
       { 
          try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","1234asdf");
            Statement stmt=con.createStatement();
            ResultSet a1=stmt.executeQuery("SELECT max(tid) FROM transaction");
            while(a1.next())
                tid=a1.getInt(1)+1;
            System.out.println("Enter item's id: ");
            int id=s.nextInt();
            ResultSet a4=stmt.executeQuery("select * from item where id="+id);
              int init=0;
              while (a4.next())
                {
                    System.out.println("Name: "+a4.getString(2));
                    System.out.println("Present quantity: "+(init=a4.getInt(3)));
                }
               System.out.println("Enter number of item to be added: ");
               int a=s.nextInt();
                stmt.executeUpdate("update item set quantity="+(a+init)+"where id="+id);
              String exe="INSERT into transaction(tid,itemid,operation,dated) values("+tid+", "+id+ ", "+a+" ,'"+t_date+"')";
              System.out.println("Updated quantity: "+(a+init)+" ID: "+id);
              ResultSet rs=stmt.executeQuery(exe);
              System.out.println("Data saved Successfuly !");
             con.close();
        }
               catch(Exception e){ System.out.println("Error code:"+e);}
     }
     private static void subtract()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","1234asdf");
            Statement stmt=con.createStatement();
            ResultSet a1=stmt.executeQuery("SELECT max(tid) FROM transaction");
            while(a1.next())
                tid=a1.getInt(1)+1;
            System.out.println("Enter item's id: ");
            int id=s.nextInt();
            ResultSet a4=stmt.executeQuery("select * from item where id="+id);
            int q =0;
            while (a4.next())
            {
                System.out.println("Name: "+a4.getString(2));
                System.out.println("Present quantity: "+(q=a4.getInt(3)));

                System.out.println("---------------------");
            }

            System.out.println("Enter number of item to be removed when present quantity is "+q+" : ");
            int a=s.nextInt();
            stmt.executeUpdate("update item set quantity="+(q-a)+"where id="+id);
            String exe="INSERT into transaction(tid,itemid,operation,dated) values("+tid+", "+id+ ", -"+a+" ,'"+t_date+"')";
            ResultSet rs=stmt.executeQuery(exe);
            System.out.println("Updated quantity: "+(q-a)+" ID: "+id);
            con.close();
        }
        catch(Exception e){ System.out.println("Error code:"+e);}

    }
    protected static void view()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","1234asdf");
            Statement stmt=con.createStatement();
            ResultSet aa=stmt.executeQuery("SELECT * FROM item");
            System.out.println("ID    NAME     PRESENT QUANTITY");
            System.out.println("-------------------------------");
            while(aa.next())
                System.out.println(aa.getInt(1)+"     "+aa.getString(2)+"    "+aa.getInt(3));
            con.close();
        }
        catch(Exception e){ System.out.println("Error code:"+e);}
    }


    public static void bill()
    {
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","1234asdf");
            Statement stmt=con.createStatement();
            System.out.println("Enter id of the product :");
            int i=s.nextInt();
            ResultSet a5=stmt.executeQuery("select name from item where id="+i);
            String name = null;
            while(a5.next())
                 name=a5.getString(1);

            ResultSet a6=stmt.executeQuery("select * from transaction where itemid="+i);
            while (a6.next())
            {
                System.out.println(":::::::RECIPT::::::");
                System.out.println("Date : "+a6.getString(4));
                System.out.println("Name: "+name);
                System.out.println("Item Id : "+a6.getInt(2));
                System.out.println("Transaction Id: "+a6.getInt(1));
                System.out.println("operation : "+a6.getInt(3));
                System.out.println("---------------------");
            }
            con.close();
        }
        catch(Exception e){ System.out.println("Error code:"+e);}
    }

    public static void main(String[] args){

        System.out.println("--------------IMS--------------");
        System.out.println("1: ADMIN  2:USER");
        boolean admin=false;
        boolean user=false;
        int aa=s.nextInt();
        if(aa==1)
            admin=true;
        else user=true;

        boolean run=true;


        while (run && admin)
        {       System.out.println(":::ADMIN MODE:::");
            System.out.println("01: View inventory");
            System.out.println("02: Add to inventory");
            System.out.println("03: Subtract from inventory");
            System.out.println("04: Bill/Recipt");
            System.out.println("ENTER INDEX TO Select OPERATION");
            System.out.println("to exit ENTER index 999: ");
            System.out.println();
            System.out.println("Enter choice !");
            int choice=s.nextInt();

            InventoryManagementSystem a1=new InventoryManagementSystem();
            if (choice==999)
            {
                run=false;
                System.out.println("Exiting & closing db connection...");
                break;
            }
            else if(choice==1)
            { a1.view();}
            else if(choice==2)
            { a1.add();}
            else if(choice==3)
            { a1.subtract();}
            else if(choice==4)
            { a1.bill();}
            else
                System.out.print("enter correct choice");
        }

        while (run && user)
        {       System.out.println(":::USER MODE:::");
            System.out.println("01: View inventory");
            System.out.println("02: Bill/Recipt");
            System.out.println("ENTER INDEX TO Select OPERATION");
            System.out.println("to exit ENTER index 999: ");

            System.out.println();
            System.out.println("Enter choice !");
            int choice=s.nextInt();
            User u1=new User();
            if (choice==999)
            {
                run=false;
                System.out.println("Exiting & closing db connection...");
                break;
            }
            else if(choice==1)
            { u1.view();}
            else if(choice==2)
            { u1.bill();}
            else
                System.out.print("enter correct choice");
        }
        //boolean run=true;
        //System.out.println("to exit ENTER index 999: ");
        //System.out.println("Enter choice !");
         //int choice=s.nextInt();
     
}
}
 //////////////////////////////////////////
class User extends InventoryManagementSystem
 {
     protected static void view()
     {
         try{
             Class.forName("oracle.jdbc.driver.OracleDriver");
             Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","1234asdf");
             Statement stmt=con.createStatement();
             ResultSet aa=stmt.executeQuery("SELECT * FROM item");
             System.out.println("ID    NAME");
             System.out.println("-------------------------------");
             while(aa.next())
                 System.out.println(aa.getInt(1)+"     "+aa.getString(2));
             con.close();
         }
         catch(Exception e){ System.out.println("Error code:"+e);}
     }


     public static void bill()
     {
         try{
             Class.forName("oracle.jdbc.driver.OracleDriver");
             Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","1234asdf");
             Statement stmt=con.createStatement();
             System.out.println("Enter Transaction ID:");
             int tid=s.nextInt();

             ResultSet a6=stmt.executeQuery("select * from transaction where tid="+tid);
             while (a6.next())
             {
                 System.out.println(":::::::RECIPT::::::");
                 System.out.println("Date : "+a6.getString(4));
                 //System.out.println("Name: "+name);
                 System.out.println("Item Id : "+a6.getInt(2));
                 System.out.println("Transaction Id: "+a6.getInt(1));
                 System.out.println("operation : "+a6.getInt(3));
                 System.out.println("---------------------");
             }
             con.close();
         }
         catch(Exception e){ System.out.println("Error code:"+e);}
     }

 }