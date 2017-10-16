package net.lightapi.portal.form.command;

public class UpdateFormCommand implements FormCommand {
    String id;
    String data;

    public UpdateFormCommand(String id, String data) {
        this.id = id;
        this.data = data;
    }

    public String getMenu() {
        return data;
    }

    public String getId() { return id; }
}
