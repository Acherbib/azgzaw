import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CommentOfGiftComponentsPage, CommentOfGiftDeleteDialog, CommentOfGiftUpdatePage } from './comment-of-gift.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CommentOfGift e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let commentOfGiftComponentsPage: CommentOfGiftComponentsPage;
  let commentOfGiftUpdatePage: CommentOfGiftUpdatePage;
  let commentOfGiftDeleteDialog: CommentOfGiftDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CommentOfGifts', async () => {
    await navBarPage.goToEntity('comment-of-gift');
    commentOfGiftComponentsPage = new CommentOfGiftComponentsPage();
    await browser.wait(ec.visibilityOf(commentOfGiftComponentsPage.title), 5000);
    expect(await commentOfGiftComponentsPage.getTitle()).to.eq('azgzawApp.commentOfGift.home.title');
  });

  it('should load create CommentOfGift page', async () => {
    await commentOfGiftComponentsPage.clickOnCreateButton();
    commentOfGiftUpdatePage = new CommentOfGiftUpdatePage();
    expect(await commentOfGiftUpdatePage.getPageTitle()).to.eq('azgzawApp.commentOfGift.home.createOrEditLabel');
    await commentOfGiftUpdatePage.cancel();
  });

  it('should create and save CommentOfGifts', async () => {
    const nbButtonsBeforeCreate = await commentOfGiftComponentsPage.countDeleteButtons();

    await commentOfGiftComponentsPage.clickOnCreateButton();
    await promise.all([
      commentOfGiftUpdatePage.setBodyCommentInput('bodyComment'),
      commentOfGiftUpdatePage.setCommentOfGiftImageInput(absolutePath),
      commentOfGiftUpdatePage.setDateOfCreationInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      commentOfGiftUpdatePage.giftSelectLastOption(),
      commentOfGiftUpdatePage.authorSelectLastOption()
    ]);
    expect(await commentOfGiftUpdatePage.getBodyCommentInput()).to.eq(
      'bodyComment',
      'Expected BodyComment value to be equals to bodyComment'
    );
    expect(await commentOfGiftUpdatePage.getCommentOfGiftImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected CommentOfGiftImage value to be end with ' + fileNameToUpload
    );
    expect(await commentOfGiftUpdatePage.getDateOfCreationInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfCreation value to be equals to 2000-12-31'
    );
    await commentOfGiftUpdatePage.save();
    expect(await commentOfGiftUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await commentOfGiftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last CommentOfGift', async () => {
    const nbButtonsBeforeDelete = await commentOfGiftComponentsPage.countDeleteButtons();
    await commentOfGiftComponentsPage.clickOnLastDeleteButton();

    commentOfGiftDeleteDialog = new CommentOfGiftDeleteDialog();
    expect(await commentOfGiftDeleteDialog.getDialogTitle()).to.eq('azgzawApp.commentOfGift.delete.question');
    await commentOfGiftDeleteDialog.clickOnConfirmButton();

    expect(await commentOfGiftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
