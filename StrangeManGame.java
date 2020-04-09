/* 
Progammer: Munqiz Minhas / 11AM 
Instructor: Dr. Karl Zerangue 
Due: 4-9-2020
Assignment: Programming Assignment #2 - StrangeMan Game

A Java application, StrangeMan, reminiscent of the traditional, 
Hangman Game, but no drawing of a hanging stick figure will be produced.
-----------------------------------------------------------------------------
| Requirements:                                                             |
|    1. The secret word for each game must be randomly chosen from          | 
|       the wordlist.txt file, which contains 33,736 words.                 |
|    2. Displaying the secret word in a disguised format:                   |
|        - disguised word contains a dash ['-'] in the corresponding        |
|          position of each consonant                                       |
|        - disguised word contains an equal sign ['='] in the corresponding |
|          position of each vowel                                           |
|    3. If the guessed letter is not in the secret word, the incorrect      |
|       guess is added to the list of incorrect guesses (but only if it     |
|       is not already in the list)                                         |
|    4. The guess count should be incremented when a correct guess and      |
|       the first time an incorrect letter is guessed                       |
|    5. The guess count is NOT incremented if a guessed letter has already  |
|       been guessed correctly or incorrectly                               |
| Game Over Scoring:                                                        |
| The sum of the letters percentage and the word percentage                 |
| letters percentage: (100 - guessCount / 26.0 * 100)                       |
| word length percentage: (Length of the secret word / 26.0 * 100)          |
-----------------------------------------------------------------------------
*/
import java.util.Random;
import java.util.Scanner; 
import java.io.*; 
public class StrangeManGame {

    final public static int listCount = 33736; 
    public static void main(String[] args) throws IOException{
        Random randNum = new Random();
        
        // welcome text 
        System.out.println("Welcome to StrangeMan: The Word Guessing Game Unlike HangMan!"
                + "\nPlay as many games as you like. I'll remember your top score"
                + "\nand also compute your average for all games played.");
        // header
        System.out.println("---------------------------------------");
        
        // variable declerations 
        String secretWord = ""; 
        String hiddenWord = ""; 
        char userGuess; 
        String previousGuesses = "";
        int randomWordLocation;
        int position;
        int guessCount = 0; 
        double lettersPercentage; 
        double wordLengthPercentage;
        double score;
        char playerContinue; 
        boolean doYouWantToPlay; 
        
        Scanner kb = new Scanner(System.in);
        do{ // main do-while
        // read secret word from wordList file
            File file = new File("wordList.txt"); 
            Scanner fileToScan = new Scanner(file);
            for(randomWordLocation = randNum.nextInt(listCount); randomWordLocation < listCount; randomWordLocation++) {
                secretWord = fileToScan.nextLine();
            }
            fileToScan.close(); 
        // generating a disguised word for the secret random word   
            for (int x = 0; x < secretWord.length(); x++) {
                switch(secretWord.charAt(x)) {
                    case 'a': case 'e': case 'i': case 'o': case 'u':
                        hiddenWord = hiddenWord + "="; 
                    break; 
                    default: 
                        hiddenWord = hiddenWord + "-";
                    break;
                }
            } 
            do {
            // user guesses 
            System.out.printf("Guess a letter in this word: %s ?? ", hiddenWord);
            userGuess = kb.next().charAt(0); 
            // input check
                while(userGuess < 97 || userGuess > 122) {
                    System.out.print("Invalid input. Guess again: ");
                    userGuess = kb.next().charAt(0); 
            }                
            // input check - if input matches one of the letters in the secret word...   
            position = -1;     
                for(int i = 0; i < secretWord.length(); i++) {
                    if((secretWord.charAt(i) == userGuess) && (hiddenWord.charAt(i) == '=' || hiddenWord.charAt(i) == '-')) {
                       position = i; 
                       i = secretWord.length();  
                    }
                }
                if(previousGuesses.indexOf(userGuess) >= 0) {
                    guessCount--;
                }
                guessCount++;
                // update hiddenWord
                if(position != -1) {
                  hiddenWord = hiddenWord.substring(0, position) + userGuess + hiddenWord.substring(position + 1, hiddenWord.length());
                }
                // display list of incorrect letters guessed
                else {
                    previousGuesses += userGuess; 
                    System.out.println("Letters already guessed: " + previousGuesses);
                }
                // keep guessing till hiddenWord is exposed
                // inner game loop end
            } while (hiddenWord.compareTo(secretWord) != 0);
            
            // score
            System.out.println("\nYou guessed this word: " + secretWord + " in " + guessCount + " guesses!"); 
            lettersPercentage = (100 - guessCount / 26.0 * 100); 
            wordLengthPercentage = (secretWord.length() / 26.0 * 100);
            score = lettersPercentage + wordLengthPercentage; 
            System.out.printf("Your score: %.1f%%!%n", score);  
            
            System.out.print("\nWould you like to play again? (Enter 'y' to continue): ");
            playerContinue = kb.next().charAt(0); 
            if(playerContinue == 'y' || playerContinue == 'Y') {
                doYouWantToPlay = true; 
            }
            else {
                System.out.println("\nThanks for playing..."); 
                doYouWantToPlay = false;
            }
            hiddenWord = "";
            previousGuesses = "";
            score = 0; 
            guessCount = 0;
        } while(doYouWantToPlay);
        
        kb.close();
    }
}
