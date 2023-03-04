package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{
        User user = userRepository2.findById(userId).get();

        //if user is already connected
        if(user.getMaskedIP()!=null){
            throw new Exception("Already connected");
        }
        //if user is in it's own country
        else if(countryName.equalsIgnoreCase(user.getCountry().getCountryName().toString())){
            return user;
        }
        //if user has not subscribed
        else {
            if (user.getServiceProviderList()==null){
                throw new Exception("Unable to connect");
            }
            //if user is subscribed
            List<ServiceProvider> serviceProviderList = user.getServiceProviderList();
            int a = Integer.MAX_VALUE;
            ServiceProvider serviceProvider = null;
            Country country =null;

            for(ServiceProvider serviceProvider1:serviceProviderList){

                List<Country> countryList = serviceProvider1.getCountryList();

                for (Country country1: countryList){
                    //getting minimum service id of more than one service provider gives service for the given country
                    if(countryName.equalsIgnoreCase(country1.getCountryName().toString()) && a > serviceProvider1.getId() ){
                        a=serviceProvider1.getId();
                        serviceProvider=serviceProvider1;
                        country=country1;
                    }
                }
            }
            if (serviceProvider!=null){
                Connection connection = new Connection();
                connection.setUser(user);
                connection.setServiceProvider(serviceProvider);

                String cc = country.getCode();
                int givenId = serviceProvider.getId();
                String mask = cc+"."+givenId+"."+userId;

                user.setConnected(true);
                user.setMaskedIP(mask);

                user.getConnectionList().add(connection);

                serviceProvider.getConnectionList().add(connection);
                //updating user repo as asked in the controller
                userRepository2.save(user);
                // updating service provider repo
                serviceProviderRepository2.save(serviceProvider);


            }
        }
        return user;
    }
    @Override
    public User disconnect(int userId) throws Exception {
        User user = userRepository2.findById(userId).get();
        if(user.getConnected()==false){
            throw new Exception("Already disconnected");
        }
        user.setConnected(false);
        user.setMaskedIP(null);

        userRepository2.save(user);
        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        User user=new User();
        return user;
    }
}
