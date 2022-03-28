public class Vaccine {
    private String id;
    private String batchId;

    public Vaccine(String id, String batch_id) {
        this.id = id;
        this.batchId = batch_id;
    }

    public String getId() {
        return id;
    }

    public String getBatchId() {
        return batchId;
    }

    public String toString() {
        return "Vaccine{" + "id=" + id + ", batch_id=" + batchId + '}';
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setId(String id) {
        this.id = id;
    }
}

