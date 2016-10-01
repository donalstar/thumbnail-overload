package com.donal.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Result object for return to the client
 */

@XmlRootElement(name = "result")
public class Result {

    int code;
    String message;

    List<Resolution> resolutions;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<Resolution> getResolutions() {
        return resolutions;
    }

    public void setResolutions(List<Resolution> resolutions) {
        this.resolutions = resolutions;
    }
}
