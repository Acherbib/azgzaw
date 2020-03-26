import { element, by, ElementFinder } from 'protractor';

export class CommentOfCitationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-comment-of-citation div table .btn-danger'));
  title = element.all(by.css('jhi-comment-of-citation div h2#page-heading span')).first();

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

export class CommentOfCitationUpdatePage {
  pageTitle = element(by.id('jhi-comment-of-citation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  bodyCommentInput = element(by.id('field_bodyComment'));
  commentOfCitationImageInput = element(by.id('file_commentOfCitationImage'));
  dateOfCreationInput = element(by.id('field_dateOfCreation'));
  citationSelect = element(by.id('field_citation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setBodyCommentInput(bodyComment: string): Promise<void> {
    await this.bodyCommentInput.sendKeys(bodyComment);
  }

  async getBodyCommentInput(): Promise<string> {
    return await this.bodyCommentInput.getAttribute('value');
  }

  async setCommentOfCitationImageInput(commentOfCitationImage: string): Promise<void> {
    await this.commentOfCitationImageInput.sendKeys(commentOfCitationImage);
  }

  async getCommentOfCitationImageInput(): Promise<string> {
    return await this.commentOfCitationImageInput.getAttribute('value');
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

export class CommentOfCitationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-commentOfCitation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-commentOfCitation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
