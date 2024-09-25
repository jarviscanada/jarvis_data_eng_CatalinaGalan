package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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

    ArrayList<String> matchedLines = new ArrayList<>();

      for (File file : listFiles(getRootPath()))
        for (String line : readLines(file))
          if (containsPattern(line)) {
            matchedLines.add(line);
          }
      writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) throws IOException {
    Path startPath = Paths.get(rootDir);
    List<File> files = new ArrayList<>();

    if (Files.isRegularFile(startPath)) {
      files.add(startPath.toFile());
    } else {
      files = Files.walk(startPath)
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .collect(Collectors.toList());
    }
    return files;
  }

  @Override
  public List<String> readLines(File inputFile) throws IOException {
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    }

    return lines;
  }

  @Override
  public boolean containsPattern(String line) {
    Pattern regex = Pattern.compile(getRegex());
    Matcher matcher = regex.matcher(line);
    return matcher.find();
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    Path path = Paths.get(getOutFile());
    Files.write(path, lines);
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

    try {
      javaGrepImp.process();
    } catch (Exception ex) {
      javaGrepImp.logger.error("Error: unable to process: {}", ex.getMessage(), ex);
    }
  }
}
