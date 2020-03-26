import { element, by, ElementFinder } from 'protractor';

export class StatusComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-status div table .btn-danger'));
  title = element.all(by.css('jhi-status div h2#page-heading span')).first();

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

export class StatusUpdatePage {
  pageTitle = element(by.id('jhi-status-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  statusMediaInput = element(by.id('file_statusMedia'));
  numberSeenOfStatusInput = element(by.id('field_numberSeenOfStatus'));
  statusPrivacySelect = element(by.id('field_statusPrivacy'));
  authorOfStatusSelect = element(by.id('field_authorOfStatus'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setStatusMediaInput(statusMedia: string): Promise<void> {
    await this.statusMediaInput.sendKeys(statusMedia);
  }

  async getStatusMediaInput(): Promise<string> {
    return await this.statusMediaInput.getAttribute('value');
  }

  async setNumberSeenOfStatusInput(numberSeenOfStatus: string): Promise<void> {
    await this.numberSeenOfStatusInput.sendKeys(numberSeenOfStatus);
  }

  async getNumberSeenOfStatusInput(): Promise<string> {
    return await this.numberSeenOfStatusInput.getAttribute('value');
  }

  async setStatusPrivacySelect(statusPrivacy: string): Promise<void> {
    await this.statusPrivacySelect.sendKeys(statusPrivacy);
  }

  async getStatusPrivacySelect(): Promise<string> {
    return await this.statusPrivacySelect.element(by.css('option:checked')).getText();
  }

  async statusPrivacySelectLastOption(): Promise<void> {
    await this.statusPrivacySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorOfStatusSelectLastOption(): Promise<void> {
    await this.authorOfStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorOfStatusSelectOption(option: string): Promise<void> {
    await this.authorOfStatusSelect.sendKeys(option);
  }

  getAuthorOfStatusSelect(): ElementFinder {
    return this.authorOfStatusSelect;
  }

  async getAuthorOfStatusSelectedOption(): Promise<string> {
    return await this.authorOfStatusSelect.element(by.css('option:checked')).getText();
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

export class StatusDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-status-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-status'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
