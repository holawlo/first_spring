package trials.intermediate.users;

import lombok.Getter;

@Getter
public enum Countries {
    USA("Stany Zjednoczone", "US"),
    POLAND("Polska","PL");

    private final String plName;
    private final String symbol;

    Countries(String plName, String symbol) {
        this.plName = plName;
        this.symbol = symbol;
    }
}
