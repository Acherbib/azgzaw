import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LikeOfStatusComponentsPage, LikeOfStatusDeleteDialog, LikeOfStatusUpdatePage } from './like-of-status.page-object';

const expect = chai.expect;

describe('LikeOfStatus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let likeOfStatusComponentsPage: LikeOfStatusComponentsPage;
  let likeOfStatusUpdatePage: LikeOfStatusUpdatePage;
  let likeOfStatusDeleteDialog: LikeOfStatusDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LikeOfStatuses', async () => {
    await navBarPage.goToEntity('like-of-status');
    likeOfStatusComponentsPage = new LikeOfStatusComponentsPage();
    await browser.wait(ec.visibilityOf(likeOfStatusComponentsPage.title), 5000);
    expect(await likeOfStatusComponentsPage.getTitle()).to.eq('azgzawApp.likeOfStatus.home.title');
  });

  it('should load create LikeOfStatus page', async () => {
    await likeOfStatusComponentsPage.clickOnCreateButton();
    likeOfStatusUpdatePage = new LikeOfStatusUpdatePage();
    expect(await likeOfStatusUpdatePage.getPageTitle()).to.eq('azgzawApp.likeOfStatus.home.createOrEditLabel');
    await likeOfStatusUpdatePage.cancel();
  });

  it('should create and save LikeOfStatuses', async () => {
    const nbButtonsBeforeCreate = await likeOfStatusComponentsPage.countDeleteButtons();

    await likeOfStatusComponentsPage.clickOnCreateButton();
    await promise.all([
      likeOfStatusUpdatePage.typeOfLikeSelectLastOption(),
      likeOfStatusUpdatePage.statusSelectLastOption(),
      likeOfStatusUpdatePage.authorOfLikeSelectLastOption()
    ]);
    await likeOfStatusUpdatePage.save();
    expect(await likeOfStatusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await likeOfStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LikeOfStatus', async () => {
    const nbButtonsBeforeDelete = await likeOfStatusComponentsPage.countDeleteButtons();
    await likeOfStatusComponentsPage.clickOnLastDeleteButton();

    likeOfStatusDeleteDialog = new LikeOfStatusDeleteDialog();
    expect(await likeOfStatusDeleteDialog.getDialogTitle()).to.eq('azgzawApp.likeOfStatus.delete.question');
    await likeOfStatusDeleteDialog.clickOnConfirmButton();

    expect(await likeOfStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
