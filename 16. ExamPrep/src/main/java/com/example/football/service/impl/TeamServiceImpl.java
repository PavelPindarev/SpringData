package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;

        this.gson = new GsonBuilder().create();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        Path path = Path.of("src", "main", "resources", "files", "json", "teams.json");

        return Files.readString(path);
    }

    @Override
    public String importTeams() throws IOException {
        String json = this.readTeamsFileContent();

        TeamImportDTO[] teamImportDTOS = this.gson.fromJson(json, TeamImportDTO[].class);

        List<String> messages = new ArrayList<>();

        for (TeamImportDTO dto : teamImportDTOS) {
            Set<ConstraintViolation<TeamImportDTO>> errors = this.validator.validate(dto);

            if (!errors.isEmpty()) {
                messages.add("Invalid Team");
            } else {
                Optional<Team> optTeam = this.teamRepository.findByName(dto.getName());
                if (optTeam.isPresent()) {
                    messages.add("Invalid Team");
                } else {
                    Town town = this.townRepository.findByName(dto.getTownName()).get();
                    Team team = modelMapper.map(dto, Team.class);
                    team.setTown(town);

                    this.teamRepository.save(team);
                    messages.add("Successfully imported " + team);
                }
            }
        }
        return String.join("\n", messages);
    }
}
