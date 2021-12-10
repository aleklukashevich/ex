package com.pearson.regression;

import com.pearson.BaseTest;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import java.util.List;

public class g extends BaseTest {

    @Test
    @Story("111")
    public void es_4519_loginAdminTest() {
        List<Integer> buildResultList = List.of(1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1);
        var f = buildResultList.subList(0, 3).stream().reduce(0, Integer::sum);
        var s = buildResultList.subList(3, 6).stream().reduce(0, Integer::sum);

        if (f == 0 && s == 3) {
            System.out.println("0");
        } else if (f == 3 && s == 0) {
            System.out.println("1");
        } else {
            System.out.println("-1");
        }
    }
}
