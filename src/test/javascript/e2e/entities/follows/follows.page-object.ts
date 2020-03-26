import { element, by, ElementFinder } from 'protractor';

export class FollowsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-follows div table .btn-danger'));
  title = element.all(by.css('jhi-follows div h2#page-heading span')).first();

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

export class FollowsUpdatePage {
  pageTitle = element(by.id('jhi-follows-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  followingStartDateInput = element(by.id('field_followingStartDate'));
  acceptedInput = element(by.id('field_accepted'));
  blockedInput = element(by.id('field_blocked'));
  profileSelect = element(by.id('field_profile'));
  friendSelect = element(by.id('field_friend'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFollowingStartDateInput(followingStartDate: string): Promise<void> {
    await this.followingStartDateInput.sendKeys(followingStartDate);
  }

  async getFollowingStartDateInput(): Promise<string> {
    return await this.followingStartDateInput.getAttribute('value');
  }

  getAcceptedInput(): ElementFinder {
    return this.acceptedInput;
  }
  getBlockedInput(): ElementFinder {
    return this.blockedInput;
  }

  async profileSelectLastOption(): Promise<void> {
    await this.profileSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async profileSelectOption(option: string): Promise<void> {
    await this.profileSelect.sendKeys(option);
  }

  getProfileSelect(): ElementFinder {
    return this.profileSelect;
  }

  async getProfileSelectedOption(): Promise<string> {
    return await this.profileSelect.element(by.css('option:checked')).getText();
  }

  async friendSelectLastOption(): Promise<void> {
    await this.friendSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async friendSelectOption(option: string): Promise<void> {
    await this.friendSelect.sendKeys(option);
  }

  getFriendSelect(): ElementFinder {
    return this.friendSelect;
  }

  async getFriendSelectedOption(): Promise<string> {
    return await this.friendSelect.element(by.css('option:checked')).getText();
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

export class FollowsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-follows-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-follows'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
