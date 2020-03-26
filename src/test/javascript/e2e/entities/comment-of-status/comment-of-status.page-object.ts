import { element, by, ElementFinder } from 'protractor';

export class CommentOfStatusComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-comment-of-status div table .btn-danger'));
  title = element.all(by.css('jhi-comment-of-status div h2#page-heading span')).first();

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

export class CommentOfStatusUpdatePage {
  pageTitle = element(by.id('jhi-comment-of-status-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  bodyCommentInput = element(by.id('field_bodyComment'));
  commentOfStatusImageInput = element(by.id('file_commentOfStatusImage'));
  mediaTypeInput = element(by.id('field_mediaType'));
  dateOfCreationInput = element(by.id('field_dateOfCreation'));
  statusSelect = element(by.id('field_status'));
  authorSelect = element(by.id('field_author'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setBodyCommentInput(bodyComment: string): Promise<void> {
    await this.bodyCommentInput.sendKeys(bodyComment);
  }

  async getBodyCommentInput(): Promise<string> {
    return await this.bodyCommentInput.getAttribute('value');
  }

  async setCommentOfStatusImageInput(commentOfStatusImage: string): Promise<void> {
    await this.commentOfStatusImageInput.sendKeys(commentOfStatusImage);
  }

  async getCommentOfStatusImageInput(): Promise<string> {
    return await this.commentOfStatusImageInput.getAttribute('value');
  }

  async setMediaTypeInput(mediaType: string): Promise<void> {
    await this.mediaTypeInput.sendKeys(mediaType);
  }

  async getMediaTypeInput(): Promise<string> {
    return await this.mediaTypeInput.getAttribute('value');
  }

  async setDateOfCreationInput(dateOfCreation: string): Promise<void> {
    await this.dateOfCreationInput.sendKeys(dateOfCreation);
  }

  async getDateOfCreationInput(): Promise<string> {
    return await this.dateOfCreationInput.getAttribute('value');
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async statusSelectOption(option: string): Promise<void> {
    await this.statusSelect.sendKeys(option);
  }

  getStatusSelect(): ElementFinder {
    return this.statusSelect;
  }

  async getStatusSelectedOption(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async authorSelectLastOption(): Promise<void> {
    await this.authorSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorSelectOption(option: string): Promise<void> {
    await this.authorSelect.sendKeys(option);
  }

  getAuthorSelect(): ElementFinder {
    return this.authorSelect;
  }

  async getAuthorSelectedOption(): Promise<string> {
    return await this.authorSelect.element(by.css('option:checked')).getText();
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

export class CommentOfStatusDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-commentOfStatus-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-commentOfStatus'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
