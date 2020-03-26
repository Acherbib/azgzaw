import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LikeOfCitationComponentsPage, LikeOfCitationDeleteDialog, LikeOfCitationUpdatePage } from './like-of-citation.page-object';

const expect = chai.expect;

describe('LikeOfCitation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let likeOfCitationComponentsPage: LikeOfCitationComponentsPage;
  let likeOfCitationUpdatePage: LikeOfCitationUpdatePage;
  let likeOfCitationDeleteDialog: LikeOfCitationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LikeOfCitations', async () => {
    await navBarPage.goToEntity('like-of-citation');
    likeOfCitationComponentsPage = new LikeOfCitationComponentsPage();
    await browser.wait(ec.visibilityOf(likeOfCitationComponentsPage.title), 5000);
    expect(await likeOfCitationComponentsPage.getTitle()).to.eq('azgzawApp.likeOfCitation.home.title');
  });

  it('should load create LikeOfCitation page', async () => {
    await likeOfCitationComponentsPage.clickOnCreateButton();
    likeOfCitationUpdatePage = new LikeOfCitationUpdatePage();
    expect(await likeOfCitationUpdatePage.getPageTitle()).to.eq('azgzawApp.likeOfCitation.home.createOrEditLabel');
    await likeOfCitationUpdatePage.cancel();
  });

  it('should create and save LikeOfCitations', async () => {
    const nbButtonsBeforeCreate = await likeOfCitationComponentsPage.countDeleteButtons();

    await likeOfCitationComponentsPage.clickOnCreateButton();
    await promise.all([
      likeOfCitationUpdatePage.typeOfLikeSelectLastOption(),
      likeOfCitationUpdatePage.citationSelectLastOption(),
      likeOfCitationUpdatePage.authorOfLikeSelectLastOption()
    ]);
    await likeOfCitationUpdatePage.save();
    expect(await likeOfCitationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await likeOfCitationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last LikeOfCitation', async () => {
    const nbButtonsBeforeDelete = await likeOfCitationComponentsPage.countDeleteButtons();
    await likeOfCitationComponentsPage.clickOnLastDeleteButton();

    likeOfCitationDeleteDialog = new LikeOfCitationDeleteDialog();
    expect(await likeOfCitationDeleteDialog.getDialogTitle()).to.eq('azgzawApp.likeOfCitation.delete.question');
    await likeOfCitationDeleteDialog.clickOnConfirmButton();

    expect(await likeOfCitationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
