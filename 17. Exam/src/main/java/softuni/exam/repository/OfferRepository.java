package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportBestOffersDTO;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer,Long> {

    @Query("SELECT new softuni.exam.models.dto.ExportBestOffersDTO(" +
           "o.agent.firstName, o.agent.lastName, o.id, o.apartment.area, o.apartment.town.townName, o.price)" +
           " FROM Offer o " +
           " WHERE o.apartment.apartmentType = :type" +
           " ORDER BY o.apartment.area DESC, o.price ASC"
    )
    List<ExportBestOffersDTO> getBestOffers(ApartmentType type);
}
