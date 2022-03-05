public class StoreBuffer {
    private String name;
    private boolean busy;
    private int address;
    private String vj;
    private String qj;
    private int cyclesRemaining;

    public int getCyclesRemaining() {
        return cyclesRemaining;
    }

    public void setCyclesRemaining(int cyclesRemaining) {
        this.cyclesRemaining = cyclesRemaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getVj() {
        return vj;
    }

    public void setVj(String vj) {
        this.vj = vj;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    @Override
    public String toString() {
        return "StoreBuffer{" +
                "name='" + name + '\'' +
                ", busy=" + busy +
                ", address=" + address +
                ", vj='" + vj + '\'' +
                ", qj='" + qj + '\'' +
                ", cyclesRemaining=" + cyclesRemaining +
                '}';
    }

    public StoreBuffer(String name, boolean busy, int address, String vj, String qj, int cyclesRemaining) {
        this.name = name;
        this.busy = busy;
        this.address = address;
        this.vj = vj;
        this.qj = qj;
        this.cyclesRemaining = cyclesRemaining;
    }
}
