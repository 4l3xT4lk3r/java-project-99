package hexlet.code.service;

import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamsDTO;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import hexlet.code.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository repository;

    private TaskMapper mapper;

    private TaskSpecification specificationBuilder;

    public Page<TaskDTO> getAll(TaskParamsDTO params, int page) {
        Specification<Task> specification = specificationBuilder.build(params);
        Page<Task> tasks = repository.findAll(specification, PageRequest.of(page - 1, 10));
        return tasks.map(mapper::map);
    }

    public TaskDTO findById(Long id) {
        Task task = repository.findById(id).orElseThrow();
        return mapper.map(task);
    }

    public TaskDTO create(TaskDTO taskData) {
        Task task = mapper.map(taskData);
        repository.save(task);
        return mapper.map(task);
    }

    public TaskDTO update(TaskDTO taskData, Long id) {
        Task task = repository.findById(id).orElseThrow();
        mapper.update(taskData, task);
        repository.save(task);
        return mapper.map(task);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
