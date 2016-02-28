package RS.domain;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Replay extends AbstractPersistable<Long> {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @NotBlank
    private String name;
    
    private String gameDate;
    
    private String version;
    
    private ArrayList<String> players;
    
    private int downloads;

    public byte[] getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getGameDate() {
        return gameDate;
    }

    public String getVersion() {
        return version;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPlayers(ArrayList<String> players) {
        this.players = players;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}
