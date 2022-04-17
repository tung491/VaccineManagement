public class Vaccine {
    private String id;
    private String batchId;
    private boolean isInjected;

    public Vaccine(String id, String batch_id, boolean isInjected) {
        this.id = id;
        this.batchId = batch_id;
        this.isInjected = isInjected;
    }

    public boolean isInjected() {
        return isInjected;
    }

    public void setInjected(boolean injected) {
        isInjected = injected;
    }

    public String getId() {
        return id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setId(String id) {
        this.id = id;
    }
}

