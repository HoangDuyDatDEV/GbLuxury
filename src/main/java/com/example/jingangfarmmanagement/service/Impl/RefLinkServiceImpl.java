package com.example.jingangfarmmanagement.service.Impl;


import com.example.jingangfarmmanagement.model.BaseResponse;
import com.example.jingangfarmmanagement.model.response.CategoryRes;
import com.example.jingangfarmmanagement.model.response.MenuConfigRes;
import com.example.jingangfarmmanagement.model.response.NewsRes;
import com.example.jingangfarmmanagement.repository.BaseRepository;
import com.example.jingangfarmmanagement.repository.MenuConfigRepository;
import com.example.jingangfarmmanagement.repository.NewsRepository;
import com.example.jingangfarmmanagement.repository.RefLinkRepository;
import com.example.jingangfarmmanagement.repository.entity.MenuConfig;
import com.example.jingangfarmmanagement.repository.entity.News;
import com.example.jingangfarmmanagement.repository.entity.RefLink;
import com.example.jingangfarmmanagement.service.MenuConfigService;
import com.example.jingangfarmmanagement.service.RefLinkService;
import com.example.jingangfarmmanagement.uitl.ContextUtil;
import com.example.jingangfarmmanagement.uitl.DateUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RefLinkServiceImpl extends BaseServiceImpl<RefLink> implements RefLinkService {
    @Autowired
    private RefLinkRepository refLinkRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    ContextUtil contextUtil;

    @Override
    protected BaseRepository<RefLink> getRepository() {
        return refLinkRepository;
    }
//    @Override
//    public BaseResponse uploadFile(MultipartFile file) {
//        try {
//            // Kiểm tra nếu bucket không tồn tại thì tạo mới
////            News news = newsRepository.getById(id);
//            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//            if (!isExist) {
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//            }
//
//            String forder ="gb_luxury/" + contextUtil.getUserName() + file.getOriginalFilename() + DateUtil.getCurrenDateTime();
//            // Upload file lên MinIO
//            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(forder).stream(file.getInputStream(), file.getInputStream().available(), -1).contentType("image/jpeg").build());
////            RefLink image = RefLink.builder().link(forder).news(news).no(no).build();
////            super.create(image);
////            return new BaseResponse().success("File uploaded successfully: " + file.getOriginalFilename());
//            String fileUrl = String.format("%s/%s/%s", "http://103.56.163.94:9000", bucketName, folder);
//
//            return new BaseResponse().success("File uploaded successfully", fileUrl);
//        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
//            System.err.println("Error occurred: " + e);
//            return new BaseResponse().fail(e.getMessage());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
@Override
public BaseResponse uploadFile(MultipartFile file) {
    try {
        // Kiểm tra nếu bucket không tồn tại thì tạo mới
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Tạo đường dẫn trong bucket
        String folder = "gb_luxury/" + contextUtil.getUserName() + "_" + file.getOriginalFilename() + "_" + DateUtil.getCurrenDateTime();

        // Upload file lên MinIO
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(folder)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        // Tạo URL truy cập file
        String fileUrl = String.format("%s/%s/%s", "http://103.56.163.94:9000", bucketName, folder);

        return new BaseResponse(200,"OK",fileUrl);
    } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
        System.err.println("Error occurred: " + e);
        return new BaseResponse().fail(e.getMessage());
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

}

