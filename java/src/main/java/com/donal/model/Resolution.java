package com.donal.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Simple POJO to contain resolution details
 */

@XmlRootElement(name = "resolution")
public class Resolution {
    private String value;
    private int count;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
