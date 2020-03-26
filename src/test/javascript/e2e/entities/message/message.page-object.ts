import { element, by, ElementFinder } from 'protractor';

export class MessageComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-message div table .btn-danger'));
  title = element.all(by.css('jhi-message div h2#page-heading span')).first();

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

export class MessageUpdatePage {
  pageTitle = element(by.id('jhi-message-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  contentInput = element(by.id('field_content'));
  dateOfSendInput = element(by.id('field_dateOfSend'));
  mediaInput = element(by.id('file_media'));
  seenInput = element(by.id('field_seen'));
  authorOfMessageSelect = element(by.id('field_authorOfMessage'));
  receiverOfMessageSelect = element(by.id('field_receiverOfMessage'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setContentInput(content: string): Promise<void> {
    await this.contentInput.sendKeys(content);
  }

  async getContentInput(): Promise<string> {
    return await this.contentInput.getAttribute('value');
  }

  async setDateOfSendInput(dateOfSend: string): Promise<void> {
    await this.dateOfSendInput.sendKeys(dateOfSend);
  }

  async getDateOfSendInput(): Promise<string> {
    return await this.dateOfSendInput.getAttribute('value');
  }

  async setMediaInput(media: string): Promise<void> {
    await this.mediaInput.sendKeys(media);
  }

  async getMediaInput(): Promise<string> {
    return await this.mediaInput.getAttribute('value');
  }

  getSeenInput(): ElementFinder {
    return this.seenInput;
  }

  async authorOfMessageSelectLastOption(): Promise<void> {
    await this.authorOfMessageSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorOfMessageSelectOption(option: string): Promise<void> {
    await this.authorOfMessageSelect.sendKeys(option);
  }

  getAuthorOfMessageSelect(): ElementFinder {
    return this.authorOfMessageSelect;
  }

  async getAuthorOfMessageSelectedOption(): Promise<string> {
    return await this.authorOfMessageSelect.element(by.css('option:checked')).getText();
  }

  async receiverOfMessageSelectLastOption(): Promise<void> {
    await this.receiverOfMessageSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async receiverOfMessageSelectOption(option: string): Promise<void> {
    await this.receiverOfMessageSelect.sendKeys(option);
  }

  getReceiverOfMessageSelect(): ElementFinder {
    return this.receiverOfMessageSelect;
  }

  async getReceiverOfMessageSelectedOption(): Promise<string> {
    return await this.receiverOfMessageSelect.element(by.css('option:checked')).getText();
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

export class MessageDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-message-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-message'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
