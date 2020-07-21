package com.lib.webLibrary.controllers;

import com.lib.webLibrary.models.Author;
import com.lib.webLibrary.models.Book;
import com.lib.webLibrary.models.User;
import com.lib.webLibrary.repo.AuthorRepository;
import com.lib.webLibrary.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    private Long autId;

    public AuthorController() {
    }

    @GetMapping("/library/authors")
    public String library(Model model) {
        Iterable<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "library-Authors";
    }

    @GetMapping("/addAut")
    public String addAut(Model model) {
        return "author-Add";
    }

    @PostMapping("/addAut")
    public String autAdd(@RequestParam String FIO,
                         @AuthenticationPrincipal User user,
                         Model model) {
        //String username=user.getUsername();
        Author author = new Author(FIO,user);
        authorRepository.save(author);
        return "redirect:/library/authors";
    }

    //для дополнительно
    @GetMapping("/library/author_{id}")
    public String authorDop(@PathVariable(value = "id") long id, Model model) {
        if (!authorRepository.existsById(id)) {
            return "redirect:/library/authors";
        }
        Optional<Author> author = authorRepository.findById(id);
        ArrayList<Author> authorList = new ArrayList<>();
        author.ifPresent(authorList::add);
        model.addAttribute("author", authorList);

        return "author-Additionally";
    }
    //книги автора
    @GetMapping("/library/author_books_{id}")
    public String authorBooks(@PathVariable(value="id") long idd, Model model) {
        Optional<Author> author=authorRepository.findById(idd);
        List<Book> bookList=new ArrayList<>();
        List<Author> authors=new ArrayList<>();
        author.ifPresent(authors::add);
        for(Author b:authors) {
            bookList=(b.getBooks());
        }
        model.addAttribute("bookList",bookList);
        autId=idd;
        model.addAttribute("idd",idd);
        return "author-Books";
    }
//добавление книги для автора
    @PostMapping("/library/author_books_{id}/add")
    public String autAdd(@PathVariable(value = "id") long id,
                         @RequestParam Long idd,
                         Model model) {
        if (!bookRepository.existsById(idd)) {
            return "redirect:/library/authors";
        }
        Book book=bookRepository.findById(idd).get();
        Author author=authorRepository.findById(id).get();
        author.getBooks().add(book);
        authorRepository.save(author);
        return "redirect:/library/authors";
    }
    //удаление книги автора
    @PostMapping("/library/author_books_{id}/remove")
    public String authorBookDel(@PathVariable(value = "id") long id,
                                Model model) {
        Author author=authorRepository.findById(autId).get();
        Book book=bookRepository.findById(id).get();
        author.getBooks().remove(book);
        return "redirect:/library/authors";
    }
    //редактирование автора
    @GetMapping("/library/author_{id}/edit")
    public String authorEdit(@PathVariable(value = "id") long id, Model model) {
        if (!authorRepository.existsById(id)) {
            return "redirect:/library/authors";
        }
        Optional<Author> author = authorRepository.findById(id);
        ArrayList<Author> authorList = new ArrayList<>();
        author.ifPresent(authorList::add);
        model.addAttribute("author", authorList);
        return "author-Edit";
    }

    @PostMapping("/library/author_{id}/edit")
    public String authorUpdate(@PathVariable(value = "id") long id,
                               @RequestParam String FIO,
                               Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        author.setFIO(FIO);
        authorRepository.save(author);
        return "redirect:/library/authors";
    }

    @PostMapping("/library/author_{id}/remove")
    public String authorUpdate(@PathVariable(value = "id") long id,
                               Model model) {
        Author author = authorRepository.findById(id).orElseThrow();
        authorRepository.delete(author);
        return "redirect:/library/authors";
    }
}

