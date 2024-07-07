package test;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * The Dictionary class manages word lookups and caching for the BookScrabble game.
 * It uses a Bloom filter and two cache managers to efficiently check if words exist.
 */
public class Dictionary 
{
    private String[] allFileNames; // Array of file names containing the dictionary words
    private CacheManager wordsExists; // Cache for words that exist
    private CacheManager wordsNotExists; // Cache for words that do not exist
    private BloomFilter bloomFilter; // Bloom filter for probabilistic word existence checks
    
    /**
     * Constructor to create a Dictionary with the specified file names.
     *
     * @param fileNames The names of the files containing the dictionary words.
     */
    public Dictionary(String...fileNames)
    {
        this.allFileNames = fileNames;
        this.wordsExists = new CacheManager(400,new LRU()); // LRU cache for words that exist
        this.wordsNotExists = new CacheManager(100,new LFU()); // LFU cache for words that do not exist
        this.bloomFilter = new BloomFilter(256, "SHA1","MD5"); // Bloom filter with SHA1 and MD5 hash functions
       
        // Populate the Bloom filter with words from the dictionary files
        for(String fileName : fileNames)
        {
            Scanner myScanner = null;
            try{
                myScanner = new Scanner(new BufferedReader(new FileReader(fileName)));
                while(myScanner.hasNext())
                {
                    this.bloomFilter.add(myScanner.next());
                }
            }
            catch(FileNotFoundException e){
                throw new RuntimeException("file " + fileName + "is not found");
            }
            finally{
                if (myScanner != null)
                    myScanner.close();
            }
        }
    }

    /**
     * Queries if a word exists in the dictionary.
     *
     * @param word The word to query.
     * @return True if the word exists, false otherwise.
     */
    public boolean query(String word)
    {
        if (wordsExists.query(word))
        {
            return true;
        }
        if (wordsNotExists.query(word))
        {
            return false;
        }
        if (bloomFilter.contains(word))
        {
            wordsExists.add(word);
            return true;
        }
        else 
        {
            wordsNotExists.add(word);
            return false;
        }
    }

    /**
     * Challenges if a word exists in the dictionary files.
     *
     * @param word The word to challenge.
     * @return True if the word exists in the dictionary files, false otherwise.
     */
    public boolean challenge(String word)
    {
        try{
            if (IOSearcher.search(word,allFileNames))
            {
                wordsExists.add(word);
                return true;
            }
            else
            {
                wordsNotExists.add(word);
                return false;
            }
        }
        catch(Exception e){
            throw new RuntimeException("File does not exists");
        }
    }
}
