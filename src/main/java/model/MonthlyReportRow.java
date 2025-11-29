package model;

public class MonthlyReportRow {

    private Long numberOfBooksBought;
    private Long totalPrice;
    private String username;


    public Long getNumberOfBooksBought() {
        return numberOfBooksBought;
    }

    public void setNumberOfBooksBought(Long numberOfBooksBought) {
        this.numberOfBooksBought = numberOfBooksBought;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
