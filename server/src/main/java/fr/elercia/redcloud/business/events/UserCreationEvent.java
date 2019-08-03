package fr.elercia.redcloud.business.events;

import fr.elercia.redcloud.business.entity.AppUser;
import org.springframework.context.ApplicationEvent;

public class UserCreationEvent extends ApplicationEvent {

    private AppUser user;

    public UserCreationEvent(Object source, AppUser user) {
        super(source);
        this.user = user;
    }

    public AppUser getUser() {
        return user;
    }
}
