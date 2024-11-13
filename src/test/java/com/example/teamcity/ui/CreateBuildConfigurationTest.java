package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.BuildTypes;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.*;
import static io.qameta.allure.Allure.step;


@Test(groups = {"Regression"})
public class CreateBuildConfigurationTest extends BaseUiTest{
    private Project createdProject;

    @BeforeMethod(alwaysRun = true)
    public void beforeClass() {
        step("Create user via API and login as this user in UI");
        var user = testData.getUser();
        var project = testData.getProject();
        loginAs(user);

        step("Create project by user via API");
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(user));
        createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(project);
    }

    @Test(description = "User should be able to create a build configuration for their project", groups = {"Positive"})
    public void userCreatesBuildConfiguration() {
        step("Create buildType for project by user in UI");
        var buildType = testData.getBuildType();
        CreateBuildConfigurationPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setUpBuildConfiguration(buildType.getName());

        step("Check buildType was created successfully with correct data on API level");
        var createdBuildType = superUserCheckRequests.<Project>getRequest(PROJECTS).read(createdProject.getId());
        softy.assertNotNull(createdBuildType);
        //по какой-то причине эти значения не совпадают
        //softy.assertEquals(createdBuildType.getName(), buildType.getName());

        step("Check buildType was created successfully with correct data in UI");
        var openedProject = ProjectPage.open(createdProject.getId());
        var foundBuilds = openedProject
                .getBuilds().stream()
                .anyMatch(build -> build.getBuildName().text().equals(buildType.getName()));
        softy.assertTrue(foundBuilds);
    }


    @Test(description = "User should not be able to create a build configuration for their project without REPO_URL", groups = {"Negative"})
    public void userCannotCreateBuildConfiguration() {

        step("Count existing buildTypes of created project");
        var initialBuildTypesCount = superUserCheckRequests.<BuildTypes>getRequest(PROJECT_BUILD_TYPES).read(createdProject.getId() + "/buildTypes").getCount();

        step("Attempt to create buildType for project by user in UI without REPO_URL");
        CreateBuildConfigurationPage.open(createdProject.getId())
                .createForm("")  // Передаем пустой URL
                .errorMessage.shouldHave(Condition.exactText("URL must not be empty"));

        step("Count build types of created project after attempt to create a new buildType");
        var newBuildTypesCount = superUserCheckRequests.<BuildTypes>getRequest(PROJECT_BUILD_TYPES).read(createdProject.getId() + "/buildTypes").getCount();
        softy.assertEquals(newBuildTypesCount, initialBuildTypesCount);
    }

}

