package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBasePage extends BasePage {
    protected static final String CREATE_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=%s";

    public SelenideElement errorMessage = $("#error_url");
    protected SelenideElement urlInput = $("#url");
    protected SelenideElement submitButton = $(Selectors.byAttribute("value", "Proceed"));
    protected SelenideElement buildTypeNameInput = $("#buildTypeName");
    protected SelenideElement connectionSuccessfulMessage = $(".connectionSuccessful");

    protected void baseCreateForm(String url) {
        urlInput.val(url);
        submitButton.click();
        if (url.isEmpty()) {
            errorMessage.should(Condition.appear, BASE_WAITING);  // check if error message appears for empty URL
        } else {
            connectionSuccessfulMessage.should(Condition.appear, BASE_WAITING); // check if success message appears otherwise
        }
    }
}
