package com.Barath._EC011;
import java.util.*;
public class NumbersResponse {
    private List<Integer> windowPrevState;
    private List<Integer> windowCurrState;
    private List<Integer> numbers;
    private String avg;

    public List<Integer> getWindowPrevState() {
        return windowPrevState;
    }

    public void setWindowPrevState(List<Integer> windowPrevState) {
        this.windowPrevState = windowPrevState;
    }

    public List<Integer> getWindowCurrState() {
        return windowCurrState;
    }

    public void setWindowCurrState(List<Integer> windowCurrState) {
        this.windowCurrState = windowCurrState;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }
}

