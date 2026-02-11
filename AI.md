# AI

AI Used: VSCode copilot.

## Week 2
AI was used to autocomplete lines of code that I was writing, while I was writing them.

The majority of the entire Task.java file was also auto-generated without me having to specify anything other than the name of the file. Perhaps it recognizes this project simply from the rest of the context?

The various task subclassess were also generated via AI.

---

## Week 3

AI was used to generate the regex operations for loading the Task classes from their saveState strings.

AI was also used to adapt the code across the different class types when copy-pasting (it automatically renames the various variables + changes the lines of code to match the differing variables. It does the same for javadoc comments.)

AI was also used to automatically generate the JUnit test cases.

AI automatically generated the entire 'find' feature required by Level-9.

## Week 4

From here onwards, I have changed AI models and usage.

I am now using GLM 4.7, from Z.ai's coding lite plan. I am combining this with Cline for VSCode.
I am also no longer writing any code beyond the bare minimum / bug fixes. _Everything_ is AI generated from now.

The entire transition from TUI to GUI was AI powered. This included
- Generating the GUI elements.
- Changing the parser to use String input instead of scanner directly.
- Having the app be toggleable between terminal output and gui from an argument.


## Week 5

Continuing the fully AI generated code, the following refactors to my code were done.
- Changed commands to each have their own class.
- Created an abstract command class for said commands to inherit from.