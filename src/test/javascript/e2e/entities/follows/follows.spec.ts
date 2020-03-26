import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FollowsComponentsPage, FollowsDeleteDialog, FollowsUpdatePage } from './follows.page-object';

const expect = chai.expect;

describe('Follows e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let followsComponentsPage: FollowsComponentsPage;
  let followsUpdatePage: FollowsUpdatePage;
  let followsDeleteDialog: FollowsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Follows', async () => {
    await navBarPage.goToEntity('follows');
    followsComponentsPage = new FollowsComponentsPage();
    await browser.wait(ec.visibilityOf(followsComponentsPage.title), 5000);
    expect(await followsComponentsPage.getTitle()).to.eq('azgzawApp.follows.home.title');
  });

  it('should load create Follows page', async () => {
    await followsComponentsPage.clickOnCreateButton();
    followsUpdatePage = new FollowsUpdatePage();
    expect(await followsUpdatePage.getPageTitle()).to.eq('azgzawApp.follows.home.createOrEditLabel');
    await followsUpdatePage.cancel();
  });

  it('should create and save Follows', async () => {
    const nbButtonsBeforeCreate = await followsComponentsPage.countDeleteButtons();

    await followsComponentsPage.clickOnCreateButton();
    await promise.all([
      followsUpdatePage.setFollowingStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      followsUpdatePage.profileSelectLastOption(),
      followsUpdatePage.friendSelectLastOption()
    ]);
    expect(await followsUpdatePage.getFollowingStartDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected followingStartDate value to be equals to 2000-12-31'
    );
    const selectedAccepted = followsUpdatePage.getAcceptedInput();
    if (await selectedAccepted.isSelected()) {
      await followsUpdatePage.getAcceptedInput().click();
      expect(await followsUpdatePage.getAcceptedInput().isSelected(), 'Expected accepted not to be selected').to.be.false;
    } else {
      await followsUpdatePage.getAcceptedInput().click();
      expect(await followsUpdatePage.getAcceptedInput().isSelected(), 'Expected accepted to be selected').to.be.true;
    }
    const selectedBlocked = followsUpdatePage.getBlockedInput();
    if (await selectedBlocked.isSelected()) {
      await followsUpdatePage.getBlockedInput().click();
      expect(await followsUpdatePage.getBlockedInput().isSelected(), 'Expected blocked not to be selected').to.be.false;
    } else {
      await followsUpdatePage.getBlockedInput().click();
      expect(await followsUpdatePage.getBlockedInput().isSelected(), 'Expected blocked to be selected').to.be.true;
    }
    await followsUpdatePage.save();
    expect(await followsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await followsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Follows', async () => {
    const nbButtonsBeforeDelete = await followsComponentsPage.countDeleteButtons();
    await followsComponentsPage.clickOnLastDeleteButton();

    followsDeleteDialog = new FollowsDeleteDialog();
    expect(await followsDeleteDialog.getDialogTitle()).to.eq('azgzawApp.follows.delete.question');
    await followsDeleteDialog.clickOnConfirmButton();

    expect(await followsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
