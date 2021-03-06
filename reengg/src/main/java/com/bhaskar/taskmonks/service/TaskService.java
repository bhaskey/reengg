package com.bhaskar.taskmonks.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bhaskar.taskmonks.domain.Task;
import com.bhaskar.taskmonks.repository.TaskRepository;

@Service
@Transactional
public class TaskService implements TaskServiceInterface{
	
	@Autowired
	protected TaskRepository taskRepository;

	@Override
	public Task saveTask(Task task) {
		// Creating task url to make a representational url for task
		String taskUri = task.getTaskName().replaceAll("[,|*|;|'|#]+","")
										.trim().replaceAll(" +", " ")
										.replace(' ', '-')
										.toLowerCase();
		if (taskUri.length() > 50) {
			taskUri = taskUri.substring(0, 49);
		}
		task.setTaskUri(taskUri);
		
		return taskRepository.save(task);
	}

	@Override
	public Boolean deleteTask(Long taskId) {
		Task temp = taskRepository.findOne(taskId);
		if(temp!=null){
			taskRepository.delete(temp);
			return true;
		}
		return false;
	}

	@Override
	public Task editTask(Task task) {
		// TODO Auto-generated method stub
		if(task.getTaskUri()==null){
			String taskUri = task.getTaskName().replaceAll("[,|*|;|'|#]+","")
												.trim().replaceAll(" +", " ")
												.replace(' ', '-')
												.toLowerCase();	
			if (taskUri.length() > 50) {
				taskUri = taskUri.substring(0, 49);
			}
			task.setTaskUri(taskUri);
		}
		return taskRepository.save(task);
	}

	@Override
	public Task findTask(Long taskId) {
		// TODO Auto-generated method stub
		return taskRepository.findOne(taskId);
	}

	@Override
	public Collection<Task> getAllTasks() {
		Iterable<Task> itr = taskRepository.findAll();
		return (Collection<Task>) itr;
	}

	@Override
	public int countTasks() {
		// Very bad way but will change it later
		return this.getAllTasks().size();
	}

	@Override
	public Collection<Task> findAllTasksByCatUri(String catUri) {
		Iterable<Task> itr = taskRepository.findAllTasksByCatUri(catUri);
		return (Collection<Task>) itr;
	}

	@Override
	public Task findTaskByTaskUri(String taskUri) {
		// TODO Auto-generated method stub
		return taskRepository.findTaskByTaskUri(taskUri);
	}
}
