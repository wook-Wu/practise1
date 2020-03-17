package com.yh.application.test;

import com.yh.application.model.User;
import com.yh.application.util.JxlsUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class ObjectCollectionDemo {

    public static void main(String[] args) throws ParseException, IOException {
        log.info("开始Object Collection demo");

        List<User> Users = generateSampleUserData();
        File file = new File("application/data/object_collection_output.xls");
        /*if (!file.exists()) {
            System.out.println(file.getAbsolutePath());
        }*/
        OutputStream os = new FileOutputStream(file);
        Map<String, Object> model = new HashMap<>();
        model.put("Users", Users);
        model.put("nowDate", new Date());
        JxlsUtils.exportExcel("object_collection_template.xls", os, model);
        os.close();
    }

    public static List<User> generateSampleUserData() throws ParseException {
        List<User> Users = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        Users.add(new User("Elsa", dateFormat.parse("1970-Jul-10"), new BigDecimal("1500"), new BigDecimal("0.15"),1));
        Users.add(new User("Oleg", dateFormat.parse("1973-Apr-30"), new BigDecimal("2300"), new BigDecimal("0.25"),2));
        Users.add(new User("Neil", dateFormat.parse("1975-Oct-05"), new BigDecimal("2500"), new BigDecimal("0.00"),3));
        Users.add(new User("Maria", dateFormat.parse("1978-Jan-07"), new BigDecimal("1700"), new BigDecimal("0.15"),4));
        Users.add(new User("John", dateFormat.parse("1969-May-30"), new BigDecimal("2800"), new BigDecimal("0.20"),5));
        return Users;
    }
}
