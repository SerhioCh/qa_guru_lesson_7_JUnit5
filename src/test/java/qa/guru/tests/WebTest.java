package qa.guru.tests;

import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.By;
import qa.guru.data.Locale;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WebTest extends BeseTest {

    @DisplayName("Проверка валидационных алертов")
    @Test
    void checkValidationAlerts (){
        open("https://accounts.google.com/signup/v2/webcreateaccount?service=mail&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&flowName=GlifWebSignIn&flowEntry=SignUp");
        $("#accountDetailsNext").click();

        $(By.xpath("//div[@class='OyEIQ uSvLId']")).shouldHave(text("Введите имя и фамилию"));
        $(By.xpath("//div[@class='o6cuMc']")).shouldHave(text("Укажите адрес Gmail"));
        $(By.xpath("//div[@class='SdBahf Fjk18 OcVpRe DbQnIe ia6RDd Jj6Lae']")).shouldHave(text("Введите пароль"));
    }

    @CsvSource(value = {
            "ser, Имя пользователя должно содержать от 6 до 30 символов.",
            "serhio, Это имя пользователя уже занято. Попробуйте другое."
    })
    @ParameterizedTest(name = "Проверка алертов поля почты")
    void checkValidationAlertsOnFieldEmail(String setFields,String expectedResult){
        open("https://accounts.google.com/signup/v2/webcreateaccount?service=mail&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&flowName=GlifWebSignIn&flowEntry=SignUp");
        $("input[name='firstName']").setValue("ser");
        $("input[name='lastName']").setValue("ser");
        $("input[name='Username']").setValue(setFields);
        $("input[name='Passwd']").setValue("qwertyyyy123");
        $("input[name='ConfirmPasswd']").setValue("qwertyyyy123");
        $("#accountDetailsNext").click();

        $(By.xpath("//div[@class='o6cuMc']")).shouldHave(text(expectedResult));
    }

    static Stream<Arguments> selenideSiteButtonsTextDataProvider() {
        return Stream.of(
                Arguments.of(List.of("Sign in\n" +
                        "Sign in with Google\n" +
                        "or use your email to sign in:\n" +
                        "Work email\n" +
                        "Password\n" +
                        "\n" +
                        "I forgot my password\n" +
                        "\n" +
                        "Sign in\n" +
                        "Sign in with Single Sign On"), Locale.EN.getDesc()),
                Arguments.of(List.of("Anmelden\n" +
                        "Anmelden mit Google\n" +
                        "oder verwenden Sie Ihre E-Mail, um sich anzumelden:\n" +
                        "Arbeits-E-Mail\n" +
                        "Passwort\n" +
                        "\n" +
                        "Passwort vergessen?\n" +
                        "Anmelden\n" +
                        "Anmeldung mit SSO"), Locale.DE.getDesc())
        );
    }
    @MethodSource("selenideSiteButtonsTextDataProvider")
    @ParameterizedTest(name = "Проверка отображения названия текста для локали: {1}")
        void fillFormMiro(List<String> buttonsTexts, String locale) {
            open("https://miro.com/login/");
            $(By.xpath("//div[@id='language-switcher']")).click();
           $(By.xpath("//a[@data-locale="+"'"+locale+"'"+"]")).click();

            $$(By.xpath("//div[@class='signup__container']")).shouldHave(CollectionCondition.texts(buttonsTexts));
        }
    @EnumSource(Locale.class)
    @ParameterizedTest
    void checkLocaleTest(String locale) {
        open("https://miro.com/login/");
        $(By.xpath("//div[@id='language-switcher']")).click();
        $$(By.xpath("//div[@class='Box--803j5x PopupListWrapper--1eu53ul hOvqT hEPoEE']")).find(text(locale)).shouldBe(visible);

    }
    @Disabled
    @EnumSource(Locale.class)
    @ParameterizedTest(name = "Повторяющийся тест который должен быть пока задизейблен")
    void checkLocaleTestDisabled(Locale locale) {
        open("https://miro.com/login/");
        $(By.xpath("//div[@id='language-switcher']")).click();
        $$(By.xpath("//div[@class='Box--803j5x PopupListWrapper--1eu53ul hOvqT hEPoEE']")).find(text(locale.name())).shouldBe(visible);

    }

}
