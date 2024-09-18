# Wordle Java Clone

This is a command-line version of the popular word-guessing game **Wordle**, written in Java. The game allows players to guess a hidden word within 6 tries, receiving color-coded feedback on each guess.

## How It Works

- The user selects a difficulty level:
  - **Easy**: 5-letter word
  - **Medium**: 6-letter word
  - **Hard**: 7-letter word
- After selecting a difficulty, a random word is chosen from a pre-defined list of words.
- The user has 6 attempts to guess the word.
- After each guess, the program provides feedback:
  - **Green**: Correct letter in the correct position.
  - **Yellow**: Correct letter in the wrong position.
  - **No Color**: Letter not in the word.
- The game continues until the user either guesses the word or runs out of attempts.
