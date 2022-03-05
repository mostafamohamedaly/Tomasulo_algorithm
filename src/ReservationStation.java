public class ReservationStation {
    private String name;
    private boolean busy;
    private String opcode;
    private String vj;
    private String vk;
    private String qj;
    private String qk;
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

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getVj() {
        return vj;
    }

    public void setVj(String vj) {
        this.vj = vj;
    }

    public String getVk() {
        return vk;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public String getQj() {
        return qj;
    }

    public void setQj(String qj) {
        this.qj = qj;
    }

    public int getCyclesRemaining() {
        return cyclesRemaining;
    }

    public void setCyclesRemaining(int cyclesRemaining) {
        this.cyclesRemaining = cyclesRemaining;
    }

    public String getQk() {
        return qk;
    }

    public void setQk(String qk) {
        this.qk = qk;
    }

    @Override
    public String toString() {
        return "ReservationStation{" +
                "name='" + name + '\'' +
                ", busy=" + busy +
                ", opcode='" + opcode + '\'' +
                ", qj='" + qj + '\'' +
                ", qk='" + qk + '\'' +
                ", vj='" + vj + '\'' +
                ", vk='" + vk + '\'' +
                ", cyclesRemaining=" + cyclesRemaining +
                '}';
    }


    public ReservationStation(String name, boolean busy, String opcode, String vj, String vk, String qj, String qk, int cyclesRemaining) {
        this.name=name;
        this.busy = busy;
        this.opcode = opcode;
        this.vj = vj;
        this.vk = vk;
        this.qj = qj;
        this.qk = qk;
        this.cyclesRemaining = cyclesRemaining;
    }
}
