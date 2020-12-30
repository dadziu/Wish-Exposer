package pl.dawid.wishexposer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawid.wishexposer.WishExposer;
import pl.dawid.wishexposer.domain.Email;
import pl.dawid.wishexposer.domain.Item;
import pl.dawid.wishexposer.domain.User;
import pl.dawid.wishexposer.repository.ItemRepository;
import pl.dawid.wishexposer.repository.UserRepository;
import pl.dawid.wishexposer.service.EmailService;
import pl.dawid.wishexposer.service.ItemService;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    public static final String EMAIL_SUBJECT = "Wish list of ";
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final EmailService emailService;

    @Autowired
    public ItemServiceImpl(UserRepository userRepository, ItemRepository itemRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendList(String username, List<Item> items, Email email, boolean isDynamicList) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findUserByUsername(username);
        StringBuilder emailMessage = new StringBuilder();
        emailMessage.append("Wish list of " + user.getFirstName() + " " + user.getLastName() + "\n\n");
        if (isDynamicList)
            emailMessage.append("\n" + WishExposer.BASE_URL + "/wishes/" + user.getUserId());
        else
            items.forEach(item ->
                    emailMessage.append(item.getName() + "(" + item.getDescription() + ") - " + item.getUrl() + "\n"));
        email.setTitle(EMAIL_SUBJECT + user.getFirstName() + " " + user.getLastName());
        email.setMessage(emailMessage.toString());
        emailService.sendNewEmail(email);
    }

    @Override
    public Item addItem(String username, Item item) {
        User user = userRepository.findUserByUsername(username);
        item.setUser(user);
        itemRepository.save(item);
        return item;
    }

    @Override
    public Item updateItem(Long id, String name, String description, String url) {
        Item item = itemRepository.findItemById(id);
        item.setName(name);
        item.setDescription(description);
        item.setUrl(url);
        itemRepository.save(item);
        return item;
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByUsername(String username) {
        return itemRepository.findAllByUser_Username(username);
    }

    @Override
    public List<Item> getItemsByUserId(String userId) {
        return itemRepository.findAllByUser_UserId(userId);
    }

    @Override
    public Item findItemById(Long id) {
        return itemRepository.findItemById(id);
    }

    @Override
    public void deleteItem(Long id) {
        Item item = itemRepository.findItemById(id);
        itemRepository.deleteById(item.getId());
    }

}
