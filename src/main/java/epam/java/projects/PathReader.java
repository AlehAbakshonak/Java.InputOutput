package epam.java.projects;

import java.io.File;
import java.io.IOException;

public class PathReader {

   private static String treePrefix = "";

   public static void writeDirectoryInnerFiletreeInTxt(File dir) throws IOException {
      File[] listFiles = dir.listFiles();
      if (listFiles.length == 0 && treePrefix.equals("")) {
         throw new IOException("Directory is empty.");
      } else {
         proceedWithDirectoryStructure(listFiles);
      }
   }

   public static void analysePath(File dir, boolean last) throws IOException {
      boolean currentDirectionIsDirectory = dir.isDirectory();
      boolean currentDirectionIsFile = dir.isFile();

      if (currentDirectionIsDirectory) treePrefix += last ? "└───" : "├───";
      if (currentDirectionIsFile) treePrefix += treePrefix.length() == 0 ? "│   " : "    ";
      String currentLine = treePrefix + "[" + dir.getName() + "]";
      Runner.mainWriter.write(String.format("%14s", currentLine.hashCode()) + currentLine + "\n");

      if (currentDirectionIsDirectory) {
         treePrefix = treePrefix.substring(0, treePrefix.length() - 4);
         treePrefix += last ? "    " : "│   ";
         File[] listFiles = dir.listFiles();
         if (listFiles.length > 0) {
            proceedWithDirectoryStructure(listFiles);
         }
      }
      treePrefix = treePrefix.substring(0, treePrefix.length() - 4);
   }

   private static void proceedWithDirectoryStructure(File[] listFiles) throws IOException {
      for (int i = 0; i < listFiles.length; i++) {
         File item = listFiles[i];
         analysePath(item, listFiles.length - i == 1);
      }
   }
}
