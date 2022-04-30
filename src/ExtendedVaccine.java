import java.sql.Date;

public class ExtendedVaccine extends Vaccine {
    private String vaccineName;
    private Date productionDate;
    private Date expirationDate;
    private int interval;

    public ExtendedVaccine(String id, String batch_id, String vaccineName, Date productionDate,
                           Date expirationDate, int interval, boolean injected) {
        super(id, batch_id, injected);
        this.vaccineName = vaccineName;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "vaccineName='" + vaccineName + '\'' +
                ", productionDate=" + productionDate +
                ", expirationDate=" + expirationDate +
                ", interval=" + interval ;
    }

    public boolean isInjected() {
        return this.injected;
    }

    public void setInjected(boolean injected) {
        this.injected = injected;
    }

    public String getVaccineName() {
        return this.vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public Date getProductionDate() {
        return this.productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getInterval() {
        return this.interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
