package JavaAPI_DUC_Company1.exception;

public class ResourceNotFoundException extends RuntimeException
{
   public ResourceNotFoundException(String message){
       super(message);
   }
}
