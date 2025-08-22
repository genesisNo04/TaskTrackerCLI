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
                if (args.length < 3) {
                    System.out.println("Error: Missing task description.");
                } else {
                    Task task = new Task();
                    task.setId(1L);
                    task.setDescription(args[1]);
                    task.setStatus(args[2]);
                    task.setCreatedAt(LocalDateTime.now());
                    task.setUpdateAt(LocalDateTime.now());
                    taskService.addTask(task);
                    System.out.println("Task added: " + args[1] + ", status: " + args[2]);
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
                        case "not done":
                            taskService.listAllNotDone();
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
                System.out.println("  available status - done, in progress, not done, \"\"");
                System.out.println("  help          - Show help");
                break;

            default:
                System.out.println("Unknown command: " + command);
                System.out.println("Use 'help' to see available commands.");
        }
    }
}