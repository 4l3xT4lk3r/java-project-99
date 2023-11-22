package hexlet.code.service;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {

    private LabelRepository repository;

    private LabelMapper mapper;

    public List<LabelDTO> getAll() {
        return repository.findAll().stream().map(label -> mapper.map(label)).toList();
    }

    public LabelDTO findById(Long id) {
        Label label = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with " + id + " not found!"));
        return mapper.map(label);
    }

    public LabelDTO create(LabelCreateDTO labelData) {
        Label label = mapper.map(labelData);
        repository.save(label);
        return mapper.map(label);
    }

    public LabelDTO update(LabelUpdateDTO labelData, Long id) {
        Label label = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with " + id + " not found!"));
        mapper.update(labelData, label);
        repository.save(label);
        return mapper.map(label);
    }

    public void delete(Long id) {
        Label label = repository.findById(id).orElse(null);
        if (label != null) {
            if (label.getTasks().isEmpty()) {
                repository.deleteById(id);
            } else {
                throw new RuntimeException();
            }
        }
    }

}
