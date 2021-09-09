package sn.graim.jutil;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = file.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Impossible d'enregistrer le fichier: "+fileName, ioe);
		}
	}
}
