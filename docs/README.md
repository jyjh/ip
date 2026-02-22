# Silver User Guide

Welcome to Silver - a CLI-like task manager app meant for people who prefer typing to clicking.

## Using the app

The app has two forms. A pure terminal interface and it's own GUI.
To use the GUI, simply launch the jar file.
To use the CLI, launch the jar file with the argument `--terminal`.


## Help

To view the list of commands available, simply use the `help` command in the app.
Do note that dates must follow the YYYY-MM-DD format. IE, if using the month of January, it must be specified as '01', and not just '1'.

## File Storage

A folder will be created to store all the created tasks and notes in the same directory the jar is launched from, `/data`. Two files will be stored inside, `tasks.json` and `notes.json`.

### File corruption
In the event of file corruption, the corrupted files will be renamed to `tasks-old.json` and `notes-old.json`, depending on which got corrupted, and will then be replaced by new, empty files. If an existing `tasks-old.json` is already present, it will be overwritten. Thus, in the event of corruption, it is important to recover your data the moment you are notified.
