import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CommentOfCitationComponentsPage,
  CommentOfCitationDeleteDialog,
  CommentOfCitationUpdatePage
} from './comment-of-citation.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('CommentOfCitation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let commentOfCitationComponentsPage: CommentOfCitationComponentsPage;
  let commentOfCitationUpdatePage: CommentOfCitationUpdatePage;
  let commentOfCitationDeleteDialog: CommentOfCitationDeleteDialog;
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

  it('should load CommentOfCitations', async () => {
    await navBarPage.goToEntity('comment-of-citation');
    commentOfCitationComponentsPage = new CommentOfCitationComponentsPage();
    await browser.wait(ec.visibilityOf(commentOfCitationComponentsPage.title), 5000);
    expect(await commentOfCitationComponentsPage.getTitle()).to.eq('azgzawApp.commentOfCitation.home.title');
  });

  it('should load create CommentOfCitation page', async () => {
    await commentOfCitationComponentsPage.clickOnCreateButton();
    commentOfCitationUpdatePage = new CommentOfCitationUpdatePage();
    expect(await commentOfCitationUpdatePage.getPageTitle()).to.eq('azgzawApp.commentOfCitation.home.createOrEditLabel');
    await commentOfCitationUpdatePage.cancel();
  });

  it('should create and save CommentOfCitations', async () => {
    const nbButtonsBeforeCreate = await commentOfCitationComponentsPage.countDeleteButtons();

    await commentOfCitationComponentsPage.clickOnCreateButton();
    await promise.all([
      commentOfCitationUpdatePage.setBodyCommentInput('bodyComment'),
      commentOfCitationUpdatePage.setCommentOfCitationImageInput(absolutePath),
      commentOfCitationUpdatePage.setDateOfCreationInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      commentOfCitationUpdatePage.citationSelectLastOption()
    ]);
    expect(await commentOfCitationUpdatePage.getBodyCommentInput()).to.eq(
      'bodyComment',
      'Expected BodyComment value to be equals to bodyComment'
    );
    expect(await commentOfCitationUpdatePage.getCommentOfCitationImageInput()).to.endsWith(
      fileNameToUpload,
      'Expected CommentOfCitationImage value to be end with ' + fileNameToUpload
    );
    expect(await commentOfCitationUpdatePage.getDateOfCreationInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfCreation value to be equals to 2000-12-31'
    );
    await commentOfCitationUpdatePage.save();
    expect(await commentOfCitationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await commentOfCitationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CommentOfCitation', async () => {
    const nbButtonsBeforeDelete = await commentOfCitationComponentsPage.countDeleteButtons();
    await commentOfCitationComponentsPage.clickOnLastDeleteButton();

    commentOfCitationDeleteDialog = new CommentOfCitationDeleteDialog();
    expect(await commentOfCitationDeleteDialog.getDialogTitle()).to.eq('azgzawApp.commentOfCitation.delete.question');
    await commentOfCitationDeleteDialog.clickOnConfirmButton();

    expect(await commentOfCitationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
