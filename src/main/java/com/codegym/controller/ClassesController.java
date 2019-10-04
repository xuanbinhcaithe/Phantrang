package com.codegym.controller;

import com.codegym.model.Classes;
import com.codegym.model.Student;
import com.codegym.service.ClassesService;
import com.codegym.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClassesController {
    @Autowired
    private ClassesService classesService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/view-classes/{id}")
    public ModelAndView viewClass(@PathVariable Long id) {
        Classes classes = classesService.findById(id);
        if (classes == null) {
            return new ModelAndView("/error.404");
        }
        Iterable<Student> students = studentService.findAllByClass(classes);
        ModelAndView modelAndView = new ModelAndView("/classes/view");
        modelAndView.addObject("classes",classes);
        modelAndView.addObject("students",students);
        return modelAndView;
    }

    @GetMapping("/classes")
    public ModelAndView listClasses(){
        Iterable<Classes> classes = classesService.findAll();
        ModelAndView modelAndView = new ModelAndView("/classes/list");
        modelAndView.addObject("class",classes);
        return modelAndView;

    }
    @GetMapping("/create-classes")
    public ModelAndView showCreateClass() {
        ModelAndView modelAndView = new ModelAndView("/classes/create");
        modelAndView.addObject("classes",new Classes());
        return modelAndView;
    }
    @PostMapping("/create-classes")
    public ModelAndView createClass(@ModelAttribute("classes") Classes classes) {
        classesService.save(classes);
        ModelAndView modelAndView = new ModelAndView("/classes/create");
        modelAndView.addObject("classes",new Classes());
        modelAndView.addObject("message","New class created successfully!");
        return modelAndView;
    }
    @GetMapping("/edit-classes/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Classes classes = classesService.findById(id);
        if (classes != null) {
            ModelAndView modelAndView = new ModelAndView("/classes/edit");
            modelAndView.addObject("classes",classes);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-classes")
    public ModelAndView updateClass(@ModelAttribute("classes") Classes classes) {
        classesService.save(classes);
        ModelAndView modelAndView = new ModelAndView("/classes/edit");
        modelAndView.addObject("classes",classes);
        modelAndView.addObject("message","updated class successfully!");
        return modelAndView;
    }
    @GetMapping("/delete-classes/{id}")
    public ModelAndView showDeleteClass(@PathVariable Long id) {
        Classes classes = classesService.findById(id);
        if (classes != null) {
            ModelAndView modelAndView = new ModelAndView("/classes/delete");
            modelAndView.addObject("classes",classes);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/delete-classes")
    public String deleteClass(@ModelAttribute("classes") Classes classes) {
    classesService.remove(classes.getId());
    return "redirect:classes";
    }


}
