package com.example.football.service.impl;

import com.example.football.models.dto.NameDTO;
import com.example.football.models.dto.PlayerImportDTO;
import com.example.football.models.dto.PlayerImportRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final StatRepository statRepository;

    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "players.xml");

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository,
                             TownRepository townRepository, StatRepository statRepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.statRepository = statRepository;

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
        this.modelMapper.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(MappingContext<String, LocalDate> context) {
                return LocalDate.parse(context.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        });

        JAXBContext context = JAXBContext.newInstance(PlayerImportRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(path);
    }

    @Override
    public String importPlayers() throws FileNotFoundException, JAXBException {
        PlayerImportRootDTO rootDTO = (PlayerImportRootDTO)
                this.unmarshaller.unmarshal(new FileReader(path.toAbsolutePath().toString()));

        List<String> messages = new ArrayList<>();

        rootDTO.getPlayers().
                forEach(dto -> {
                    Set<ConstraintViolation<PlayerImportDTO>> errors = this.validator.validate(dto);

                    if (!errors.isEmpty()) {
                        messages.add("Invalid Player");
                    } else {
                        Optional<Player> optPlayer = this.playerRepository.findByEmail(dto.getEmail());

                        if (optPlayer.isPresent()) {
                            messages.add("Invalid Player");
                        } else {
                            Town town = this.townRepository.findByName(dto.getTown().getName()).get();
                            Team team = this.teamRepository.findByName(dto.getTeam().getName()).get();
                            Stat stat = this.statRepository.findById(dto.getStat().getId()).get();

                            Player player = this.modelMapper.map(dto, Player.class);

                            player.setTown(town);
                            player.setTeam(team);
                            player.setStat(stat);

                            this.playerRepository.save(player);

                            messages.add(String.format("Successfully imported Player %s %s - %s",
                                    player.getFirstName(),
                                    player.getLastName(),
                                    player.getPosition().toString()
                            ));
                        }
                    }
                });

        return String.join("\n", messages);
    }

    @Override
    public String exportBestPlayers() {
        LocalDate from = LocalDate.of(1995, 1, 1);
        LocalDate to = LocalDate.of(2003, 1, 1);

        List<Player> players = this.playerRepository.
                findByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(from, to);

        StringBuilder builder = new StringBuilder();

        players.forEach(p -> builder.append(p).append(System.lineSeparator()));

        return builder.toString().trim();
    }
}
