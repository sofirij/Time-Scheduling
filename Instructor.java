import java.io.Serializable;

public class Instructor implements Serializable {
    private String name;
    private int num;

    public Instructor(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void method() {
    }
}