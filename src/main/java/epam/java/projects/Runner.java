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
      try {
         if (!dir.exists()) {
            throw new IOException("Path don't exist");
         }
         if (dir.isFile()) {
             System.out.println("Target is file. Begin analysing file structure.\n");
             FileAnalyser.TextFiletreeAnalyse(dir);
         }
      } catch (IOException ex) {
         System.out.println(ex.getMessage());
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
