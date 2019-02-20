package fr.elercia.redcloud.business.events;

import fr.elercia.redcloud.business.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserCreationEvent extends ApplicationEvent {

    private User user;

    public UserCreationEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
