public class Register {

    private String name;
    private Object value;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Register{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Register(String name, Object value){
        this.name=name;
        this.value=value;
    }

}
