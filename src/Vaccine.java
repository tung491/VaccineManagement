public class Vaccine {
    protected String id;
    protected String batchId;
    protected boolean injected;

    public Vaccine(String id, String batch_id, boolean isInjected) {
        this.id = id;
        this.batchId = batch_id;
        this.injected = isInjected;
    }

    public boolean isInjected() {
        return this.injected;
    }

    public void setInjected(boolean injected) {
        this.injected = injected;
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

