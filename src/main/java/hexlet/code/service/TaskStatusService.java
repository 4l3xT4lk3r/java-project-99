package hexlet.code.service;

import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusDTO;
import hexlet.code.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskStatusService {

    private TaskStatusRepository repository;

    private TaskStatusMapper mapper;

    public List<TaskStatusDTO> getAll() {
        return repository.findAll().stream().map(u -> mapper.map(u)).toList();
    }

    public TaskStatusDTO findById(Long id) {
        TaskStatus taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with " + id + " not found!"));
        return mapper.map(taskStatus);
    }

    public TaskStatusDTO findByName(String name) {
        TaskStatus taskStatus = repository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Task with " + name + " not found!"));
        return mapper.map(taskStatus);
    }


    public TaskStatusDTO create(TaskStatusCreateDTO taskStatusData) {
        TaskStatus taskStatus = mapper.map(taskStatusData);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO taskStatusData, Long id) {
        TaskStatus taskStatus = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task status with " + id + " not found!"));
        mapper.update(taskStatusData, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }
    public void delete(Long id) {
        TaskStatus taskStatus = repository.findById(id).orElse(null);
        if (taskStatus != null) {
            if (taskStatus.getTasks().isEmpty()) {
                repository.deleteById(id);
            } else {
                throw new RuntimeException();
            }
        }


    }

}
