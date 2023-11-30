package hexlet.code.controller;

import hexlet.code.dto.LabelDTO;
import hexlet.code.service.LabelService;
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
@RequestMapping(path = "/api/labels")
public class LabelController {

    private LabelService service;

    @Operation(summary = "Get list of all labels")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Show labels"),
        @ApiResponse(responseCode = "403", description = "Not authorized for show labels")
    })
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<LabelDTO>> index() {
        List<LabelDTO> list = service.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(list.size()))
                .body(list);
    }

    @Operation(summary = "Get label by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Label found"),
        @ApiResponse(responseCode = "401", description = "Not authorized for show label"),
        @ApiResponse(responseCode = "403", description = "Forbidden for show label"),
        @ApiResponse(responseCode = "404", description = "Label with desired id not found")
    })
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO show(
            @Parameter(description = "Id of label to be found")
            @PathVariable long id) {
        return service.findById(id);
    }

    @Operation(summary = "Create new label")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Label created"),
        @ApiResponse(responseCode = "401", description = "Not authorized to create label"),
        @ApiResponse(responseCode = "400", description = "Bad data for creating label")
    })
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDTO create(
            @Parameter(description = "Data to create label")
            @Valid
            @RequestBody LabelDTO labelData) {
        return service.create(labelData);
    }

    @Operation(summary = "Update label")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Label updated"),
        @ApiResponse(responseCode = "401", description = "Not authorized for updating label"),
        @ApiResponse(responseCode = "400", description = "Bad data for updating label")
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelDTO update(
            @Parameter(description = "Data for updating label")
            @Valid
            @RequestBody LabelDTO labelData,
            @Parameter(description = "Label id for update")
            @PathVariable long id) {
        return service.update(labelData, id);
    }

    @Operation(summary = "Delete label")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Label deleted"),
        @ApiResponse(responseCode = "401", description = "Not authorized for deleting label"),
        @ApiResponse(responseCode = "403", description = "Forbid to delete label"),
        @ApiResponse(responseCode = "404", description = "Label not found")
    })
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
