package com.mobiletest.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class FileUtilties {

    public void createDirectory(String fPath){
        File file=new File(System.getProperty("user.dir"),fPath);
        if(!file.isDirectory()){
            if(file.mkdir()){
                System.out.println("Directory created sucessfully");
            }
            else{
                System.out.println("Directory is not created");
            }
        }
    }

    public void deleteExisitngFolder(String fPath){

        File file = new File(fPath);
        if(file.isDirectory()){
            try {
                FileUtils.cleanDirectory(file);
                FileUtils.forceDelete(file);
                FileUtils.forceMkdir(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
       }

    public Properties readProperties() {
        File file=new File(System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config.properties");
        FileInputStream input= null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop=new Properties();
        try {
            prop.load(input);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return prop;

    }
}
