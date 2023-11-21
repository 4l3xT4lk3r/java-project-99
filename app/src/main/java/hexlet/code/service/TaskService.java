package hexlet.code.service;

import hexlet.code.dto.task.TaskDTO;
import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository repository;

    private TaskMapper mapper;

    public List<TaskDTO> getAll() {
        return repository.findAll().stream().map(task -> mapper.map(task)).toList();
    }

    public TaskDTO findById(Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with " + id + " not found!"));
        return mapper.map(task);
    }

    public TaskDTO create(TaskCreateDTO taskData) {
        Task task = mapper.map(taskData);
        repository.save(task);
        return mapper.map(task);
    }

    public TaskDTO update(TaskUpdateDTO taskData, Long id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with " + id + " not found!"));
        mapper.update(taskData, task);
        repository.save(task);
        return mapper.map(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
