class User {
    private Integer accountNumber;
    private Integer password;
    private String accountHolderName;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }
}
