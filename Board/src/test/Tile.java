package test;


import java.util.Random;


/**
 * The Tile class represents a single tile in the Scrabble game.
 * Each tile has a letter and a score value.
 */
public class Tile {
    public final char letter; // The letter on the tile
    public final int score; // The score value of the tile


    /**
     * Private constructor to create a Tile.
     *
     * @param letter The letter on the tile.
     * @param score  The score value of the tile.
     */
    private Tile(char letter, int score) 
    {
        this.letter = letter;
        this.score = score;
    }

    /**
     * Gets the letter on the tile.
     *
     * @return The letter on the tile.
     */
    public char getLetter() 
    {
        return letter;
    }

    /**
     * Gets the score of the tile.
     *
     * @return The score value of the tile.
     */
    public int getScore() 
    {
        return score;
    }

    /**
     * Generates a hash code for the tile.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() 
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + letter;
        result = prime * result + score;
        return result;
    }

    /**
     * Checks if this tile is equal to another object.
     *
     * @param obj The object to compare.
     * @return true if the tiles are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (letter != other.letter)
            return false;
        if (score != other.score)
            return false;
        return true;
    }

    /**
     * The Bag class represents a collection of tiles for the Scrabble game.
     * It uses the singleton pattern to ensure only one instance of the bag exists.
     */
    public static class Bag
    {
        private static Bag bag=null;
        private final int[] quantities;
        private final Tile[] tiles;
        private int total_letters;

        /**
         * Private constructor to create the Bag.
         */
        private Bag() 
        {
            this.quantities = new int[] {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            this.total_letters= 98;
            this.tiles = new Tile[] 
            {
                new Tile('A', 1),
                new Tile('B', 3),
                new Tile('C', 3),
                new Tile('D', 2),
                new Tile('E', 1),
                new Tile('F', 4), 
                new Tile('G', 2),
                new Tile('H', 4),
                new Tile('I', 1),
                new Tile('J', 8),
                new Tile('K', 5),
                new Tile('L', 1), 
                new Tile('M', 3),
                new Tile('N', 1),
                new Tile('O', 1),
                new Tile('P', 3),
                new Tile('Q', 10),
                new Tile('R', 1), 
                new Tile('S', 1),
                new Tile('T', 1),
                new Tile('U', 1),
                new Tile('V', 4),
                new Tile('W', 4),
                new Tile('X', 8), 
                new Tile('Y', 4),
                new Tile('Z', 10)                
            };
        }

        /**
         * Gets the singleton instance of the Bag.
         * 
         * @return The singleton Bag instance.
         */
        public static Bag getBag()
        {
            if(bag==null)
            {
                bag=new Bag();
            }
            return bag;
        }

        /**
         * Gets the quantities of each letter tile in the bag.
         *
         * @return An array of quantities.
         */
        public int[] getQuantities()
        {
            int[] q=this.quantities.clone();
            return q;
        }

        /**
         * Gets a random tile from the bag.
         *
         * @return A random Tile, or null if the bag is empty.
         */
        public Tile getRand()
        {
            if (this.total_letters == 0) return null;
            Random random = new Random();
            int randomIndex = random.nextInt(this.tiles.length);
            while(this.quantities[randomIndex]==0)
            {
                randomIndex = random.nextInt(this.tiles.length);
            }
            this.quantities[randomIndex]--;
            this.total_letters--;
            return tiles[randomIndex];
        }

        /**
         * Gets a specific tile from the bag based on the letter.
         *
         * @param letter The letter of the tile to get.
         * @return The Tile, or null if the tile is not available.
         */
        public Tile getTile(char letter)
        {
            int index=letter-'A';
            if (this.total_letters == 0 || index< 0 || index>25 || quantities[index]==0) return null;
            this.quantities[index]--;
            this.total_letters--;
            return tiles[index];
        }

        /**
         * Puts a tile back into the bag.
         *
         * @param t The Tile to put back.
         */
        public void put(Tile t)
        {
            if(total_letters>=98)
            return;
            int index=t.letter-'A';
            this.quantities[index]++;
            this.total_letters++;
        }

        /**
         * Gets the number of tiles remaining in the bag.
         *
         * @return The number of tiles remaining.
         */
        public int size()
        {
            return this.total_letters;

        }
    
    }

}
