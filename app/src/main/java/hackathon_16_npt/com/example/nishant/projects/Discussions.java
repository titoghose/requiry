package hackathon_16_npt.com.example.nishant.projects;

/**
 * Created by Work on 23/10/16.
 */

public class Discussions {
    private String id;
    private String project;
    private String username;
    private String message;

    public Discussions() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discussions that = (Discussions) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (project != null ? !project.equals(that.project) : that.project != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        return message != null ? message.equals(that.message) : that.message == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (project != null ? project.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Discussions{" +
                "id='" + id + '\'' +
                ", project='" + project + '\'' +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Discussions(String id, String project, String username, String message) {
        this.id = id;
        this.project = project;
        this.username = username;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
