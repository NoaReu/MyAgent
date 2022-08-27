package com.example.myagent.objects;

public class Customer {
    private String firstName;
    private String lastName;
    private String id;
    private int phone;
    private String email;
    private String address;

    public Customer(String firstName, String lastName, String id, int phone, String email, String address){
        this.firstName=firstName;
        this.lastName=lastName;
        this.id=id;
        this.phone=phone;
        this.email=email;
        this.address=address;
    }
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public int getPhone() {return phone;}
    public void setPhone(int phone) { this.phone = phone;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

}
