package org.zhuzhenxi.test.serializable;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExampleObject implements Serializable {
    private String username;
    private String password;

    public ExampleObject(String username1,String password1){
        username = username1;
        password = password1;
    }
}
