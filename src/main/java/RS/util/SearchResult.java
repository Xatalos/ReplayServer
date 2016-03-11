package RS.util;

import RS.domain.*;
import java.util.List;

public class SearchResult {
    
    private List<Replay> replays;

    public SearchResult(List<Replay> replays) {
        this.replays = replays;
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public void setReplays(List<Replay> replays) {
        this.replays = replays;
    }
}
