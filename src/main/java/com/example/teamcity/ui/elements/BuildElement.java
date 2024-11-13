package com.example.teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

@Getter
public class BuildElement extends BasePageElement{
    private SelenideElement buildName;
    private SelenideElement buildLink;
    private SelenideElement buildButton;

    public BuildElement(SelenideElement element) {
        super(element);
        this.buildName = find("span[class*='MiddleEllipsis']");
        this.buildLink = find("a");
        this.buildButton = find("button");
    }
}
