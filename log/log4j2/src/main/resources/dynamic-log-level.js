var ctx = org.apache.logging.log4j.LogManager.getContext(false);
var config = ctx.getConfiguration();
var loggerConfig = config.getLoggerConfig("com.example");
loggerConfig.setLevel(org.apache.logging.log4j.Level.DEBUG);
ctx.updateLoggers(config);