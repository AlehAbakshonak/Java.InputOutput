package epam.java.projects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Runner {
   public static BufferedWriter mainWriter;

   public static void main(String[] args) {
      //Scanner scanner = new Scanner(System.in);
      //String filePath = scanner.nextLine();
      //String filePath = "D://Music";
      String filePath = "D://result.txt";
      File dir = new File(filePath);
      if (!dir.exists()) {
         System.out.println("Path don't exist");
      } else {
         try {
            if (dir.isFile()) {
               System.out.println("Target is file. Begin analysing file structure.\n");
               FileAnalyser fileAnalyser = new FileAnalyser();
               fileAnalyser.TextFiletreeAnalyse(dir);
            }
         } catch (IOException ex) {
            System.out.println(ex.getMessage());
         }
      }

      if (dir.isDirectory()) {
         try (BufferedWriter bw = new BufferedWriter(new FileWriter("D://result.txt"))) {
            mainWriter = bw;
            PathReader.writeDirectoryInnerFiletreeInTxt(dir);
         } catch (IOException ex) {
            System.out.println(ex.getMessage());
         }
      }
   }
}