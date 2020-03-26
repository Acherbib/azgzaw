import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GiftComponentsPage, GiftDeleteDialog, GiftUpdatePage } from './gift.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Gift e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let giftComponentsPage: GiftComponentsPage;
  let giftUpdatePage: GiftUpdatePage;
  let giftDeleteDialog: GiftDeleteDialog;
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

  it('should load Gifts', async () => {
    await navBarPage.goToEntity('gift');
    giftComponentsPage = new GiftComponentsPage();
    await browser.wait(ec.visibilityOf(giftComponentsPage.title), 5000);
    expect(await giftComponentsPage.getTitle()).to.eq('azgzawApp.gift.home.title');
  });

  it('should load create Gift page', async () => {
    await giftComponentsPage.clickOnCreateButton();
    giftUpdatePage = new GiftUpdatePage();
    expect(await giftUpdatePage.getPageTitle()).to.eq('azgzawApp.gift.home.createOrEditLabel');
    await giftUpdatePage.cancel();
  });

  it('should create and save Gifts', async () => {
    const nbButtonsBeforeCreate = await giftComponentsPage.countDeleteButtons();

    await giftComponentsPage.clickOnCreateButton();
    await promise.all([
      giftUpdatePage.setTitleInput('title'),
      giftUpdatePage.setDescriptionInput('description'),
      giftUpdatePage.setCityInput('city'),
      giftUpdatePage.setCountryInput('country'),
      giftUpdatePage.setStartPriceInput('5'),
      giftUpdatePage.setBackgroundColorInput('backgroundColor'),
      giftUpdatePage.setNumberLikesOfGiftsInput('5'),
      giftUpdatePage.setNumberSeenOfGiftsInput('5'),
      giftUpdatePage.setDateOfCreationInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      giftUpdatePage.setImageInput(absolutePath),
      giftUpdatePage.authorOfGiftSelectLastOption()
    ]);
    expect(await giftUpdatePage.getTitleInput()).to.eq('title', 'Expected Title value to be equals to title');
    expect(await giftUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    const selectedIsAGift = giftUpdatePage.getIsAGiftInput();
    if (await selectedIsAGift.isSelected()) {
      await giftUpdatePage.getIsAGiftInput().click();
      expect(await giftUpdatePage.getIsAGiftInput().isSelected(), 'Expected isAGift not to be selected').to.be.false;
    } else {
      await giftUpdatePage.getIsAGiftInput().click();
      expect(await giftUpdatePage.getIsAGiftInput().isSelected(), 'Expected isAGift to be selected').to.be.true;
    }
    const selectedReserved = giftUpdatePage.getReservedInput();
    if (await selectedReserved.isSelected()) {
      await giftUpdatePage.getReservedInput().click();
      expect(await giftUpdatePage.getReservedInput().isSelected(), 'Expected reserved not to be selected').to.be.false;
    } else {
      await giftUpdatePage.getReservedInput().click();
      expect(await giftUpdatePage.getReservedInput().isSelected(), 'Expected reserved to be selected').to.be.true;
    }
    expect(await giftUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await giftUpdatePage.getCountryInput()).to.eq('country', 'Expected Country value to be equals to country');
    const selectedIsAuction = giftUpdatePage.getIsAuctionInput();
    if (await selectedIsAuction.isSelected()) {
      await giftUpdatePage.getIsAuctionInput().click();
      expect(await giftUpdatePage.getIsAuctionInput().isSelected(), 'Expected isAuction not to be selected').to.be.false;
    } else {
      await giftUpdatePage.getIsAuctionInput().click();
      expect(await giftUpdatePage.getIsAuctionInput().isSelected(), 'Expected isAuction to be selected').to.be.true;
    }
    expect(await giftUpdatePage.getStartPriceInput()).to.eq('5', 'Expected startPrice value to be equals to 5');
    expect(await giftUpdatePage.getBackgroundColorInput()).to.eq(
      'backgroundColor',
      'Expected BackgroundColor value to be equals to backgroundColor'
    );
    expect(await giftUpdatePage.getNumberLikesOfGiftsInput()).to.eq('5', 'Expected numberLikesOfGifts value to be equals to 5');
    expect(await giftUpdatePage.getNumberSeenOfGiftsInput()).to.eq('5', 'Expected numberSeenOfGifts value to be equals to 5');
    expect(await giftUpdatePage.getDateOfCreationInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfCreation value to be equals to 2000-12-31'
    );
    expect(await giftUpdatePage.getImageInput()).to.endsWith(fileNameToUpload, 'Expected Image value to be end with ' + fileNameToUpload);
    await giftUpdatePage.save();
    expect(await giftUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await giftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Gift', async () => {
    const nbButtonsBeforeDelete = await giftComponentsPage.countDeleteButtons();
    await giftComponentsPage.clickOnLastDeleteButton();

    giftDeleteDialog = new GiftDeleteDialog();
    expect(await giftDeleteDialog.getDialogTitle()).to.eq('azgzawApp.gift.delete.question');
    await giftDeleteDialog.clickOnConfirmButton();

    expect(await giftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
