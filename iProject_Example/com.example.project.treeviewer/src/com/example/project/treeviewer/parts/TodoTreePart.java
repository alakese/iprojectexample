
package com.example.project.treeviewer.parts;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.example.project.treeviewer.events.ProjectEventConstants;
import com.example.project.treeviewer.ids.IDs;
import com.example.project.treeviewer.parts.utils.ViewContentProvider;
import com.example.project.treeviewer.parts.utils.ViewLabelProvider;

/**
 * Part-Class with treeviewer
 *
 *
 * @author Yasin Alakese
 * @date 02.10.2016
 */
public class TodoTreePart {
	private TreeViewer viewer;

	@Inject
	ESelectionService service;

	/**
	 *
	 * @param parent
	 *            For layout
	 * @param menuService
	 *            Contextmenus can be registered through menuService
	 *
	 * @author Yasin Alakese
	 * @date 02.10.2016
	 */
	@PostConstruct
	public void createControls(Composite parent, EMenuService menuService) {
		// create the icons which will be used in treeviewer
		final List<ImageDescriptor> images = new ArrayList<>();
		images.add(createImage("icons/openedprj.gif"));
		images.add(createImage("icons/closedprj.gif"));
		images.add(createImage("icons/table.gif"));

		parent.setLayout(new GridLayout(1, false));

		viewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		Tree tree = viewer.getTree();
		// If the mouse will be clicked, then open a popup menu
		tree.addListener(SWT.MouseDown, new Listener() {
			@Override
			public void handleEvent(Event event) {
				// Decide, which contextmenu will be shown
				// e.g. if Project is clicked, then show for example "Close
				// project" else "Open project"
				Point point = new Point(event.x, event.y);
				TreeItem item = tree.getItem(point);
				if (item != null) {
					Object data = item.getData();
					if (data instanceof IProject) {
						if (((IProject) data).isOpen()) {
							menuService.registerContextMenu(TodoTreePart.this.viewer.getControl(),
									IDs.POPUPMENUID_PROJECT_CLICKED_TO_CLOSEPROJECT);
						} else {
							menuService.registerContextMenu(TodoTreePart.this.viewer.getControl(),
									IDs.POPUPMENUID_PROJECT_CLICKED_TO_OPENPROJECT);
						}
						IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
						service.setSelection(selection.getFirstElement());
					}
					if (data instanceof IFile)
						menuService.registerContextMenu(TodoTreePart.this.viewer.getControl(),
								IDs.POPUPMENUID_FILE_CLICKED);
				} else {
					menuService.registerContextMenu(TodoTreePart.this.viewer.getControl(),
							IDs.POPUPMENUID_TABLE_CLICKED);
				}
			}
		});
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		// Add treeviewer stuff
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider(images));
		// Set the projects from the workspace
		viewer.setInput(ResourcesPlugin.getWorkspace());
	}

	/*
	 * Receive the event : events will be sent from "New project",
	 * "Delete project", "Close project" and "Open project" This is the auto
	 * communication between the treeviewer and the handlers
	 */
	@Inject
	@Optional
	private void subscribeTopicTodoAllTopics(
			@UIEventTopic(ProjectEventConstants.TOPIC_TODO_ALLTOPICS) Map<String, String> event) {
		if (this.viewer != null) {
			viewer.setInput(ResourcesPlugin.getWorkspace());
			viewer.refresh();
		}
	}

	/**
	 * Create the images for icons here
	 *
	 * @param path
	 *            Pfad zu den Icons. Default it is under /icons
	 * @return An ImageDescriptor
	 */
	private ImageDescriptor createImage(final String path) {
		Bundle bundle = FrameworkUtil.getBundle(ViewLabelProvider.class);
		URL url = FileLocator.find(bundle, new Path(path), null);
		ImageDescriptor folderImage = ImageDescriptor.createFromURL(url);
		return folderImage;
	}

	/*
	 * This is necessary
	 */
	@Focus
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}