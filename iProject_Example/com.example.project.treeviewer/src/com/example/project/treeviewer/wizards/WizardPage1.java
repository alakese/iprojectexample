package com.example.project.treeviewer.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard page which takes a projectname. If it exists, it shows an error text
 * on the page.
 *
 * @author Yasin Alakese
 * @date 02.10.2016
 */
public class WizardPage1 extends WizardPage {
	private Text locText;

	public WizardPage1() {
		super("New project");
		setTitle("Create a new project");
		setDescription("Create a new project");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Project name");

		locText = new Text(container, SWT.BORDER);
		locText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				// Get the widget whose text was modified
				locText = (Text) event.widget;
				// System.out.println(locText.getText());
				WizardMyDialog myDialog = (WizardMyDialog) getWizard();

				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
				IProject project = root.getProject(locText.getText());

				myDialog.setFinish(!project.exists());
				myDialog.getContainer().updateButtons();

				if (project.exists()) {
					setErrorMessage("Project already exists");
				} else {
					setErrorMessage(null);
				}
			}
		});
		locText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		setControl(container);
		// setPageComplete(false);
	}

	public String getText() {
		return locText.getText();
	}
}
