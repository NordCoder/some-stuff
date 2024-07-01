package common.commands;

import client.ResponseReceiver;

import java.io.Serializable;

public class Response implements Serializable {
    private String text;
    private ResponseReceiver.LoggingIn loginStatus = ResponseReceiver.LoggingIn.NOT_USED;

    public Response(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ResponseReceiver.LoggingIn getLoginVerificationFlag() {
        return loginStatus;
    }

    public void setLoginStatus(ResponseReceiver.LoggingIn loginStatus) {
        this.loginStatus = loginStatus;
    }
}
