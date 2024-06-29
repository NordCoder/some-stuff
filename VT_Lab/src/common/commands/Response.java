package common.commands;

import java.io.Serializable;

public class Response implements Serializable {
    private String text;

    public Response(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
