package com.example.project.treeviewer.wizards;

import javax.inject.Inject;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

/**
 * Opens a wizard dialog for projectname. There is only one page for now.
 *
 *
 * @author Yasin Alakese
 * @date 02.10.2016
 */
public class WizardMyDialog extends Wizard {
	private boolean finish = false;
	private WizardPage1 page;
	private StringBuilder projectName;

	@Inject
	public WizardMyDialog(StringBuilder projectName) {
		this.setWindowTitle("New project");
		this.projectName = projectName;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	@Override
	public void addPages() {
		page = new WizardPage1();
		this.addPage(page);
	}

	@Override
	public boolean performFinish() {
		projectName.append(page.getText());
		return true;
	}

	@Override
	public boolean canFinish() {
		return this.finish;
	}

	@Override
	public IWizardPage getNextPage(IWizardPage page) {
		return super.getNextPage(page);
	}
}
