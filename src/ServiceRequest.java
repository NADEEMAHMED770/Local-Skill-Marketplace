public class ServiceRequest {
    private int requestId;
    private int postedBy;
    private String skillNeeded;
    private String description;
    private String status;

    public ServiceRequest(){

    }
    public ServiceRequest(int postedBy, String skillNeeded, String description){
        this.status = "open";
        this.postedBy = postedBy;
        this.skillNeeded = skillNeeded;
        this.description = description;
    }

    public ServiceRequest(int requestId,int postedBy, String skillNeeded, String description, String status){
        
        this.requestId = requestId;
        this.postedBy = postedBy;
        this.skillNeeded = skillNeeded;
        this.description = description;
        this.status = status;
    }

    public int getRequestId(){
        return this.requestId;
    }
    public int getPostedBy(){
        return this.postedBy;
    }
    public void setPostedBy(int postedBy){
        this.postedBy = postedBy;
    }
    public String getSkillNeeded(){
        return this.skillNeeded;
    }
    public void setSkillNeeded(String skillNeeded){
        this.skillNeeded = skillNeeded;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getStatus(){
        return this.status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
