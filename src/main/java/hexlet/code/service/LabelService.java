package hexlet.code.service;

import hexlet.code.dto.LabelDTO;
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
        Label label = repository.findById(id).orElseThrow();
        return mapper.map(label);
    }

    public LabelDTO create(LabelDTO labelData) {
        Label label = mapper.map(labelData);
        repository.save(label);
        return mapper.map(label);
    }

    public LabelDTO update(LabelDTO labelData, Long id) {
        Label label = repository.findById(id).orElseThrow();
        mapper.update(labelData, label);
        repository.save(label);
        return mapper.map(label);
    }

    public void delete(Long id) {
        Label label = repository.findById(id).orElseThrow();
        repository.deleteById(id);
    }
}
