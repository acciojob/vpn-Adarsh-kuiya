package com.driver.services.impl;

import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.model.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception {
        User user = new User();
        if (countryName.equalsIgnoreCase("IND") || countryName.equalsIgnoreCase("AUS") || countryName.equalsIgnoreCase("USA") || countryName.equalsIgnoreCase("CHI") || countryName.equalsIgnoreCase("JPN")) {
        user.setUsername(username);
        user.setPassword(password);
//setting the country name and country code  of country wrt given country name due to one to one relation b/w country and user
        Country country=new Country();

        if(countryName.equalsIgnoreCase("IND")){
        country.setCountryName(CountryName.IND);
        country.setCode(CountryName.IND.toCode());
        }

        if(countryName.equalsIgnoreCase("AUS")){
            country.setCountryName(CountryName.AUS);
            country.setCode(CountryName.AUS.toCode());
        }

        if(countryName.equalsIgnoreCase("USA")){
            country.setCountryName(CountryName.USA);
            country.setCode(CountryName.USA.toCode());
        }

        if(countryName.equalsIgnoreCase("CHI")){
            country.setCountryName(CountryName.CHI);
            country.setCode(CountryName.CHI.toCode());
        }

        if(countryName.equalsIgnoreCase("JPN")){
            country.setCountryName(CountryName.JPN);
            country.setCode(CountryName.JPN.toCode());
        }
            country.setUser(user);//one to one linking
        user.setConnected(false);
        user.setOriginalCountry(country);//one to one


        String Code=country.getCode()+"."+userRepository3.save(user).getId();
        user.setOriginalIp(Code);




        userRepository3.save(user);

        }
        else {
           throw new Exception("Country not found") ;
        }
       return user;
    }
    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {

    User user=userRepository3.findById(userId).get();
    ServiceProvider serviceProvider=serviceProviderRepository3.findById(serviceProviderId).get();

    user.getServiceProviderList().add(serviceProvider);
    serviceProvider.getUsers().add(user);

    serviceProviderRepository3.save(serviceProvider); // many to many so by saving one other get automatically save

    return user;
    }
}
