package fr.elercia.redcloud.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerWrapper {

    private Logger LOG;

    public LoggerWrapper(Class clazz) {
        LOG = LoggerFactory.getLogger(clazz);
    }

    public void debug(String log, Object... objs) {
        LOG.debug(buildMessage(log, objs));
    }

    public void entryDebug(String log) {
        LOG.debug(entry(log));
    }

    public void exitDebug(String log) {
        LOG.debug(exit(log));
    }

    public void info(String log, Object... objs) {
        LOG.info(buildMessage(log, objs));
    }

    public void entryInfo(String log) {
        LOG.info(entry(log));
    }

    public void exitInfo(String log) {
        LOG.info(exit(log));
    }

    public void error(String log, Object... objs) {
        LOG.error(buildMessage(log, objs));
    }

    public void error(String log, Throwable error) {
        LOG.error(log, error);
    }

    public void warn(String log, Object... objs) {
        LOG.warn(buildMessage(log, objs));
    }

    public void warn(String log, Throwable error) {
        LOG.error(log, error);
    }

    public void trace(String log, Object... objs) {
        LOG.trace(buildMessage(log, objs));
    }

    public void entryTrace(String log) {
        LOG.trace(entry(log));
    }

    public void exitTrace(String log) {
        LOG.trace(exit(log));
    }

    private String buildMessage(String log, Object... objs) {

        StringBuilder builder = new StringBuilder();

        for (Object o : objs) {

            builder.append("[").append(o.toString()).append("]");
        }

        return builder.toString() + " " + log;
    }

    private String entry(String log) {
        return log + "...";
    }

    private String exit(String log) {
        return "..." + log;
    }
}
