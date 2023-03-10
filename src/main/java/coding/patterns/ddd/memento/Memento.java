package coding.patterns.ddd.memento;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Memento {
    @Getter
    private List<IDirtyHint> dirtyHints = new LinkedList<IDirtyHint>();

    public void register(IDirtyHint dirtyHint) {
        dirtyHints.add(dirtyHint);
    }
}
