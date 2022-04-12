package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "offers")
public class OfferRootImportDTO {

    @XmlElement(name = "offer")
    private List<OfferImportDTO> offers;

    public List<OfferImportDTO> getOffers() {
        return offers;
    }
}
