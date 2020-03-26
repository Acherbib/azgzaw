import { element, by, ElementFinder } from 'protractor';

export class GiftComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-gift div table .btn-danger'));
  title = element.all(by.css('jhi-gift div h2#page-heading span')).first();

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

export class GiftUpdatePage {
  pageTitle = element(by.id('jhi-gift-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  titleInput = element(by.id('field_title'));
  descriptionInput = element(by.id('field_description'));
  isAGiftInput = element(by.id('field_isAGift'));
  reservedInput = element(by.id('field_reserved'));
  cityInput = element(by.id('field_city'));
  countryInput = element(by.id('field_country'));
  isAuctionInput = element(by.id('field_isAuction'));
  startPriceInput = element(by.id('field_startPrice'));
  backgroundColorInput = element(by.id('field_backgroundColor'));
  numberLikesOfGiftsInput = element(by.id('field_numberLikesOfGifts'));
  numberSeenOfGiftsInput = element(by.id('field_numberSeenOfGifts'));
  dateOfCreationInput = element(by.id('field_dateOfCreation'));
  imageInput = element(by.id('file_image'));
  authorOfGiftSelect = element(by.id('field_authorOfGift'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTitleInput(title: string): Promise<void> {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput(): Promise<string> {
    return await this.titleInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  getIsAGiftInput(): ElementFinder {
    return this.isAGiftInput;
  }
  getReservedInput(): ElementFinder {
    return this.reservedInput;
  }
  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setCountryInput(country: string): Promise<void> {
    await this.countryInput.sendKeys(country);
  }

  async getCountryInput(): Promise<string> {
    return await this.countryInput.getAttribute('value');
  }

  getIsAuctionInput(): ElementFinder {
    return this.isAuctionInput;
  }
  async setStartPriceInput(startPrice: string): Promise<void> {
    await this.startPriceInput.sendKeys(startPrice);
  }

  async getStartPriceInput(): Promise<string> {
    return await this.startPriceInput.getAttribute('value');
  }

  async setBackgroundColorInput(backgroundColor: string): Promise<void> {
    await this.backgroundColorInput.sendKeys(backgroundColor);
  }

  async getBackgroundColorInput(): Promise<string> {
    return await this.backgroundColorInput.getAttribute('value');
  }

  async setNumberLikesOfGiftsInput(numberLikesOfGifts: string): Promise<void> {
    await this.numberLikesOfGiftsInput.sendKeys(numberLikesOfGifts);
  }

  async getNumberLikesOfGiftsInput(): Promise<string> {
    return await this.numberLikesOfGiftsInput.getAttribute('value');
  }

  async setNumberSeenOfGiftsInput(numberSeenOfGifts: string): Promise<void> {
    await this.numberSeenOfGiftsInput.sendKeys(numberSeenOfGifts);
  }

  async getNumberSeenOfGiftsInput(): Promise<string> {
    return await this.numberSeenOfGiftsInput.getAttribute('value');
  }

  async setDateOfCreationInput(dateOfCreation: string): Promise<void> {
    await this.dateOfCreationInput.sendKeys(dateOfCreation);
  }

  async getDateOfCreationInput(): Promise<string> {
    return await this.dateOfCreationInput.getAttribute('value');
  }

  async setImageInput(image: string): Promise<void> {
    await this.imageInput.sendKeys(image);
  }

  async getImageInput(): Promise<string> {
    return await this.imageInput.getAttribute('value');
  }

  async authorOfGiftSelectLastOption(): Promise<void> {
    await this.authorOfGiftSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async authorOfGiftSelectOption(option: string): Promise<void> {
    await this.authorOfGiftSelect.sendKeys(option);
  }

  getAuthorOfGiftSelect(): ElementFinder {
    return this.authorOfGiftSelect;
  }

  async getAuthorOfGiftSelectedOption(): Promise<string> {
    return await this.authorOfGiftSelect.element(by.css('option:checked')).getText();
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

export class GiftDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-gift-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-gift'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
