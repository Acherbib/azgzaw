import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AuctionComponentsPage, AuctionDeleteDialog, AuctionUpdatePage } from './auction.page-object';

const expect = chai.expect;

describe('Auction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let auctionComponentsPage: AuctionComponentsPage;
  let auctionUpdatePage: AuctionUpdatePage;
  let auctionDeleteDialog: AuctionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Auctions', async () => {
    await navBarPage.goToEntity('auction');
    auctionComponentsPage = new AuctionComponentsPage();
    await browser.wait(ec.visibilityOf(auctionComponentsPage.title), 5000);
    expect(await auctionComponentsPage.getTitle()).to.eq('azgzawApp.auction.home.title');
  });

  it('should load create Auction page', async () => {
    await auctionComponentsPage.clickOnCreateButton();
    auctionUpdatePage = new AuctionUpdatePage();
    expect(await auctionUpdatePage.getPageTitle()).to.eq('azgzawApp.auction.home.createOrEditLabel');
    await auctionUpdatePage.cancel();
  });

  it('should create and save Auctions', async () => {
    const nbButtonsBeforeCreate = await auctionComponentsPage.countDeleteButtons();

    await auctionComponentsPage.clickOnCreateButton();
    await promise.all([
      auctionUpdatePage.cardTypeSelectLastOption(),
      auctionUpdatePage.setCardNumberInput('5'),
      auctionUpdatePage.setEndDateInput('2000-12-31'),
      auctionUpdatePage.setCvcInput('5'),
      auctionUpdatePage.setPriceOfferInput('5'),
      auctionUpdatePage.giftSelectLastOption(),
      auctionUpdatePage.authorSelectLastOption()
    ]);
    expect(await auctionUpdatePage.getCardNumberInput()).to.eq('5', 'Expected cardNumber value to be equals to 5');
    expect(await auctionUpdatePage.getEndDateInput()).to.eq('2000-12-31', 'Expected endDate value to be equals to 2000-12-31');
    expect(await auctionUpdatePage.getCvcInput()).to.eq('5', 'Expected cvc value to be equals to 5');
    expect(await auctionUpdatePage.getPriceOfferInput()).to.eq('5', 'Expected priceOffer value to be equals to 5');
    await auctionUpdatePage.save();
    expect(await auctionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await auctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Auction', async () => {
    const nbButtonsBeforeDelete = await auctionComponentsPage.countDeleteButtons();
    await auctionComponentsPage.clickOnLastDeleteButton();

    auctionDeleteDialog = new AuctionDeleteDialog();
    expect(await auctionDeleteDialog.getDialogTitle()).to.eq('azgzawApp.auction.delete.question');
    await auctionDeleteDialog.clickOnConfirmButton();

    expect(await auctionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
