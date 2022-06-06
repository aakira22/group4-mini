package con.tbs.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogActivity {
    @NotBlank
    private String action;

    @NotBlank
    private String data;

    @NotBlank
    private String identity;

    public LogActivity(String action, String data, String identity) {
        this.action = action;
        this.data = data;
        this.identity = identity;
    }
}
