package com.company;

/*************************************
 *  Student Name: Nicole Berty
 *
 *  Student ID: 18246702
 *
 *  Group Members: Rioghan Lowry 18226531
 *                 Michele Cavaliere 18219365
 *                 Sean Lynch 18245137
 *
 *************************************/
import java.io.*;
import java.util.*;
import java.lang.*;

public class WordSearchPuzzle {

    private char[][] puzzle;
    private List<String> puzzleWords = new LinkedList<>();
    private List<String> wordDetails = new LinkedList<>();

    public WordSearchPuzzle(List<String> userSpecifiedWords) {
        for (int i = 0; i < userSpecifiedWords.size(); i++)
            puzzleWords.add(userSpecifiedWords.get(i).toUpperCase());
        Collections.sort(puzzleWords);
    }

    public WordSearchPuzzle(String wordFile, int wordCount, int shortest, int longest) {
        try {
            FileReader aFileReader = new FileReader(wordFile);
            BufferedReader aBufferReader = new BufferedReader(aFileReader);
            String lineFromFile;
            ArrayList<String> words = new ArrayList<>();
            lineFromFile = aBufferReader.readLine();
            while (lineFromFile != null) {
                words.add(lineFromFile.toUpperCase());
                lineFromFile = aBufferReader.readLine();
            }
            aBufferReader.close();
            aFileReader.close();
            int rand;
            for (int i = 0; i < wordCount; i++) {
                rand = (int) (Math.random() * 848);
                if ((words.get(rand)).length() >= shortest && (words.get(rand)).length() <= longest && !(puzzleWords.contains(rand))) {
                    puzzleWords.add(words.get(rand));
                } else {
                    i--;
                }
            }
        } catch (IOException x) {
            System.out.println("Error");
        }
        Collections.sort(puzzleWords);
    }

    public char[][] getPuzzleAsGrid() {
        double sum = 0;
        for (int i = 0; i < puzzleWords.size(); i++) {
            sum += puzzleWords.get(i).length();
        }
        sum *= 4;
        System.out.println("\nSum of length of words used * 4 = " + sum);
        System.out.println("Square root of sum = " + Math.sqrt(sum));
        sum = (Math.sqrt(sum));
        int dimensions = (int) Math.ceil(sum);
        System.out.println("Number of rows: " + dimensions);
        System.out.println("Number of columns: " + dimensions);
        puzzle = new char[dimensions][dimensions];
        generateWordSearchPuzzle();
        return puzzle;
    }

    public List<String> getWordSearchList() {
        System.out.println("\nThe list of words used in the puzzle: ");
        for (int i = 0; i < puzzleWords.size(); i++) {
            System.out.println(puzzleWords.get(i));
        }
        return puzzleWords;
    }

    public String getPuzzleAsString() {
        System.out.println("\nPuzzle as a string with unused positions filled with random characters: ");
        String TheWholeLoad = "";
        for (int i = 0; i < puzzle.length; i++) {
            TheWholeLoad += puzzle[i];
            System.out.println(puzzle[i]);
        }
        return TheWholeLoad;
    }

    public void showWordSearchPuzzle(boolean hide) {
        if (hide) {
            getPuzzleAsGrid();
            getWordSearchList();
        }
        else{
            getPuzzleAsGrid();
            System.out.println("\nPuzzle words, showing their [row] [col] positions and directions: ");
            for (int i = 0; i < wordDetails.size(); i ++)
                System.out.println(wordDetails.get(i));
        }
    }

    private void generateWordSearchPuzzle() {
        int dimensions = puzzle.length;
        HashMap<Integer, String> wordDirection = new HashMap<Integer, String>();
        String[] Directions = {"Going Down", "Going Up", "Left to Right", "Right to Left"};
        int direction;
        for (int i = 0; i < puzzleWords.size(); i++) {
            direction = (int) (Math.random() * 4);
            wordDirection.put(i, Directions[direction]);
        }
        int startingCols, startRow, startCol, startingRows, counter;
        for (int i = 0; i < puzzleWords.size(); i++) {
            counter = 0;
            String temp = puzzleWords.get(i);
            int positionClear = 0;
            startRow = 0;
            startCol = 0;
            boolean lineClear = false;
            if (wordDirection.get(i) == "Left to Right" || wordDirection.get(i) == "Right to Left") {
                startingCols = dimensions - temp.length();
                //checks to see if there is anything in the way
                while (!lineClear) {
                    startRow = (int) (Math.random() * dimensions);
                    startCol = (int) (Math.random() * startingCols);
                    counter++;
                    if (startCol + temp.length() >= dimensions) {
                        startCol = (int) (Math.random() * startingCols);
                    }
                    for (int k = 0; k < temp.length(); k++) {
                        if(puzzle[startRow][startCol + k] == 0) {
                            positionClear++;
                        }
                        else {
                            positionClear = 0;
                            break;
                        }
                        if (positionClear == temp.length()){
                            lineClear = true;
                        }
                        else {
                            lineClear = false;
                        }
                        if (counter > 250000) {
                            break;
                        }
                    }
                }
            } else {
                startingRows = dimensions - temp.length();
                lineClear = false;
                //checks to see if there is anything in the way
                while (!lineClear) {
                    startCol = (int) (Math.random() * dimensions);
                    startRow = (int) (Math.random() * startingRows);
                    counter++;
                    if (startRow + temp.length() >= dimensions) {
                        startRow = (int) (Math.random() * startingRows);
                    }
                    for (int k = 0; k < temp.length(); k++) {
                        if (puzzle[startRow + k][startCol] == 0) {
                            positionClear++;
                        } else {
                            positionClear = 0;
                            break;
                        }
                        if (positionClear == temp.length()) {
                            lineClear = true;
                        } else {
                            lineClear = false;
                        }
                        if (counter > 250000) {
                            break;
                        }
                    }
                }
            }
            if (puzzleWords.size() > 0) {
                switch (wordDirection.get(i)) {
                    case "Left to Right":
                        wordDetails.add(puzzleWords.get(i) + "[" + startRow + "][" + startCol + "]" + " " + wordDirection.get(i));
                        for (int x = 0; x < temp.length(); x++) {
                            puzzle[startRow][startCol + x] = temp.charAt(x);
                        }
                        break;
                    case "Right to Left":
                        wordDetails.add(puzzleWords.get(i) + "[" + startRow + "][" + (startCol + temp.length() - 1) + "]" + " " + wordDirection.get(i));
                        for (int x = temp.length() - 1; x >= 0; x--) {
                            puzzle[startRow][startCol + x] = temp.charAt(temp.length() - x - 1);
                        }
                        break;
                    case "Going Up":
                        wordDetails.add(puzzleWords.get(i) + "[" + (startRow + temp.length() - 1) + "][" + startCol + "]" + " " + wordDirection.get(i));
                        for (int x = temp.length() - 1; x >= 0; x--) {
                            puzzle[startRow + x][startCol] = temp.charAt(temp.length() - x - 1);
                        }
                        break;
                    case "Going Down":
                        wordDetails.add(puzzleWords.get(i) + "[" + startRow + "][" + startCol + "]" + " " + wordDirection.get(i));
                        for (int x = 0; x < temp.length(); x++) {
                            puzzle[startRow + x][startCol] = temp.charAt(x);
                        }
                        break;
                }
            }
        }
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (puzzle[i][j] == 0) {
                    int rand = (int) (Math.random() * 26);
                    puzzle[i][j] = alphabet[rand];
                }
            }
        }
        System.out.println("\nThe puzzle in grid form:\n");
        for(int i = 0; i < dimensions; i ++) {
            for (int j = 0; j < dimensions; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println();
        }
    }
}