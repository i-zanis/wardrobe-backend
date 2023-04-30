package io.noblackhole.wardrobe.wardrobebackend.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

// NOT ACTIVE
// TODO(jtl): To be refactored into a Service
//@RestController
public class BackgroundRemovalController {
  private final Logger logger = LoggerFactory.getLogger(BackgroundRemovalController.class);

  @NotNull
  private static Path saveUploadedFileTemporarily(MultipartFile file) throws IOException {
    Path tempFile = Files.createTempFile("image", file.getOriginalFilename());
    try (OutputStream os = Files.newOutputStream(tempFile)) {
      os.write(file.getBytes());
    }
    return tempFile;
  }

  private static byte[] removeBackground(Path tempFile) throws IOException {
    Process process = new ProcessBuilder("rembg", "i", tempFile.toString())
      .redirectError(ProcessBuilder.Redirect.INHERIT)
      .start();
    byte[] outputBytes;
    try (InputStream is = process.getInputStream()) {
      outputBytes = is.readAllBytes();
    }
    return outputBytes;
  }

  @PostMapping("/remove-background")
  public ResponseEntity<byte[]> removeBackground(@RequestParam("file") MultipartFile file) throws IOException {
    logger.info("Received file: {}", file.getOriginalFilename());
    Path tempFile = saveUploadedFileTemporarily(file);
    byte[] outputBytes = removeBackground(tempFile);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_PNG);
    return new ResponseEntity<>(outputBytes, headers, HttpStatus.OK);
  }
}
