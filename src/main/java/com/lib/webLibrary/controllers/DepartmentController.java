package com.lib.webLibrary.controllers;

import com.lib.webLibrary.models.BookInstance;
import com.lib.webLibrary.models.Department;
import com.lib.webLibrary.models.User;
import com.lib.webLibrary.repo.BookInstanceRepository;
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
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @GetMapping("/library/departments")
    public String library( Model model) {
        Iterable<Department> departments= departmentRepository.findAll();
        model.addAttribute("departments",departments);
        return "library-Departments";
    }
    @GetMapping("/addDep")
    public String addDep( Model model) {
        return "department-Add";
    }
    @PostMapping("/addDep")
    public String depAdd(@RequestParam Long id,
                         @RequestParam String nameDep,
                         @AuthenticationPrincipal User user,
                         Model model) {
        Department department=new Department(id,nameDep,user);
        departmentRepository.save(department);
        return "redirect:/library/departments";
    }
    //для дополнительно
    @GetMapping("/library/department_{id}")
    public String departmentDop(@PathVariable(value="id") long id, Model model) {
        if(!departmentRepository.existsById(id)){
            return "redirect:/library/departments";
        }
        Optional<Department> department=departmentRepository.findById(id);
        ArrayList<Department> departmentList=new ArrayList<>();
        department.ifPresent(departmentList::add);
        model.addAttribute("department",departmentList);

        Iterable<BookInstance> bookInstance= bookInstanceRepository.findAll();
        ArrayList<BookInstance> bookInstanceList=new ArrayList<>();
        for(BookInstance b:bookInstance){
            if(b.getId_Department().getId()==id){
                bookInstanceList.add(b);
            }
        }
        model.addAttribute("bookInstanceList",bookInstanceList);

        return "department-Additionally";
    }

    //редактирование
    @GetMapping("/library/department_{id}/edit")
    public String departmentEdit(@PathVariable(value="id") long id, Model model) {
        if(!departmentRepository.existsById(id)){
            return "redirect:/library/departments";
        }
        Optional<Department> department=departmentRepository.findById(id);
        ArrayList<Department> departmentList=new ArrayList<>();
        department.ifPresent(departmentList::add);
        model.addAttribute("department",departmentList);
        return "department-Edit";
    }
    @PostMapping("/library/department_{id}/edit")
    public String departmentUpdate(@PathVariable(value="id") long id,
                                   @RequestParam String department_name,
                                   Model model) {
        Department department=departmentRepository.findById(id).orElseThrow();
        department.setDepartment_name(department_name);
        departmentRepository.save(department);
        return "redirect:/library/departments";
    }
    //удаление
    @PostMapping("/library/department_{id}/remove")
    public String departmentDelete(@PathVariable(value="id") long id,
                                   Model model) {
        Department department=departmentRepository.findById(id).orElseThrow();
        departmentRepository.delete(department);
        return "redirect:/library/departments";
    }

}
