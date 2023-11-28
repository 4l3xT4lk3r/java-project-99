package hexlet.code.controller;


import hexlet.code.dto.TaskStatusDTO;
import hexlet.code.service.TaskStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/task_statuses")
public class TaskStatusController {

    private TaskStatusService service;

    @Operation(summary = "Get list of all task statuses")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Show task statuses"),
        @ApiResponse(responseCode = "403", description = "Not authorized for show task statuses")
    })
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TaskStatusDTO>> index() {
        List<TaskStatusDTO> list = service.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(list.size()))
                .body(list);
    }

    @Operation(summary = "Get status task by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status task found"),
        @ApiResponse(responseCode = "401", description = "Not authorized for show task status"),
        @ApiResponse(responseCode = "403", description = "Forbidden for show task status"),
        @ApiResponse(responseCode = "404", description = "Task status with desired id not found")
    })
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO show(
            @Parameter(description = "Id of task status to be found")
            @PathVariable(name = "id") long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create new task status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Task status created"),
        @ApiResponse(responseCode = "401", description = "Not authorized to create task status"),
        @ApiResponse(responseCode = "400", description = "Bad data for creating task status")
    })
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusDTO create(
            @Parameter(description = "Data to create task status")
            @Valid
            @RequestBody TaskStatusDTO taskStatusData) {
        return service.create(taskStatusData);
    }

    @Operation(summary = "Update task status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task status updated"),
        @ApiResponse(responseCode = "401", description = "Not authorized for updating task status"),
        @ApiResponse(responseCode = "400", description = "Bad data for updating task status")
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskStatusDTO update(
            @Parameter(description = "Data for updating task status")
            @Valid
            @RequestBody TaskStatusDTO taskStatusData,
            @Parameter(description = "Task status id for update")
            @PathVariable long id) {
        return service.update(taskStatusData, id);
    }

    @Operation(summary = "Delete task status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Task status deleted"),
        @ApiResponse(responseCode = "401", description = "Not authorized for deleting task status"),
        @ApiResponse(responseCode = "403", description = "Forbid to delete task status"),
        @ApiResponse(responseCode = "404", description = "Task status not found")
    })
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
