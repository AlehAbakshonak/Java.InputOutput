package epam.java.projects;

import java.io.*;

public class FileAnalyser {
   private static int lineIndex = 0;
   private static int fileCounter = 0;
   private static int directoryCounter = 0;
   private static int innerDirectoryFilesCounter = 0;
   private static int fullLengthOfAllFilenames = 0;
   private static int currentTreeDepth = 4;
   private static BufferedReader bufferedReader = null;

   public static void TextFiletreeAnalyse(File targetFile) throws IOException {
      bufferedReader = new BufferedReader(new FileReader(targetFile.getAbsolutePath()));
      analyseFilesInDirectoryBasedOnDepth(currentTreeDepth);
      System.out.println(
            "File amount = " + fileCounter + "\n" +
                  "Directory amount = " + directoryCounter + "\n" +
                  "Average file amount in inner directories = " + (double) innerDirectoryFilesCounter / directoryCounter + "\n" +
                  "Average filename length = " + fullLengthOfAllFilenames / fileCounter + "\n");
   }

   public static void analyseFilesInDirectoryBasedOnDepth(int directoryFilesDepth) throws IOException {
      while (currentTreeDepth >= directoryFilesDepth) {
         String currentLine = bufferedReader.readLine();
         analyseFileLine(currentLine);
      }
   }

   private static void analyseFileLine(String currentLine) throws IOException {
      if (currentLine != null) {
         String currentLineHash = currentLine.substring(0, 14);
         String currentLineWithoutHash = currentLine.substring(14);
         if (Integer.parseInt(currentLineHash.trim()) == currentLineWithoutHash.hashCode()) {
            lineIndex++;
            char[] currentLineAsArray = currentLine.toCharArray();
            int namePosInLine = currentLine.indexOf("[");
            if (namePosInLine != currentTreeDepth) {
               currentTreeDepth = namePosInLine;
            }
            switch (currentLineAsArray[currentTreeDepth - 1]) {
               case ' ':
                  if (currentTreeDepth != 4) {
                     innerDirectoryFilesCounter++;
                  }
                  fileCounter++;
                  fullLengthOfAllFilenames += currentLine.length() - namePosInLine - 2;
                  break;
               case '─':
                  currentTreeDepth += 4;
                  directoryCounter++;
                  analyseFilesInDirectoryBasedOnDepth(currentTreeDepth);
                  break;
            }
         } else {
            throw new IOException(
                  "Wrong line hashcode at line " + lineIndex +
                        "; Expect " + currentLineHash.trim() +
                        "; Found " + currentLineWithoutHash.hashCode() +
                        "; Line without hash: \"" + currentLineWithoutHash + "\"");
         }
      } else {
         currentTreeDepth = 0;
         if (lineIndex == 0) throw new IOException("File is empty");
      }
   }
}
