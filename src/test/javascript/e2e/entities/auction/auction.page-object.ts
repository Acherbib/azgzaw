import { element, by, ElementFinder } from 'protractor';

export class AuctionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-auction div table .btn-danger'));
  title = element.all(by.css('jhi-auction div h2#page-heading span')).first();

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

export class AuctionUpdatePage {
  pageTitle = element(by.id('jhi-auction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  cardTypeSelect = element(by.id('field_cardType'));
  cardNumberInput = element(by.id('field_cardNumber'));
  endDateInput = element(by.id('field_endDate'));
  cvcInput = element(by.id('field_cvc'));
  priceOfferInput = element(by.id('field_priceOffer'));
  giftSelect = element(by.id('field_gift'));
  authorSelect = element(by.id('field_author'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCardTypeSelect(cardType: string): Promise<void> {
    await this.cardTypeSelect.sendKeys(cardType);
  }

  async getCardTypeSelect(): Promise<string> {
    return await this.cardTypeSelect.element(by.css('option:checked')).getText();
  }

  async cardTypeSelectLastOption(): Promise<void> {
    await this.cardTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setCardNumberInput(cardNumber: string): Promise<void> {
    await this.cardNumberInput.sendKeys(cardNumber);
  }

  async getCardNumberInput(): Promise<string> {
    return await this.cardNumberInput.getAttribute('value');
  }

  async setEndDateInput(endDate: string): Promise<void> {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput(): Promise<string> {
    return await this.endDateInput.getAttribute('value');
  }

  async setCvcInput(cvc: string): Promise<void> {
    await this.cvcInput.sendKeys(cvc);
  }

  async getCvcInput(): Promise<string> {
    return await this.cvcInput.getAttribute('value');
  }

  async setPriceOfferInput(priceOffer: string): Promise<void> {
    await this.priceOfferInput.sendKeys(priceOffer);
  }

  async getPriceOfferInput(): Promise<string> {
    return await this.priceOfferInput.getAttribute('value');
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

export class AuctionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-auction-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-auction'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
