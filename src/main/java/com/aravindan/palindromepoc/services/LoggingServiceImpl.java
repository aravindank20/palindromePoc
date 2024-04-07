package com.aravindan.palindromepoc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Logging service implementation
 *
 * @author Aravindan Kalaiselvan
 *
 */
@Service
public class LoggingServiceImpl implements LoggingService{

    Logger logger = LoggerFactory.getLogger("LoggingServiceImpl");

    /**
     * log the info
     * @param loggerString log info string
     */
    @Override
    public void logInfo(String loggerString) {
        logger.info(loggerString);
    }

    /**
     * log the error
     * @param loggerString log error string
     */
    @Override
    public void logError(String loggerString) {
        logger.error(loggerString);
    }
}
