package test;

import java.util.Arrays;

/**
 * The Word class represents a word formed by tiles in the Scrabble game.
 * It includes the tiles, the starting position (row and column), and the orientation (vertical or horizontal).
 */
public class Word {
Tile[] tiles; // Array of tiles forming the word
int row,col; // Starting row and column position of the word
boolean vertical; // Orientation of the word: true for vertical, false for horizontal

/**
 * Constructor to create a new Word.
 *
 * @param tiles    The tiles forming the word.
 * @param row      The starting row position of the word.
 * @param col      The starting column position of the word.
 * @param vertical True if the word is placed vertically, false if horizontally.
 */
public Word(Tile[] tiles, int row, int col, boolean vertical) 
{
    this.tiles = tiles;
    this.row = row;
    this.col = col;
    this.vertical = vertical;
}

/**
 * Gets the tiles forming the word.
 *
 * @return An array of tiles.
 */
public Tile[] getTiles() 
{
    return tiles;
}

/**
 * Gets the starting row position of the word.
 *
 * @return The starting row position.
 */
public int getRow() 
{
    return row;
}

/**
 * Gets the starting column position of the word.
 *
 * @return The starting column position.
 */
public int getCol() 
{
    return col;
}

/**
 * Checks if the word is placed vertically.
 *
 * @return True if the word is vertical, false if horizontal.
 */
public boolean isVertical() 
{
    return vertical;
}

/**
 * Checks if this word is equal to another object.
 *
 * @param obj The object to compare.
 * @return True if the words are equal, false otherwise.
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
    Word other = (Word) obj;
    if (!Arrays.equals(tiles, other.tiles))
        return false;
    if (row != other.row)
        return false;
    if (col != other.col)
        return false;
    if (vertical != other.vertical)
        return false;
    return true;
}

/**
 * Gets the length of the word (number of tiles).
 *
 * @return The length of the word.
 */
public int getWordLength()
{
    return this.tiles.length;
}


}
