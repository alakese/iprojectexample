
package com.example.project.treeviewer.handlers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;

import com.example.project.treeviewer.events.ProjectEventConstants;

/**
 * Opens the project
 *
 *
 * @author Yasin Alakese
 * @date 02.10.2016
 */
public class OpenProjectHandler {
	/**
	 * Executes the delete handler.
	 *
	 * @param project
	 *            Selected project. Must be registered in TodoTreePart. See
	 *            ESelectionService service in TodoTreePart.
	 * @param broker
	 *            Used to fire an event to notify the treeviewer that a project
	 *            has been closed
	 * @throws CoreException
	 *             if project can not be opened
	 */
	@Execute
	public void execute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) IProject project, IEventBroker broker)
			throws CoreException {
		System.out.println("Opening the project " + project.getName());
		project.open(null);
		// Send an event to treeviewer to update the list
		final Map<String, String> map = new HashMap<String, String>();
		map.put(ProjectEventConstants.TOPIC_TODO, ProjectEventConstants.TOPIC_TODO_OPEN);
		broker.post(ProjectEventConstants.TOPIC_TODO_OPEN, map);

		System.out.println("Project is opened.");
	}

}