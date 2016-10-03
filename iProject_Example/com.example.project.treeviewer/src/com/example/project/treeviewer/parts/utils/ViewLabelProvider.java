package com.example.project.treeviewer.parts.utils;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * Standard class for treeviewer to show IProject
 *
 *
 * @author Yasin Alakese
 * @date 02.10.2016
 */
public class ViewLabelProvider implements ILabelProvider {
	private static final int IMG_PROJECT_OPENED = 0;
	private static final int IMG_PROJECT_CLOSED = 1;
	private static final int IMG_FILE = 2;
	private ImageDescriptor projectOpenImage;
	private ImageDescriptor projectCloseImage;
	private ImageDescriptor fileImage;
	private ResourceManager resourceManager;

	/**
	 *
	 * @param imageDescriptors
	 *            Images for icons
	 */
	public ViewLabelProvider(List<ImageDescriptor> imageDescriptors) {
		this.projectOpenImage = imageDescriptors.get(IMG_PROJECT_OPENED);
		this.projectCloseImage = imageDescriptors.get(IMG_PROJECT_CLOSED);
		this.fileImage = imageDescriptors.get(IMG_FILE);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IProject) {
			String text = ((IProject) element).getName();
			return text;
		}
		// if (element instanceof IFolder) {
		// String text = ((IFolder) element).getName();
		// return text;
		// }
		if (element instanceof IFile) {
			String text = ((IFile) element).getName();
			return text;
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof IProject) {
			final boolean isPrjOpen = ((IProject) element).isOpen();
			if (isPrjOpen)
				return getResourceManager().createImage(projectOpenImage);
			else
				return getResourceManager().createImage(projectCloseImage);
		}
		// if (element instanceof IFolder) {
		// return getResourceManager().createImage(folderImage);
		// }
		return getResourceManager().createImage(fileImage);
	}

	@Override
	public void dispose() {
		// garbage collect system resources
		if (resourceManager != null) {
			resourceManager.dispose();
			resourceManager = null;
		}
	}

	protected ResourceManager getResourceManager() {
		if (resourceManager == null) {
			resourceManager = new LocalResourceManager(JFaceResources.getResources());
		}
		return resourceManager;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

}
