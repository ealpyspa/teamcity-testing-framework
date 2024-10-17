package com.example.teamcity.api;

import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})

public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct.");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create buildType for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {
        //steps are kept for better understanding
        step ("Create project");
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        step("Create user with PROJECT_ADMIN role for the project");
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());

        step ("Create buildType for the project by user (PROJECT_ADMIN)");
        var projectAdminCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        projectAdminCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        step("Check buildType was created successfully");
        var createdBuildType = projectAdminCheckRequests.<BuildType>getRequest(BUILD_TYPES).read(testData.getBuildType().getId());
        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct.");
    }

    @Test(description = "Project admin should not be able to create buildType for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
        //steps are kept for better understanding
        step("Create project1");
        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        step("Create project2");
        var project2 = TestDataGenerator.generate(Project.class); // Generate project2
        superUserCheckRequests.getRequest(PROJECTS).create(project2); // Create project2

        step("Create user1 with PROJECT_ADMIN role for the project1");
        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId())); // Assign PROJECT_ADMIN role for user1
        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser()); // Create user1

        step("Create user2 with PROJECT_ADMIN role for the project2");
        var user2 = TestDataGenerator.generate(User.class); // Generate user1
        user2.setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + project2.getId())); // Assign PROJECT_ADMIN role for user2
        superUserCheckRequests.<User>getRequest(USERS).create(user2); // Create user2

        step("Attempt to crete buildType for project2 by user1");
        var user1CheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser())); // User1 authentication

        var buildTypeForProject2 = generate(BuildType.class, project2.getId()); // Generate buildType for project2

        // Attempt to create buildType for project2 where user1 doesn't have PROJECT_ADMIN role
        user1CheckRequests.getRequest(BUILD_TYPES)
                .create(buildTypeForProject2)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN) // Expect response with http status code 403
                .body(Matchers.containsString("Access is denied.")); // Expected error message
    }
}
