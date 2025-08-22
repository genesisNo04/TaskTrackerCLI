# Task Tracker CLI

A simple **Command Line Interface (CLI) task tracker** written in Java. This tool allows you to manage tasks with statuses like **to do**, **in progress**, and **done**, all from your terminal.

---

## Features

- Add new tasks with an optional status (`to do` by default).
- Delete tasks by ID.
- Update task descriptions.
- Mark tasks as `to do`, `in progress`, or `done`.
- List all tasks or filter by status.
- Stores tasks in a JSON file (`tasks.json`) so data persists between runs.

---

## Installation

1. Clone the repository:

```bash
git clone <your-repo-url>
cd TaskTrackerCLI
```

2. Build the project using Maven:
```bash
mvn clean package
```
3. The executable JAR will be in the target folder:
```bash
target/TaskTrackerCLI-1.0-SNAPSHOT-jar-with-dependencies.jar
```

3. Run the CLI with the following command:
```bash
java -jar target/TaskTrackerCLI-1.0-SNAPSHOT-jar-with-dependencies.jar <command> [options]
```
4. Command for use:
```
| Command                 | Description                                                     |
| ----------------------- | --------------------------------------------------------------- |
| `add <task> [status]`   | Add a new task. Status is optional (`to do` by default).        |
| `delete <id>`           | Delete a task by its ID.                                        |
| `update <id> <desc>`    | Update the description of a task by ID.                         |
| `mark-to-do <id>`       | Mark a task as **to do**.                                       |
| `mark-in-progress <id>` | Mark a task as **in progress**.                                 |
| `mark-done <id>`        | Mark a task as **done**.                                        |
| `list`                  | List all tasks.                                                 |
| `list <status>`         | List tasks filtered by status (`to do`, `in progress`, `done`). |
| `help`                  | Display help information.                                       |
```

5. Example:
```
# Add a new task with default status
java -jar TaskTrackerCLI.jar add "Finish homework"

# Add a task with a specific status
java -jar TaskTrackerCLI.jar add "Learn Java" "in progress"

# Mark a task as done
java -jar TaskTrackerCLI.jar mark-done 1

# Update a task description
java -jar TaskTrackerCLI.jar update 2 "Finish Java CLI project"

# List all tasks
java -jar TaskTrackerCLI.jar list

# List tasks by status
java -jar TaskTrackerCLI.jar list "done"

```

6. Data persistent:
```
Tasks are stored in tasks.json in the project root.

Each task has:

    id: unique numeric ID

    description: task details

    status: to do, in progress, or done

    createdAt and updatedAt: timestamps
```

7. Dependencies
```
    Jackson Databind for JSON serialization/deserialization.
    Jackson JSR310 for handling Java LocalDateTime.
```

