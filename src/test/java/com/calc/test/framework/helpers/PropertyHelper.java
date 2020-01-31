package com.calc.test.framework.helpers;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Roshan
 */
public class PropertyHelper {

    private static final String PROPERTY_FILE_PATH = System.getProperty("user.dir")+"/src/test/resources/config.properties";
    private static Properties properties;

    private PropertyHelper() throws Exception {
        properties = new Properties();
        FileInputStream input = new FileInputStream(PROPERTY_FILE_PATH);
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new Exception("There was a problem in reading the property file \n" + e.getMessage());
        }
        properties.putAll(System.getProperties());
    }

    public static String getProperty(String property) throws Exception {
        if(properties == null){
            new PropertyHelper();
        }
        return properties.getProperty(property);
    }


    public static String getBaseUrl() throws Exception {
        String host = System.getProperty("environment.host");
        System.out.println("Property Helper System HOST value : "+host);
        if(StringUtils.isBlank(host)){
            host = getProperty("local.host").toLowerCase();
            System.out.println("Property Helper Local HOST value : "+host);
        }
        String port = System.getProperty("environment.port");
        System.out.println("Property Helper System PORT value : "+port);
        if(StringUtils.isBlank(port)){
            port = getProperty("local.port").toLowerCase();
            System.out.println("Property Helper Local PORT value : "+port);
        }
        if(host.contains("emirates.com"))
            return "https://"+host;
        else
            return "http://"+host+":"+port;
    }

    public static String getApplicationContextPath() throws Exception {
        String path = System.getProperty("environment.path");
        System.out.println("Property Helper System PATH value : "+path);
        if(StringUtils.isBlank(path)){
            path = getProperty("local.path").toLowerCase();
            System.out.println("Property Helper Local PATH value : "+path);
        }
        return path;
    }

}
