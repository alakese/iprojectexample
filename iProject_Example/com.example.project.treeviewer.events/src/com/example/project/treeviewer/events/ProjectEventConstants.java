package com.example.project.treeviewer.events;

/**
 * Events. See Vogella.
 *
 * @author Yasin Alakese
 *
 */
public interface ProjectEventConstants {
	// Vogella-Info : topic identifier for all todo topics
	String TOPIC_TODO = "TOPIC_TODOS";

	// Vogella-Info : this key can only be used for event registration, you
	// cannot
	// send out generic events
	String TOPIC_TODO_ALLTOPICS = "TOPIC_TODOS/*";
	String TOPIC_TODO_NEW = "TOPIC_TODOS/TODO/NEW";
	String TOPIC_TODO_DELETE = "TOPIC_TODOS/TODO/DELETED";
	String TOPIC_TODO_OPEN = "TOPIC_TODOS/TODO/OPEN";
	String TOPIC_TODO_CLOSE = "TOPIC_TODOS/TODO/CLOSE";
}
