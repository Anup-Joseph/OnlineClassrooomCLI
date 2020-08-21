import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class sign{
    protected static String name;
    protected static String occ;
    private static String User_name;
    private static String Password;

    static void register()  {
        boolean flag = true;

        try {
            FileWriter myWriter = new FileWriter("ID.txt", true);
            ArrayList l= new ArrayList();
            l=list();
            ArrayList<String> ID = new ArrayList();
            Scanner input = new Scanner(System.in);
            System.out.println("Enter a Username : ");
            User_name = input.nextLine();
            for (int i = 1; i < l.size(); i = i + 5) {
                if (User_name.toLowerCase().equals(l.get(i).toString().toLowerCase())) {
                    System.out.println("Username exists.");
                    flag = false;
                }
            }
            if (flag) {
                System.out.println("Enter a password :");
                Password = input.nextLine();
                System.out.println("Enter Your First Name:");
                String Real_Name = input.nextLine();
                System.out.println("Enter your last name: ");
                String LastName=input.nextLine();
                System.out.println("Are you a student or a teacher?");
                System.out.println("1.Student");
                System.out.println("2.Teacher");
                System.out.println("Enter a option :");
                    int tos = input.nextInt();
                    String Id;
                    switch (tos) {
                        case 1: {
                            System.out.println("Enter your Branch (eg:EC_A) :");
                            String branch=input.next();
                            Id = " " + User_name + " " + Password + " " + Real_Name +" "+ LastName+" "+branch+" Student ";
                            ID.add(Id);
                        }
                        break;
                        case 2:
                            System.out.println("Enter your Qualification :");
                            String qualification=input.next();
                            Id = " " + User_name + " " + Password + " " + Real_Name +" "+ LastName+" "+qualification+" Teacher ";
                            ID.add(Id);
                    }
                    myWriter.write(String.valueOf(ID));
                    myWriter.close();
                } else {
                    register();
                }
        } catch (IOException var7) {
            System.out.println("An error occurred.");
            var7.printStackTrace();
        }
    }


    static void login() throws IOException {
        boolean val = false;
        Scanner input = new Scanner(System.in);
        System.out.println("Username : ");
        String User_name = input.nextLine();
        System.out.println("Password :");
        String Password = input.nextLine();
        String Id = " " + User_name + " " + Password + " ";

        try {
            File myObj = new File("ID.txt");
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.contains(Id)) {
                    val = true;
                }
            }

            myReader.close();
        } catch (FileNotFoundException var8) {
            System.out.println("An error occurred.");
            var8.printStackTrace();
        }

        if (val) {

            ArrayList l = list();
            for (int i = 1; i < l.size(); i = i +7 ) {
                if (User_name.equals(l.get(i))) {
                    name = (String) l.get(i + 2);
                    occ = (String) l.get(i + 5);
                }
            }
            System.out.println("Successful Login " + name);
            String std = "Student";
            String tcr = "Teacher";
            if (occ.equals(std)) {
                students s1 = new students();
                s1.student();
            } else if (occ.equals(tcr)) {
                teachers t = new teachers();
                t.teacher();
            }
        } else {
            System.out.println("User does not exist");
            login();
        }

    }
    static ArrayList list() throws FileNotFoundException {
        Scanner s = new Scanner(new File("ID.txt"));
        ArrayList details = new ArrayList();
        while(s.hasNext()) {
            details.add(s.next());
        }
        s.close();
        return details;
    }
}

class students extends sign{
    public void student() throws FileNotFoundException {
        Scanner input= new Scanner(System.in);
        int ch;
        System.out.println("1.Ask a doubt\n2.Check answers\n3.View Study Materials\n4.Exit");
        ch=input.nextInt();
        switch(ch){
            case 1 : askdoubt();
                break;
            case 2: checkanswer();
                break;
            case 3: viewStudyMaterial();
                break;
            case 4:System.exit(0);
                break;
            default: {
                System.out.println("Invalid Option\n");
                student();
            }
        }

    }
    public static void askdoubt() {
        System.out.println("Write down your doubt:");
        Scanner input = new Scanner(System.in);
        String doubt ="Q:"+input.nextLine()+"\n";
        try {
            FileWriter myWriter = new FileWriter(name+".txt", true);
            myWriter.write(String.valueOf(doubt));
            myWriter.close();
            System.out.println("\n1.Another doubt\n2.Logout");
            int option;
            option = input.nextInt();
            if(option == 1)
                askdoubt();
            else
                System.exit(0);


        } catch (IOException var7) {
            System.out.println("An error occurred.");
            var7.printStackTrace();
        }

    }
    public void checkanswer() throws FileNotFoundException {
        String file,data;
        file="ans_"+name+".txt";
        File f = new File(file);
        if (f.isFile()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println( data);
            }
            myReader.close();
        }
        else
        {
            System.out.println("No doubts asked\n");
            student();
        }
    }
    void viewStudyMaterial() throws FileNotFoundException {
        Scanner input= new Scanner(System.in);
        int ch,s_no=1;
        ArrayList materials =new ArrayList();
        File f= new File("Study_Materials.txt");
        if(f.isFile())
        {
            Scanner s = new Scanner(f);
            while(s.hasNext()) {
                materials.add(s.next());
            }
            s.close();
            System.out.println("\nStudy Material");
            for( int i=0 ;i<materials.size() ; i++) {
                System.out.println(s_no+". "+materials.get(i));
                s_no++;
            }
            System.out.println("Enter your choice to view :");
            ch=input.nextInt();
            if(ch<=materials.size()) {
                String FileName = materials.get(ch - 1).toString();
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File(FileName));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("Please enter a valid input");
                viewStudyMaterial();
            }
        }else{
            System.out.println("No Study Material Available");
        }
    }

}

class teachers extends students {
    public void teacher() throws IOException {
        int opt;
        Scanner input = new Scanner(System.in);
        System.out.println("1.Answer doubts\n2.Student List\n3.Study Materials\n4.Exit");
        opt = input.nextInt();
        switch (opt) {
            case 1:
                ansdoubt();
                break;
            case 2:
                StudentList();
                break;
            case 3:
                StudyMaterial();
                break;
            case 4: System.exit(0);
            break;
            default: {
                System.out.println("Invalid Option \n");
                teacher();
                break;
            }
        }

    }

    public void ansdoubt() throws FileNotFoundException {
        String file;
        Scanner input = new Scanner(System.in);
        ArrayList l=list();
        ArrayList doubts=new ArrayList();
        System.out.println("DOUBTS");
        for (int i = 3; i < l.size(); i = i + 7) {
            int count=0;
            file = l.get(i).toString() + ".txt";
            File f = new File(file);
            if (f.isFile()) {
                doubts.add(file);
                Scanner myRead = new Scanner(f);
                while(myRead.hasNextLine()){
                    String data=myRead.nextLine();
                    count++;
                }
                System.out.println(l.get(i).toString()+" has "+ count + " unanswered doubts");
                myRead.close();
            }
        }
        for (int i = 0; i < doubts.size(); i++) {
            try {
                String data, ans;
                File myObj = new File(doubts.get(i).toString());
                FileWriter myWriter = new FileWriter("ans_" + doubts.get(i).toString());
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    data = myReader.nextLine();
                    System.out.println("\n"+data);
                    System.out.println("Answer:");
                    ans = input.nextLine();
                    myWriter.write(data + "\n" + "A:" + ans + "\n");
                }
                myWriter.close();
                myReader.close();
                myObj.delete();

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    void StudentList() throws FileNotFoundException {
        ArrayList l = list();
        Scanner input = new Scanner(System.in);
        System.out.println("\nStudent List");
        String std = "Student";
        int s_no = 1, count = 0;
        String data;
        for (int a = 6; a < l.size(); a += 7) {
            if (std.equals(l.get(a))) {
                System.out.println(s_no + "." + l.get(a - 3).toString() +" "+l.get(a-2).toString()+" "+l.get(a-1).toString());
            }
            s_no++;
        }
    }
    void StudyMaterial() throws IOException {
        Scanner input = new Scanner(System.in);
        int ch;
        System.out.println("1.View Study Materials\n2.Add Study Materials\n3.Go back");
        ch= input.nextInt();
        switch (ch){
            case 1: viewStudyMaterial();
                    break;
            case 2: addStudyMaterial();
                    break;
            case 3: teacher();
                    break;
            default: {
                System.out.println("Invalid option");
                StudyMaterial();
                break;
            }

        }
    }
    void addStudyMaterial() throws IOException {
        String ch;
        Scanner input = new Scanner(System.in);
        FileWriter f =new FileWriter("Study_materials.txt",true);
        do {
            System.out.println("Enter the file name :");
            String FileName = input.nextLine()+" ";
            f.write(FileName);
            System.out.println("Do you want to add more files? (y/n)");
            ch = input.nextLine();
        }while(ch.equals("y") || ch.equals("Y"));
        f.close();
    }

}

class main extends teachers{
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        int ch;
        do {
            System.out.println("MENU");
            System.out.println("1.Register");
            System.out.println("2.Login");
            System.out.println("3.Exit");
            System.out.println("Enter a option :");
            ch = input.nextInt();
            switch (ch) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        } while (ch != 3 && ch != 2);

    }
}