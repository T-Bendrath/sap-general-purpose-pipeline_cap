require("./pages/manageBooksPage");
require("./pages/newBookPage");

describe("teched", function () {
  let bookId = "1";
  let bookTitle = "How to cook Pancakes";
  it("create a new book", function () {
    When.onTheManageBooksPage.iClickOnCreateNewBook();
    When.onTheManageBooksPage.iEnterBookId(bookId);
    When.onTheManageBooksPage.iClickOnCreateInDialog();
    When.onTheNewBookPage.iEnterTitle(bookTitle);
    When.onTheNewBookPage.iSelectGenre();
    When.onTheNewBookPage.iSelectAuthor();
    When.onTheNewBookPage.iClickOnSaveButton();
    Then.onTheNewBookPage.iSeeEditButton();
  });

  it("should check book is added", function () {
    When.onTheNewBookPage.iNavigateBack();
    Then.onTheManageBooksPage.theBookListContains(bookTitle);
  });
});
