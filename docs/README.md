# Falco User Guide

![Screenshot of Falco Chatbot's GUI being utilized as a list tracker.](/docs/Ui.png)

### Welcome to Falco!

Falco is your intelligent, interactive assistant designed to help keep track your future plans.
Whether you want to do simple tasks, chase a deadline, or remind yourself of an event, Falco
is here to help—anytime, anywhere.

## Viewing help: 'help'

Shows a message that gives list of available commands

Format: 'help'

```
________________________________________________________________________________________________________
    Here are the commands available: (￣^￣ )ゞ
    list, reset, help, find, delete/remove, mark, unmark, todo, deadline, event, period
________________________________________________________________________________________________________
```

## Adding no-time task: 'todo'

Adds task that has no time limit

Format: 'todo <TASK DETAILS>'

Example: `todo buy bread`

```
________________________________________________________________________________________________________
    Yessir! I've added this task: 
    	[T][ ] buy bread
    Now you have 1 tasks in the list, Sir! (￣^￣ )ゞ
________________________________________________________________________________________________________
```

## Adding deadlines task: 'deadline'

Adds task that has a deadline. 

Format: 'deadline <TASK DETAILS> /by dd/MM/yyyy hhMM'

Example: `deadline do homework /by 18/08/2025 1800`

```
________________________________________________________________________________________________________
    Yessir! I've added this task: 
    	[D][ ] do homework (by: 18 August 2025 6:00 pm)
    Now you have 2 tasks in the list, Sir! (￣^￣ )ゞ
________________________________________________________________________________________________________
```

## Adding events task: 'event'

Adds task that is an event.

Format: 'event <TASK DETAILS> /from dd/MM/yyyy hhMM /to dd/MM/yyyy hhMM'

Example: `event zoom meeting /from 18/08/2025 1800 /to 18/08/2025 2000`

```
________________________________________________________________________________________________________
    Yessir! I've added this task: 
    	[E][ ] zoom meeting (from: 18 August 2025 6:00 pm, to: 18 August 2025 8:00 pm)
    Now you have 3 tasks in the list, Sir! (￣^￣ )ゞ
________________________________________________________________________________________________________
```

## Adding period task: 'period'

Adds task that must be done within a period.

Format: 'period <TASK DETAILS> /between dd/MM/yyyy hhMM /and dd/MM/yyyy hhMM'

Example: `period collect certificate /between 18/08/2025 1800 /and 18/08/2025 2000`

```
________________________________________________________________________________________________________
    Yessir! I've added this task: 
    	[P][ ] collect certificate, between 18 August 2025 6:00 pm and 18 August 2025 8:00 pm
    Now you have 4 tasks in the list, Sir! (￣^￣ )ゞ
________________________________________________________________________________________________________
```

## Listing all tasks: 'list'

Shows a list of all tasks that has been posted.

Format: 'list'

## Resetting the list: 'reset'

Resets the list by clearing out all tasks in the list.

Format: 'reset'

## Finding tasks: 'find'

Finds and list out all the tasks containing the keyword. 

Format: 'find <KEYWORD>'

Example: `find buy`

```
________________________________________________________________________________________________________
    Sir, here are the matching tasks in your list: (￣^￣ )ゞ
    1.[T][ ] buy bread
    2.[T][ ] buy milk
________________________________________________________________________________________________________
```

## Deleting a task: 'delete' 'remove'

Deletes the specified task from the list.

Format: 'delete <TASK NUMBER>' 'remove <TASK NUMBER>'

Example: 'delete 2' or 'remove 2' Removes out the number 2 task in the list.

## Marking a task: 'mark'

Marks the specified task as done.

Format: 'mark <TASK NUMBER>'

## Unmarking a task: 'unmark'

Unmarks the specified task as done.

Format: 'unmark <TASK NUMBER>'

## Exiting the program: 'bye'

Exits the program.

Format: 'bye'