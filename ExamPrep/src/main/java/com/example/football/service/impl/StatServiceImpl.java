package com.example.football.service.impl;

import com.example.football.models.dto.StatImportDTO;
import com.example.football.models.dto.StatRootImportDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "stats.xml");

    @Autowired
    public StatServiceImpl(StatRepository statRepository) throws JAXBException {
        this.statRepository = statRepository;

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();

        JAXBContext context = JAXBContext.newInstance(StatRootImportDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        StatRootImportDTO rootImportDTO = (StatRootImportDTO)
                this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        List<String> messages = new ArrayList<>();

        rootImportDTO.getStats()
                .forEach(dto -> {
                    Set<ConstraintViolation<StatImportDTO>> errors = this.validator.validate(dto);

                    if (!errors.isEmpty()) {
                        messages.add("Invalid Stats");
                    } else {
                        Optional<Stat> optStats = this.statRepository.findByPassingAndShootingAndEndurance(
                                dto.getPassing(), dto.getShooting(), dto.getEndurance());
                        if (optStats.isPresent()){
                            messages.add("Invalid Stats");
                        } else {
                            Stat stat = modelMapper.map(dto, Stat.class);
                            statRepository.save(stat);

                            messages.add("Successfully imported " + stat);
                        }
                    }
                });

        return String.join("\n", messages);
    }
}
