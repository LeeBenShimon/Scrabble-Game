package test;


import java.util.HashMap;

/**
 * The DictionaryManager class manages multiple Dictionary instances.
 * It follows the singleton pattern to ensure only one instance of the manager exists.
 */
public class DictionaryManager 
{
    private static DictionaryManager instance; // Singleton instance of DictionaryManager
    private HashMap<String,Dictionary> dictionaries; // HashMap to store dictionaries by file name
    
    /**
     * Private constructor to create a DictionaryManager.
     * Initializes the HashMap to store dictionaries.
     */
    private DictionaryManager()
    {
        this.dictionaries = new HashMap<>();
    }

    /**
     * Gets the singleton instance of DictionaryManager.
     *
     * @return The singleton instance of DictionaryManager.
     */
    public static DictionaryManager get()
    {
        if(instance == null)
        {
            instance = new DictionaryManager();
        }
        return instance;
    }

    /**
     * Queries if a word exists in any of the specified dictionary files.
     *
     * @param files The names of the dictionary files and the word to query.
     * @return True if the word exists in any of the dictionaries, false otherwise.
     */
    public boolean query(String...files)
    {
        boolean existsWord = false;
        String word_to_search = files[files.length-1];
        int length = files.length - 1;
        for(int i = 0 ; i < length ; i++)
        {
            if (!dictionaries.containsKey(files[i]))
            {
                Dictionary new_dic = new Dictionary(files[i]);
                dictionaries.put(files[i], new_dic);
            }
            if(dictionaries.get(files[i]).query(word_to_search))
            {
                existsWord = true;
            }

        }
        return existsWord;
    }

    /**
     * Challenges if a word exists in any of the specified dictionary files.
     *
     * @param files The names of the dictionary files and the word to challenge.
     * @return True if the word exists in any of the dictionaries, false otherwise.
     */
    public boolean challenge(String...files)
    {
        boolean existsWord = false;
        String word_to_search = files[files.length - 1];
        int length = files.length - 1;
        for(int i = 0 ; i < length ; i++)
        {
            if (!dictionaries.containsKey(files[i]))
            {
                Dictionary new_dic = new Dictionary(files[i]);
                dictionaries.put(files[i], new_dic);
            }
            if (dictionaries.get(files[i]).challenge(word_to_search))
            {
                existsWord = true;
            }

        }
        return existsWord;
    }

    /**
     * Gets the number of dictionaries managed by DictionaryManager.
     * 
     *  @return The nuber of dictionaries.
     */
    public int getSize()
    {
        return dictionaries.size();
    }
}
