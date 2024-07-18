package unam.cruz.victor.donation;

import java.util.List;

public class Donor {
    public String id;
    public List<OrganType> organs;
    public int age;

    public Donor(String id, int age, List<OrganType> organs) {
        this.id = id;
        this.organs = organs;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Donor{" +
                "gender='" + id + '\'' +
                ", organs='" + organs + '\'' +
                ", age=" + age +
                '}';
    }
}
