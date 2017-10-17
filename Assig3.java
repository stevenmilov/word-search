//Steven Milov
//Assignment 3 Extra Credit
//CS 0445 Spring 2016
//Ramirez

import java.io.*;
import java.util.*;

public class Assig3{

public static void main(String[] args){
  new Assig3();
}

public Assig3(){

  //read in filename

  Scanner inScan = new Scanner(System.in);
  Scanner fReader;
  File fName;
  String fString = "", phrase = "";

  // Make sure the file name is valid
  while (true)
  {
     try
     {
         System.out.println("Please enter grid filename:");
         fString = inScan.nextLine();
         fName = new File(fString);
         fReader = new Scanner(fName);

         break;
     }
     catch (IOException e)
     {
         System.out.println("Problem " + e);
     }
  }

  // Parse input file to create 2-d grid of characters
  String [] dims = (fReader.nextLine()).split(" ");
  int rows = Integer.parseInt(dims[0]);
  int cols = Integer.parseInt(dims[1]);

  char [][] theBoard = new char[rows][cols];

  for (int i = 0; i < rows; i++)
  {
    String rowString = fReader.nextLine(); //MAKE ROWS AND COLS CONSISTENT!!!
    for (int j = 0; j < rowString.length(); j++)
    {
      theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
    }
  }

  printGrid(theBoard);
  //initializations
  boolean found = false;
  String[] words;
  String[] output;
  System.out.println("\nPlease enter phrase (sep. by single spaces):");
  phrase = inScan.nextLine().toLowerCase();
  if(phrase.equals(""))
    System.exit(0);


  while(!phrase.equals("")){

    //find number of words
    words = phrase.split(" ");
    output = new String[words.length];
    System.out.println("Looking for: "+phrase+"\ncontaining "+words.length+" words");
    System.out.println("The phrase: "+phrase);

    //================ Find Phrase =============================================
    for(int r = 0; r < rows && !found; r++){
      for(int c = 0; c < cols && !found; c++){
        //resetGrid(theBoard);
        found = findPhrase(r,c,words,0,theBoard,output,0); //Right
        if(!found)
          found = findPhrase(r,c,words,0,theBoard,output,4); // Down Right
        if(!found)
          found = findPhrase(r,c,words,0,theBoard,output,1); // Down
        if(!found)
          found = findPhrase(r,c,words,0,theBoard,output,5); //Down Left
        if(!found)
          found = findPhrase(r,c,words,0,theBoard,output,2); // Left
        if(!found)
          found = findPhrase(r,c,words,0,theBoard,output,6); // Up Left
        if(!found)
          found = findPhrase(r,c,words,0,theBoard,output,3); // Up
        if(!found)
          found = findPhrase(r,c,words,0,theBoard,output,7); // Up Right
      }
    }

    if(!found){
      System.out.println("was not found");

    }
    else{
      System.out.println("was found:");
      for(int i = 0; i < words.length && output[i]!=null; i++)
        System.out.println(output[i]);
      printGrid(theBoard);
    }


    //================ End of Find Phrase ======================================


    //resetBoard
    resetGrid(theBoard);
    found = false;
    System.out.println("\nPlease enter phrase (sep. by single spaces):");
    phrase = inScan.nextLine().toLowerCase();
  } // End of While Loop








} // End of Constructor

public boolean findPhrase(int r, int c, String[] words, int loc, char [][] theBoard, String[] output,int dir){
  boolean answer = true;

  if(loc == words.length){ //if iterated through all the words

    return true;
  }
  else{ //if words still left to find
    answer = false;


          int[] wordFound = findWord(r,c,words[loc],theBoard,dir);
          int rCurr = r;
          int cCurr = c;
          if(wordFound[0] == 0){// word not found
            //System.out.println(words[loc]+" not found");
            return answer;
          }
          else if(wordFound[0] == 1){ // word found
            output[loc] = words[loc]+": ("+rCurr+","+cCurr+") to ";
            int rIn = rCurr;
            int cIn = cCurr;
            //finds location of last letter in word
            if(wordFound[1] == 0) //right
              cCurr = cCurr + words[loc].length()-1;
            if(wordFound[1] == 1) //down
              rCurr = rCurr + words[loc].length()-1;
            if(wordFound[1] == 2) //left
              cCurr = cCurr - (words[loc].length()-1);
            if(wordFound[1] == 3) //up
              rCurr = rCurr - (words[loc].length()-1);
            if(wordFound[1] == 4){//down right
              rCurr = rCurr + words[loc].length()-1;
              cCurr = cCurr + words[loc].length()-1;
            }
            if(wordFound[1] == 5){//down left
              rCurr = rCurr + words[loc].length()-1;
              cCurr = cCurr - (words[loc].length()-1);
            }
            if(wordFound[1] == 6){//up left
              rCurr = rCurr - (words[loc].length()-1);
              cCurr = cCurr - (words[loc].length()-1);
            }
            if(wordFound[1] == 7){//up right
              rCurr = rCurr - (words[loc].length()-1);
              cCurr = cCurr + words[loc].length()-1;
            }
            int rFi = rCurr;
            int cFi = cCurr;
            output[loc] = output[loc]+"("+rCurr+","+cCurr+")";

            //make recursive calls to find next word in the phrase



            answer = findPhrase(rCurr,cCurr+1,words,loc+1,theBoard,output,0); // Right
            if(!answer)
              answer = findPhrase(rCurr+1,cCurr+1,words,loc+1,theBoard,output,4); // Down Right
            if(!answer)
              answer = findPhrase(rCurr+1, cCurr, words, loc+1, theBoard,output,1);  // Down
            if(!answer)
              answer = findPhrase(rCurr+1,cCurr-1,words,loc+1,theBoard,output,5); // Down Left
            if (!answer)
              answer = findPhrase(rCurr, cCurr-1, words, loc+1, theBoard,output,2);  // Left
            if(!answer)
              answer = findPhrase(rCurr-1,cCurr-1,words,loc+1,theBoard,output,6); // Up Left
            if (!answer)
              answer = findPhrase(rCurr-1, cCurr, words, loc+1, theBoard,output,3);  // Up
            if(!answer)
              answer = findPhrase(rCurr-1,cCurr+1,words,loc+1,theBoard,output,7); // Up Right
            if(!answer){
              //makes word lowercase if no more words found
              makeLowerCase(rIn,cIn,rFi,cFi,theBoard);
              //System.out.println("Making "+ words[loc]+" lowercase...");
              output[loc] = null;
            }

          }


    return answer;
  }
} // End of findPhrase =========================================================

public int[] findWord(int r, int c, String word, char [][] theBoard, int dir){


  int[] result = new int[2];//0 or 1 if false or true, and 0 through 3 for direction of the word
  result[0] = 0;
  result[1] = 0;
  // direction depends on given int dir

      String test = "";
      if(r >= theBoard.length || r < 0 || c >= theBoard[0].length || c < 0){
        return result;
      }
          //right direction
      if(dir==0){
          for(int i = 0; i < word.length() && (c+i < theBoard[0].length) ;i++){
            test = test+theBoard[r][c+i];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 0;
            for(int i = 0; i < word.length();i++){
              theBoard[r][c+i] = Character.toUpperCase(theBoard[r][c+i]);
            }
          }
      }


          //down direction
      if(dir==1){
          for(int i = 0; i < word.length() && (r+i < theBoard.length);i++){
            test = test+theBoard[r+i][c];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 1;

            for(int i = 0; i < word.length();i++){
              theBoard[r+i][c] = Character.toUpperCase(theBoard[r+i][c]);
            }
          }
      }

          //left direction
      if(dir==2){
          for(int i = 0; i < word.length() && (c-i >= 0);i++){
            test = test+theBoard[r][c-i];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 2;

            for(int i = 0; i < word.length();i++){
              theBoard[r][c-i] = Character.toUpperCase(theBoard[r][c-i]);
            }
          }
      }

          //up direction
      if(dir==3){
          for(int i = 0; i < word.length() && (r-i >= 0);i++){
            test = test+theBoard[r-i][c];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 3;
            for(int i = 0; i < word.length();i++){
              theBoard[r-i][c] = Character.toUpperCase(theBoard[r-i][c]);
            }
          }
      }
          //diagonal down right
      if(dir==4){
          for(int i = 0; i < word.length() && (r+i < theBoard.length) && (c+i < theBoard[0].length);i++){
            test = test+theBoard[r+i][c+i];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 4;
            for(int i = 0; i < word.length();i++){
              theBoard[r+i][c+i] = Character.toUpperCase(theBoard[r+i][c+i]);
            }
          }
      }
          //diagonal down left
      if(dir==5){
          for(int i = 0; i < word.length() && (r+i < theBoard.length) && (c-i >= 0);i++){
            test = test+theBoard[r+i][c-i];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 5;
            for(int i = 0; i < word.length();i++){
              theBoard[r+i][c-i] = Character.toUpperCase(theBoard[r+i][c-i]);
            }
          }
      }

        //diagonal up left
      if(dir==6){
          for(int i = 0; i < word.length() && (r-i >= 0) && (c-i >= 0);i++){
            test = test+theBoard[r-i][c-i];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 6;
            for(int i = 0; i < word.length();i++){
              theBoard[r-i][c-i] = Character.toUpperCase(theBoard[r-i][c-i]);
            }
          }
      }
        //diagonal up right
      if(dir==7){
          for(int i = 0; i < word.length() && (r-i >= 0) && (c+i < theBoard[0].length);i++){
            test = test+theBoard[r-i][c+i];
          }
          if(test.equals(word)){
            result[0] = 1;
            result[1] = 7;
            for(int i = 0; i < word.length();i++){
              theBoard[r-i][c+i] = Character.toUpperCase(theBoard[r-i][c+i]);
            }
          }
      }

  return result;
} //End of findWord ============================================================

public void makeLowerCase(int rIn,int cIn,int rFi,int cFi,char[][] theBoard){
  //HORIZONTAL
  if(rFi == rIn){ //right
      if(cIn<cFi){
        //System.out.print("lowercasing right: ");
        for(int i = cIn; i<=cFi;i++){
          //System.out.println(theBoard[rFi][i]+" "+rFi + "," + i);
          theBoard[rFi][i] = Character.toLowerCase(theBoard[rFi][i]);
          //System.out.println(theBoard[rFi][i]);
        }
      }
      if(cFi<cIn){ //left
        //System.out.print("lowercasing left: ");
        for(int i = cIn; i>=cFi;i--){
          //System.out.println(theBoard[rFi][i]+" "+rFi + "," + i);
          theBoard[rFi][i] = Character.toLowerCase(theBoard[rFi][i]);
          //System.out.println(theBoard[rFi][i]);
        }
      }
    }
    //VERTICAL
    if(cFi == cIn){
      if(rIn<rFi){ //down
        //System.out.print("lowercasing down: ");
        for(int i = rIn; i<=rFi;i++){
          //System.out.println(theBoard[i][cFi]+" "+i+","+cFi);
          theBoard[i][cFi] = Character.toLowerCase(theBoard[i][cFi]);
          //System.out.println(theBoard[i][cFi]);
        }
      }
      if(rFi<rIn){ //up
        //System.out.print("lowercasing up: ");
        for(int i = rIn; i>=rFi;i--){
          //System.out.println(theBoard[i][cFi]+" "+i+","+cFi);
          theBoard[i][cFi] = Character.toLowerCase(theBoard[i][cFi]);
          //System.out.println(theBoard[i][cFi]);
        }
      }
    }
    //DIAGONAL RIGHT
    if(cIn < cFi){
      if(rIn<rFi){ //down right
        //System.out.print("lowercasing down right: ");
        for(int i = 0; i<=(rFi-rIn);i++){
          //System.out.println(theBoard[i][cFi]+" "+i+","+cFi);
          theBoard[rIn+i][cIn+i] = Character.toLowerCase(theBoard[rIn+i][cIn+i]);
          //System.out.println(theBoard[i][cFi]);
        }
      }
      if(rIn>rFi){ //up right
        //System.out.print("lowercasing up right: ");
        for(int i = 0; i<=(rIn-rFi);i++){
          //System.out.println(theBoard[i][cFi]+" "+i+","+cFi);
          theBoard[rIn-i][cIn+i] = Character.toLowerCase(theBoard[rIn-i][cIn+i]);
          //System.out.println(theBoard[i][cFi]);
        }
      }
    }
    //DIAGONAL LEFT
    if(cIn > cFi){
      if(rIn<rFi){ //down left
        //System.out.print("lowercasing down left: ");
        for(int i = 0; i<=(rFi-rIn);i++){
          //System.out.println(theBoard[i][cFi]+" "+i+","+cFi);
          theBoard[rIn+i][cIn-i] = Character.toLowerCase(theBoard[rIn+i][cIn-i]);
          //System.out.println(theBoard[i][cFi]);
        }
      }
      if(rIn>rFi){ //up left
        //System.out.print("lowercasing up left: ");
        for(int i = 0; i<=(rIn-rFi);i++){
          //System.out.println(theBoard[i][cFi]+" "+i+","+cFi);
          theBoard[rIn-i][cIn-i] = Character.toLowerCase(theBoard[rIn-i][cIn-i]);
          //System.out.println(theBoard[i][cFi]);
        }
      }
    }

} // End of makeLowerCase

public void printGrid(char [][] theBoard){

  for (int i = 0; i < theBoard.length; i++){
    for (int j = 0; j < theBoard[0].length; j++){
      System.out.print(theBoard[i][j] + " ");
    }
    System.out.println();
  }
} // End of printGrid

public void resetGrid(char [][] theBoard){
  for (int i = 0; i < theBoard.length; i++){
    for (int j = 0; j < theBoard[0].length; j++){
      theBoard[i][j]= Character.toLowerCase(theBoard[i][j]);
    }
  }
} // End of resetGrid






} // EOF
