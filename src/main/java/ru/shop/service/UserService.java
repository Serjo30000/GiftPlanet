package ru.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shop.entity.Product;
import ru.shop.entity.Relation;
import ru.shop.entity.Role;
import ru.shop.entity.User;
import ru.shop.repository.RelationRepository;
import ru.shop.repository.RoleRepository;
import ru.shop.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService,IUserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        for (int i=0;i<userRepository.findUserWithRole(user.getId()).size();i++){
            user.getRoles().add(userRepository.findUserWithRole(user.getId()).get(i));
        }
        return user;
    }

    public User findUserById(int userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        for (int i=0;i<userRepository.findUserWithRole(userFromDb.get().getId()).size();i++){
            userFromDb.get().getRoles().add(userRepository.findUserWithRole(userFromDb.get().getId()).get(i));
        }
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        List<User>lstUser=(List<User>)userRepository.findAll();
        for (int i=0;i<lstUser.size();i++){
            for (int j=0;j<userRepository.findUserWithRole(lstUser.get(i).getId()).size();j++){
                lstUser.get(i).getRoles().add(userRepository.findUserWithRole(lstUser.get(i).getId()).get(j));
            }
        }
        return lstUser;
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        int idRole=1;
        user.setRoles(Collections.singleton(new Role(1, "ROLE_ADMIN")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Relation relation = new Relation();
        relation.setUserId(userRepository.findByUsername(user.getUsername()).getId());
        relation.setRoleId(idRole);
        relationRepository.save(relation);
        return true;
    }

    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        if (user == null) {
            return true;
        }
        User userToUpdate = userRepository.findById(user.getId()).get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setPasswordConfirm(user.getPasswordConfirm());
        userToUpdate.setRoles(user.getRoles());
        userRepository.updateUser(userToUpdate.getId(),userToUpdate.getUsername(),userToUpdate.getPassword());
        relationRepository.updateRelation(userToUpdate.getId(),userToUpdate.getRoles().iterator().next().getId());
        return false;
    }

    @Override
    public List<User> sortUser(List<User>lstUs, String sortName, String sortBy){
        List<User> lstUser = new ArrayList<>();
        for (int i=0;i<lstUs.size();i++){
            for (int j=0;j<userRepository.findUserWithRole(lstUs.get(i).getId()).size();j++){
                lstUs.get(i).getRoles().add(userRepository.findUserWithRole(lstUs.get(i).getId()).get(j));
            }
        }
        if (sortName.equals("Отсутствует")){
            return lstUs;
        }
        if (sortName.equals("По номеру")){
            if (sortBy.equals("По возрастанию")){
                lstUser=userRepository.findUserOrderByIdAsc();
            }
            else{
                lstUser=userRepository.findUserOrderByIdDesc();
            }
        }
        if (sortName.equals("По логину")){
            if (sortBy.equals("По возрастанию")){
                lstUser=userRepository.findUserOrderByLoginAsc();
            }
            else{
                lstUser=userRepository.findUserOrderByLoginDesc();
            }
        }
        List<User>lstCopyUser=new ArrayList<>();
        for (int i=0;i<lstUser.size();i++){
            for (int j=0;j<lstUs.size();j++){
                if(lstUser.get(i).getId()==lstUs.get(j).getId()){
                    lstCopyUser.add(lstUs.get(j));
                    break;
                }
            }
        }
        for (int i=0;i<lstCopyUser.size();i++){
            for (int j=0;j<userRepository.findUserWithRole(lstCopyUser.get(i).getId()).size();j++){
                lstCopyUser.get(i).getRoles().add(userRepository.findUserWithRole(lstCopyUser.get(i).getId()).get(j));
            }
        }
        return lstCopyUser;
    }
}
