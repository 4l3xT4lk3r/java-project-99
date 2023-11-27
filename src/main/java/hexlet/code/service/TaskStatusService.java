package hexlet.code.service;


import hexlet.code.dto.TaskStatusDTO;
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
        TaskStatus taskStatus = repository.findById(id).orElseThrow();
        return mapper.map(taskStatus);
    }

    public TaskStatusDTO findByName(String name) {
        TaskStatus taskStatus = repository.findByName(name).orElseThrow();
        return mapper.map(taskStatus);
    }
    public TaskStatusDTO create(TaskStatusDTO taskStatusData) {
        TaskStatus taskStatus = mapper.map(taskStatusData);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }
    public TaskStatusDTO update(TaskStatusDTO taskStatusData, Long id) {
        TaskStatus taskStatus = repository.findById(id).orElseThrow();
        mapper.update(taskStatusData, taskStatus);
        repository.save(taskStatus);
        return mapper.map(taskStatus);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
