package hackathon_16_npt.com.example.nishant.projects;

import java.io.DataInput;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ResearchProject implements Serializable {
        private String id;
        private String projectName;
        private String authorName;
        private String description;
        private String emailId;
        private String contactNum;

        public ResearchProject(){

        }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", description='" + description + '\'' +
                ", emailId='" + emailId + '\'' +
                ", contactNum='" + contactNum + '\'' +
                '}';
    }

    public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getProjectName() {
                return projectName;
        }

        public void setProjectName(String projectName) {
                this.projectName = projectName;
        }

        public String getAuthorName() {
                return authorName;
        }

        public void setAuthorName(String authorName) {
                this.authorName = authorName;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getEmailId() {
                return emailId;
        }

        public void setEmailId(String emailId) {
                this.emailId = emailId;
        }

        public String getContactNum() {
                return contactNum;
        }

        public void setContactNum(String contactNum) {
                this.contactNum = contactNum;
        }

        @Override
        public boolean equals(Object o) {
                return super.equals(o);
        }


}


