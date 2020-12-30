package pl.dawid.wishexposer.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.dawid.wishexposer.domain.Email;
import pl.dawid.wishexposer.domain.HttpResponse;
import pl.dawid.wishexposer.domain.Item;
import pl.dawid.wishexposer.service.ItemService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/", "/wishlist"})
public class ItemResource {

    public static final String EMAIL_SENT = "An email with new password was sent to: ";
    public static final String ITEM_DELETED_SUCCESSFULLY = "Item deleted successfully";
    private final ItemService itemService;

    @Autowired
    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/sendList")
    public ResponseEntity<HttpResponse> sendList(@RequestParam("username") String username, @RequestParam("items") String items, @RequestParam("email") String email, @RequestParam("isDynamicList") boolean isDynamicList) throws MessagingException, UnsupportedEncodingException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        List<Item> deserializedItems = Arrays.asList(gson.fromJson(items, Item[].class));
        Email deserializedEmail = gson.fromJson(email, Email.class);
        itemService.sendList(username, deserializedItems, deserializedEmail, isDynamicList);
        return response(OK, EMAIL_SENT + email);
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestParam("username") String username, @RequestParam("item") String item) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Item deserializedItem = gson.fromJson(item, Item.class);
        Item newItem = itemService.addItem(username, deserializedItem);
        return new ResponseEntity<>(newItem, OK);
    }

    @PostMapping("/updateItem")
    public ResponseEntity<Item> updateItem(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("url") String url) {
        Item item = itemService.updateItem(Long.valueOf(id), name, description, url);
        return new ResponseEntity<>(item, OK);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getItems();
        return new ResponseEntity<>(items, OK);
    }

    @GetMapping("/list/{username}")
    public ResponseEntity<List<Item>> getAllItemsByUsername(@PathVariable("username") String username) {
        List<Item> itemsByUsername = itemService.getItemsByUsername(username);
        return new ResponseEntity<>(itemsByUsername, OK);
    }

    @GetMapping("/listByUserId/{userId}")
    public ResponseEntity<List<Item>> getAllItemsByUserId(@PathVariable("userId") String userId) {
        List<Item> itemsByUserId = itemService.getItemsByUserId(userId);
        return new ResponseEntity<>(itemsByUserId, OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable("id") String id) {
        Item item = itemService.findItemById(Long.valueOf(id));
        return new ResponseEntity<>(item, OK);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<HttpResponse> deleteItem(@PathVariable("itemId") String itemId) {
        itemService.deleteItem(Long.valueOf(itemId));
        return response(OK, ITEM_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase()), httpStatus);
    }
}
