package softuni.exam.models.dto;

import java.math.BigDecimal;

public class ExportBestOffersDTO {
    //⦁	"Agent {firstName} {lastName} with offer №{offerId}:
    //   		-Apartment area: {area}
    //   		--Town: {townName}
    //   		---Price: {price}$
    private String firstName;

    private String lastName;

    private long offerId;

    private double area;

    private String townName;

    private BigDecimal price;

    public ExportBestOffersDTO() {
    }

    public ExportBestOffersDTO(String firstName, String lastName,
                               long offerId, double area,
                               String townName, BigDecimal price) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.offerId = offerId;
        this.area = area;
        this.townName = townName;
        this.price = price;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getOfferId() {
        return offerId;
    }

    public double getArea() {
        return area;
    }

    public String getTownName() {
        return townName;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
