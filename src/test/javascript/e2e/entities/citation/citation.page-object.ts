import { element, by, ElementFinder } from 'protractor';

export class CitationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-citation div table .btn-danger'));
  title = element.all(by.css('jhi-citation div h2#page-heading span')).first();

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

export class CitationUpdatePage {
  pageTitle = element(by.id('jhi-citation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  greenCitationMediaInput = element(by.id('file_greenCitationMedia'));
  dateOfCreationInput = element(by.id('field_dateOfCreation'));
  backgroundColorInput = element(by.id('field_backgroundColor'));
  greenCitationPrivacySelect = element(by.id('field_greenCitationPrivacy'));
  authorOfCitationSelect = element(by.id('field_authorOfCitation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setGreenCitationMediaInput(greenCitationMedia: string): Promise<void> {
    await this.greenCitationMediaInput.sendKeys(greenCitationMedia);
  }

  async getGreenCitationMediaInput(): Promise<string> {
    return await this.greenCitationMediaInput.getAttribute('value');
  }

  async setDateOfCreationInput(dateOfCreation: string): Promise<void> {
    await this.dateOfCreationInput.sendKeys(dateOfCreation);
  }

  async getDateOfCreationInput(): Promise<string> {
    return await this.dateOfCreationInput.getAttribute('value');
  }

  async setBackgroundColorInput(backgroundColor: string): Promise<void> {
    await this.backgroundColorInput.sendKeys(backgroundColor);
  }

  async getBackgroundColorInput(): Promise<string> {
    return await this.backgroundColorInput.getAttribute('value');
  }

  async setGreenCitationPrivacySelect(greenCitationPrivacy: string): Promise<void> {
    await this.greenCitationPrivacySelect.sendKeys(greenCitationPrivacy);
  }

  async getGreenCitationPrivacySelect(): Promise<string> {
    return await this.greenCitationPrivacySelect.element(by.css('option:checked')).getText();
  }

  async greenCitationPrivacySelectLastOption(): Promise<void> {
    await this.greenCitationPrivacySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorOfCitationSelectLastOption(): Promise<void> {
    await this.authorOfCitationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorOfCitationSelectOption(option: string): Promise<void> {
    await this.authorOfCitationSelect.sendKeys(option);
  }

  getAuthorOfCitationSelect(): ElementFinder {
    return this.authorOfCitationSelect;
  }

  async getAuthorOfCitationSelectedOption(): Promise<string> {
    return await this.authorOfCitationSelect.element(by.css('option:checked')).getText();
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

export class CitationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-citation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-citation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
