package com.example.teamcity.ui;

import com.example.teamcity.ui.setup.FirstStartPage;
import org.testng.annotations.Test;

public class SetUpServerTest extends BaseUiTest{
    @Test(groups= {"Setup"})
    public void setUpTeamCityServerTest() {
        FirstStartPage.open().setUpFirstStart();
    }
}
