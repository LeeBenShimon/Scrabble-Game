package test;
import java.util.HashMap;
import java.util.Map;


public class LRU implements CacheReplacementPolicy
{
private Map<String,Integer> lruMap;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public LRU()
    {
        this.lruMap = new HashMap();
    }

    @Override
    public void add(String word)
    {
        lruMap.put(word,lruMap.getOrDefault(word, 0) + 1);
    }
    @Override
    public String remove()
    {
        String victim = null;
        int lastCall = Integer.MAX_VALUE;
        for (Map.Entry<String,Integer> entry : lruMap.entrySet())
        {
            if (entry.getValue() < lastCall)
            {
                victim = entry.getKey();
                lastCall = entry.getValue();
            }
        }
        lruMap.remove(victim);
        return victim;
    }
}



