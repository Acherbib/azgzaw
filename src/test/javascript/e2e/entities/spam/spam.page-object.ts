import { element, by, ElementFinder } from 'protractor';

export class SpamComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-spam div table .btn-danger'));
  title = element.all(by.css('jhi-spam div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class SpamUpdatePage {
  pageTitle = element(by.id('jhi-spam-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  causeInput = element(by.id('field_cause'));
  dateOfCreationInput = element(by.id('field_dateOfCreation'));
  citationSelect = element(by.id('field_citation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCauseInput(cause: string): Promise<void> {
    await this.causeInput.sendKeys(cause);
  }

  async getCauseInput(): Promise<string> {
    return await this.causeInput.getAttribute('value');
  }

  async setDateOfCreationInput(dateOfCreation: string): Promise<void> {
    await this.dateOfCreationInput.sendKeys(dateOfCreation);
  }

  async getDateOfCreationInput(): Promise<string> {
    return await this.dateOfCreationInput.getAttribute('value');
  }

  async citationSelectLastOption(): Promise<void> {
    await this.citationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async citationSelectOption(option: string): Promise<void> {
    await this.citationSelect.sendKeys(option);
  }

  getCitationSelect(): ElementFinder {
    return this.citationSelect;
  }

  async getCitationSelectedOption(): Promise<string> {
    return await this.citationSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class SpamDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-spam-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-spam'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
