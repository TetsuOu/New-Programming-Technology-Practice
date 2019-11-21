public class Person{
    private String username;
    private String name;//Primary key
    private Integer age;
    private String teleno;
    public Person(String username,String name,Integer age,String teleno){
        this.username = username;
        this.name = name;
        this.age = age;
        this.teleno = teleno;
    }
    public Person(String username,String name){
        this(username,name,null,"");
    }
    public Person(String username,String name,Integer age){
        this(username,name,age,"");
    }
    public String getUsername(){
        return username;
    }
    public String getName(){
        return name;
    }
    public Integer getAge(){
        return age;
    }
    public String getTeleno(){
        return teleno;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setAge(Integer age){
        this.age = age;
    }
    public void setTeleno(String teleno){
        this.teleno = teleno;
    }
}