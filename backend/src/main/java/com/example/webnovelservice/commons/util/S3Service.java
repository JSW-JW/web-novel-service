package com.example.webnovelservice.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	private AmazonS3Client s3Client;

	private static final String FILE_EXTENSION_SEPARATOR = ".";
	public S3Service(AmazonS3Client s3Client) {
		this.s3Client = s3Client;
	}

	public String uploadFile(MultipartFile multipartFile) throws IOException {
		String originalFileName = Objects.requireNonNull(multipartFile.getOriginalFilename());

		File file = convertMultiPartToFile(multipartFile);
		String fileName = generateFileName(originalFileName);
		uploadFileTos3bucket(fileName, file);
		file.delete();
		return fileName;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		Objects.requireNonNull(file.getOriginalFilename());

		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public static String generateFileName(String originalFileName) {
		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		String fileExtension = originalFileName.substring(fileExtensionIndex);
		String fileName = originalFileName.substring(0, fileExtensionIndex).replace(" ", "_");
		String now = String.valueOf(System.currentTimeMillis());

		return fileName + now + fileExtension;
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
	}

}
