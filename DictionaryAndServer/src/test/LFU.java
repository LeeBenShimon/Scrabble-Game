package test;


import java.util.HashMap;
import java.util.Map;

/**
 * The LFU class implements the CacheReplacementPolicy interface using the Least Frequently Used (LFU) policy.
 * It tracks the frequency of access for each word and removes the least frequently used word when necessary.
 */
public class LFU implements CacheReplacementPolicy
{
    private Map<String,Integer> lfuMap; // Map to store words and their access frequencies

    /**
     * Constructor to create an LFU cache replacement policy.
     * Initializes the map to store words and their frequencies.
     */
    public LFU()
    {
        this.lfuMap = new HashMap<>();
    }

    /**
     * Adds a word to the cache and updates its access frequency.
     *
     * @param word The word to add to the cache.
     */
    @Override
    public void add(String word)
    {
        lfuMap.put(word,lfuMap.getOrDefault(word, 0) + 1);
    }

    /**
     * Removes the least frequently used word from the cache.
     *
     * @return The word that was removed from the cache.
     */
    @Override
    public String remove()
    {
    String victim = null;
    int minFreq = Integer.MAX_VALUE;

    // Find the word with the minimum frequency
    for (Map.Entry<String,Integer> entry : lfuMap.entrySet())
    {
        if (entry.getValue() < minFreq)
        {
            minFreq = entry.getValue();
            victim = entry.getKey();
        }
    }

    // Remove the least frequently used word from the map
    lfuMap.remove(victim);
    return victim;
    }
}




