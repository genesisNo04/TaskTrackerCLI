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

    private static final String JSON_FILE = "tasks.json";
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

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

    public void addList(List<Task> list) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void listAllTask() {
        List<Task> currentList = loadTask();
        currentList.stream().forEach(task -> System.out.println(task));
    }

    public void listAllDone() {
        List<Task> currentList = loadTask();
        currentList.stream().filter(task -> task.getStatus().equals("done")).forEach(task -> System.out.println(task));
    }

    public void listAllNotDone() {
        List<Task> currentList = loadTask();
        currentList.stream().filter(task -> task.getStatus().equals("not done")).forEach(task -> System.out.println(task));
    }

    public void listAllInProgress() {
        List<Task> currentList = loadTask();
        currentList.stream().filter(task -> task.getStatus().equals("in progress")).forEach(task -> System.out.println(task));
    }
}
