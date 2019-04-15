package com.grizzly.baseapplication.base.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

  private static final String IP_ADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
      + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
      + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
      + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

  public static boolean isNullOrEmpty(@Nullable String string) {
    return string == null || string.trim().length() == 0 || string.trim().equalsIgnoreCase("null");
  }

  public static boolean isNotNullOrEmpty(String string) {
    return !isNullOrEmpty(string);
  }

  @NonNull static String cleanHtmlString(String string) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY).toString();
    } else {
      //noinspection deprecation
      return Html.fromHtml(string).toString();
    }
  }

  @NonNull public static String formatName(@Nullable String firstName, @Nullable String lastName) {
    if (isNotNullOrEmpty(firstName)) {
      return isNotNullOrEmpty(lastName) ? String.format(Locale.getDefault(), "%s %s", firstName,
          lastName).trim() : firstName.trim();
    } else {
      return isNotNullOrEmpty(lastName) ? lastName.trim() : "";
    }
  }

  @NonNull public static String formatCreditCardNumber(@Nullable String creditCardNumber) {
    return formatCreditCardNumber(creditCardNumber, false);
  }

  @NonNull
  public static String formatCreditCardNumber(@Nullable String creditCardNumber, boolean mask) {
    if (isNotNullOrEmpty(creditCardNumber)) {
      StringBuilder result = new StringBuilder();
      for (int i = 0; i < creditCardNumber.length(); i++) {
        if (i % 4 == 0 && i != 0) {
          result.append(" ");
        }
        result.append(creditCardNumber.charAt(i));
      }
      if (!mask) {
        return result.toString();
      } else {
        String[] parts = result.toString().split(" ");
        String resultStr = "";
        for (int i = 0; i < parts.length - 1; i++) {
          resultStr = resultStr.concat(parts[i].replaceAll("[0-9]", "X"));
          resultStr += " ";
        }
        return resultStr.concat(parts[parts.length - 1]);
      }
    } else {
      return "";
    }
  }

  @NonNull public static String removeSpaces(@Nullable String str) {
    if (isNotNullOrEmpty(str)) {
      return str.replaceAll("\\s+", "");
    } else {
      return "";
    }
  }

  @NonNull public static String formatNumber(@Nullable String phoneNumber) {
    if (isNotNullOrEmpty(phoneNumber)) {
      phoneNumber = phoneNumber.replaceAll(" ", "");
      if (phoneNumber.startsWith("+")) {
        return ViewUtil.isRTL() ? phoneNumber.substring(1) + "+" : "+" + phoneNumber.substring(1);
      }
      return phoneNumber;
    } else {
      return "";
    }
  }

  @NonNull public static String limitString(@NonNull String string, int limit) {
    if (string.length() <= limit) return string;
    String limitedStr = string.substring(0, limit);
    return limitedStr.concat("...");
  }

  @NonNull public static String reverseDomain(@Nullable final String domain) {
    if (isNullOrEmpty(domain)) return "";
    final List<String> components = Arrays.asList(domain.split("\\."));
    Collections.reverse(components);
    return TextUtils.join(".", components);
  }

  /**
   * Based on : http://grepcode.com/file/repository.grepcode.com/java/ext/com.google.android/android/2.3.3_r1/android/webkit/CookieManager.java#CookieManager.getBaseDomain%28java.lang.String%29
   * Get the base domain for a given host or url. E.g. mail.google.com will return google.com
   */
  @NonNull public static String urlToAccountName(@NonNull final String url) {
    String host = getHost(url);

    if (validateIP(host)) return host;

    int startIndex = 0;
    int nextIndex = host.indexOf('.');
    int lastIndex = host.lastIndexOf('.');
    while (nextIndex < lastIndex) {
      startIndex = nextIndex + 1;
      nextIndex = host.indexOf('.', startIndex);
    }
    if (startIndex > 0) {
      return host.substring(startIndex, lastIndex);
    } else {
      return host;
    }
  }

  /**
   * Will take a url such as http://www.stackoverflow.com and return www.stackoverflow.com
   */
  @NonNull private static String getHost(@NonNull final String url) {
    if (url.length() == 0) return "";

    int doubleslash = url.indexOf("//");
    if (doubleslash == -1) {
      doubleslash = 0;
    } else {
      doubleslash += 2;
    }

    int end = url.indexOf('/', doubleslash);
    end = end >= 0 ? end : url.length();

    int port = url.indexOf(':', doubleslash);
    end = (port > 0 && port < end) ? port : end;

    return url.substring(doubleslash, end);
  }

  private static boolean validateIP(@NonNull final String ip) {
    Pattern pattern = Pattern.compile(IP_ADDRESS_PATTERN);
    Matcher matcher = pattern.matcher(ip);
    return matcher.matches();
  }

  @Nullable public static String cleanUrl(@Nullable String url) {
    if (StringUtil.isNotNullOrEmpty(url)) {
      String host = getHost(url);
      String[] split = host.split("/");
      return split[0];
    }
    return url;
  }

  public static int getPasswordStrength(@NonNull String password) {
    double numFactor = 1.01;
    double symFactor = 1.01;

    if (password.matches(".*\\d+.*")) {
      numFactor = 2.0;
    }

    if (password.matches(".*[.,;_\\-()*&^#]+.*")) {
      symFactor = 4.0;
    }

    double complexity = ((double) password.length() / 1.5) * (numFactor + symFactor);

    if (complexity < 30) {
      // WEAK
      return 0;
    } else if (complexity < 70) {
      // OK
      return 1;
    } else {
      // Strong
      return 2;
    }
  }

  @NonNull public static String fullYear(@NonNull String expYear) {
    return "20" + expYear;
  }

  @NonNull public static String capitalizeFirstLetter(@NonNull String word) {
    if (word.length() > 0) {
      return word.substring(0, 1).toUpperCase() + word.substring(1);
    } else {
      return "";
    }
  }

  @NonNull public static boolean isVAlidSecret(String secret) {
    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
    Matcher m = p.matcher(secret);
    if (m.find()) {
      return false;
    } else {
      return true;
    }
  }

  public static String getCountryCode(String countryName) {
    // Get all country codes in a string array.
    String[] isoCountryCodes = Locale.getISOCountries();
    Map<String, String> countryMap = new HashMap<>();

    // Iterate through all country codes:
    for (String code : isoCountryCodes) {
      // Create a locale using each country code
      Locale locale = new Locale("", code);
      // Get country name for each code.
      String name = locale.getDisplayCountry();
      // Map all country names and codes in key - value pairs.
      countryMap.put(name, code);
    }
    // Get the country code for the given country name using the map.
    // Here you will need some validation or better yet
    // a list of countries to give to user to choose from.
    String countryCode = countryMap.get(countryName); // "NL" for Netherlands.

    return countryCode;
  }


  public static long getRandomDoubleBetweenRange(long min, long max){
    double x = (Math.random()*((max-min)+1))+min;
    return (long) x;
  }

}
