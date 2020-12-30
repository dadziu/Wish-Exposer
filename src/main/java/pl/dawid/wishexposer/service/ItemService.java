package pl.dawid.wishexposer.service;

import pl.dawid.wishexposer.domain.Email;
import pl.dawid.wishexposer.domain.Item;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface ItemService {
    void sendList(String username, List<Item> items, Email email, boolean isDynamicList) throws MessagingException, UnsupportedEncodingException;
    Item addItem(String username, Item item);
    Item updateItem(Long id, String name, String description, String url);
    List<Item> getItems();
    List<Item> getItemsByUsername(String username);
    List<Item> getItemsByUserId(String userId);
    Item findItemById(Long id);
    void deleteItem(Long id);
}
