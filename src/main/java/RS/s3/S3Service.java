package RS.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3Service {

	@Autowired
	private AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
        
        public String getBucketName() {
            return bucket;
        }

	private PutObjectResult upload(String filePath, String uploadKey) throws FileNotFoundException {
		return upload(new FileInputStream(filePath), uploadKey);
	}

	private PutObjectResult upload(InputStream inputStream, String uploadKey) {
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());
		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);
		IOUtils.closeQuietly(inputStream);
		return putObjectResult;
	}

	public List<PutObjectResult> uploadFile(MultipartFile file) throws IOException {
		List<PutObjectResult> putObjectResults = new ArrayList<>();
		putObjectResults.add(upload(file.getInputStream(), file.getOriginalFilename()));
		return putObjectResults;
	}

	public List<S3ObjectSummary> listObjects() {
		ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));
		List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();
		return s3ObjectSummaries;
	}
}
