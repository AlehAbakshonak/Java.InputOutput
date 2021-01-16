package epam.java.projects;

import java.io.*;

public class FileAnalyser {
   private int lineIndex;
   private int fileCounter;
   private int directoryCounter;
   private int innerDirectoryFilesCounter;
   private int fullLengthOfAllFilenames;
   private int currentTreeDepth;
   private BufferedReader bufferedReader;

   public FileAnalyser() {
      this.lineIndex = 0;
      this.fileCounter = 0;
      this.directoryCounter = 0;
      this.innerDirectoryFilesCounter = 0;
      this.fullLengthOfAllFilenames = 0;
      this.currentTreeDepth = 4;
      this.bufferedReader = null;
   }

   private void wipeAllFieldsToDefault() {
      this.lineIndex = 0;
      this.fileCounter = 0;
      this.directoryCounter = 0;
      this.innerDirectoryFilesCounter = 0;
      this.fullLengthOfAllFilenames = 0;
      this.currentTreeDepth = 4;
      this.bufferedReader = null;
   }

   public void TextFiletreeAnalyse(File targetFile) throws IOException {
      this.bufferedReader = new BufferedReader(new FileReader(targetFile.getAbsolutePath()));
      analyseFilesInDirectoryBasedOnDepth(this.currentTreeDepth);
      System.out.println(
            "File amount = " + this.fileCounter + "\n" +
                  "Directory amount = " +
                  this.directoryCounter + "\n" +
                  "Average file amount in inner directories = " +
                  (double) this.innerDirectoryFilesCounter / this.directoryCounter + "\n" +
                  "Average filename length = " +
                  this.fullLengthOfAllFilenames / this.fileCounter + "\n");
      wipeAllFieldsToDefault();
   }

   private void analyseFilesInDirectoryBasedOnDepth(int directoryFilesDepth) throws IOException {
      while (this.currentTreeDepth >= directoryFilesDepth) {
         String currentLine = this.bufferedReader.readLine();
         analyseFileLine(currentLine);
      }
   }

   private void analyseFileLine(String currentLine) throws IOException {
      if (currentLine != null) {
         String currentLineHash = currentLine.substring(0, 14);
         String currentLineWithoutHash = currentLine.substring(14);
         if (Integer.parseInt(currentLineHash.trim()) == currentLineWithoutHash.hashCode()) {
            this.lineIndex++;
            char[] currentLineAsArray = currentLine.toCharArray();
            int namePosInLine = currentLine.indexOf("[");
            if (namePosInLine != this.currentTreeDepth) {
               this.currentTreeDepth = namePosInLine;
            }
            switch (currentLineAsArray[this.currentTreeDepth - 1]) {
               case ' ':
                  if (this.currentTreeDepth != 4) {
                     this.innerDirectoryFilesCounter++;
                  }
                  this.fileCounter++;
                  this.fullLengthOfAllFilenames += currentLine.length() - namePosInLine - 2;
                  break;
               case '─':
                  this.currentTreeDepth += 4;
                  this.directoryCounter++;
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
         this.currentTreeDepth = 0;
         if (this.lineIndex == 0) throw new IOException("File is empty");
      }
   }
}
