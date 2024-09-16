package ca.jrvs.apps.grep;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {
    File dir = new File(this.rootPath);

    if (!dir.exists()) {
      throw new IllegalArgumentException("The specified path does not exist: " + dir);
    }

//    if (!dir.isDirectory()) {
//      throw new IllegalArgumentException("The specified path is not a directory: " + dir);
//    }

    if (!dir.canRead()) {
      throw new IllegalArgumentException("The specified directory cannot be read: " + dir);
    }
  }

  @Override
  public List<File> listFiles(String rootDir) {
    File dir = new File(rootDir);

    if (dir.isFile()) {
      return new ArrayList<>(Collections.singletonList(dir));
    } else {
      File[] fileArray = dir.listFiles();
      if (fileArray != null) {
        return new ArrayList<File>(Arrays.asList(fileArray));
      }
    }
    return null;
  }

  @Override
  public List<String> readLines(File inputFile) {
    Path path = Paths.get(String.valueOf(inputFile));

    try {
      return Files.readAllLines(path, StandardCharsets.UTF_8);
      // Read all lines from the file
    } catch (IOException e) {
      // throw RuntimeException with context
      throw new IllegalArgumentException("IOException: Failed to read file. " + path.toAbsolutePath()+ " is not a UTF-8 encoded file.", e);
    }
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern regex = Pattern.compile("\\b" +this.regex+ "\\b");
    Matcher match = regex.matcher(line);
    return match.find();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    Path path = Paths.get(this.outFile);
    try {
      Files.write(path, lines, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getRegex() {
    return this.regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public String getOutFile() {
    return this.outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  public static void main(String[] args) {

    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    // use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    List<String> matches = new ArrayList<>();

    try {
      javaGrepImp.process();
    } catch (IOException ex) {
      javaGrepImp.logger.error("Error: unable to process", ex);
    }

    for (File file : javaGrepImp.listFiles(javaGrepImp.getRootPath())) {
      if (file.isFile()) {
        for (String line : javaGrepImp.readLines(file))
          if (javaGrepImp.containsPattern(line)) {
            matches.add(line);
          }
      }
    }

    try {
      javaGrepImp.writeToFile(matches);
    } catch (IOException e) {
      javaGrepImp.logger.error("Error writing to file", e);
    }
  }
}
