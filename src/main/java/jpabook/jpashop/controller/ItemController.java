package jpabook.jpashop.controller;
import javassist.Loader;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.FileStore;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.BidService;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.web.BidForm;
import jpabook.jpashop.web.BookForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.xml.crypto.dsig.SignatureMethod;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;
    private final BidService bidService;

    @GetMapping(value = "/items/new")
    public String creatForm(Model model) {

        model.addAttribute("form", new BookForm());
        model.addAttribute("item", new Book());
        return "items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(BookForm form, RedirectAttributes redirectAttributes) throws IOException {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setOpen(form.getOpen());
        book.setSpec(form.getSpec());

        if (form.getBidCheck()) {
            log.info("getBidCheck()={}", form.getBidCheck());
            book.setCurrentPrice(form.getStartPrice());
            book.setStartPrice(form.getStartPrice());
            book.setEndPrice(form.getEndPrice());
            book.setBidMinValue(form.getBidMinValue());
            book.setBidCheck(form.getBidCheck());
            book.setBidEndTime(form.getBidEndTime());

        }


        List<UploadFile> storeImageFiles =
                fileStore.storeFiles(form.getImageFiles());
        setImageCreate(book, storeImageFiles);
        redirectAttributes.addAttribute("itemId", book.getId());
        itemService.saveItem(book);
        log.info("book.getId()={}", book.getId());

        return "redirect:/items";
    }

    private void setImageCreate(Book book, List<UploadFile> storeImageFiles) {
        book.setImg1(storeImageFiles.get(0).getStoreFileName());
        book.setImg2(storeImageFiles.get(1).getStoreFileName());
        book.setImg3(storeImageFiles.get(2).getStoreFileName());
//        book.setAttachFile(attachFile);
        book.setImageFiles(storeImageFiles);
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId,
                                 Model model) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setOpen(item.getOpen());
        form.setSpec(item.getSpec());


        model.addAttribute("form", form);
        model.addAttribute("item", item);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") BookForm form) {
        itemService.updateItem(form.getId(), form.getName(), form.getPrice(), form.getOpen(), form.getSpec());
        return "redirect:/items";
    }

    //이미지 테스트 한거 출력 위해
    @GetMapping("/items/{id}")
    public String single_item(@PathVariable Long id, Model model,
                              @ModelAttribute OrderItem orderItem,
                              @SessionAttribute(name = "loginMember", required = false) Member loginMember,
                              @ModelAttribute BidItem bidItem) throws ParseException {

        Item item = itemService.findOne(id);
        model.addAttribute("item", item);
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("loginmember", loginMember);

        log.info("item.getBidCheck()={}", item.getBidCheck());

            // 아이템 폼 html에서 폼 엑션값을 done/itemNo 주니까 바로 거기로 이동.
        if (item.getBidCheck()) {
            String bidEndTime = item.getBidEndTime();
            int year = Integer.parseInt(bidEndTime.substring(0, 4));
            int month = Integer.parseInt(bidEndTime.substring(5, 7));
            int day = Integer.parseInt(bidEndTime.substring(8, 10));
            int hour = Integer.parseInt(bidEndTime.substring(11, 13));
            int minute = Integer.parseInt(bidEndTime.substring(14, 16));

            LocalDateTime dateNow = LocalDateTime.now();
            LocalDateTime dateTarget = LocalDateTime.of(year, month, day, hour, minute, 0);

            long betweenSeconds = ChronoUnit.SECONDS.between(dateNow, dateTarget);

            if (betweenSeconds == 0 || betweenSeconds <= 0) {
                log.info("betweenSeconds={}", betweenSeconds);
                return "/bids/noRemainTime";
            }
            bidService.betweenDays(item, bidItem);
            return "/test/auctionTest";

        }
        return "/items/single-product";

    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));

    }


    }

