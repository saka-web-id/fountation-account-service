package id.web.saka.fountation.account;

public class Account {

    private String id;
    private String name;

    public Account(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
