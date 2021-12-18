package sysutil;

import java.util.Locale;

/**
 * THANKS TO bastengao (https://gist.github.com/bastengao)
 * Helper class to check the operating system
 */

public class OsCheck {
    // Type of Operating systems
    public enum OSType {
        Windows,
        MacOS,
        Linux,
        Other
    }

    protected static OSType detectedOS;

    /**
     * detected the operating system from the os.name System property and cache
     * the result
     *
     * @return the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase();
            if (OS.contains("win")) {
                detectedOS = OSType.Windows;
            }
            else if ((OS.contains("mac")) || (OS.contains("darwin"))) {
                detectedOS = OSType.MacOS;
            }
            else if (OS.contains("nux")) {
                detectedOS = OSType.Linux;
            }
            else {
                detectedOS = OSType.Other;
            }
        }

        return detectedOS;
    }
}
