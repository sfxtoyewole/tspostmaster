package com.ts.postmaster.utility;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * @author toyewole
 */
@UtilityClass
public class CommonLogic {


    public static byte[] getByteArray(String imageBase64) {
        if (StringUtils.isBlank(imageBase64)){
            return  null;
        }

        return  Base64.getDecoder().decode(imageBase64);
    }

    public static String toBase64(byte[] img) {
        if (img ==null || img.length ==0 ){
            return null;
        }
       return Base64.getEncoder().encodeToString(img);
    }
}
