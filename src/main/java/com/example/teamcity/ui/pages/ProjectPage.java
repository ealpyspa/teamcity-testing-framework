package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";

    public SelenideElement title = $("span[class*= 'ProjectPageHeader']");

    public ElementsCollection projectBuildTypes = $$("div[class*='BuildTypeLine__buildTypeInfo']");

    private SelenideElement projectPageHeader = $(".MainPanel__router--gF > div");

    @Step("Open project page")
    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

    public ProjectPage() {
        projectPageHeader.shouldBe(Condition.visible, BASE_WAITING);
    }

    public List<BuildElement> getBuilds() {
        return generatePageElements(projectBuildTypes, BuildElement::new);
    }
}
