package com.example.myagent.objects;

public class Agent {
    private String firstName;
    private String lastName;
    private String id;
    private int phone;
    private String email;
    private String address;
    private String password;
    public Agent(String firstName, String lastName, String id, int phone, String email, String address, String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.id=id;
        this.phone=phone;
        this.email=email;
        this.address=address;
        this.password=password;
    }
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) { this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public int getPhone() {return phone;}
    public void setPhone(int phone) {this.phone = phone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}