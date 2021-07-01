package ru.netology.domain;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {



    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }


    @Test
    void shouldAcceptInformation() {
        $("[data-test-id=city] input").setValue(DataGenerator.rand());
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String dateFirst = DataGenerator.getNewDate(3);
        $("[data-test-id='date'] input").setValue(dateFirst);
        $("[data-test-id='name'] input").setValue(DataGenerator.getNewName());
        $("[data-test-id='phone'] input").setValue(DataGenerator.getNewPhoneNumber());
        $("[data-test-id=agreement]").click();
        $$("button").get(1).click();
        $(withText("Встреча успешно"))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification']")
                .shouldBe(Condition.visible);
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String dateSecond = DataGenerator.getNewDate(5);
        $("[data-test-id='date'] input").setValue(dateSecond);
        $$("button").get(1).click();
        $(withText("У вас уже запланирована встреча на другую дату."))
                .shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(withText("Перепланировать")).click();
        $(withText("Встреча успешно ")).shouldBe(Condition.visible);
        $("[data-test-id='success-notification']").shouldBe(Condition.visible).shouldHave(Condition.exactText("Успешно"));

    }

}