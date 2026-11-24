package pl.sages.jext.lambdastreams.step1.lambda;

/**
 * Przycisk ze Sposobu III-V: kod kliknięcia nie siedzi w klasie przycisku,
 * tylko w obiekcie {@link ButtonAction} podanym z zewnątrz.
 */
public class Button {

    private final String text;
    private ButtonAction action;

    public Button(String text) {
        this.text = text;
    }

    public void click() {
        action.clickAction();
    }

    public void setAction(ButtonAction action) {
        this.action = action;
    }

    public String getText() {
        return text;
    }
}
