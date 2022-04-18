package softuni.exam.service.impl;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ExportBestOffersDTO;
import softuni.exam.models.dto.OfferImportDTO;
import softuni.exam.models.dto.OfferRootImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    private final Validator validator;
    private final ModelMapper modelMapper;
    private final Unmarshaller unmarshaller;
    private final Path path = Path.of("src", "main", "resources", "files", "xml", "offers.xml");

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository, ApartmentRepository apartmentRepository) throws JAXBException {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;

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

        JAXBContext context = JAXBContext.newInstance(OfferRootImportDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(this.path);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        OfferRootImportDTO rootImportDTO = (OfferRootImportDTO)
                this.unmarshaller.unmarshal(new FileReader(this.path.toString()));

        List<String> messages = new ArrayList<>();

        rootImportDTO.getOffers()
                .stream()
                .forEach(dto -> {
                    Set<ConstraintViolation<OfferImportDTO>> errors = this.validator.validate(dto);
                    if (!errors.isEmpty()) {
                        messages.add("Invalid offer");
                    } else {
                        Optional<Agent> optAgent = this.agentRepository.findByFirstName(dto.getAgent().getFirstName());
                        if (optAgent.isEmpty()) {
                            messages.add("Invalid offer");
                        } else {
                            Agent agent = optAgent.get();
                            Apartment apartment = this.apartmentRepository.findById(dto.getApartment().getId()).get();

                            Offer offer = this.modelMapper.map(dto, Offer.class);

                            offer.setAgent(agent);
                            offer.setApartment(apartment);

                            this.offerRepository.save(offer);

                            messages.add(String.format("Successfully imported offer %.2f",
                                    offer.getPrice()));
                        }
                    }
                });

        return String.join("\n", messages);
    }

    @Override
    public String exportOffers() {
        List<ExportBestOffersDTO> bestOffers = this.offerRepository.getBestOffers(ApartmentType.three_rooms);

        List<String> offers = bestOffers
                .stream()
                .map(offer -> {
                    return String.format("Agent %s %s with offer №%d:\n" +
                                         "   \t\t-Apartment area: %.2f\n" +
                                         "   \t\t--Town: %s\n" +
                                         "   \t\t---Price: %.2f$",
                            offer.getFirstName(), offer.getLastName(),
                            offer.getOfferId(),
                            offer.getArea(),
                            offer.getTownName(),
                            offer.getPrice());
                }).collect(Collectors.toList());

        return String.join("\n", offers);
    }
}
