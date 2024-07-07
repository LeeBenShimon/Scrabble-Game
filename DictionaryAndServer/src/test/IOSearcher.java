package test;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * The IOSearcher class provides a method to search for a word in multiple files.
 */
public class IOSearcher {
    /**
     * Searches for a word in the specified files.
     *
     * @param word      The word to search for.
     * @param fileNames The names of the files to search in.
     * @return True if the word is found in any of the files, false otherwise.
     */
    public static boolean search(String word,String...fileNames)
    {
        Scanner myScanner = null;
        for(String fileName : fileNames)
        {
            try{
                myScanner=new Scanner(new BufferedReader(new FileReader(fileName)));
                while(myScanner.hasNext())
                {
                    if(word.equals(myScanner.next()))
                    {
                        return true;
                    }
                }

            } catch(FileNotFoundException e){
                throw new RuntimeException("File" + fileName + "is not found");
            }

            finally{
                if(myScanner != null)
                myScanner.close();
            }  
        }
        return false;
    }
}
