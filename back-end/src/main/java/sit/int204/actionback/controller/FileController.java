package sit.int204.actionback.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.service.FileStorageService;

import java.io.IOException;



import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/file")
@CrossOrigin(origins = "*")
@RestControllerAdvice
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        System.out.println(fileName);
        return ResponseEntity.status(HttpStatus.OK).body(fileName + " uploaded!");
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity deleteFile(@PathVariable String filename) {
        Event event = eventRepository.findEventByAttachment(filename);
        if(event != null){
            eventRepository.updateAttachment(event.getId(),null);
        }
        String ans = fileStorageService.deleteFile(filename);
        return ResponseEntity.status(HttpStatus.OK).body(ans);

    }

    @GetMapping("/get/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String fileSizeExceptionHandler(RedirectAttributes ra) {
        return "File size should be less than 10MB!";
    }
}
