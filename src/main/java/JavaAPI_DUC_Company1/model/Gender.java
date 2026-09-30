package JavaAPI_DUC_Company1.model;

public enum Gender {
     Male,
     Female
}

// If the above 4 lines are replaced by the following 4 lines, it will be much easier to code because
// Frontend will send gender (String).If Backend utilizes gender (enum) , I will have to face difficulties of
// converting String to Enum ,Enum to String in several files.
//public class Gender {
//    public final String Male = "Male";
//    public final String  Female = "Female";
//}
