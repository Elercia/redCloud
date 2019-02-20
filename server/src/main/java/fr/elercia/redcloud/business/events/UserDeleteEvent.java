package fr.elercia.redcloud.business.events;

import fr.elercia.redcloud.business.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserDeleteEvent extends ApplicationEvent {

    private User user;

    public UserDeleteEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
