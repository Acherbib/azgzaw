import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProfileComponentsPage, ProfileDeleteDialog, ProfileUpdatePage } from './profile.page-object';

const expect = chai.expect;

describe('Profile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let profileComponentsPage: ProfileComponentsPage;
  let profileUpdatePage: ProfileUpdatePage;
  let profileDeleteDialog: ProfileDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Profiles', async () => {
    await navBarPage.goToEntity('profile');
    profileComponentsPage = new ProfileComponentsPage();
    await browser.wait(ec.visibilityOf(profileComponentsPage.title), 5000);
    expect(await profileComponentsPage.getTitle()).to.eq('azgzawApp.profile.home.title');
  });

  it('should load create Profile page', async () => {
    await profileComponentsPage.clickOnCreateButton();
    profileUpdatePage = new ProfileUpdatePage();
    expect(await profileUpdatePage.getPageTitle()).to.eq('azgzawApp.profile.home.createOrEditLabel');
    await profileUpdatePage.cancel();
  });

  it('should create and save Profiles', async () => {
    const nbButtonsBeforeCreate = await profileComponentsPage.countDeleteButtons();

    await profileComponentsPage.clickOnCreateButton();
    await promise.all([
      profileUpdatePage.setNumberOfMinutesConnectedInput('5'),
      profileUpdatePage.setJoinedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      profileUpdatePage.setDateOfBirthInput('2000-12-31'),
      profileUpdatePage.setPhoneNumberInput('5'),
      profileUpdatePage.setAboutMeInput('aboutMe'),
      profileUpdatePage.genderSelectLastOption(),
      profileUpdatePage.maritalStatusSelectLastOption(),
      profileUpdatePage.profilePrivacySelectLastOption(),
      profileUpdatePage.locationSelectLastOption(),
      profileUpdatePage.userSelectLastOption()
    ]);
    expect(await profileUpdatePage.getNumberOfMinutesConnectedInput()).to.eq(
      '5',
      'Expected numberOfMinutesConnected value to be equals to 5'
    );
    const selectedVerified = profileUpdatePage.getVerifiedInput();
    if (await selectedVerified.isSelected()) {
      await profileUpdatePage.getVerifiedInput().click();
      expect(await profileUpdatePage.getVerifiedInput().isSelected(), 'Expected verified not to be selected').to.be.false;
    } else {
      await profileUpdatePage.getVerifiedInput().click();
      expect(await profileUpdatePage.getVerifiedInput().isSelected(), 'Expected verified to be selected').to.be.true;
    }
    expect(await profileUpdatePage.getJoinedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected joinedDate value to be equals to 2000-12-31'
    );
    expect(await profileUpdatePage.getDateOfBirthInput()).to.eq('2000-12-31', 'Expected dateOfBirth value to be equals to 2000-12-31');
    const selectedDateOfBirthVisible = profileUpdatePage.getDateOfBirthVisibleInput();
    if (await selectedDateOfBirthVisible.isSelected()) {
      await profileUpdatePage.getDateOfBirthVisibleInput().click();
      expect(await profileUpdatePage.getDateOfBirthVisibleInput().isSelected(), 'Expected dateOfBirthVisible not to be selected').to.be
        .false;
    } else {
      await profileUpdatePage.getDateOfBirthVisibleInput().click();
      expect(await profileUpdatePage.getDateOfBirthVisibleInput().isSelected(), 'Expected dateOfBirthVisible to be selected').to.be.true;
    }
    expect(await profileUpdatePage.getPhoneNumberInput()).to.eq('5', 'Expected phoneNumber value to be equals to 5');
    const selectedPhoneNumberVisible = profileUpdatePage.getPhoneNumberVisibleInput();
    if (await selectedPhoneNumberVisible.isSelected()) {
      await profileUpdatePage.getPhoneNumberVisibleInput().click();
      expect(await profileUpdatePage.getPhoneNumberVisibleInput().isSelected(), 'Expected phoneNumberVisible not to be selected').to.be
        .false;
    } else {
      await profileUpdatePage.getPhoneNumberVisibleInput().click();
      expect(await profileUpdatePage.getPhoneNumberVisibleInput().isSelected(), 'Expected phoneNumberVisible to be selected').to.be.true;
    }
    expect(await profileUpdatePage.getAboutMeInput()).to.eq('aboutMe', 'Expected AboutMe value to be equals to aboutMe');
    const selectedAboutMeVisible = profileUpdatePage.getAboutMeVisibleInput();
    if (await selectedAboutMeVisible.isSelected()) {
      await profileUpdatePage.getAboutMeVisibleInput().click();
      expect(await profileUpdatePage.getAboutMeVisibleInput().isSelected(), 'Expected aboutMeVisible not to be selected').to.be.false;
    } else {
      await profileUpdatePage.getAboutMeVisibleInput().click();
      expect(await profileUpdatePage.getAboutMeVisibleInput().isSelected(), 'Expected aboutMeVisible to be selected').to.be.true;
    }
    const selectedGenderVisible = profileUpdatePage.getGenderVisibleInput();
    if (await selectedGenderVisible.isSelected()) {
      await profileUpdatePage.getGenderVisibleInput().click();
      expect(await profileUpdatePage.getGenderVisibleInput().isSelected(), 'Expected genderVisible not to be selected').to.be.false;
    } else {
      await profileUpdatePage.getGenderVisibleInput().click();
      expect(await profileUpdatePage.getGenderVisibleInput().isSelected(), 'Expected genderVisible to be selected').to.be.true;
    }
    const selectedMaritalStatusVisible = profileUpdatePage.getMaritalStatusVisibleInput();
    if (await selectedMaritalStatusVisible.isSelected()) {
      await profileUpdatePage.getMaritalStatusVisibleInput().click();
      expect(await profileUpdatePage.getMaritalStatusVisibleInput().isSelected(), 'Expected maritalStatusVisible not to be selected').to.be
        .false;
    } else {
      await profileUpdatePage.getMaritalStatusVisibleInput().click();
      expect(await profileUpdatePage.getMaritalStatusVisibleInput().isSelected(), 'Expected maritalStatusVisible to be selected').to.be
        .true;
    }
    await profileUpdatePage.save();
    expect(await profileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await profileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Profile', async () => {
    const nbButtonsBeforeDelete = await profileComponentsPage.countDeleteButtons();
    await profileComponentsPage.clickOnLastDeleteButton();

    profileDeleteDialog = new ProfileDeleteDialog();
    expect(await profileDeleteDialog.getDialogTitle()).to.eq('azgzawApp.profile.delete.question');
    await profileDeleteDialog.clickOnConfirmButton();

    expect(await profileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
