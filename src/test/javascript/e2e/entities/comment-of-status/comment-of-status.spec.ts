import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CommentOfStatusComponentsPage, CommentOfStatusDeleteDialog, CommentOfStatusUpdatePage } from './comment-of-status.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CommentOfStatus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let commentOfStatusComponentsPage: CommentOfStatusComponentsPage;
  let commentOfStatusUpdatePage: CommentOfStatusUpdatePage;
  let commentOfStatusDeleteDialog: CommentOfStatusDeleteDialog;
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

  it('should load CommentOfStatuses', async () => {
    await navBarPage.goToEntity('comment-of-status');
    commentOfStatusComponentsPage = new CommentOfStatusComponentsPage();
    await browser.wait(ec.visibilityOf(commentOfStatusComponentsPage.title), 5000);
    expect(await commentOfStatusComponentsPage.getTitle()).to.eq('azgzawApp.commentOfStatus.home.title');
  });

  it('should load create CommentOfStatus page', async () => {
    await commentOfStatusComponentsPage.clickOnCreateButton();
    commentOfStatusUpdatePage = new CommentOfStatusUpdatePage();
    expect(await commentOfStatusUpdatePage.getPageTitle()).to.eq('azgzawApp.commentOfStatus.home.createOrEditLabel');
    await commentOfStatusUpdatePage.cancel();
  });

  it('should create and save CommentOfStatuses', async () => {
    const nbButtonsBeforeCreate = await commentOfStatusComponentsPage.countDeleteButtons();

    await commentOfStatusComponentsPage.clickOnCreateButton();
    await promise.all([
      commentOfStatusUpdatePage.setBodyCommentInput('bodyComment'),
      commentOfStatusUpdatePage.setCommentOfStatusImageInput(absolutePath),
      commentOfStatusUpdatePage.setMediaTypeInput('mediaType'),
      commentOfStatusUpdatePage.setDateOfCreationInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      commentOfStatusUpdatePage.statusSelectLastOption(),
      commentOfStatusUpdatePage.authorSelectLastOption()
    ]);
    expect(await commentOfStatusUpdatePage.getBodyCommentInput()).to.eq(
      'bodyComment',
      'Expected BodyComment value to be equals to bodyComment'
    );
    expect(await commentOfStatusUpdatePage.getCommentOfStatusImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected CommentOfStatusImage value to be end with ' + fileNameToUpload
    );
    expect(await commentOfStatusUpdatePage.getMediaTypeInput()).to.eq('mediaType', 'Expected MediaType value to be equals to mediaType');
    expect(await commentOfStatusUpdatePage.getDateOfCreationInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfCreation value to be equals to 2000-12-31'
    );
    await commentOfStatusUpdatePage.save();
    expect(await commentOfStatusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await commentOfStatusComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CommentOfStatus', async () => {
    const nbButtonsBeforeDelete = await commentOfStatusComponentsPage.countDeleteButtons();
    await commentOfStatusComponentsPage.clickOnLastDeleteButton();

    commentOfStatusDeleteDialog = new CommentOfStatusDeleteDialog();
    expect(await commentOfStatusDeleteDialog.getDialogTitle()).to.eq('azgzawApp.commentOfStatus.delete.question');
    await commentOfStatusDeleteDialog.clickOnConfirmButton();

    expect(await commentOfStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
