package jpabook.jpashop.web;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class BookForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;
    private Boolean open;
    private String spec;
    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;



    private String img1;
    private String img2;
    private String img3;

}
