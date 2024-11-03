package com.example.teamcity.ui.pages.admin;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildConfigurationPage extends CreateBasePage {
    private static final String BUILD_SHOW_MODE = "createBuildTypeMenu";
    private SelenideElement successfulCreationBuild = $("#unprocessed_objectsCreated");

    public static CreateBuildConfigurationPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_SHOW_MODE), CreateBuildConfigurationPage.class);
    }

    public CreateBuildConfigurationPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public void setUpBuildConfiguration(String buildType) {
        buildTypeNameInput.val(buildType);
        submitButton.click();
        successfulCreationBuild.should(Condition.appear, BASE_WAITING);
    }


}
