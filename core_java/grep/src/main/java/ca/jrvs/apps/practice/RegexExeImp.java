package ca.jrvs.apps.practice;
import java.util.regex.Pattern;

public class RegexExeImp implements RegexExe {
  String filename;
  String ip;
  String line;

  @Override
  public boolean isJpeg(String filename) {
    String regex = ".+.jpe?g";
    return Pattern.matches(regex, filename);
//    return Objects.equals(filename, " ");
  }

  @Override
  public boolean validIp(String ip) {
    String regex = "(\\d{1,3}\\.){3}\\d{1,3}";
    return Pattern.matches(regex, ip);
  }

  @Override
  public boolean isLineEmpty(String line) {
    String regex = "\\s";
    return Pattern.matches(regex, line);
  }

/*
  public static void main(String[] args) {
    RegexExeImp regexExample = new RegexExeImp();

    System.out.println(regexExample.isJpeg("helloWOrld.jpg"));
    System.out.println(regexExample.validIp("0.0.0.0"));
    System.out.println(regexExample.isLineEmpty(" "));
  }
*/
}
