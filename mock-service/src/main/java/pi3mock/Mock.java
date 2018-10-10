package pi3mock;

public class Mock {

    private final long id;
    private final String content;

    public Mock(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
