package AutomationDay.Java_program;

public class Emp {

    int age;
    String Occ;
    String FName;
    String LName;

    void DisplayData() {
        System.out.println("Age"+":" + age);
        System.out.println("Occupation"+":" + Occ);
        System.out.println("FirstName"+":" + FName);
        System.out.println("LastName"+":" + LName);
    }

    public static void main(String[] args) {
        Emp e = new Emp();
        e.FName = "Suraj";
        e.Occ = "IT";
        e.age = 30;
        e.LName = "Singh";

        e.DisplayData();
    }

}
