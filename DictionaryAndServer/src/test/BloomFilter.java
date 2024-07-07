package test;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * The BloomFilter class implements a space-efficient probabilistic data structure.
 * It is used to test whether an element is a member of a set with a possibility of false positives.
 */
public class BloomFilter 
{
    private int size; // Size of the BitSet
    private BitSet bits; // BitSet to store the hashed values 
    private List<MessageDigest> hashFuncs; // List of hash functions to use

    /**
     * Constructor to create a BloomFilter with the specified size and hash functions.
     *
     * @param size The size of the BitSet.
     * @param algs The names of the hash algorithms to use.
     */
    public BloomFilter(int size,String...algs) 
    {
        this.size = size;
        this.bits = new BitSet(size);
        this.hashFuncs = new ArrayList();
        for(String alg : algs)
        {
            try{
                hashFuncs.add(MessageDigest.getInstance(alg));
            }
            catch(NoSuchAlgorithmException e){
                throw new IllegalArgumentException("invalid hash function:" + alg);
            }
        }
    }

    /**
     * Adds a word to the BloomFilter.
     *
     * @param word The word to add.
     */
    public void add(String word)
    {
        for(MessageDigest hashFunc : hashFuncs)
        {
            byte[] bts = hashFunc.digest(word.getBytes());
            int inndex = (Math.abs(new BigInteger(bts).intValue())) % size;
            bits.set(inndex);
        }
    }

    /**
     * Checks if a word is possibly contained in the BloomFilter.
     *
     * @param word The word to check.
     * @return True if the word is possibly in the filter, false if it is definitely not.
     */
    public boolean contains(String word)
    {
        for(MessageDigest hashFunc:hashFuncs)
        {
            byte[] bts = hashFunc.digest(word.getBytes());
            int inndex = (Math.abs(new BigInteger(bts).intValue())) % size;
            if (!bits.get(inndex))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the BloomFilter.
     *
     * @return A string representation of the BitSet.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length(); i++) 
        {
            sb.append(bits.get(i) ? "1" : "0");
        }
        return sb.toString();
    }
}
