package hexlet.code.controller;

import hexlet.code.dto.TaskDTO;
import hexlet.code.dto.TaskParamsDTO;
import hexlet.code.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/tasks")
public class TaskController {

    private TaskService service;

    @Operation(summary = "Get list of all tasks")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Show tasks"),
        @ApiResponse(responseCode = "403", description = "Not authorized for show tasks")
    })
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskDTO>> index(TaskParamsDTO params, @RequestParam(defaultValue = "1") int page) {
        Page<TaskDTO> tasks = service.getAll(params, page);
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page))
                .body(tasks.stream().toList());
    }

    @Operation(summary = "Get task by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task found"),
        @ApiResponse(responseCode = "401", description = "Not authorized for show task"),
        @ApiResponse(responseCode = "403", description = "Forbidden for show task"),
        @ApiResponse(responseCode = "404", description = "Task with desired id not found")
    })
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO show(
            @Parameter(description = "Id of task to be found")
            @PathVariable long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create new task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Task created"),
        @ApiResponse(responseCode = "401", description = "Not authorized to create task"),
        @ApiResponse(responseCode = "400", description = "Bad data for creating task")
    })
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(
            @Parameter(description = "Data to create task")
            @Valid
            @RequestBody TaskDTO taskData) {
        return service.create(taskData);
    }

    @Operation(summary = "Update task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task updated"),
        @ApiResponse(responseCode = "401", description = "Not authorized for updating task"),
        @ApiResponse(responseCode = "400", description = "Bad data for updating task")
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO update(
            @Parameter(description = "Data for updating task")
            @Valid
            @RequestBody TaskDTO taskData,
            @Parameter(description = "Task id for update")
            @PathVariable long id) {
        return service.update(taskData, id);
    }

    @Operation(summary = "Delete task")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Task deleted"),
        @ApiResponse(responseCode = "401", description = "Not authorized for deleting task"),
        @ApiResponse(responseCode = "403", description = "Forbid to delete task"),
        @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
