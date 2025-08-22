package org.example;

import org.example.Model.Task;
import org.example.Service.TaskService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();

        if (args.length == 0) {
            System.out.println("Welcome to Task Track CLI!");
            System.out.println("Usage: java -jar TaskTrackerCLI-1.0-SNAPSHOT.jar <command> [options]");
            return;
        }

        String command = args[0];

        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Error: Missing task description.");
                } else {
                    String status = (args.length > 2) ? args[2] : null;
                    Task task = new Task();
                    task.setId(1L);
                    task.setDescription(args[1]);
                    task.setStatus(status == null ? "to do" : status);
                    task.setCreatedAt(LocalDateTime.now());
                    task.setUpdateAt(LocalDateTime.now());
                    taskService.addTask(task);
                    System.out.println("Task added: " + args[1] + ", status: " + task.getStatus());
                }
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Error: missing task id");
                } else {
                    Long id = Long.parseLong(args[1]);
                    taskService.deleteTask(id);
                }
                break;
            case "update":
                if (args.length < 3) {
                    System.out.println("Error: Missing task description or id.");
                } else {
                    Long id = Long.parseLong(args[1]);
                    String description = args[2];
                    taskService.updateTask(id, description);
                }
                break;
            case "mark-to-do":
                if (args.length < 2) {
                    System.out.println("Error: missing task id");
                } else {
                    Long id = Long.parseLong(args[1]);
                    taskService.markToDo(id);
                }
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Error: missing task id");
                } else {
                    Long id = Long.parseLong(args[1]);
                    taskService.markInProgress(id);
                }
                break;
            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Error: missing task id");
                } else {
                    Long id = Long.parseLong(args[1]);
                    taskService.markDone(id);
                }
                break;

            case "list":
                if (args.length == 1) {
                    taskService.listAllTask();
                } else {
                    String status = args[1];
                    switch(status) {
                        case "done":
                            taskService.listAllDone();
                            break;
                        case "in progress":
                            taskService.listAllInProgress();
                            break;
                        case "to do":
                            taskService.listAllToDo();
                            break;
                        default:
                            System.out.println("Invalid Status: " + status);
                            System.out.println("Use 'help' to see available commands.");
                    }
                }
                break;

            case "help":
                System.out.println("Available commands:");
                System.out.println("  add <task>    - Add a new task");
                System.out.println("  list          - List all tasks");
                System.out.println("  list <status> - List all tasks");
                System.out.println("  available status - todo, done, in progress\"\"");
                System.out.println("  help          - Show help");
                break;

            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Use 'help' to see available commands.");
        }
    }
}