import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CitationComponentsPage, CitationDeleteDialog, CitationUpdatePage } from './citation.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Citation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let citationComponentsPage: CitationComponentsPage;
  let citationUpdatePage: CitationUpdatePage;
  let citationDeleteDialog: CitationDeleteDialog;
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

  it('should load Citations', async () => {
    await navBarPage.goToEntity('citation');
    citationComponentsPage = new CitationComponentsPage();
    await browser.wait(ec.visibilityOf(citationComponentsPage.title), 5000);
    expect(await citationComponentsPage.getTitle()).to.eq('azgzawApp.citation.home.title');
  });

  it('should load create Citation page', async () => {
    await citationComponentsPage.clickOnCreateButton();
    citationUpdatePage = new CitationUpdatePage();
    expect(await citationUpdatePage.getPageTitle()).to.eq('azgzawApp.citation.home.createOrEditLabel');
    await citationUpdatePage.cancel();
  });

  it('should create and save Citations', async () => {
    const nbButtonsBeforeCreate = await citationComponentsPage.countDeleteButtons();

    await citationComponentsPage.clickOnCreateButton();
    await promise.all([
      citationUpdatePage.setDescriptionInput('description'),
      citationUpdatePage.setGreenCitationMediaInput(absolutePath),
      citationUpdatePage.setDateOfCreationInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      citationUpdatePage.setBackgroundColorInput('backgroundColor'),
      citationUpdatePage.greenCitationPrivacySelectLastOption(),
      citationUpdatePage.authorOfCitationSelectLastOption()
    ]);
    expect(await citationUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await citationUpdatePage.getGreenCitationMediaInput()).to.endsWith(
      fileNameToUpload,
      'Expected GreenCitationMedia value to be end with ' + fileNameToUpload
    );
    expect(await citationUpdatePage.getDateOfCreationInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfCreation value to be equals to 2000-12-31'
    );
    expect(await citationUpdatePage.getBackgroundColorInput()).to.eq(
      'backgroundColor',
      'Expected BackgroundColor value to be equals to backgroundColor'
    );
    await citationUpdatePage.save();
    expect(await citationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await citationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Citation', async () => {
    const nbButtonsBeforeDelete = await citationComponentsPage.countDeleteButtons();
    await citationComponentsPage.clickOnLastDeleteButton();

    citationDeleteDialog = new CitationDeleteDialog();
    expect(await citationDeleteDialog.getDialogTitle()).to.eq('azgzawApp.citation.delete.question');
    await citationDeleteDialog.clickOnConfirmButton();

    expect(await citationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
