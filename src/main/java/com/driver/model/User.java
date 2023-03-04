package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String userName;

   private String password;

   private String originalIP;

   private String maskedIP;

   private Boolean connected;


   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
   List<Connection>connectionList;

   @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
   private Country country;

   @ManyToMany
   @JoinColumn
   private List<ServiceProvider>serviceProviderList;

   public User(int id, String userName, String password, String originalIP, String maskedIP, Boolean connected) {

      this.userName = userName;
      this.password = password;
      this.originalIP = originalIP;
      this.maskedIP = maskedIP;
      this.connected = connected;

   }

   public User() {
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getOriginalIP() {
      return originalIP;
   }

   public void setOriginalIP(String originalIP) {
      this.originalIP = originalIP;
   }

   public String getMaskedIP() {
      return maskedIP;
   }

   public void setMaskedIP(String maskedIP) {
      this.maskedIP = maskedIP;
   }

   public Boolean getConnected() {
      return connected;
   }

   public void setConnected(Boolean connected) {
      this.connected = connected;
   }

   public List<Connection> getConnectionList() {
      return connectionList;
   }

   public void setConnectionList(List<Connection> connectionList) {
      this.connectionList = connectionList;
   }

   public Country getCountry() {
      return country;
   }

   public void setCountry(Country country) {
      this.country = country;
   }

   public List<ServiceProvider> getServiceProviderList() {
      return serviceProviderList;
   }

   public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
      this.serviceProviderList = serviceProviderList;
   }
}
