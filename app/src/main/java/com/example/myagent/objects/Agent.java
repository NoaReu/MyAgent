package com.example.myagent.objects;

public class Agent {
    private String firstName;
    private String lastName;
    private String agentId;
    private String phone;

    public Agent(String firstName, String lastName, String id, String phone){
        this.firstName=firstName;
        this.lastName=lastName;
        this.agentId=id;
        this.phone=phone;

    }
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) { this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getId() {return agentId;}
    public void setId(String id) {this.agentId = id;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

}
