import { element, by, ElementFinder } from 'protractor';

export class LikeOfGiftComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-like-of-gift div table .btn-danger'));
  title = element.all(by.css('jhi-like-of-gift div h2#page-heading span')).first();

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

export class LikeOfGiftUpdatePage {
  pageTitle = element(by.id('jhi-like-of-gift-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  typeOfLikeSelect = element(by.id('field_typeOfLike'));
  giftSelect = element(by.id('field_gift'));
  authorOfLikeSelect = element(by.id('field_authorOfLike'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTypeOfLikeSelect(typeOfLike: string): Promise<void> {
    await this.typeOfLikeSelect.sendKeys(typeOfLike);
  }

  async getTypeOfLikeSelect(): Promise<string> {
    return await this.typeOfLikeSelect.element(by.css('option:checked')).getText();
  }

  async typeOfLikeSelectLastOption(): Promise<void> {
    await this.typeOfLikeSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

  async authorOfLikeSelectLastOption(): Promise<void> {
    await this.authorOfLikeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorOfLikeSelectOption(option: string): Promise<void> {
    await this.authorOfLikeSelect.sendKeys(option);
  }

  getAuthorOfLikeSelect(): ElementFinder {
    return this.authorOfLikeSelect;
  }

  async getAuthorOfLikeSelectedOption(): Promise<string> {
    return await this.authorOfLikeSelect.element(by.css('option:checked')).getText();
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

export class LikeOfGiftDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-likeOfGift-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-likeOfGift'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
