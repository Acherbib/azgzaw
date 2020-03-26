import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MessageComponentsPage, MessageDeleteDialog, MessageUpdatePage } from './message.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Message e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let messageComponentsPage: MessageComponentsPage;
  let messageUpdatePage: MessageUpdatePage;
  let messageDeleteDialog: MessageDeleteDialog;
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

  it('should load Messages', async () => {
    await navBarPage.goToEntity('message');
    messageComponentsPage = new MessageComponentsPage();
    await browser.wait(ec.visibilityOf(messageComponentsPage.title), 5000);
    expect(await messageComponentsPage.getTitle()).to.eq('azgzawApp.message.home.title');
  });

  it('should load create Message page', async () => {
    await messageComponentsPage.clickOnCreateButton();
    messageUpdatePage = new MessageUpdatePage();
    expect(await messageUpdatePage.getPageTitle()).to.eq('azgzawApp.message.home.createOrEditLabel');
    await messageUpdatePage.cancel();
  });

  it('should create and save Messages', async () => {
    const nbButtonsBeforeCreate = await messageComponentsPage.countDeleteButtons();

    await messageComponentsPage.clickOnCreateButton();
    await promise.all([
      messageUpdatePage.setContentInput('content'),
      messageUpdatePage.setDateOfSendInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      messageUpdatePage.setMediaInput(absolutePath),
      messageUpdatePage.authorOfMessageSelectLastOption(),
      messageUpdatePage.receiverOfMessageSelectLastOption()
    ]);
    expect(await messageUpdatePage.getContentInput()).to.eq('content', 'Expected Content value to be equals to content');
    expect(await messageUpdatePage.getDateOfSendInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfSend value to be equals to 2000-12-31'
    );
    expect(await messageUpdatePage.getMediaInput()).to.endsWith(
      fileNameToUpload,
      'Expected Media value to be end with ' + fileNameToUpload
    );
    const selectedSeen = messageUpdatePage.getSeenInput();
    if (await selectedSeen.isSelected()) {
      await messageUpdatePage.getSeenInput().click();
      expect(await messageUpdatePage.getSeenInput().isSelected(), 'Expected seen not to be selected').to.be.false;
    } else {
      await messageUpdatePage.getSeenInput().click();
      expect(await messageUpdatePage.getSeenInput().isSelected(), 'Expected seen to be selected').to.be.true;
    }
    await messageUpdatePage.save();
    expect(await messageUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await messageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Message', async () => {
    const nbButtonsBeforeDelete = await messageComponentsPage.countDeleteButtons();
    await messageComponentsPage.clickOnLastDeleteButton();

    messageDeleteDialog = new MessageDeleteDialog();
    expect(await messageDeleteDialog.getDialogTitle()).to.eq('azgzawApp.message.delete.question');
    await messageDeleteDialog.clickOnConfirmButton();

    expect(await messageComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
