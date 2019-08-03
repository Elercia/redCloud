package fr.elercia.redcloud.business.events;

import fr.elercia.redcloud.business.entity.AppUser;
import org.springframework.context.ApplicationEvent;

public class UserDeleteEvent extends ApplicationEvent {

    private AppUser user;

    public UserDeleteEvent(Object source, AppUser user) {
        super(source);
        this.user = user;
    }

    public AppUser getUser() {
        return user;
    }
}
