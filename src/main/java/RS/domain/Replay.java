package RS.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Replay extends AbstractPersistable<Long> {

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @NotBlank
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date gameDate;
    
    private String version;
    
    @OneToMany(mappedBy = "replay", fetch = FetchType.EAGER)
    private List<Player> players;
    
    @Column(length = 2147483647, name = "downloads")
    private int downloads;

    public byte[] getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public String getVersion() {
        return version;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}
