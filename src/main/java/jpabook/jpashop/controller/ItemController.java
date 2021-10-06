package jpabook.jpashop.controller;
import jpabook.jpashop.domain.UploadFile;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.FileStore;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.web.BookForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;

    @GetMapping(value = "/items/new")
    public String creatForm(Model model) {

        model.addAttribute("form", new BookForm());
        model.addAttribute("item", new Book());
        return "/items/createItemForm";
    }

    @PostMapping(value = "/items/new")
    public String create(BookForm form, RedirectAttributes redirectAttributes) throws IOException {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setOpen(form.getOpen());
        book.setSpec(form.getSpec());


        //-------- image upload test part --------//
        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile> storeImageFiles =
                fileStore.storeFiles(form.getImageFiles());
        setImageCreate(book, attachFile, storeImageFiles);
        redirectAttributes.addAttribute("itemId", book.getId());
        itemService.saveItem(book);
        return "redirect:/items";
    }

    private void setImageCreate(Book book, UploadFile attachFile, List<UploadFile> storeImageFiles) {
        book.setImg1(storeImageFiles.get(0).getStoreFileName());
        book.setImg2(storeImageFiles.get(1).getStoreFileName());
        //book.setImg3(storeImageFiles.get(2).getStoreFileName());
        book.setAttachFile(attachFile);
        book.setImageFiles(storeImageFiles);
    }

    @GetMapping(value = "/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "items/{itemId}/edit")
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
    public String items(@PathVariable Long id, Model model) {
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);
        return "item-view";
    }


    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws
            MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));

    }

    }

