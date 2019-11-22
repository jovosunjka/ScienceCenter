package com.jovo.ScienceCenter.service;


import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Primary // Intellij pronalazi vise implementacija za UserDetailService,
// zato koristimo ovu anotaciju, da bi ova implementacija imala prednost u odnosu na druge,
// i da bi @Autowired znao koju implementaciju da izabere
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // @Autowired
    // private UserRepository userRepository;



    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*User user = userRepository.findByUsername(username);

        if(user == null) {
            String message = String.format("No user found with username '%s' in database.", username);
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }

        if(user.getUserStatus() != UserStatus.ACTIVATED) {
            String message = String.format("No user found with username '%s' in database.", username);
            logger.error(message);
            throw new UsernameNotFoundException(message);
        }

        List<GrantedAuthority> garantedAuthorities = null;
        if(user.getUserType() == UserType.PASSENGER) {
            Passenger passenger = (Passenger) user;
            if(passenger.getPassengerType() != PassengerType.OTHER) {
                if (passenger.getExpirationDate() == null) {
                    resetPassengerType(passenger, false);
                }
                else if(LocalDate.now().isAfter(passenger.getExpirationDate())) {
                    resetPassengerType(passenger, true);
                }
            }

            garantedAuthorities = AuthorityUtils.createAuthorityList(user.getUserType().toString(),
                    passenger.getPassengerType().toString());
        }
        else {
            garantedAuthorities = AuthorityUtils.createAuthorityList(user.getUserType().toString());
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), garantedAuthorities);*/
        List<GrantedAuthority> garantedAuthorities = AuthorityUtils.createAuthorityList("pera's authority");
        return new org.springframework.security.core.userdetails.User("pera", "pera", garantedAuthorities);
    }

    /*
    private void resetPassengerType(Passenger passenger, boolean cancelExpirationDate) {
        // isteklo vazenje potvrde o specifinosti korisnika,
        // dok ne dostavi novu, setujemo da bude obican passenger
        passenger.setPassengerType(PassengerType.OTHER);
        passenger.setExpirationDate(null);
        if(cancelExpirationDate) passenger.setVerifiedBy(null);

        passengerService.save(passenger);
    }
    */
}
