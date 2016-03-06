package RS.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Player extends AbstractPersistable<Long> {

    private String name;
    
    @ManyToOne
    private Replay replay;

    public String getName() {
        return name;
    }

    public Replay getReplay() {
        return replay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReplay(Replay replay) {
        this.replay = replay;
    }
}
