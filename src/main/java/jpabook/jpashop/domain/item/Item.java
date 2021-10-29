package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.UploadFile;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")

@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    private Boolean open; // 최상단 섹션에 상품 등록 여부 체크
    @Transient
    private List<String> regions; // 등록 지역
    private ItemType itemType;
    private String deliveryCode;
    private String spec;

    private String img1;
    private String img2;
    private String img3;

    private Boolean bidCheck;
    private int startPrice;
    private int endPrice;
    private int bidMinValue;


    private int currentPrice;

    private String bidEndTime;




    @Transient
    @Setter
    private UploadFile attachFile;
    @Transient
    @Setter
    private List<UploadFile> imageFiles;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;

    }
}
