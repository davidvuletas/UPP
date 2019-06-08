package com.scientificcenter.service.form;

import com.scientificcenter.model.journal.ScientificAreaCodeBook;
import com.scientificcenter.model.users.User;
import com.scientificcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CamundaFormService {


    private final UserService userService;

    @Autowired
    public CamundaFormService(UserService userService) {
        this.userService = userService;
    }

    public Map<String,String> usersToMap(List<User> users) {
        Map<String, String> mapAuthors = new HashMap<>();
        for (User author : users) {
            mapAuthors.put(author.getId().toString(), author.getName().concat(" ").concat(author.getLastname()));
        }
        return mapAuthors;
    }

    public Map<String, String> codeBooksToMap(List<ScientificAreaCodeBook> codeBooks) {
        Map<String, String> mapCodeBooks = new HashMap<>();
        for (ScientificAreaCodeBook codeBook : codeBooks) {
            mapCodeBooks.put(codeBook.getId().toString(), codeBook.getSubArea().concat("-").concat(codeBook.getArea()));
        }
        return mapCodeBooks;
    }
}
