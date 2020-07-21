package com.lib.webLibrary.controllers;

import com.lib.webLibrary.models.Author;
import com.lib.webLibrary.models.Book;
import com.lib.webLibrary.models.BookInstance;
import com.lib.webLibrary.models.User;
import com.lib.webLibrary.repo.BookInstanceRepository;
import com.lib.webLibrary.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookInstanceRepository bookInstanceRepository;


    @GetMapping("/library")
    public String library( Model model) {
        Iterable<Book> books= bookRepository.findAll();
        model.addAttribute("books",books);
        return "library-Books";
    }
    @GetMapping("/addBook")
    public String addBook( Model model) {
        return "book-Add";
    }
    @PostMapping("/addBook")
    public String bookAdd(@RequestParam String book_title,
                          @RequestParam String publishing_house,
                          @RequestParam Year year_publishing,
                          @RequestParam String place_publication,
                          @AuthenticationPrincipal User user,
                          Model model) {

        Book book=new Book(book_title, publishing_house, year_publishing, place_publication,user);
        bookRepository.save(book);
        return "redirect:/library";
    }
    //для дополнительно
    @GetMapping("/library/book_{id}")
    public String bookDop(@PathVariable(value="id") long id, Model model) {
        if(!bookRepository.existsById(id)){
            return "redirect:/library";
        }
        Optional<Book> book=bookRepository.findById(id);
        ArrayList<Book> bookList=new ArrayList<>();
        book.ifPresent(bookList::add);
        model.addAttribute("book",bookList);

        Iterable<BookInstance> bookInstance= bookInstanceRepository.findAll();
        ArrayList<BookInstance> bookInstanceList=new ArrayList<>();
        for(BookInstance b:bookInstance){
            if(b.getId_Book().getId()==id){
                bookInstanceList.add(b);
            }
        }
        model.addAttribute("bookInstanceList",bookInstanceList);
        return "book-Additionally";
    }
   //авторы книги
   @GetMapping("/library/book_authors_{id}")
   public String bookAuthors(@PathVariable(value="id") long id, Model model) {
       Optional<Book> book=bookRepository.findById(id);
       List<Book> bookList=new ArrayList<>();
       List<Author> authors=new ArrayList<>();
       book.ifPresent(bookList::add);
       for(Book b:bookList) {
           authors=(b.getAuthors());
       }
       model.addAttribute("authors",authors);
       return "book-Authors";
   }
    //редактирование
    @GetMapping("/library/book_{id}/edit")
    public String bookEdit(@PathVariable(value="id") long id, Model model) {
        if(!bookRepository.existsById(id)){
            return "redirect:/library";
        }
        Optional<Book> book=bookRepository.findById(id);
        ArrayList<Book> bookList=new ArrayList<>();
        book.ifPresent(bookList::add);
        model.addAttribute("book",bookList);
        return "book-Edit";
    }

    @PostMapping("/library/book_{id}/edit")
    public String bookUpdate(@PathVariable(value="id") long id,
                             @RequestParam String book_title,
                             @RequestParam String publishing_house,
                             @RequestParam Year year_publishing,
                             @RequestParam String place_publication,
                             Model model) {
        Book book=bookRepository.findById(id).orElseThrow();
        book.setBook_title(book_title);
        book.setPlace_publication(place_publication);
        book.setPublishing_house(publishing_house);
        book.setYear_publishing(year_publishing);
        bookRepository.save(book);
        return "redirect:/library";
    }
    //удаление
    @PostMapping("/library/book_{id}/remove")
    public String bookDelete(@PathVariable(value="id") long id,
                             Model model) {
        Book book=bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return "redirect:/library";
    }



}
