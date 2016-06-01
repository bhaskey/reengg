package com.bhaskar.taskmonks.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bhaskar.taskmonks.domain.Category;
import com.bhaskar.taskmonks.domain.Task;
import com.bhaskar.taskmonks.service.CategoryServiceInterface;
import com.bhaskar.taskmonks.service.TaskServiceInterface;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	protected CategoryServiceInterface categoryService;
	
	@Autowired
	protected TaskServiceInterface taskService;
	
	@RequestMapping(value = {"/"}, method = RequestMethod.GET)
	public String adminHome(Model model){
		return "admin/index";
	}
	
	@RequestMapping(value = {"/category","/savecat"}, method = RequestMethod.GET)
	public String saveCatPage(Model model) {
		model.addAttribute("category", new Category());
		model.addAttribute("icons", getIcons());
		model.addAttribute("allCategories", (ArrayList<Category>)categoryService.getAllCategories());
		return "admin/saveCatPage";
	}
	
	@RequestMapping(value = {"/category/save"}, method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") Category category,
			final RedirectAttributes redirectAttributes) {
		
		if(categoryService.saveCategory(category)!=null) {
			redirectAttributes.addFlashAttribute("saveCategory", "success");
		} else {
			redirectAttributes.addFlashAttribute("saveCategory", "unsuccess");
		}
		
		return "redirect:/admin/savecat";
	}
	
	@RequestMapping(value = "/category/{operation}/{catId}", method = RequestMethod.GET)
	public String editRemoveCategory(@PathVariable("operation") String operation,
			@PathVariable("catId") Long catId, final RedirectAttributes redirectAttributes,
			Model model) {
		if(operation.equals("delete")) {
			if(categoryService.deleteCategory(catId)) {
				redirectAttributes.addFlashAttribute("deletion", "success");
			} else {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
			}
		} else if(operation.equals("edit")){
		  Category editCategory = categoryService.findCategory(catId);
		  if(editCategory!=null) {
			   model.addAttribute("icons", getIcons());
		       model.addAttribute("editCategory", editCategory);
		       return "admin/editCatPage";
		  } else {
			  redirectAttributes.addFlashAttribute("status","notfound");
		  }
		}
		
		return "redirect:/admin/savecat";
	}
	
	@RequestMapping(value = "/category/update", method = RequestMethod.POST)
	public String updateCategory(@ModelAttribute("editCategory") Category editCategory,
			final RedirectAttributes redirectAttributes) {
		if(categoryService.editCategory(editCategory)!=null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
		return "redirect:/admin/savecat";
	}
	
	
	/*Admin Services request-mappings*/
	
	@RequestMapping(value = {"/service","/saveservice"}, method = RequestMethod.GET)
	public String saveTaskPage(Model model) {
		model.addAttribute("task", new Task());
		model.addAttribute("icons", getIcons());
		model.addAttribute("allTasks", (ArrayList<Task>)taskService.getAllTasks());
		return "admin/saveTaskPage";
	}
	
	@RequestMapping(value = {"/service/save"}, method = RequestMethod.POST)
	public String saveTask(@ModelAttribute("task") Task task,
			final RedirectAttributes redirectAttributes) {
		
		if(taskService.saveTask(task)!=null) {
			redirectAttributes.addFlashAttribute("saveTask", "success");
		} else {
			redirectAttributes.addFlashAttribute("saveTask", "unsuccess");
		}
		
		return "redirect:/admin/saveservice";
	}
	
	@RequestMapping(value = "/service/{operation}/{taskId}", method = RequestMethod.GET)
	public String editRemoveTask(@PathVariable("operation") String operation,
			@PathVariable("taskId") Long taskId, final RedirectAttributes redirectAttributes,
			Model model) {
		if(operation.equals("delete")) {
			if(taskService.deleteTask(taskId)) {
				redirectAttributes.addFlashAttribute("deletion", "success");
			} else {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
			}
		} else if(operation.equals("edit")){
		  Task editTask = taskService.findTask(taskId);
		  if(editTask!=null) {
			   model.addAttribute("icons", getIcons());
		       model.addAttribute("editTask", editTask);
		       return "admin/editTaskPage";
		  } else {
			  redirectAttributes.addFlashAttribute("status","notfound");
		  }
		}
		
		return "redirect:/admin/saveservice";
	}
	
	@RequestMapping(value = "/service/update", method = RequestMethod.POST)
	public String updateTask(@ModelAttribute("editTask") Task editTask,
			final RedirectAttributes redirectAttributes) {
		if(taskService.editTask(editTask)!=null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
		return "redirect:/admin/saveservice";
	}
	
	public static List<String> getIcons(){
		return Arrays.asList(new String[]{
											"glyphicon glyphicon-cloud",
											"glyphicon glyphicon-envelope",
											"glyphicon glyphicon-glass",
											"glyphicon glyphicon-music",
											"glyphicon glyphicon-search",
											"glyphicon glyphicon-heart",
											"glyphicon glyphicon-film",
											"glyphicon glyphicon-road",
											"glyphicon glyphicon-camera",
											"glyphicon glyphicon-plane",
											"glyphicon glyphicon-briefcase",
											"glyphicon glyphicon-education",
											"glyphicon glyphicon-globe",
											"glyphicon glyphicon-calendar"});
	}

}
