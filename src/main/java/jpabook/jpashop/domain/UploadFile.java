package jpabook.jpashop.domain;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UploadFile {

    private String uploadFileName;



    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

}
