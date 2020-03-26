import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SpamComponentsPage, SpamDeleteDialog, SpamUpdatePage } from './spam.page-object';

const expect = chai.expect;

describe('Spam e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let spamComponentsPage: SpamComponentsPage;
  let spamUpdatePage: SpamUpdatePage;
  let spamDeleteDialog: SpamDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Spams', async () => {
    await navBarPage.goToEntity('spam');
    spamComponentsPage = new SpamComponentsPage();
    await browser.wait(ec.visibilityOf(spamComponentsPage.title), 5000);
    expect(await spamComponentsPage.getTitle()).to.eq('azgzawApp.spam.home.title');
  });

  it('should load create Spam page', async () => {
    await spamComponentsPage.clickOnCreateButton();
    spamUpdatePage = new SpamUpdatePage();
    expect(await spamUpdatePage.getPageTitle()).to.eq('azgzawApp.spam.home.createOrEditLabel');
    await spamUpdatePage.cancel();
  });

  it('should create and save Spams', async () => {
    const nbButtonsBeforeCreate = await spamComponentsPage.countDeleteButtons();

    await spamComponentsPage.clickOnCreateButton();
    await promise.all([
      spamUpdatePage.setCauseInput('cause'),
      spamUpdatePage.setDateOfCreationInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      spamUpdatePage.citationSelectLastOption()
    ]);
    expect(await spamUpdatePage.getCauseInput()).to.eq('cause', 'Expected Cause value to be equals to cause');
    expect(await spamUpdatePage.getDateOfCreationInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfCreation value to be equals to 2000-12-31'
    );
    await spamUpdatePage.save();
    expect(await spamUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await spamComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Spam', async () => {
    const nbButtonsBeforeDelete = await spamComponentsPage.countDeleteButtons();
    await spamComponentsPage.clickOnLastDeleteButton();

    spamDeleteDialog = new SpamDeleteDialog();
    expect(await spamDeleteDialog.getDialogTitle()).to.eq('azgzawApp.spam.delete.question');
    await spamDeleteDialog.clickOnConfirmButton();

    expect(await spamComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
