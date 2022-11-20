package com.ry.io;

import java.io.InputStream;

/**
 * @author ryang
 * @Description
 * @date 2022年08月25日 9:14 上午
 */
public class Resources {
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
