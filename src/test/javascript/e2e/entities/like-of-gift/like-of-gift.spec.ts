import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LikeOfGiftComponentsPage, LikeOfGiftDeleteDialog, LikeOfGiftUpdatePage } from './like-of-gift.page-object';

const expect = chai.expect;

describe('LikeOfGift e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let likeOfGiftComponentsPage: LikeOfGiftComponentsPage;
  let likeOfGiftUpdatePage: LikeOfGiftUpdatePage;
  let likeOfGiftDeleteDialog: LikeOfGiftDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LikeOfGifts', async () => {
    await navBarPage.goToEntity('like-of-gift');
    likeOfGiftComponentsPage = new LikeOfGiftComponentsPage();
    await browser.wait(ec.visibilityOf(likeOfGiftComponentsPage.title), 5000);
    expect(await likeOfGiftComponentsPage.getTitle()).to.eq('azgzawApp.likeOfGift.home.title');
  });

  it('should load create LikeOfGift page', async () => {
    await likeOfGiftComponentsPage.clickOnCreateButton();
    likeOfGiftUpdatePage = new LikeOfGiftUpdatePage();
    expect(await likeOfGiftUpdatePage.getPageTitle()).to.eq('azgzawApp.likeOfGift.home.createOrEditLabel');
    await likeOfGiftUpdatePage.cancel();
  });

  it('should create and save LikeOfGifts', async () => {
    const nbButtonsBeforeCreate = await likeOfGiftComponentsPage.countDeleteButtons();

    await likeOfGiftComponentsPage.clickOnCreateButton();
    await promise.all([
      likeOfGiftUpdatePage.typeOfLikeSelectLastOption(),
      likeOfGiftUpdatePage.giftSelectLastOption(),
      likeOfGiftUpdatePage.authorOfLikeSelectLastOption()
    ]);
    await likeOfGiftUpdatePage.save();
    expect(await likeOfGiftUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await likeOfGiftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LikeOfGift', async () => {
    const nbButtonsBeforeDelete = await likeOfGiftComponentsPage.countDeleteButtons();
    await likeOfGiftComponentsPage.clickOnLastDeleteButton();

    likeOfGiftDeleteDialog = new LikeOfGiftDeleteDialog();
    expect(await likeOfGiftDeleteDialog.getDialogTitle()).to.eq('azgzawApp.likeOfGift.delete.question');
    await likeOfGiftDeleteDialog.clickOnConfirmButton();

    expect(await likeOfGiftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
