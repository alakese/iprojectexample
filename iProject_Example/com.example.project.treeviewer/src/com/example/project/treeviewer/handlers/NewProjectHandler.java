
package com.example.project.treeviewer.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Composite;

import com.example.project.treeviewer.events.ProjectEventConstants;
import com.example.project.treeviewer.ids.IDs;
import com.example.project.treeviewer.wizards.WizardMyDialog;

/**
 * Creates a new project
 *
 *
 * @author Yasin Alakese
 * @date 02.10.2016
 */
public class NewProjectHandler {
	/**
	 * Executes the delete handler.
	 *
	 * @param parent
	 *            Necessary for layout
	 * @param broker
	 *            Used to fire an event to notify the treeviewer that a project
	 *            has been closed
	 * @throws CoreException
	 *             if project can not be created
	 */
	@Execute
	public void execute(Composite parent, IEventBroker broker) throws CoreException {
		// Get the projectname from page as reference, since only one string
		StringBuilder sb = new StringBuilder(512);
		WizardDialog wizardDialog = new WizardDialog(parent.getShell(), new WizardMyDialog(sb));

		if (wizardDialog.open() == Window.OK) {
			NewProjectHandler.createEmptyProject(sb.toString());
			System.out.println("Project created : " + sb.toString());
			// Send an event to treeviewer to update the list
			final Map<String, String> map = new HashMap<String, String>();
			map.put(ProjectEventConstants.TOPIC_TODO, ProjectEventConstants.TOPIC_TODO_NEW);
			broker.post(ProjectEventConstants.TOPIC_TODO_NEW, map);
		}
	}

	/**
	 * Creates an empty prject. For empty project there is a nature defined in
	 * extension points*
	 *
	 * @param projectName
	 *            Name of the project
	 * @return Created project
	 * @throws CoreException
	 *             if project can not be created
	 */
	private static IProject createEmptyProject(final String projectName) throws CoreException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(projectName);
		if (!project.exists()) {
			project.create(null);
		} else {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		}

		if (!project.isOpen()) {
			project.open(null);
		}

		// set the my nature
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		newNatures[natures.length] = IDs.MY_NATURE_ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);

		return project;
	}
}