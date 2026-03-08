# Clowns User Guide

Clowns is a command-line task manager for tracking daily tasks.
It supports todos, deadlines, and events, with automatic saving to local storage.

## Command Summary

| Command | Format |
| --- | --- |
| [Add todo](#add-a-todo) | `todo <description>` |
| [Add deadline](#add-a-deadline) | `deadline <description> /by <dd-MM-yyyy HHmm>` |
| [Add event](#add-an-event) | `event <description> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm>` |
| [List tasks](#list-tasks) | `list` |
| [Mark task done](#mark-or-unmark-a-task) | `mark <task number>` |
| [Unmark task](#mark-or-unmark-a-task) | `unmark <task number>` |
| [Delete task](#delete-a-task) | `delete <task number>` |
| [Find tasks](#find-tasks) | `find <keyword>` |
| [Exit app](#exit) | `exit` |

## Date and Time Input Format

Clowns accepts date-time input in this format:

`dd-MM-yyyy HHmm` (24-hour format)

Example: `06-03-2026 1830`

## Features

<a id="add-a-todo"></a>
### 1) Add a Todo

Adds a basic task.

Format: `todo <description>`

Example:
`todo CS2113 IP Markdown`

Expected response:

```text
  ---------------------------------
  ToDo added: todo CS2113 IP Markdown
  You now have 1 clownery in total.
  ---------------------------------
```

<a id="add-a-deadline"></a>
### 2) Add a Deadline

Adds a task with a deadline (due by).

Format: `deadline <description> /by <dd-MM-yyyy HHmm>`

Example:
`deadline push CS2113 branch /by 10-03-2026 2359`

Expected response:
```text
  ---------------------------------
  Deadline added: deadline push CS2113 branch /by 10-03-2026 2359
  You now have 1 clownery in total.
  ---------------------------------
```

<a id="add-an-event"></a>
### 3) Add an Event

Adds a task with start and end time.

Format: `event <description> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm>`

Example:
`event Week 7 open /from 09-03-2026 1400 /to 09-03-2026 1600`

Notes:
- `/to` cannot be earlier than `/from`.
- Both `/from` and `/to` are required.
- The date/time format must be strictly followed.

Expected response:
```text
  ---------------------------------
  Event added: event Week 7 open /from 09-03-2026 1400 /to 09-03-2026 1600
  You now have 2 clownery in total.
  ---------------------------------
```

<a id="list-tasks"></a>
### 4) List Tasks

Shows all tasks with numbering.

Format: `list`

Example output:

```text
  ---------------------------------
  Here is your list of clownery:
  1. [D][ ] push CS2113 branch (by: 10 Mar 2026 2359)
  2. [E][ ] Week 7 open (from: 09 Mar 2026 1400 to: 09 Mar 2026 1600)

  ---------------------------------
```

<a id="mark-or-unmark-a-task"></a>
### 5) Mark or Unmark a Task

Marks task as done or undone using its task number.

Format:
- `mark <task number>`
- `unmark <task number>`

Examples:
- `mark 2`
- `unmark 2`

Expected output:
```text
  ---------------------------------
  Amazing work! Marked 2 as done.
  ---------------------------------
```

```text
  ---------------------------------
  What a clown. Task 2 is now unmarked.
  [E][ ] Week 7 open (from: 09 Mar 2026 1400 to: 09 Mar 2026 1600)
  ---------------------------------
```

<a id="delete-a-task"></a>
### 6) Delete a Task

Deletes a task by task number.

Format: `delete <task number>`

Example:
`delete 1`

Expected output:
```text
  ---------------------------------
  Deleted task: [D][ ] push CS2113 branch (by: 10 Mar 2026 2359)
  You now have 1 clownery in total.
  ---------------------------------
```

<a id="find-tasks"></a>
### 7) Find Tasks

Finds tasks whose descriptions contain a keyword (case-insensitive).

Format: `find <keyword>`

Example:
`find week`

Expected output:
```text
  ---------------------------------
  Here are the matching clownery tasks:
  1. [E][ ] Week 7 open (from: 09 Mar 2026 1400 to: 09 Mar 2026 1600)

  ---------------------------------
```

<a id="exit"></a>
### 8) Exit

Ends the program.

Format: `exit`

Expected output:
```text
  ---------------------------------
  Clowning complete.
  Goodbye fellow clown!
  ---------------------------------
```

## Data Storage

Tasks are saved automatically to:

`src/main/java/clowns/data/ClownList.txt`

Any change-producing command (`todo`, `deadline`, `event`, `mark`, `unmark`, `delete`) updates the file automatically.

## Error Handling

There is validation for user inputs:

- Unknown commands
- Missing or empty arguments
- Invalid task numbers
- Invalid date-time format
- Invalid event time ranges