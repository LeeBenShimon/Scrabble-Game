package test;

/**
 * The CacheReplacementPolicy interface defines the methods for a cache replacement policy.
 * Implementations of this interface determine how items are added to and removed from a cache.
 */
public interface CacheReplacementPolicy{
	/**
     * Adds a word to the cache according to the replacement policy.
     *
     * @param word The word to be added.
     */
	void add(String word);

	/**
     * Removes a word from the cache according to the replacement policy.
     *
     * @return The word that was removed from the cache.
     */
	String remove(); 
}
