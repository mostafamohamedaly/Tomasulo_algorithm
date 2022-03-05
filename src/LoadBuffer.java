public class LoadBuffer {
    private String name;
    private boolean busy;
    private int address;

    public int getCyclesRemaining() {
        return cyclesRemaining;
    }

    public void setCyclesRemaining(int cyclesRemaining) {
        this.cyclesRemaining = cyclesRemaining;
    }

    private int cyclesRemaining;


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

    @Override
    public String toString() {
        return "LoadBuffer{" +
                "name='" + name + '\'' +
                ", busy=" + busy +
                ", address=" + address +
                ", cyclesRemaining=" + cyclesRemaining +
                '}';
    }

    public LoadBuffer(String name, boolean busy, int address, int cyclesRemaining) {
        this.name = name;
        this.busy = busy;
        this.address = address;
        this.cyclesRemaining = cyclesRemaining;
    }
}
