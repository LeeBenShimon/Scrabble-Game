package test;

import java.util.HashSet;

/**
 * The CacheManager class manages a cache of words with a specified replacement policy.
 */
public class CacheManager 
{
    private int size; // Maximum size of the cache
    private CacheReplacementPolicy crp; // Cache replacement policy
    private HashSet<String> words; // Set to store cached words

    /**
     * Constructor to create a CacheManager with the specified size and replacement policy.
     *
     * @param size The maximum size of the cache.
     * @param crp  The cache replacement policy to use.
     */
    public CacheManager(int size, CacheReplacementPolicy crp)
    {
        this.size = size;
        this.crp = crp;
        this.words = new HashSet<>();
    }

    /**
     * Queries the cache to check if a word is present.
     *
     * @param word The word to query.
     * @return True if the word is in the cache, false otherwise.
     */
	public boolean query(String word)
    {
        return words.contains(word);
    }

    /**
     * Adds a word to the cache. If the cache is full, it removes a word based on the replacement policy.
     *
     * @param word The word to add to the cache.
     */
    public void add(String word)
    {
        crp.add(word);
        if(words.size() == this.size)
        {
            String s = crp.remove();
            words.remove(s);
        }
        words.add(word);
    }
}
