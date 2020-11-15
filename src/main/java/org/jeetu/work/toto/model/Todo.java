package org.jeetu.work.toto.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todo")
public class Todo {
	@Id
	private String id;
	private String task;
	private boolean completed;
	private Date added;
	private Date finished;

	public Todo() {
	}
	
	public Todo(String id, String task, boolean completed, Date added, Date finished) {
		this.id = id;
		this.task = task;
		this.completed = completed;
		this.added = added;
		this.finished = finished;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", task=" + task + ", completed=" + completed + ", added=" + added + ", finished="
				+ finished + "]";
	}
}
