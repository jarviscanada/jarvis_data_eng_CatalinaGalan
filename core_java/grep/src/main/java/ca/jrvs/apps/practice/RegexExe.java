package ca.jrvs.apps.practice;

public interface RegexExe {
  /*
    return true if file is jpg or jpeg (case-insensitive)
    @param = filename
    @return
   */
  public boolean isJpeg(String filename);

  /*
    return true if ip is valid
    @param = ip
    @return
   */
  public boolean validIp(String ip);

  /*
    return true if line is empty (whitespace, tab, etc.)
    @param = line
    @return
   */
  public boolean isLineEmpty(String line);

}
