package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {

  /**
   * Top level search workflow
   * @throws IOException Illegal Argument Exception if file path is not valid:
   * if path does not exist, or if path cannot be read, or if path in an empty directory.
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and return all files
   * @param rootDir input directory
   * @return files under the rootDir
//   * @throws IllegalArgumentException if given root path is invalid or an empty directory
   */
  List<File> listFiles(String rootDir) throws IOException;

  /**
   * Read a file and return all the lines
   * This method uses FileReader as a source for BufferReader.
   * FileReader reads contents of the input file as a stream of characters one at a time,
   * and will use the environment default character encoding to interpret the contents of the file.
   * BufferReader buffers the input to provide efficient reading of lines.
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if File is not a readable file
   */
  List<String> readLines(File inputFile) throws IOException;

  /**
   * check if a line contains regex pattern (passed by user)
   * @param line input string
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   *
   * Explore: FileOutputStream, OutputStreamWriter, and BufferWriter
   *
   * @param lines matched line
   * @throws IOException if write failed
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);
}
