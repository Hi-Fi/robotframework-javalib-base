package com.github.hi_fi.javalibbase;

import static org.junit.Assert.*;

import org.junit.Test;

public class RobotTest {
    
    String[] args;

    @Test
    public void testBoolean() {
        Boolean value = Robot.getParamsValue(args, 0, true);
        assertTrue(value);
    }
    
    @Test
    public void testString() {
        String value = Robot.getParamsValue(args, 0, "value");
        assertTrue(value.equals("value"));
    }
}