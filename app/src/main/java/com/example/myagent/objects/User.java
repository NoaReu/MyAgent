package com.example.myagent.objects;

public class User {
    private String firstName;
    private String lastName;
    private String userId;
    private String agentId;
    private String phone;
    private String email;
    private String address;
    private boolean isAnAgent;

    public User(String firstName, String lastName, String id, String agentId, String phone, String email, String address,  boolean isAgent){
        this.firstName=firstName;
        this.lastName=lastName;
        this.userId=id;
        this.agentId=agentId;
        this.phone=phone;
        this.email=email;
        this.address=address;
        this.isAnAgent=isAgent;
    }
    public User(String firstName, String lastName, String id, String agentId, String phone, String email, String address){
        this.firstName=firstName;
        this.lastName=lastName;
        this.userId=id;
        this.agentId=agentId;
        this.phone=phone;
        this.email=email;
        this.address=address;
        this.isAnAgent=false;
    }
    public User(String firstName, String lastName, String agentId, String phone){
        this.firstName=firstName;
        this.lastName=lastName;
        this.agentId=agentId;
        this.phone=phone;
        this.isAnAgent=true;
    }
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getUserId() {return userId;}
    public void setUserId(String id) {this.userId = id;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) { this.phone = phone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getAgentId() {return agentId;}
    public void setAgentId(String agentId) {this.agentId = agentId;}
    public boolean isAnAgent() {return isAnAgent; }
    public void setAnAgent(boolean agent) { isAnAgent = agent;}
}
