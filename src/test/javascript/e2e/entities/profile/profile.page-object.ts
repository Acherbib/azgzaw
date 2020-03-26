import { element, by, ElementFinder } from 'protractor';

export class ProfileComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-profile div table .btn-danger'));
  title = element.all(by.css('jhi-profile div h2#page-heading span')).first();

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

export class ProfileUpdatePage {
  pageTitle = element(by.id('jhi-profile-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  numberOfMinutesConnectedInput = element(by.id('field_numberOfMinutesConnected'));
  verifiedInput = element(by.id('field_verified'));
  joinedDateInput = element(by.id('field_joinedDate'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  dateOfBirthVisibleInput = element(by.id('field_dateOfBirthVisible'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  phoneNumberVisibleInput = element(by.id('field_phoneNumberVisible'));
  aboutMeInput = element(by.id('field_aboutMe'));
  aboutMeVisibleInput = element(by.id('field_aboutMeVisible'));
  genderSelect = element(by.id('field_gender'));
  genderVisibleInput = element(by.id('field_genderVisible'));
  maritalStatusSelect = element(by.id('field_maritalStatus'));
  maritalStatusVisibleInput = element(by.id('field_maritalStatusVisible'));
  profilePrivacySelect = element(by.id('field_profilePrivacy'));
  locationSelect = element(by.id('field_location'));
  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumberOfMinutesConnectedInput(numberOfMinutesConnected: string): Promise<void> {
    await this.numberOfMinutesConnectedInput.sendKeys(numberOfMinutesConnected);
  }

  async getNumberOfMinutesConnectedInput(): Promise<string> {
    return await this.numberOfMinutesConnectedInput.getAttribute('value');
  }

  getVerifiedInput(): ElementFinder {
    return this.verifiedInput;
  }
  async setJoinedDateInput(joinedDate: string): Promise<void> {
    await this.joinedDateInput.sendKeys(joinedDate);
  }

  async getJoinedDateInput(): Promise<string> {
    return await this.joinedDateInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth: string): Promise<void> {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput(): Promise<string> {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  getDateOfBirthVisibleInput(): ElementFinder {
    return this.dateOfBirthVisibleInput;
  }
  async setPhoneNumberInput(phoneNumber: string): Promise<void> {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput(): Promise<string> {
    return await this.phoneNumberInput.getAttribute('value');
  }

  getPhoneNumberVisibleInput(): ElementFinder {
    return this.phoneNumberVisibleInput;
  }
  async setAboutMeInput(aboutMe: string): Promise<void> {
    await this.aboutMeInput.sendKeys(aboutMe);
  }

  async getAboutMeInput(): Promise<string> {
    return await this.aboutMeInput.getAttribute('value');
  }

  getAboutMeVisibleInput(): ElementFinder {
    return this.aboutMeVisibleInput;
  }
  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getGenderVisibleInput(): ElementFinder {
    return this.genderVisibleInput;
  }
  async setMaritalStatusSelect(maritalStatus: string): Promise<void> {
    await this.maritalStatusSelect.sendKeys(maritalStatus);
  }

  async getMaritalStatusSelect(): Promise<string> {
    return await this.maritalStatusSelect.element(by.css('option:checked')).getText();
  }

  async maritalStatusSelectLastOption(): Promise<void> {
    await this.maritalStatusSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  getMaritalStatusVisibleInput(): ElementFinder {
    return this.maritalStatusVisibleInput;
  }
  async setProfilePrivacySelect(profilePrivacy: string): Promise<void> {
    await this.profilePrivacySelect.sendKeys(profilePrivacy);
  }

  async getProfilePrivacySelect(): Promise<string> {
    return await this.profilePrivacySelect.element(by.css('option:checked')).getText();
  }

  async profilePrivacySelectLastOption(): Promise<void> {
    await this.profilePrivacySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async locationSelectLastOption(): Promise<void> {
    await this.locationSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async locationSelectOption(option: string): Promise<void> {
    await this.locationSelect.sendKeys(option);
  }

  getLocationSelect(): ElementFinder {
    return this.locationSelect;
  }

  async getLocationSelectedOption(): Promise<string> {
    return await this.locationSelect.element(by.css('option:checked')).getText();
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

export class ProfileDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-profile-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-profile'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
