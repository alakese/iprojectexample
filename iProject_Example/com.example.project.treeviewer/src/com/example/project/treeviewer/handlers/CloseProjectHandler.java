
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
 * Nature to create an empty project
 *
 *
 * @author Yasin Alakese
 * @date 02.10.2016
 */
public class CloseProjectHandler {
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
	 *             if project can not be closed
	 */
	@Execute
	public void execute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) IProject project, IEventBroker broker)
			throws CoreException {
		System.out.println("closing project " + project.getName());
		project.close(null);

		// Send an event to treeviewer to update the list
		final Map<String, String> map = new HashMap<String, String>();
		map.put(ProjectEventConstants.TOPIC_TODO, ProjectEventConstants.TOPIC_TODO_CLOSE);
		broker.post(ProjectEventConstants.TOPIC_TODO_CLOSE, map);

		System.out.println("Project is closed.");
		// TODO change pic

		// if (selection != null && !selection.isEmpty()) {
		// final Object project = selection.getFirstElement();
		// if (project instanceof IProject) {
		// System.out.println(((IProject) project).getName());
		// }
		//
		// // /* test : remove which exists */
		// // IProject[] projects =
		// // ResourcesPlugin.getWorkspace().getRoot().getProjects();
		// // for (IProject iProject : projects) {
		// // System.out.println(iProject.getName());
		// // // if (iProject.getName().startsWith("Test")) {
		// // // iProject.delete(true, null);
		// // // continue;
		// // // }
		// // }
		// }
	}
}