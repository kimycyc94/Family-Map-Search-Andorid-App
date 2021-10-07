package com.example.familymapclient;

import org.junit.Test;
import org.junit.*;
import com.example.familymapclient.ServerProxy;
import Request.*;
import Result.*;

import static org.junit.Assert.*;

public class ServerProxyTest {
    ServerProxy sp;
    RegisterRequest regiRequeset;
    RegisterResult regiResult;

    @Before
    public void setUp() {
        sp = new ServerProxy("10.0.2.2", 8080);

    }

    @After
    public void deconstruct() {
        sp = null;
        regiRequeset = null;
        regiResult = null;
    }

    @Test
    public void registerPassTest() {
        regiRequeset = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
        assertNotNull(regiResult.getAuthtoken());
        assertNotNull(regiResult.getPersonID());
    }

    @Test
    public void registerFailTest() {

    }

    @Test
    public void loginPassTest() {

    }

    @Test
    public void loginFailTest() {

    }

    @Test
    public void getEventPassTest() {

    }

    @Test
    public void getEventFailTest() {

    }

    @Test
    public void getPersonPassTest() {

    }

    @Test
    public void getPersonFailTest() {

    }

}