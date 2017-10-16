package net.lightapi.portal.form.command;

public class CreateFormCommand implements FormCommand {

    String data;

    public CreateFormCommand(String data) {
        this.data = data;
    }

    public String getForm() {
        return data;
    }
}
