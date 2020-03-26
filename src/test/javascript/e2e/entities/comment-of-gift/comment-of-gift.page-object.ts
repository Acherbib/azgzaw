import { element, by, ElementFinder } from 'protractor';

export class CommentOfGiftComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-comment-of-gift div table .btn-danger'));
  title = element.all(by.css('jhi-comment-of-gift div h2#page-heading span')).first();

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

export class CommentOfGiftUpdatePage {
  pageTitle = element(by.id('jhi-comment-of-gift-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  bodyCommentInput = element(by.id('field_bodyComment'));
  commentOfGiftImageInput = element(by.id('file_commentOfGiftImage'));
  dateOfCreationInput = element(by.id('field_dateOfCreation'));
  giftSelect = element(by.id('field_gift'));
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

  async setCommentOfGiftImageInput(commentOfGiftImage: string): Promise<void> {
    await this.commentOfGiftImageInput.sendKeys(commentOfGiftImage);
  }

  async getCommentOfGiftImageInput(): Promise<string> {
    return await this.commentOfGiftImageInput.getAttribute('value');
  }

  async setDateOfCreationInput(dateOfCreation: string): Promise<void> {
    await this.dateOfCreationInput.sendKeys(dateOfCreation);
  }

  async getDateOfCreationInput(): Promise<string> {
    return await this.dateOfCreationInput.getAttribute('value');
  }

  async giftSelectLastOption(): Promise<void> {
    await this.giftSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async giftSelectOption(option: string): Promise<void> {
    await this.giftSelect.sendKeys(option);
  }

  getGiftSelect(): ElementFinder {
    return this.giftSelect;
  }

  async getGiftSelectedOption(): Promise<string> {
    return await this.giftSelect.element(by.css('option:checked')).getText();
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

export class CommentOfGiftDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-commentOfGift-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-commentOfGift'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
