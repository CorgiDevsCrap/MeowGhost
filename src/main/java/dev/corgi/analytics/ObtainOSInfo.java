package dev.corgi.analytics;

import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.SystemUtils;

public class ObtainOSInfo {
    public static String getOSName() {
        String a = SystemUtils.OS_NAME;
        return a;
    }
    public static String getOSArch() {
        String a = SystemUtils.OS_ARCH;
        return a;
    }
    public static String getOSVer() {
        String a = SystemUtils.OS_VERSION;
        return a;
    }
    public static boolean get64BitJava() {
        boolean a = Minecraft.getMinecraft().isJava64bit();
        return a;
    }


}
