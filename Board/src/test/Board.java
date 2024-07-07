package test;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import test.Tile.Bag;

/**
 * The Board class represents the Scrabble game board.
 * It manages the placement of tiles, checks word validity, and calculates scores.
 */
public class Board {
    // Constants
    final static int SIZE = 15; // Size of the Scrabble board
    private static Board board=null; // Singleton instance of the board

    // Board attributes
    Tile[][] myBoard; // 2D array representing the board tiles
    private ArrayList<Word> allWordsOnBoard; // List of all words placed on the board
    private boolean boardIsEmpty; // Flag to check if the board is empty


    // Score multipliers for the board positions
    public static char[][] scoreBoard = {
        {'R',' ',' ','L',' ',' ',' ','R',' ',' ',' ','L',' ',' ','R'},
        {' ','Y',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','Y',' '},
        {' ',' ','Y',' ',' ',' ','L',' ','L',' ',' ',' ','Y',' ',' '},
        {'L',' ',' ','Y',' ',' ',' ','L',' ',' ',' ','Y',' ',' ','L'},
        {' ',' ',' ',' ','Y',' ',' ',' ',' ',' ','y',' ',' ',' ',' '},
        {' ','B',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','B',' '},
        {' ',' ','L',' ',' ',' ','L',' ','L',' ',' ',' ','L',' ',' '},
        {'R',' ',' ','L',' ',' ',' ','S',' ',' ',' ','L',' ',' ','R'},
        {' ',' ','L',' ',' ',' ','L',' ','L',' ',' ',' ','L',' ',' '},
        {' ','B',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','B',' '},
        {' ',' ',' ',' ','Y',' ',' ',' ',' ',' ','Y',' ',' ',' ',' '},
        {'L',' ',' ','Y',' ',' ',' ','L',' ',' ',' ','Y',' ',' ','L'},
        {' ',' ','Y',' ',' ',' ','L',' ','L',' ',' ',' ','Y',' ',' '},
        {' ','Y',' ',' ',' ','B',' ',' ',' ','B',' ',' ',' ','Y',' '},
        {'R',' ',' ','L',' ',' ',' ','R',' ',' ',' ','L',' ',' ','R'}
    };

    //Constructor to initialize the board
    public Board() 
    {
        this.myBoard =new Tile[SIZE][SIZE];
        this.boardIsEmpty = true;
        this.allWordsOnBoard = new ArrayList<Word>();
    }

    /**
     * Singleton method to get the instance of the Board.
     *
     * @return Board instance
     */
    public static Board getBoard()
    {
        if(board==null){
            board=new Board();
        }
        return board;
    }

    /**
     * Gets a copy of the board tiles.
     *
     * @return 2D array of tiles
     */
    public Tile[][] getTiles()
    {
        return this.myBoard.clone();
    }

    /**
     * Checks if the word placement is legal on an empty board.
     *
     * @param word The word to be checked
     * @return true if legal, false otherwise
     */
    private boolean emptyBoardLegal(Word word)
    {
        if(word.isVertical())
        {
            if(word.getCol()!=7 || word.getRow()>7) return false;
            if(word.getRow()+word.getWordLength()<7 || word.getRow()+word.getWordLength()>=SIZE) return false;
        }  
        else 
        {
            if(word.getRow()!=7 || word.getCol()>7) return false;
            if(word.getCol()+word.getWordLength()<7 || word.getCol()+word.getWordLength()>=SIZE) return false;
        } 
        return true;
    }

    /**
     * Checks if the word placement is legal on a non-empty board.
     *
     * @param word The word to be checked
     * @return true if legal, false otherwise
     */
    private boolean notEmptyBoardLegal(Word word)
    {
        if(word.isVertical())
        {
            if(word.getRow()+word.getWordLength()>=SIZE) return false;
        }
        else 
        {
            if(word.getCol()+word.getWordLength()>=SIZE) return false;
        }
        if(!neighborChecker(word)) return false;
        return true;
    }

    /**
     * Checks if the word has adjacent tiles or overlaps existing tiles on the board.
     *
     * @param word The word to be checked
     * @return true if it has adjacent or overlapping tiles, false otherwise
     */
    private boolean neighborChecker(Word word)
    {
        Tile[] tiles = word.getTiles();
        int length= word.getTiles().length;
        boolean vertical= word.isVertical();
        int row=word.getRow();
        int col=word.getCol();
        for (int i = 0; i < length; i++) 
        {
            int currentRow = vertical ? row + i : row;
            int currentCol = vertical ? col : col + i;
            // overlap
            if(tiles[i] == null && myBoard[currentRow][currentCol] != null) 
            {
                tiles[i] = myBoard[currentRow][currentCol];
                return true;
            }
            else 
            {
                if (vertical) 
                {
                    if (isAdjacent(currentRow, currentCol + 1) || isAdjacent(currentRow, currentCol - 1)) return true; // any tile
                }
                else 
                {
                    if (isAdjacent(currentRow + 1, currentCol) || isAdjacent(currentRow - 1, currentCol)) return true; // any tile
                }
            }
        } 
        return false;  
    }

    /**
     * Checks if a tile exists at the specified board position.
     *
     * @param row The row position
     * @param col The column position
     * @return true if a tile exists, false otherwise
     */
    public boolean isAdjacent(int row, int col) 
    {
        if(row >= 0 && row < SIZE && col >=0 && col < SIZE) 
            if (myBoard[row][col] != null) return true;
        return false;
    }

    /**
     * Checks if the word placement is legal on the board.
     *
     * @param word The word to be checked
     * @return true if legal, false otherwise
     */
    public boolean boardLegal(Word word)
    {
        if(word.getCol()<0 || word.getCol()>=SIZE || word.getRow()<0 || word.getRow()>=SIZE) return false;
        if(this.boardIsEmpty)
            return this.emptyBoardLegal(word);
        else
            return this.notEmptyBoardLegal(word);

    }

    /**
     * Placeholder for checking if the word is in the dictionary.
     *
     * @param word The word to be checked
     * @return true if the word is in the dictionary, false otherwise
     */
    public boolean dictionaryLegal(Word word)
    {
        return true;
    }

    /**
     * Gets all valid words formed by placing the given word on the board.
     *
     * @param word The word to be placed
     * @return List of valid words formed, or null if the placement is illegal
     */
    public ArrayList<Word> getWords(Word word)
    {
        if(!dictionaryLegal(word)) return null;
        if(!boardLegal(word)) return null;
        ArrayList<Word> newWords = new ArrayList<Word>();
        Tile[][] tmpBoard=this.getTiles();
        int row=word.getRow();
        int col=word.getCol();
        int length=word.getWordLength();
        boolean vertical=word.isVertical();

        newWords.add(word);
        addNewWordToBoard(word, tmpBoard);
        for(int i=0;i<length;i++)
        {
            if(vertical)
            {
                Word w = (checkLeft(row+i, col,tmpBoard));
                if(w!=null && dictionaryLegal(w))
                    newWords.add(w);
            }
            else
            {
                Word w = (checkTop(row, col+i,tmpBoard));
                if(w!=null && dictionaryLegal(w))
                    newWords.add(w);
            }    
        }
        return newWords;
    }

    /**
     * Checks for vertical words formed above the placed word.
     *
     * @param currentRow The starting row
     * @param currentCol The starting column
     * @param board      The current state of the board
     * @return The formed word, or null if no valid word is formed
     */
    private Word checkTop(int currentRow,int currentCol,Tile[][] board)
    {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        if(currentRow>0){
            while(currentRow-1 >= 0 && board[currentRow-1][currentCol]!=null)
            {
                currentRow--;
            }
        }
        int start=currentRow;
        while(currentRow <= 14 && board[currentRow][currentCol]!=null)
        {
            tiles.add(board[currentRow][currentCol]);
            currentRow++;
        }
        Tile[] tilesArray = tiles.toArray(new Tile[tiles.size()]);
        Word w=new Word(tilesArray,start,currentCol,true);
        if(w.getWordLength() == 1) return null;
        for(Word w1:this.allWordsOnBoard)
        {
            if(w1.equals(w)) return null;

        }
        return w;
    }
    /**
     * Checks for horizontal words formed to the left of the placed word.
     *
     * @param currentRow The starting row
     * @param currentCol The starting column
     * @param board      The current state of the board
     * @return The formed word, or null if no valid word is formed
     */
    private Word checkLeft(int currentRow,int currentCol,Tile[][] board)
    {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        if(currentCol>0)
        {
            while(currentCol-1 >= 0 && board[currentRow][currentCol-1]!=null)
            {
                currentCol--;
            }
        }
        int start=currentCol;
        while(currentCol <= 14 && board[currentRow][currentCol]!=null)
        {
            tiles.add(board[currentRow][currentCol]);
            currentCol++;
        }
        Tile[] tilesArray = tiles.toArray(new Tile[tiles.size()]);
        Word w=new Word(tilesArray,currentRow,start,false);
        if(w.getWordLength() == 1) return null;
        for(Word w1:this.allWordsOnBoard)
        {
            if(w1.equals(w)) return null;

        }
        return w;
        
    }

    /**
     * Calculates the score for a given word based on the board's scoring rules.
     *
     * @param word The word to be scored
     * @return The score of the word
     */
    public int getScore(Word word)
    {
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        Tile tiles[] = word.getTiles();
        int score = 0;
        int multiplyBy = 1;
    
        for(int i=0; i<tiles.length; i++) 
        {
            int currentRow = vertical ? row + i : row;
            int currentCol = vertical ? col : col + i;
            switch (scoreBoard[currentRow][currentCol]) 
            {
                case 'L':
                    score += tiles[i].getScore() * 2;
                    break;
                case 'B':
                    score += tiles[i].getScore() * 3;
                    break;
                case 'Y':
                    score += tiles[i].getScore(); multiplyBy *= 2;
                    break;
                case 'R':
                    score += tiles[i].getScore(); multiplyBy *= 3;
                    break;
                case 'S':
                    score += tiles[i].getScore();
                    if (boardIsEmpty) multiplyBy *= 2 ;
                    break;
                default:
                    score += tiles[i].getScore();
                    break;
            }
        }

        score *= multiplyBy;
        return score;
    }
    
    /**
     * Calculates the cumulative score for a list of words.
     *
     * @param words List of words to be scored
     * @return The cumulative score of the words
     */
    public int cumulativeScore(ArrayList<Word> words) 
    {
        int score = 0;
        for (Word anyWord : words) 
            score += getScore(anyWord);
        return score;
    }

    /**
     * Tries to place a word on the board and returns the score if successful.
     *
     * @param word The word to be placed
     * @return The score of the placed word, or 0 if the placement is illegal
     */
    public int tryPlaceWord(Word word) 
    {
        ArrayList<Word> newWords = getWords(word);
        if(newWords==null) return 0;
        int score = cumulativeScore(newWords);
        this.boardIsEmpty = false;
        addNewWordToBoard(word,this.myBoard);
        allWordsOnBoard.addAll(newWords);
        return score;
    }

    /**
     * Adds a new word to the board.
     *
     * @param word  The word to be added
     * @param board The current state of the board
     */
    private void addNewWordToBoard(Word word,Tile[][] board)
    {
        int currentRow=word.getRow();
        int currentCol=word.getCol();
        Tile[] tiles=word.getTiles();
        for(int i=0;i<word.getWordLength();i++)
        {
            if(word.isVertical()){
                board[currentRow+i][currentCol]=tiles[i];
            }
            else{
                board[currentRow][currentCol+i]=tiles[i];

            }
            
        }
    }
}



    