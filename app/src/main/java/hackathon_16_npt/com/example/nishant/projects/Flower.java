package hackathon_16_npt.com.example.nishant.projects;


/**
 * Created by vrs on 7/10/15.
 */
public class Flower
{
    String name;
    int id;

    public Flower(String name, int id) {
        this.name = name;
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
