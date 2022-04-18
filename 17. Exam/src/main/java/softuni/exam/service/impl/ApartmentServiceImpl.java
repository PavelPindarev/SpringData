package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDTO;
import softuni.exam.models.dto.ApartmentRootImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

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
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "apartments.xml");
    private final TownRepository townRepository;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository,
                                TownRepository townRepository) throws JAXBException {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();

        JAXBContext context = JAXBContext.newInstance(ApartmentRootImportDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(this.path);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        ApartmentRootImportDTO rootImportDTO = (ApartmentRootImportDTO)
                this.unmarshaller.unmarshal(new FileReader(this.path.toAbsolutePath().toString()));

        List<String> messages = new ArrayList<>();

        rootImportDTO.getApartments()
                .stream()
                .forEach(dto -> {
                    Set<ConstraintViolation<ApartmentImportDTO>> errors = this.validator.validate(dto);

                    if (!errors.isEmpty()) {
                        messages.add("Invalid apartment");
                    } else {

                        Optional<Apartment> optApartment = this.apartmentRepository.findByTownTownNameAndArea(dto.getTown(), dto.getArea());
                        if (optApartment.isPresent()) {
                            messages.add("Invalid apartment");
                        } else {
                            Town town = this.townRepository.findByTownName(dto.getTown()).get();

                            Apartment apartment = modelMapper.map(dto, Apartment.class);

                            apartment.setTown(town);

                            this.apartmentRepository.save(apartment);

                            messages.add("Successfully imported apartment " + apartment);
                        }
                    }
                });
        return String.join("\n", messages);
    }
}
