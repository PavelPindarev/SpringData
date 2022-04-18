package _05.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

@Entity
@DiscriminatorColumn(name = "BankAccount")
public class BankAccount extends BillingDetail {

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "SWIFT_code")
    private String SwiftCode;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return SwiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        SwiftCode = swiftCode;
    }
}
