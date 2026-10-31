package jp.tmkworks.tmk_investment.util.spring.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

import org.springframework.core.io.FileSystemResource;

public class GZIPFileSystemResource extends FileSystemResource {

  public GZIPFileSystemResource(Path filePath) {
    super(filePath);
  }
  
  @Override
  public InputStream getInputStream() throws IOException {
    var filePath = getFilePath();
    var inStream = Files.newInputStream(filePath);
    var gzipInputStream = new GZIPInputStream(inStream);
    return gzipInputStream;
  }

}
