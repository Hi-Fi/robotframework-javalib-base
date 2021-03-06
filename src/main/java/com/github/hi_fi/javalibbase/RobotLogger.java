package com.github.hi_fi.javalibbase;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;

public class RobotLogger implements Log {

    public enum Level {
        ALL(0), TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), FATAL(6), NONE(7);
        private int order;

        Level(int order) {
            this.order = order;
        }

        public int getLevel() {
            return this.order;
        }

    }

    /** The name of this simple log instance */
    protected volatile String logName = null;
    /** The current log level */
    protected volatile Level currentLogLevel;

    static protected final String systemPrefix = "org.apache.commons.logging.robotlogger.";

    private static Boolean debugOverride = false;

    public RobotLogger(String name) {
        logName = name;

        // Set initial log level
        // Used to be: set default log level to ERROR
        // IMHO it should be lower, but at least info ( costin ).
        setLevel(Level.INFO);

        // Set log level from properties
        String lvl = getStringProperty(systemPrefix + "log." + logName);
        int i = String.valueOf(name).lastIndexOf(".");
        while (null == lvl && i > -1) {
            name = name.substring(0, i);
            lvl = getStringProperty(systemPrefix + "log." + name);
            i = String.valueOf(name).lastIndexOf(".");
        }

        if (null == lvl) {
            lvl = getStringProperty(systemPrefix + "defaultlog");
        }

        if (lvl != null) {
            setLevel(Level.valueOf(lvl.toUpperCase()));
        }
        this.debug("Enabled logger for: " + logName + " with level: " + getLevel());
    }
    
    public void log(Object log, String logLevel) {
        switch (Level.valueOf(logLevel.toUpperCase())) {
        case TRACE:
            this.trace(log);
            break;
        case DEBUG:
            this.debug(log);
            break;
        case INFO:
            this.info(log);
            break;
        case WARN:
            this.warn(log);
            break;
        case ERROR:
            this.debug(log);
            break;
        case FATAL:
            this.fatal(log);
            break;
        default:
            break;
        }
    }

    public static void logHTML(Object log) {
        System.out.println("*HTML* " + log);
    }

    public void error(Object log) {
        if (isErrorEnabled()) {
            System.out.println("*ERROR* " + log);
        }
    }

    public void debug(Object log) {
        if (isDebugEnabled()) {
            System.out.println("*DEBUG* " + log);
        }
    }

    public void trace(Object log) {
        if (isTraceEnabled()) {
            System.out.println("*TRACE* " + log);
        }
    }

    public void info(Object log) {
        if (isInfoEnabled()) {
            System.out.println("*INFO* " + log);
        }
    }

    public void trace(Object message, Throwable t) {
        if (isTraceEnabled()) {
            System.out.println("*TRACE* " + message);
            System.out.println("*TRACE* " + ExceptionUtils.getStackTrace(t));
        }
    }

    public void debug(Object message, Throwable t) {
        if (isDebugEnabled()) {
            System.out.println("*DEBUG* " + message);
            System.out.println("*DEBUG* " + ExceptionUtils.getStackTrace(t));
        }
    }

    public void error(Object message, Throwable t) {
        if (isErrorEnabled()) {
            System.out.println("*ERROR* " + message);
            System.out.println("*ERROR* " + ExceptionUtils.getStackTrace(t));
        }
    }

    public void fatal(Object message) {
        if (isFatalEnabled()) {
            this.error(message);
        }
    }

    public void fatal(Object message, Throwable t) {
        if (isFatalEnabled()) {
            this.error(message, t);
        }
    }

    public void info(Object message, Throwable t) {
        if (isInfoEnabled()) {
            System.out.println("*INFO* " + message);
            System.out.println("*INFO* " + ExceptionUtils.getStackTrace(t));
        }
    }

    public void warn(Object message) {
        if (isWarnEnabled()) {
            System.out.println("*WARN* " + message);
        }
    }

    public void warn(Object message, Throwable t) {
        if (isWarnEnabled()) {
            System.out.println("*WARN* " + message);
            System.out.println("*WARN* " + ExceptionUtils.getStackTrace(t));
        }

    }

    public boolean isDebugEnabled() {
        return debugOverride || currentLogLevel.getLevel() <= Level.DEBUG.getLevel();
    }

    public boolean isErrorEnabled() {
        return currentLogLevel.getLevel() <= Level.ERROR.getLevel();
    }

    public boolean isFatalEnabled() {
        return currentLogLevel.getLevel() <= Level.FATAL.getLevel();
    }

    public boolean isInfoEnabled() {
        return currentLogLevel.getLevel() <= Level.INFO.getLevel();
    }

    public boolean isTraceEnabled() {
        return currentLogLevel.getLevel() <= Level.TRACE.getLevel();
    }

    public boolean isWarnEnabled() {
        return currentLogLevel.getLevel() <= Level.WARN.getLevel();
    }

    private static String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException e) {
            // Ignore
        }
        return prop;
    }

    public String getLevel() {
        return this.currentLogLevel.toString();
    }

    public void setLevel(Level currentLogLevel) {
        this.currentLogLevel = currentLogLevel;
    }

    public static void setDebugToAll(Boolean debug) {
        debugOverride = debug;
    }
}
