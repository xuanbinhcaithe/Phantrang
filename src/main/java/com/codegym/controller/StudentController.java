package com.codegym.controller;

import com.codegym.model.Classes;
import com.codegym.model.Student;
import com.codegym.service.ClassesService;
import com.codegym.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassesService classesService;

    @ModelAttribute("classes")
    public Iterable<Classes> classes(){
        return classesService.findAll();
    }

    @GetMapping("/create-student")
   public ModelAndView showCreatForm() {
        ModelAndView modelAndView = new ModelAndView("/student/create");
        modelAndView.addObject("student",new Student());
        return modelAndView;
    }
    @PostMapping("/create-student")
    public ModelAndView createStudent(@ModelAttribute("student") Student student, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        ModelAndView modelAndView = new ModelAndView("/student/create");
        modelAndView.addObject("errormessage","Error!!!");
        return modelAndView;
    }
    studentService.save(student);
    ModelAndView modelAndView = new ModelAndView("/student/create");
    modelAndView.addObject("student", student);
    modelAndView.addObject("message","New Student create successfully!");
    return modelAndView;
    }
    @GetMapping("/students")
    public ModelAndView listStudent(@RequestParam("s") Optional<String> s, Pageable pageable) {
        Page<Student> students;
        if(s.isPresent()){
            students = studentService.findAllByNameContaining(s.get(), pageable);
        } else {
            students = studentService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/student/list");
        modelAndView.addObject("students", students);
        return modelAndView;
    }
    @GetMapping("/edit-student/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Student student = studentService.findById(id);
        if (student != null) {
            ModelAndView modelAndView = new ModelAndView("/student/edit");
            modelAndView.addObject("student",student);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-student")
    public ModelAndView updateStudent(@ModelAttribute("student") Student student,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/student/edit");
            modelAndView.addObject("errormessage","ERROR!");
            return modelAndView;
        }
        studentService.save(student);
        ModelAndView modelAndView = new ModelAndView("/student/edit");
        modelAndView.addObject("student",student);
        modelAndView.addObject("message","Student updates successfully!");
        return modelAndView;

    }

    @GetMapping("/delete-student/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Student student = studentService.findById(id);
        if (student != null) {
            ModelAndView modelAndView = new ModelAndView("/student/delete");
            modelAndView.addObject("student",student);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
        }
      @PostMapping("/delete-student")
    public String deleteStudent(@ModelAttribute("student") Student student) {
        studentService.remove(student.getId());
        return "redirect:students";


    }
}
