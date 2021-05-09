/**
 * Class represents materials that users can add to the database and mark them as the material on the jobs.
 */

package worklist.entity;

public class Material {

    private int id;
    private String name;
    private String details;

    public Material(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public Material(int id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
