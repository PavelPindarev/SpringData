package com.example.football.service.impl;

import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "towns.json");

        return Files.readString(path);
    }

    @Override
    public String importTowns() throws IOException {
        String json = this.readTownsFileContent();

        TownImportDTO[] townImportDTOS = this.gson.fromJson(json, TownImportDTO[].class);

        List<String> messages = new ArrayList<>();

        for (TownImportDTO dto : townImportDTOS) {
            Set<ConstraintViolation<TownImportDTO>> errors = this.validator.validate(dto);

            if (!errors.isEmpty()) {
                messages.add("Invalid Town");
            } else {
                Optional<Town> optTown = this.townRepository.findByName(dto.getName());

                if (optTown.isPresent()) {
                    messages.add("Invalid Town");
                } else {
                    Town town = modelMapper.map(dto, Town.class);
                    this.townRepository.save(town);

                    String msg = String.format("Successfully imported Town %s - %d",
                            town.getName(), town.getPopulation());

                    messages.add(msg);
                }
            }

        }

        return String.join("\n", messages);
    }
}