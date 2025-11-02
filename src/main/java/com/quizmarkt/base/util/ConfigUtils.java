package com.quizmarkt.base.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.env.Environment;

/**
 * @author anercan
 */

@UtilityClass
public class ConfigUtils {

    public static boolean isLocalProfileActive(Environment environment) {
        for (String profile : environment.getActiveProfiles()) {
            if ("local".equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return false;
    }

}
