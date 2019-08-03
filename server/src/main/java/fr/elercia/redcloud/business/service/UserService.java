package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.SimpleUserDto;
import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.events.UserCreationEvent;
import fr.elercia.redcloud.business.events.UserDeleteEvent;
import fr.elercia.redcloud.business.service.utils.StringUtils;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private ApplicationEventPublisher applicationEventPublisher;
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public AppUser findByResourceId(UUID userResourceId) throws UserNotFoundException {

        AppUser user = userRepository.findByResourceId(userResourceId);

        if (user == null) {
            throw new UserNotFoundException("AppUser not found with this resource Id");
        }

        LOG.info("findByResourceId user {}", user.getResourceId());

        return user;
    }

    public AppUser findByName(String name) throws UserNotFoundException {

        AppUser user = userRepository.findByName(name);

        if (user == null) {
            throw new UserNotFoundException();
        }

        LOG.info("findByName user {}", user.getResourceId());

        return user;
    }

    public List<AppUser> findAllUsers() {

        List<AppUser> users = IteratorUtils.toList(userRepository.findAll().iterator());

        LOG.info("findAllUsers users size {}", users.size());

        return users;
    }

    public AppUser createUser(SimpleUserDto wantedUser) throws InvalidUserCreationException {

        checkUserCreationValidity(wantedUser);

        AppUser newUser = new AppUser(wantedUser.getName(), UserType.USER);

        LOG.info("createUser user {}", newUser.getResourceId());

        newUser = userRepository.save(newUser);

        applicationEventPublisher.publishEvent(new UserCreationEvent(this, newUser));

        return newUser;
    }

    private void checkUserCreationValidity(SimpleUserDto wantedUser) throws InvalidUserCreationException {

        if (StringUtils.isNullOrEmpty(wantedUser.getName())) {
            throw new InvalidUserCreationException("AppUser request is invalid");
        }

        AppUser oldUser = userRepository.findByName(wantedUser.getName());

        if (oldUser != null) {
            throw new InvalidUserCreationException("AppUser with this name already exists");
        }
    }

    public void deleteUser(AppUser user) {

        LOG.info("deleteUser user {}", user.getResourceId());

        userRepository.delete(user);

        applicationEventPublisher.publishEvent(new UserDeleteEvent(this, user));
    }

    public AppUser updateUser(AppUser user, SimpleUserDto updateUserDto) {

        LOG.info("updateUser user {}", user.getResourceId());

        user.updateName(updateUserDto.getName());

        return userRepository.save(user);
    }
}
