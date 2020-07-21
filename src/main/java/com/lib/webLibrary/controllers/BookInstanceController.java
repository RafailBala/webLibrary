package com.lib.webLibrary.controllers;


import com.lib.webLibrary.models.Book;
import com.lib.webLibrary.models.BookInstance;
import com.lib.webLibrary.models.Department;
import com.lib.webLibrary.models.User;
import com.lib.webLibrary.repo.BookInstanceRepository;
import com.lib.webLibrary.repo.BookRepository;
import com.lib.webLibrary.repo.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BookInstanceController {

    @Autowired
    private BookInstanceRepository bookInstanceRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @GetMapping("/library/bookInstances")
    public String library( Model model) {
        Iterable<BookInstance> bookInstances= bookInstanceRepository.findAll();
        model.addAttribute("bookInstances",bookInstances);
        return "library-BookInstances";
    }
    @GetMapping("/addInst")
    public String addInst( Model model) {
        return "bookInstance-Add";
    }
    @PostMapping("/addInst")
    public String instAdd(@RequestParam Long id,
                          @RequestParam Long id_Book,
                          @RequestParam Long id_Department,
                          @AuthenticationPrincipal User user,
                          Model model) {
        Book book=bookRepository.findById(id_Book).get();
        Department department=departmentRepository.findById(id_Department).get();
        BookInstance bookInstance=new BookInstance(id, book, department,user);
        bookInstanceRepository.save(bookInstance);
        return "redirect:/library/book_instances";
    }
    //для дополнительно
    @GetMapping("/library/bookInstance_{id}")
    public String bookInstanceDop(@PathVariable(value="id") long id, Model model) {
        Optional<BookInstance> bookInstance=bookInstanceRepository.findById(id);
        ArrayList<BookInstance> bookInstanceList=new ArrayList<>();
        bookInstance.ifPresent(bookInstanceList::add);
        model.addAttribute("bookInstance",bookInstanceList);
        return "bookInstance-Additionally";
    }
    //редактирование
    @GetMapping("/library/bookInstance_{id}/edit")
    public String bookInstanceEdit(@PathVariable(value="id") long id, Model model) {
        if(!bookInstanceRepository.existsById(id)){
            return "redirect:/library/bookInstances";
        }
        Optional<BookInstance> book=bookInstanceRepository.findById(id);
        ArrayList<BookInstance> bookInstance=new ArrayList<>();
        book.ifPresent(bookInstance::add);
        model.addAttribute("bookInstance",bookInstance);
        return "bookInstance-Edit";
    }
//Long id, Book id_Book, Department id_Department
    @PostMapping("/library/bookInstance_{id}/edit")
    public String bookUpdate(@PathVariable(value="id") long id,
                             @RequestParam Long id_Book,
                             @RequestParam Long id_Department,
                             Model model) {
        BookInstance bookInstance=bookInstanceRepository.findById(id).orElseThrow();
        bookInstance.setId_Book(bookRepository.findById(id_Book).get());
        bookInstance.setId_Department(departmentRepository.findById(id_Department).get());
        bookInstanceRepository.save(bookInstance);
        return "redirect:/library/bookInstances";
    }
    //удаление
    @PostMapping("/library/bookInstance_{id}/remove")
    public String bookDelete(@PathVariable(value="id") long id,
                             Model model) {
        BookInstance bookInstance=bookInstanceRepository.findById(id).orElseThrow();
        bookInstanceRepository.delete(bookInstance);
        return "redirect:/library/bookInstances";
    }

}
