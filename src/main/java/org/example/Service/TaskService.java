package org.example.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Model.Task;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    //Create a file name to save all the record
    private static final String JSON_FILE = "tasks.json";

    //ObjectMapper to map java object to json
    private static final ObjectMapper mapper;

    //instantiate the object
    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * LoadTask method for loading all the record in the store file
     *
     * @return a List of Task objects representing all tasks currently stored.
     *         Returns an empty list if the file does not exist, is empty, or an error occurs.
     */
    public List<Task> loadTask() {
        File file = new File(JSON_FILE);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try {
            return mapper.readValue(file, new TypeReference<List<Task>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * addTask method for adding new task which can have status or not and save to json file
     *
     */
    public void addTask(Task task) {
        List<Task> currentList = loadTask();
        if (currentList.size() == 0) {
            task.setId(1L);
        } else {
            Long id = currentList.get(currentList.size() - 1).getId();
            task.setId(id + 1);
        }
        currentList.add(task);

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE), currentList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * addList method use to add a whole list of task and overwrite the task in the file
     */
    public void addList(List<Task> list) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * updateTask method for searching a task and update the task and save to stored file
     */
    public void updateTask(Long id, String description) {
        List<Task> currentList = loadTask();
        Task currentTask = null;
        int index = -1;
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getId().equals(id)) {
                currentTask = currentList.get(i);
                index = i;
                break;
            }
        }

        if (currentTask != null) {
            currentTask.setDescription(description);
            currentTask.setUpdateAt(LocalDateTime.now());
            currentList.set(index, currentTask);
            addList(currentList);
            System.out.println("Task updated: " + currentTask);
        } else {
            System.out.println("Task not found");
        }
    }

    /**
     * deleteTask method to delete task from the stored file
     */
    public void deleteTask(Long id) {
        List<Task> currentList = loadTask();
        Task currentTask = currentList.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);

        if (currentTask == null) {
            System.out.println("Task not found");
        } else {
            currentList.remove(currentTask);
        }

        addList(currentList);
    }

    /**
     * ListAllTask list all task no consider any status
     */
    public void listAllTask() {
        List<Task> currentList = loadTask();
        currentList.forEach(System.out::println);
    }

    /**
     * ListAllDone list all task in done status
     */
    public void listAllDone() {
        List<Task> currentList = loadTask();
        currentList.stream().filter(task -> task.getStatus().equals("done")).forEach(System.out::println);
    }

    /**
     * ListAllToDo list all task in to do status
     */
    public void listAllToDo() {
        List<Task> currentList = loadTask();
        currentList.stream().filter(task -> task.getStatus().equals("to do")).forEach(System.out::println);
    }

    /**
     * ListAllInProgress list all task in progress status
     */
    public void listAllInProgress() {
        List<Task> currentList = loadTask();
        currentList.stream().filter(task -> task.getStatus().equals("in progress")).forEach(System.out::println);
    }

    /**
     * markInProgress mark a task in progress status
     */
    public void markInProgress(Long id) {
        List<Task> currentList = loadTask();
        Task currentTask = currentList.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);
        if (currentTask != null) {
            currentTask.setStatus("in progress");
            addList(currentList);
        } else {
            System.out.println("Task not found");
        }

    }

    /**
     * markDone mark task done status
     */
    public void markDone(Long id) {
        List<Task> currentList = loadTask();
        Task currentTask = currentList.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);

        if (currentTask != null) {
            currentTask.setStatus("done");
            addList(currentList);
        } else {
            System.out.println("Task not found");
        }
    }

    /**
     * markToDo mark task in todo status
     */
    public void markToDo(Long id) {
        List<Task> currentList = loadTask();
        Task currentTask = currentList.stream().filter(task -> task.getId().equals(id)).findFirst().orElse(null);

        if (currentTask != null) {
            currentTask.setStatus("to do");
            addList(currentList);
        } else {
            System.out.println("Task not found");
        }
    }
}
