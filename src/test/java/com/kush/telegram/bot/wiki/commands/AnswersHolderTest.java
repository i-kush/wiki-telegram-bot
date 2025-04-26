package com.kush.telegram.bot.wiki.commands;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.api.objects.User;

public class AnswersHolderTest {

    private static final String STUB = "stub";

    @Test
    public void takeFirstName() {
        String lShouldBe = "Some first name";
        boolean lCondition = true;
        User lUser = Mockito.mock(User.class);
        Mockito.when(lUser.getFirstName()).thenReturn(lShouldBe);

        AnswersHolder lAnswersHolder = Mockito.mock(AnswersHolder.class);
        Mockito.when(lAnswersHolder.getUserName(lUser, lCondition)).thenCallRealMethod();

        String lResult = lAnswersHolder.getUserName(lUser, lCondition);
        Assert.assertEquals(lShouldBe, lResult);
    }

    @Test
    public void takeLastNameIfFirstIsAbsent() {
        String lShouldBe = "Some second name";
        boolean lCondition = true;
        User lUser = Mockito.mock(User.class);
        Mockito.when(lUser.getLastName()).thenReturn(lShouldBe);

        AnswersHolder lAnswersHolder = Mockito.mock(AnswersHolder.class);
        Mockito.when(lAnswersHolder.getUserName(lUser, lCondition)).thenCallRealMethod();

        String lResult = lAnswersHolder.getUserName(lUser, lCondition);
        Assert.assertEquals(lShouldBe, lResult);
    }

    @Test
    public void takeUserNameIfAllNamesAreAbsentFirstPriority() {
        String lShouldBe = "Some username";
        boolean lCondition = true;
        User lUser = Mockito.mock(User.class);
        Mockito.when(lUser.getUserName()).thenReturn(lShouldBe);

        AnswersHolder lAnswersHolder = Mockito.mock(AnswersHolder.class);
        Mockito.when(lAnswersHolder.getUserName(lUser, lCondition)).thenCallRealMethod();

        String lResult = lAnswersHolder.getUserName(lUser, lCondition);
        Assert.assertEquals(lShouldBe, lResult);
    }

    @Test
    public void takeLastName() {
        String lShouldBe = "Some last name";
        boolean lCondition = false;
        User lUser = Mockito.mock(User.class);
        Mockito.when(lUser.getLastName()).thenReturn(lShouldBe);

        AnswersHolder lAnswersHolder = Mockito.mock(AnswersHolder.class);
        Mockito.when(lAnswersHolder.getUserName(lUser, lCondition)).thenCallRealMethod();

        String lResult = lAnswersHolder.getUserName(lUser, lCondition);
        Assert.assertEquals(lShouldBe, lResult);
    }

    @Test
    public void takeFirstNameIfLastIsAbsent() {
        String lShouldBe = "Some first name";
        boolean lCondition = false;
        User lUser = Mockito.mock(User.class);
        Mockito.when(lUser.getFirstName()).thenReturn(lShouldBe);

        AnswersHolder lAnswersHolder = Mockito.mock(AnswersHolder.class);
        Mockito.when(lAnswersHolder.getUserName(lUser, lCondition)).thenCallRealMethod();

        String lResult = lAnswersHolder.getUserName(lUser, lCondition);
        Assert.assertEquals(lShouldBe, lResult);
    }

    @Test
    public void takeUserNameIfAllNamesAreAbsentLastPriority() {
        String lShouldBe = "Some username";
        boolean lCondition = false;
        User lUser = Mockito.mock(User.class);
        Mockito.when(lUser.getUserName()).thenReturn(lShouldBe);

        AnswersHolder lAnswersHolder = Mockito.mock(AnswersHolder.class);
        Mockito.when(lAnswersHolder.getUserName(lUser, lCondition)).thenCallRealMethod();

        String lResult = lAnswersHolder.getUserName(lUser, lCondition);
        Assert.assertEquals(lShouldBe, lResult);
    }

    @Test
    public void shouldBeSingleton1() {
        AnswersHolder.init("answers.properties");
        Assert.assertEquals(AnswersHolder.getInstance().hashCode(), AnswersHolder.getInstance().hashCode());
    }

    @Test
    public void shouldBeSingleton2() {
        AnswersHolder.init("answers.properties");
        AnswersHolder.init(STUB);
        Assert.assertEquals(AnswersHolder.getInstance().hashCode(), AnswersHolder.getInstance().hashCode());
    }
}
