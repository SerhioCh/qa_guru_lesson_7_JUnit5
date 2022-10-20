package qa.guru.Components;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class AllTestData {

    @BeforeAll
    static void configure() {

        Configuration.browserSize = "1920x1080";
        Configuration.holdBrowserOpen = true;

    }
}
