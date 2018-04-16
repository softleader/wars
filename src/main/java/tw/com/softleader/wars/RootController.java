package tw.com.softleader.wars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.com.softleader.boot.fileupload.driver.StorageDriver;
import tw.com.softleader.boot.fileupload.key.Key;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class RootController {

  @Autowired private StorageDriver storageDriver;

  @PostMapping
  public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok(storageDriver.store(file));
  }

  @GetMapping
  public ResponseEntity<List<String>> list() throws IOException {
    List<String> loaded =
        storageDriver
            .loadAll()
            .map(Path::getFileName)
            .map(Path::toString)
            .collect(Collectors.toList());
    return ResponseEntity.ok(loaded);
  }
}
