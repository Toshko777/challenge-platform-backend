 package challenge.platform.backend.utils;

 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.crypto.password.PasswordEncoder;

 public class PasswordGeneratorEncoder {
     public static void main(String[] args){
         PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
         System.out.println("encoded");
         System.out.println(passwordEncoder.encode("admin001"));
         System.out.println(passwordEncoder.encode("user0001"));

     }
 }
