public class Skill {
    private int skillId;
    private int userId;
    private String skillName;
    private String category;
    private String description;
    private double pricePerHour;
    private boolean isAvailable;

    public Skill(){

    }

    public Skill(int userId,String skillName, String category, String description, double pricePerHour, boolean isAvailable){
        this.userId = userId;
        this.skillName = skillName;
        this.category = category;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.isAvailable = isAvailable;
    }

    public Skill(int skillId,int userId,String skillName, String category, String description, double pricePerHour, boolean isAvailable){
        this.skillId = skillId;
        this.userId = userId;
        this.skillName = skillName;
        this.category = category;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.isAvailable = isAvailable;
    }

    public int getSkillId(){
        return this.skillId;
    }

    public int getUserId(){
        return this.userId;
    }

    public void setUserId(int userid){
        this.userId = userid;
    }

    public String getSkillName(){
        return this.skillName;
    }

    public void setSkillName(String skillName){
        this.skillName = skillName;
    }

    public String getCategory(){
        return this.category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public double getPricePerHour(){
        return this.pricePerHour;
    }

    public void setPricePerHour(double price){
        this.pricePerHour = price;
    }

    public boolean isAvailable(){
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }

}
