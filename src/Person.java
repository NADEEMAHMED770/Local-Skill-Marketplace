public class Person {
    private int userId;
    private String name;
    private String phone;
    private String area;

    public Person(){

    }

    public Person(String name,String phone, String area){
        this.name = name;
        this.phone = phone;
        this.area = area;
    }

    public Person(int userId,String name,String phone, String area){
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.area = area;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getName(){
        return this.name;
    }

    public String getPhone(){
        return this.phone;
    }

    public String getArea(){
        return this.area;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public void setArea(String area){
        this.area = area;
    }
    // Add this to the bottom of Person.java
    @Override
    public String toString() {
        return this.name + " (" + this.area + ")";
    }

}
